package com.infy.controller;



import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.infy.dao.UserDao;
import com.infy.dao.UserDaoImpl;
import com.infy.model.User;


@Path("/employees")
public class EmployeeController {
	
	private static UserDao empDao = new UserDaoImpl();
	
	@GET
	@Path("/{empId}")
	@Produces("application/json")
	public Response getCustomer(@PathParam("empId") int empId) {		
		try {
			User emp = empDao.getEmployeeById(empId);
			
					
			return Response.ok().entity(emp).build();
		}
	
		catch (Exception e) {
			return Response.status(404).entity( e.getMessage().toString() ).build();
		}


	}
	

}
