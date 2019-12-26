package com.taskmanager.dao;

import java.util.List;

import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.User;

public interface UserDao {

	public Integer registerUser(User user) throws TaskManagerException,Exception;
	public User loginUser(User user) throws TaskManagerException,Exception;
	public List<User> getAllUserDetails(Integer usrId) throws TaskManagerException,Exception;
	public List<User> getManagers(Integer usrId) throws TaskManagerException,Exception;
	
	public List<Integer> updateMangers(List<User> usrList) throws TaskManagerException,Exception;

	
}
