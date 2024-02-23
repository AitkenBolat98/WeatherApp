package service;

import module.Sessions;
import module.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.http.HttpServletResponse;

public class RegistrationService extends Config {
    SessionService sessionService = new SessionService();
    CookieService cookieService = new CookieService();

    public void addUser(String login, String password, HttpServletResponse response){

        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Users user = Users
                .builder()
                .login(login)
                .password(password)
                .build();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            Sessions sessionModule = sessionService.createSession(user);
            cookieService.createCookie(response,sessionModule);
        }catch (Exception e){
            System.out.println("user creation exception" + e);
        }finally {
            session.close();
        }
    }
}
