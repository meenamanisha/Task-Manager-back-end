package com.infy.service;

import java.util.List;

import org.jboss.logging.Logger;

import com.infy.dao.TaskDao;
import com.infy.dao.TaskDaoImpl;
import com.infy.exception.TaskManagerException;
import com.infy.model.Task;
import com.infy.model.User;
import com.infy.validators.TaskValidators;

public class TaskServiceImpl implements TaskService {
	static  Logger log = Logger.getLogger(TaskServiceImpl.class);

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
		if(taskList==null || taskList.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return taskList; 
	}
	@Override
	public List<Integer> assignTaskToUser(List<User> us) throws TaskManagerException, Exception {		
		if(us==null || us.size()==0)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Integer> ans = taskDao.assignTaskToUser(us);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<User> getAllPendingTask(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<User> ans = taskDao.getAllPendingTask(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Integer> verifyPendingTask(Integer usrId, List<User> us) throws TaskManagerException, Exception {
		if(us==null || us.size()==0 || usrId ==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Integer> ans = taskDao.verifyPendingTask(usrId, us);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Integer> userProcessedTask(Integer usrId, List<Task> tasks) throws TaskManagerException, Exception {
		if( usrId ==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		if(tasks==null || tasks.size()==0)
			throw new TaskManagerException("Task.Service.TASK_NOT_AVAILABLE");
		List<Integer> ans = taskDao.userProcessedTask(usrId, tasks);		
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Task> getAssignedTaskOfUser(Integer usrId) throws TaskManagerException, Exception {
		if(usrId ==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Task> ans = taskDao.getAssignedTaskOfUser(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;	}

}
