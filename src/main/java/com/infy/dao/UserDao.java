package com.infy.dao;

import com.infy.exception.TaskManagerException;
import com.infy.model.User;

public interface UserDao {
	public User getEmployeeById(int empId) throws TaskManagerException,Exception;
	public Integer registerUser(User user) throws TaskManagerException,Exception;
	public Integer loginUser(User user) throws TaskManagerException,Exception;
	
}
