package com.taskmanager.service;

import java.util.List;

import org.jboss.logging.Logger;

import com.taskmanager.dao.TaskDao;
import com.taskmanager.dao.TaskDaoImpl;
import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.validators.TaskValidators;

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
	public List<Task> getAllTaskByUserId(Integer usrId, String taskDet) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("Task.service.NO_USER_ID_PRESENT");
		List<Task> taskList = taskDao.getAllTaskByUserId(usrId,taskDet);
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
	public List<User> allAssignedTask(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<User> ans = taskDao.allAssignedTask(usrId);
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
	@Override
	public List<Task> getAllUserCreatedTask(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Task> ans = taskDao.getAllUserCreatedTask(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Task> getAllPendingTask(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Task> ans = taskDao.getAllPendingTask(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Task> getAllCreatedTask(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Task> ans = taskDao.getAllCreatedTask(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}
	@Override
	public List<Task> getWeekDashBoard(Integer usrId) throws TaskManagerException, Exception {
		if(usrId==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		List<Task> ans = taskDao.getWeekDashBoard(usrId);
		if(ans==null || ans.size()==0)
			throw new TaskManagerException("Task.Service.DATABASE_CONNECTION");
		return ans;
	}

}
