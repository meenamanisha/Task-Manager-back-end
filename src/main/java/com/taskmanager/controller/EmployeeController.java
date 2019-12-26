package com.taskmanager.controller;



import java.util.List;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.taskmanager.config.PropertiesReader;
import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.User;
import com.taskmanager.service.UserService;
import com.taskmanager.service.UserServiceImpl;


@Path("/employees")
public class EmployeeController {
	public static Properties p = PropertiesReader.properties;
	private static UserService usrServ= new UserServiceImpl();
	static Logger logger = Logger.getLogger(EmployeeController.class);
	
	@GET
	@Path("/Details/{usrId}")
	@Produces("application/json")
	public Response getAllUserDetails(@PathParam("usrId")  Integer usrId)
	{
		try {
			logger.debug("Comes to Controller class");
			List<User> userList= usrServ.getAllUserDetails(usrId);					
			return Response.ok().entity(userList).build();
		}
		catch (TaskManagerException e) {
			System.out.println(e.getMessage());
			String s = p.getProperty(e.getMessage().toString());
			logger.debug("Some Exception occured : "+s);
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	
	
	
	@PUT
	@Path("/updated")
	@Consumes("application/json")
	@Produces("application/json")
	public Response updatedManagers(List<User> usrList)
	{
		try {

			List<Integer> userList= usrServ.updateMangers(usrList);					
			return Response.ok().entity(userList).build();
		}
		catch (TaskManagerException e) { 
			String s = p.getProperty(e.getMessage().toString());
			logger.debug("Some Exception occured : "+s);
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	
	
	@GET
	@Path("/Managers/{usrId}")
	@Produces("application/json")
	public Response getManagers(@PathParam("usrId")  Integer usrId )
	{
		try {

			List<User> userList= usrServ.getManagers(usrId);

					
			return Response.ok().entity(userList).build();
		}
		catch (TaskManagerException e) {
			System.out.println(e.getMessage());
			String s = p.getProperty(e.getMessage().toString());
			logger.debug("Some Exception occured : "+s);
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	

	@POST
	@Path("/registration")
	@Consumes("application/json")
	@Produces("application/json")
	public Response registration(User usr)
	{
		try {

			int usrId = usrServ.registerUser(usr);

			String ans = "{\n\"usrId\" :"+usrId+",\n \"message\":\""+p.getProperty("User.Controller.REGISTRATION_SUCCESSFUL")+"\"\n}";			
			return Response.ok().entity(ans).build();
		}
		catch (TaskManagerException e) {
			System.out.println(e.getMessage());
			String s = p.getProperty(e.getMessage().toString());
			logger.debug("Some Exception occured : "+s);
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
	}

	@Path("/login")
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response login( User usr)
	{
		try {
			User loggedInUser = usrServ.loginUser(usr);										
			return Response.ok().entity(loggedInUser).build();
		}
		catch (TaskManagerException e) {
			String s = p.getProperty(e.getMessage().toString());
			logger.debug("Some Exception occured : "+s);
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();

		}
	}



	


}
