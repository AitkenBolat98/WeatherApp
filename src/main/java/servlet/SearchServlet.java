package servlet;

import DTO.ForecastDTO;
import DTO.LocationDTO;
import Util.ThymeleafUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import module.Sessions;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import service.LocationService;
import service.SessionService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLDecoder;

@WebServlet(name = "SearchServlet", value = "/main/search")
public class SearchServlet extends HttpServlet {
    private SessionService sessionService = new SessionService();
    private LocationService locationService = new LocationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
        WebContext webContext = new WebContext(request,response,request.getServletContext());
        Sessions session = sessionService.getSessionByCookies(request.getCookies());
        String forecastParam = request.getParameter("forecast");
        ForecastDTO forecastDTO = null;

        if (forecastParam != null) {
            forecastDTO = new ObjectMapper().readValue(URLDecoder.decode(forecastParam, "UTF-8"), ForecastDTO.class);
        }

        if(session!= null) {
            webContext.setVariable("userAuthorized",true);
            webContext.setVariable("search",forecastDTO);
        }else {
            webContext.setVariable("userAuthorized",false);
        }
        templateEngine.process("searchLocation",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName =  request.getParameter("search.name");
        LocationDTO dto =locationService.getLocationByName(cityName);
        Sessions session = sessionService.getSessionByCookies(request.getCookies());
        locationService.saveLocation(session.getUser(),dto);
        response.sendRedirect("/WeatherApp/main");
    }
}