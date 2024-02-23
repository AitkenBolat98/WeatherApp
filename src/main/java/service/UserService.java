package service;

import lombok.extern.slf4j.Slf4j;
import module.Sessions;
import module.Users;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
@Slf4j
public class UserService extends Config{

    public boolean isUserExist(String login){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        boolean result = false;
        try {
            session.beginTransaction();
            String hql = "SELECT u from users u WHERE u.login =:login";
            Query query =session.createQuery(hql);
            query.setParameter("login",login);
            Users user = (Users) query.uniqueResult();
            if(user != null){
                result = true;
                return result;
            }
        }catch (Exception e){
            log.error(e.getMessage() + " isUserExist exception");
        }finally {
            session.close();
        }
        return result;
    }
    public String getUserPasswordByLogin(String login){
        Configuration configuration = getConfiguration();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession();
        String password = null;
        try {
            session.beginTransaction();
            String hql = "Select u.password from users u WHERE u.login=:login";
            Query query = session.createQuery(hql);
            query.setParameter("login",login);
            password = (String) query.uniqueResult();
        }catch (Exception e){
            log.error(e.getMessage());
        }finally {
            session.close();
        }
        return password;
    }
}
