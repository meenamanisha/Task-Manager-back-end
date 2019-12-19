package com.infy.service;

import java.util.List;

import com.infy.dao.TaskDao;
import com.infy.dao.TaskDaoImpl;
import com.infy.exception.TaskManagerException;
import com.infy.model.Task;
import com.infy.model.User;
import com.infy.validators.TaskValidators;

public class TaskServiceImpl implements TaskService {

	TaskDao taskDao = new TaskDaoImpl();
	@Override
	public Integer createTask(Task task) throws TaskManagerException, Exception {
		if(task==null)
			throw new TaskManagerException("Task.Service.TASK_NOT_AVAILABLE");
		TaskValidators.taskValidate(task);				
		Integer id =  taskDao.createTask(task);
		if(id==null)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return id;
	}
	@Override
	public List<Task> getAllTaskByUserId(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("Task.service.NO_USER_ID_PRESENT");
		List<Task> taskList = taskDao.getAllTaskByUserId(usrId);
		if(taskList==null)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return taskList; 
	}
	@Override
	public List<Integer> assignTaskToUser(User us) throws TaskManagerException, Exception {
		if(us==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Integer> ans = taskDao.assignTaskToUser(us);
		if(ans==null)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}

}
