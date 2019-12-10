package com.infy.entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateUtility {
	
	public static SessionFactory getSessionFactory() {
		SessionFactory factory;
        Configuration cfg = new Configuration();        
        cfg.configure("hibernate.cfg.xml"); 
        ServiceRegistry serviceR = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();  
        factory = cfg.buildSessionFactory(serviceR);
        return factory;
    }

}
