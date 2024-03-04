package servlet;

import DTO.ForecastDTO;
import DTO.LocationDTO;
import DTO.WeatherDto;
import Util.ThymeleafUtil;
import api.OpenWeatherApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import module.Locations;
import module.Sessions;
import org.hibernate.Session;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import service.LocationService;
import service.SessionService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@WebServlet(name = "MainPage", value = "/main")
public class MainPage extends HttpServlet {
    private SessionService sessionService = new SessionService();
    private LocationService locationService = new LocationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
        WebContext webContext = new WebContext(request,response,request.getServletContext());
        Sessions session = sessionService.getSessionByCookies(request.getCookies());

            if(session!= null) {
                List<Locations> locationsList = locationService.getAllLocationsByUser(session.getUser());
                List<ForecastDTO> forecastDTOList = locationService.getForecastsForAllSavedLocations(locationsList);
                webContext.setVariable("userAuthorized",true);
                if(forecastDTOList!=null){
                    webContext.setVariable("userLocations",forecastDTOList);
                }
            }else {
                webContext.setVariable("userAuthorized",false);
            }
        templateEngine.process("main",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getParameter("location");
        LocationDTO locationDTO = locationService.getLocationByName(city);
        WeatherDto  weatherDto =locationService.getWeatherForecast(locationDTO
                .getCoord().get("lon"),locationDTO.getCoord().get("lat"));
        ForecastDTO forecastDTO = locationService.forecast(weatherDto);
        String forecastJson = new ObjectMapper().writeValueAsString(forecastDTO);
        String redirectUrl = request.getContextPath() + "/main/search?forecast=" + URLEncoder.encode(forecastJson, "UTF-8");
        response.sendRedirect(redirectUrl);

    }
}