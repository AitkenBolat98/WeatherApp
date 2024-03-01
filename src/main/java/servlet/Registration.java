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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/registration.html");
        dispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = PasswordUtil.hashPassword(request.getParameter("password"));
        TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
        WebContext webContext = new WebContext(request,response,request.getServletContext());
        if(userService.isUserExist(login) == false){
            webContext.setVariable("login",login);
            webContext.setVariable("password",password);
            webContext.setVariable("userExist",false);
            registrationService.addUser(login,password,response);
            response.sendRedirect(request.getContextPath() + "/main");
        }else {
            webContext.setVariable("userExist",true);
        }
        templateEngine.process("registration",webContext,response.getWriter());




    }
}