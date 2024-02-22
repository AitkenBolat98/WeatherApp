package service;

import module.Locations;
import module.Users;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class Config {
    public Configuration getConfiguration(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Users.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Locations.class);
        configuration.configure();
        return configuration;
    }
}
