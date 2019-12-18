package com.infy.controller;



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

import com.infy.config.PropertiesReader;
import com.infy.exception.TaskManagerException;
import com.infy.model.User;
import com.infy.service.UserService;
import com.infy.service.UserServiceImpl;


@Path("/employees")
public class EmployeeController {
	public static Properties p = PropertiesReader.properties;
	private static UserService usrServ= new UserServiceImpl();
	
	@GET
	@Path("/Details")
	@Produces("application/json")
	public Response getAllUserDetails()
	{
		try {

			List<User> userList= usrServ.getAllUserDetails();					
			return Response.ok().entity(userList).build();
		}
		catch (TaskManagerException e) {
			System.out.println(e.getMessage());
			String s = p.getProperty(e.getMessage().toString());
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
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	
	
	@GET
	@Path("/Managers")
	@Produces("application/json")
	public Response getManagers( )
	{
		try {

			List<User> userList= usrServ.getManagers();

					
			return Response.ok().entity(userList).build();
		}
		catch (TaskManagerException e) {
			System.out.println(e.getMessage());
			String s = p.getProperty(e.getMessage().toString());
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
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();

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
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}


	}


}
