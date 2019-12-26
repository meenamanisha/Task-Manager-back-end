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

import com.taskmanager.config.PropertiesReader;
import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.service.TaskService;
import com.taskmanager.service.TaskServiceImpl; 

@Path("/tasks")
public class TaskController {
	
	TaskService taskServ = new TaskServiceImpl();
	public static Properties p = PropertiesReader.properties;
	

	@POST
	@Path("/taskcreation")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createTask(Task	task)
	{
		try {
			int taskId = taskServ.createTask(task);
			String ans = "{\"message\":\""+p.getProperty("Task.Controller.TASK_CREATTION")+" "+taskId+"\"\n}";			
			return Response.ok().entity(ans).build();
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
	@Path("/allTasks/{usrId}")
	@Produces("application/json")
	public Response getAllTaskByUserId(@PathParam("usrId")  Integer usrId)
	{
		try {
			List<Task> taskList = taskServ.getAllTaskByUserId(usrId); 					
			return Response.ok().entity(taskList).build();
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
	@Path("/pendingTask/{usrId}")
	@Produces("application/json")
	public Response getAllPendingTask(@PathParam("usrId")  Integer usrId)
	{
		try {
			List<User> taskList = taskServ.getAllPendingTask(usrId); 					
			return Response.ok().entity(taskList).build();
		}
		catch (TaskManagerException e) { 
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	
	
	@PUT
	@Path("/verifyTask/{usrId}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response verifyPendingTask(@PathParam("usrId")  Integer usrId, List<User> usr)
	{
		try {
			
			List<Integer> taskList = taskServ.verifyPendingTask(usrId, usr);
			return Response.ok().entity(taskList).build();
		}
		catch (TaskManagerException e) { 
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}
	
	
	
	@PUT
	@Path("/TaskToUser")
	@Consumes("application/json")
	@Produces("application/json")
	public Response assignTaskToUser(List<User> us)
	{
		try {
			List<Integer> taskList = taskServ.assignTaskToUser(us);			
			return Response.ok().entity(taskList).build();
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
	@Path("/getAssignedTask/{usrId}")	
	@Produces("application/json")
	public Response getAssignedTaskOfUser(@PathParam("usrId") Integer usrId)
	{
		try {
			List<Task> taskList = taskServ.getAssignedTaskOfUser(usrId);		
			return Response.ok().entity(taskList).build();
		}
		catch (TaskManagerException e) {  
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {
			return Response.status(500).entity( e.getMessage().toString() ).build();
		}		
	}	
	
	@PUT
	@Path("/completeTask/{usrId}")	
	@Consumes("application/json")
	@Produces("application/json")
	public Response userProcessedTask(@PathParam("usrId") Integer usrId, List<Task> tasks)
	{
		try {
			List<Integer> resultList = taskServ.userProcessedTask(usrId,tasks);			
			return Response.ok().entity(resultList).build();
		}
		catch (TaskManagerException e) {  
			String s = p.getProperty(e.getMessage().toString());
			return Response.status(500).entity(s).build();			
		}
		catch (Exception e) {

			return Response.status(500).entity( e.getMessage().toString() ).build();
		}	
		
	}

	
	
	


}
