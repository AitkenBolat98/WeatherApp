package service;

import lombok.extern.slf4j.Slf4j;
import module.Sessions;
import module.Users;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class AuthorizationService {
    private UserService userService = new UserService();
    private SessionService sessionService = new SessionService();
    private CookieService cookieService = new CookieService();

    public boolean isPasswordSame(String login, String password) {
        String pwdDB = userService.getUserPasswordByLogin(login);
        if (pwdDB != null) {
            if(BCrypt.checkpw(password,pwdDB)){
                return true;
            }else {
                return false;
            }

        }return false;
    }
    public Sessions login(HttpServletRequest request, HttpServletResponse response){
        if(request.getCookies().length == 1){
            String login = request.getParameter("login");
            Users user = userService.getUserByLogin(login);
            Sessions session = sessionService.createSession(user);
            cookieService.createCookie(response,session);
            return session;
        }else {
            Cookie[] cookies =request.getCookies();
            Cookie searchedCookie = null;
            for (Cookie cookie:cookies){
                if("weatherApp".equals(cookie.getName())){
                    searchedCookie=cookie;
                }
            }
            if(searchedCookie == null){
            }
            String id = searchedCookie.getValue();
            return sessionService.getSessionById(UUID.fromString(id));

        }
    }

}