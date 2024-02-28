package service;

import lombok.extern.slf4j.Slf4j;
import module.Sessions;
import module.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.http.Cookie;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
public class SessionService extends Config{

    public Sessions createSession(Users user){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Sessions sessionToCreate = getSessionByUser(user);
        if(sessionToCreate !=null) {
            sessionToCreate.setUser(user);
            sessionToCreate.setExpiredAt(LocalDateTime.now().plus(24, ChronoUnit.HOURS));
        }else {
            sessionToCreate = Sessions
                    .builder()
                    .id(UUID.randomUUID())
                    .user(user)
                    .expiredAt(LocalDateTime.now().plus(24, ChronoUnit.HOURS))
                    .build();
        }
        try {
            session.beginTransaction();
            session.saveOrUpdate(sessionToCreate);
            session.getTransaction().commit();
        }catch (Exception e){
            log.error(e.getMessage() + "session creation exception");
        }finally {
            session.close();
        }
        return sessionToCreate;
    }
    public Sessions getSessionByUser(Users user){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Sessions sessionSearched = null;
        try {
            session.beginTransaction();
            String hql = "SELECT s from sessions s WHERE s.user=:user";
            Query query = session.createQuery(hql);
            query.setParameter("user",user);
            sessionSearched = (Sessions) query.uniqueResult();
        }catch (Exception e){
            log.error(e.getMessage() + "session creation exception");
        }finally {
            session.close();
        }
        return sessionSearched;
    }
    public Sessions getSessionById(UUID id){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        Sessions sessionSearched = null;
        try {
            session.beginTransaction();
            String hql = "SELECT s from sessions s WHERE s.id =:uuid";
            Query query =session.createQuery(hql);
            query.setParameter("uuid",id);
            sessionSearched = (Sessions) query.uniqueResult();
            if(sessionSearched!=null){
                return sessionSearched;
            }
        }catch (Exception e){
            log.error(e.getMessage() + " getSessionByID exception");
        }finally {
            session.close();
        }
        return sessionSearched;
    }
    public void deleteSession(UUID id){
        Sessions sessionToDelete = getSessionById(id);
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.delete(sessionToDelete);
            session.getTransaction().commit();
        }catch (Exception e){
            log.error(e.getMessage() + " getSessionByID exception");
        }finally {
            session.close();
        }
    }
    public Sessions getSessionByCookies( Cookie[] cookies ){
        Cookie searchedCookie = null;
        for (Cookie cookie:cookies){
            if("weatherApp".equals(cookie.getName())){
                searchedCookie=cookie;
            }
        }
        if(searchedCookie == null){
            Sessions nullSession = null;
            return nullSession;
        }
        String id = searchedCookie.getValue();
        return getSessionById(UUID.fromString(id));
    }
    public boolean isSessionValid(Sessions session){
        LocalDateTime now = LocalDateTime.now();
        if(now.isBefore(session.getExpiredAt())){
            return true;
        }else {
            return false;
        }
    }
}
