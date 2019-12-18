package com.infy.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.infy.controller.EmployeeController;
import com.infy.controller.TaskController;

public class ApplicationConfig extends Application{

	private Set<Object> singletons = new HashSet<Object>();

	public ApplicationConfig(@Context Dispatcher dispatcher) {
		
		 CorsFilter corsFilter = new CorsFilter();
		   corsFilter.getAllowedOrigins().add("*");
	        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
	        singletons.add(corsFilter);
		singletons.add(new EmployeeController());
		singletons.add(new TaskController());
		PropertiesReader myInstance = new PropertiesReader();
		dispatcher.getDefaultContextObjects().put(PropertiesReader.class, myInstance);
		
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
