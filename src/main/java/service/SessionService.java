package service;

import lombok.extern.slf4j.Slf4j;
import module.Sessions;
import module.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
public class SessionService extends Config{

    public Sessions createSession(Users user){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Sessions sessionModule = Sessions
                .builder()
                .user(user)
                .expiredAt(LocalDateTime.now().plus(24, ChronoUnit.HOURS))
                .build();
        try {
            session.beginTransaction();
            session.save(sessionModule);
            session.getTransaction().commit();
        }catch (Exception e){
            log.error(e.getMessage() + "session creation exception");
        }finally {
            session.close();
        }
        return sessionModule;
    }
}
