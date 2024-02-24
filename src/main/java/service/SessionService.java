package service;

import lombok.extern.slf4j.Slf4j;
import module.Sessions;
import module.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j
public class SessionService extends Config{

    public Sessions createSession(Users user){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        UUID uuid = UUID.randomUUID();
        Sessions sessionModule = Sessions
                .builder()
                .id(uuid)
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
}
