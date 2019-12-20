package com.infy.service;

import java.util.List;

import com.infy.exception.TaskManagerException;
import com.infy.model.Task;
import com.infy.model.User;

public interface TaskService {
	public Integer createTask(Task task) throws TaskManagerException,Exception;
	public List<Task> getAllTaskByUserId(Integer usrId) throws TaskManagerException,Exception;
	public List<Integer> assignTaskToUser(List<User> us) throws TaskManagerException,Exception;
	public List<Task> getAllPendingTask(Integer usrId) throws TaskManagerException,Exception;
	

}
