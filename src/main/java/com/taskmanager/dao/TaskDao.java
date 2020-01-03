package com.taskmanager.dao;

import java.util.List;

import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;

public interface TaskDao {
	
	
	public Integer createTask(Task task) throws TaskManagerException,Exception;
	public List<Task> getAllTaskByUserId(Integer usrId,String taskDet) throws TaskManagerException,Exception;
	public List<Integer> assignTaskToUser(List<User> us) throws TaskManagerException,Exception;
	public List<User> allAssignedTask(Integer usrId) throws TaskManagerException,Exception;
	
	public List<Task> getWeekDashBoard(Integer usrId) throws TaskManagerException,Exception;
	
	public List<Task> getAllCreatedTask(Integer usrId) throws TaskManagerException,Exception;
	public List<Task> getAllPendingTask(Integer usrId) throws TaskManagerException,Exception;
	public List<Task> getAllUserCreatedTask(Integer usrId) throws TaskManagerException,Exception;
	public List<Integer> verifyPendingTask(Integer usrId, List<User> us) throws TaskManagerException,Exception;
	public List<Integer> userProcessedTask(Integer usrId, List<Task> tasks) throws TaskManagerException,Exception;
	public List<Task> getAssignedTaskOfUser(Integer usrId) throws TaskManagerException,Exception;
	
}
