package com.taskmanager.entity;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtility {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
 
    
	public static SessionFactory getSessionFactory() throws Exception{
		 if (sessionFactory == null) {
	            try {
	                // Create registry
	                registry = new StandardServiceRegistryBuilder().configure().build(); 
	                MetadataSources sources = new MetadataSources(registry); 
	                Metadata metadata = sources.getMetadataBuilder().build(); 
	                sessionFactory = metadata.getSessionFactoryBuilder().build();
	            } catch (Exception e) {
	                if (registry != null) {
	                    StandardServiceRegistryBuilder.destroy(registry);
	                }
	                throw e;
	            }
	        }
	        return sessionFactory;

    }

}
