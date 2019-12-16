package com.infy.dao;

import java.util.List;

import com.infy.exception.TaskManagerException;
import com.infy.model.User;

public interface UserDao {
	public User getEmployeeById(int empId) throws TaskManagerException,Exception;
	public Integer registerUser(User user) throws TaskManagerException,Exception;
	public User loginUser(User user) throws TaskManagerException,Exception;
	public List<User> getAllUserDetails() throws TaskManagerException,Exception;
	
}
