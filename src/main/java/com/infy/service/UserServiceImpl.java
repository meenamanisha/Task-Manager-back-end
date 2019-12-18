package com.infy.service;

import java.util.List;

import com.infy.dao.UserDao;
import com.infy.dao.UserDaoImpl;
import com.infy.exception.TaskManagerException;
import com.infy.model.User;
import com.infy.validators.UserValidators;

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
		UserValidators.userValidate(user);
		
		Integer id = userDao.registerUser(user);
		if(id==null)
			throw new TaskManagerException("User.Service.DATABASE_CONNECTION");
		return id;
	}

	@Override
	public User loginUser(User user) throws TaskManagerException,Exception {
		if(user==null)
			throw new TaskManagerException("User.Service.USER_NOT_AVAILABLE");
		if(!UserValidators.emailValidate(user.getUsrEmail()))
			throw new TaskManagerException("User.Service.IN_VALID_EMAIL_ID");
		
		
		User us = userDao.loginUser(user);	
		if(us==null)
			throw new TaskManagerException("User.Service.DATABASE_CONNECTION");
		return us;
	}

	@Override
	public List<User> getAllUserDetails() throws TaskManagerException, Exception {
		List<User> usrList = userDao.getAllUserDetails(); 
		if(usrList==null)
			throw new TaskManagerException("User.Service.DATABASE_CONNECTION");		
		return usrList;
	}

	@Override
	public List<User> getManagers() throws TaskManagerException, Exception {		
		List<User> managersList = userDao.getManagers();
		
		if(managersList==null)
			throw new TaskManagerException("User.Service.DATABASE_CONNECTION");		
		return managersList;
	}

	@Override
	public List<Integer> updateMangers(List<User> usrList) throws TaskManagerException, Exception {
		if(usrList==null)
			throw new TaskManagerException("User.Service.NO_DATA_FOUND");
		List<Integer> idsList = userDao.updateMangers(usrList);

		if(idsList==null)
			throw new TaskManagerException("User.Service.DATABASE_CONNECTION");		
		return idsList;
	}

}

