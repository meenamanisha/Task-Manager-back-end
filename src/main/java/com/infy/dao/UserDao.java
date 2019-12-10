package com.infy.dao;

import com.infy.model.User;

public interface UserDao {
	public User getEmployeeById(int empId) throws Exception;

}
