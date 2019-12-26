package com.taskmanager.service;

import java.util.List;

import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;

public interface TaskService {
	public Integer createTask(Task task) throws TaskManagerException,Exception;
	public List<Task> getAllTaskByUserId(Integer usrId) throws TaskManagerException,Exception;
	public List<Integer> assignTaskToUser(List<User> us) throws TaskManagerException,Exception;
	public List<User> getAllPendingTask(Integer usrId) throws TaskManagerException,Exception;
	public List<Integer> verifyPendingTask(Integer usrId, List<User> us) throws TaskManagerException,Exception;
	public List<Integer> userProcessedTask(Integer usrId, List<Task> tasks) throws TaskManagerException,Exception;
	public List<Task> getAssignedTaskOfUser(Integer usrId) throws TaskManagerException,Exception;

}
