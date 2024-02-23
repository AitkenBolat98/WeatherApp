package servlet;

import Util.PasswordUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import service.RegistrationService;
import service.UserService;
import Util.ThymeleafUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet(name = "Registration", value = "/registration")
public class Registration extends HttpServlet {
    private  RegistrationService registrationService = new RegistrationService();
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("reg get");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/registration.html");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("reg post");
        String login = request.getParameter("login");
        String password = PasswordUtil.hashPassword(request.getParameter("password"));
        if(userService.isUserExist(login) == false){
            TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
            WebContext webContext = new WebContext(request,response,request.getServletContext());
            webContext.setVariable("login",login);
            webContext.setVariable("password",password);
            templateEngine.process("registration",webContext,response.getWriter());
            registrationService.addUser(login,password,response);
        }else {

        }



    }
}