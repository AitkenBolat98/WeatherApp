package thymeleaf;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;


public class ThymeleafUtil {
    public static TemplateEngine createTemplateEngine(ServletContext context){
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(createTemplateResolver(context));
        return templateEngine;
    }
    public static ITemplateResolver createTemplateResolver(ServletContext context){
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(context);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");

        return templateResolver;

    }
}
