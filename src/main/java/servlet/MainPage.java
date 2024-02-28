package servlet;

import Util.ThymeleafUtil;
import module.Sessions;
import org.hibernate.Session;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import service.SessionService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "MainPage", value = "/main")
public class MainPage extends HttpServlet {
    private SessionService sessionService = new SessionService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            Sessions session = sessionService.getSessionByCookies(request.getCookies());
            TemplateEngine templateEngine = ThymeleafUtil.createTemplateEngine(request.getServletContext());
            WebContext webContext = new WebContext(request,response,request.getServletContext());
            if(session!= null) {
                webContext.setVariable("userAuthorized",true);
            }else {
                webContext.setVariable("userAuthorized",false);
            }
        templateEngine.process("main",webContext,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}