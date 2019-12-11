package com.infy.controller;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
	
	@POST
	@Path("/registration")
	@Consumes("application/json")
	public Response registration(User usr)
	{
		try {
			
			int usrId = empDao.registerUser(usr);
			
			String ans = "{\n\"usrId\" :"+usrId+",\n \"message:\"user is succesfully registrered\n}";
			return Response.ok().entity(ans).build();
		}
	
		catch (Exception e) {
			return Response.status(404).entity( e.getMessage().toString() ).build();
		}

		
	}
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
