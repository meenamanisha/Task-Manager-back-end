package com.infy.service;

import java.util.List;

import com.infy.exception.TaskManagerException;
import com.infy.model.User;

public interface UserService {
	
	public Integer registerUser(User user) throws TaskManagerException,Exception;
	public User loginUser(User user) throws TaskManagerException,Exception;
	public List<User> getAllUserDetails(Integer usrId) throws TaskManagerException,Exception;
	public List<User> getManagers(Integer usrId) throws TaskManagerException,Exception;
	public List<Integer> updateMangers(List<User> usrList) throws TaskManagerException,Exception;

}
