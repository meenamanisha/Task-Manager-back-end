package com.infy.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.infy.controller.EmployeeController;

public class ApplicationConfig extends Application{

	private Set<Object> singletons = new HashSet<Object>();

	public ApplicationConfig() {
		singletons.add(new EmployeeController());
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
