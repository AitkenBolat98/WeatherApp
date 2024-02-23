package servlet;

import Util.PasswordUtil;
import org.mindrot.jbcrypt.BCrypt;
import service.AuthorizationService;
import service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Authorization", value = "/authorization")
public class Authorization extends HttpServlet {
    private UserService userService = new UserService();
    private AuthorizationService authorizationService = new AuthorizationService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/authorization.html");
            dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if(userService.isUserExist(login) == true){
            if(authorizationService.isPasswordSame(login,password)){

            }else {

            }
        }
    }
}