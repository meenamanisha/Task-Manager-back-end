package com.infy.controller;



import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.infy.config.PropertiesReader;
import com.infy.exception.TaskManagerException;
import com.infy.model.User;
import com.infy.service.UserService;
import com.infy.service.UserServiceImpl;


@Path("/employees")
public class EmployeeController {
	public static Properties p = PropertiesReader.properties;
	private static UserService usrServ= new UserServiceImpl();

	@POST
	@Path("/registration")
	@Consumes("application/json")
	@Produces("application/json")
	public Response registration(User usr)
	{
		try {

			int usrId = usrServ.registerUser(usr);

			String ans = "{\n\"usrId\" :"+usrId+",\n \"message\":"+p.getProperty("User.Controller.REGISTRATION_SUCCESSFUL")+"\n}";			
			return Response.ok().entity(ans).build();
		}
		catch (TaskManagerException e) {
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(400).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(404).entity( e.getMessage().toString() ).build();
		}	
	}

	@Path("/login")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response login( User usr)
	{
		try {
			int id = usrServ.loginUser(usr);

			String s = p.getProperty("User.Controller.LOGIN_SUCCESSFUL") + id;
			return Response.ok().entity(s).build();


		}
		catch (TaskManagerException e) {
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(400).entity(s).build();			
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
			User emp = usrServ.getEmployeeById(empId);


			return Response.ok().entity(emp).build();
		}

		catch (Exception e) {
			return Response.status(404).entity( e.getMessage().toString() ).build();
		}


	}


}
