package com.infy.service;

import java.util.List;

import com.infy.dao.UserDao;
import com.infy.dao.UserDaoImpl;
import com.infy.exception.TaskManagerException;
import com.infy.model.User;
import com.infy.validators.UserServiceValidators;

public class UserServiceImpl implements UserService {

	private UserDao userDao= new UserDaoImpl();
	@Override
	public User getEmployeeById(int empId) throws TaskManagerException,Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer registerUser(User user) throws TaskManagerException,Exception {
		if(user==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		UserServiceValidators.userValidate(user);
		
		return userDao.registerUser(user);
	}

	@Override
	public User loginUser(User user) throws TaskManagerException,Exception {
		if(user==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		if(!UserServiceValidators.emailValidate(user.getUsrEmail()))
			throw new TaskManagerException("User.Service.IN_VALID_EMAIL_ID");
		
		
		return userDao.loginUser(user);		
	}

	@Override
	public List<User> getAllUserDetails() throws TaskManagerException, Exception {		
		return userDao.getAllUserDetails();
	}

}

