package servlet;

import DTO.LocationDTO;
import module.Sessions;
import service.LocationService;
import service.SessionService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LocationServlet", value = "/main/save-location")
public class SaveLocationServlet extends HttpServlet {
    private LocationService locationService = new LocationService();
    private SessionService sessionService = new SessionService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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