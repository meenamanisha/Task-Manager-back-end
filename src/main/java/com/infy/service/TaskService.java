package com.infy.service;

import java.util.List;

import com.infy.exception.TaskManagerException;
import com.infy.model.Task;

public interface TaskService {
	public Integer createTask(Task task) throws TaskManagerException,Exception;
	public List<Task> getAllTaskByUserId(Integer usrId) throws TaskManagerException,Exception;
	

}
