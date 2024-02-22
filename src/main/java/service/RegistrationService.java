package service;

import module.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class RegistrationService extends Config {
    public void addUser(String login,String password){

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
        }catch (Exception e){
            System.out.println("user creation exception" + e);
        }finally {
            session.close();
        }
    }
}
