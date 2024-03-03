package servlet;

import service.LocationService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "DeleteLocationServlet", value = "/main/delete-location")
public class DeleteLocationServlet extends HttpServlet {
    private LocationService locationService = new LocationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name =request.getParameter("cityName");
        locationService.deleteLocationByName(name);
        response.sendRedirect("/WeatherApp/main");
    }
}