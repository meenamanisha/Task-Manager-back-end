package com.infy.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

import com.infy.controller.EmployeeController;

public class ApplicationConfig extends Application{

	private Set<Object> singletons = new HashSet<Object>();

	public ApplicationConfig() {
		
		 CorsFilter corsFilter = new CorsFilter();
		   corsFilter.getAllowedOrigins().add("*");
	        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
	        singletons.add(corsFilter);
		singletons.add(new EmployeeController());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
