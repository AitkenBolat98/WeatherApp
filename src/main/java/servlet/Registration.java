package servlet;

import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ITemplateResolver;
import service.RegistrationService;
import thymeleaf.ThymeleafUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "Registration", value = "/registration")
public class Registration extends HttpServlet {
    private  RegistrationService registrationService = new RegistrationService();

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
        String password = request.getParameter("password").toLowerCase();
        TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
        WebContext webContext = new WebContext(request,response,request.getServletContext());
        webContext.setVariable("login",login);
        webContext.setVariable("password",password);

        templateEngine.process("registration",webContext,response.getWriter());
        registrationService.addUser(login,password);


    }
}