package service;

import module.Sessions;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieService {
    public void createCookie(HttpServletResponse response, Sessions session){
        Cookie cookie = new Cookie("weatherApp",session.getId().toString());
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
    }
}
