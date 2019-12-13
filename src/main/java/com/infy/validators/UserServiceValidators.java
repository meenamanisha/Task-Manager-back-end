package com.infy.validators;

import com.infy.exception.TaskManagerException;
import com.infy.model.User;

public class UserServiceValidators {
	public static void userValidate(User usr) throws TaskManagerException
	{
		if(!emailValidate(usr.getUsrEmail()))
			throw new TaskManagerException("User.Service.IN_VALID_EMAIL_ID");
		if(!phoneValidate(usr.getUsrPhno()))
			throw new TaskManagerException("User.Service.IN_VALID_PHONE_NO");
		if(!nameValidate(usr.getUsrName()))
			throw new TaskManagerException("User.Service.IN_VALID_NAME");
		
	}
	public static boolean emailValidate(String email)
	{

		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if(email.matches(pattern))
			return true;
		return false;
					
	}
	
	public static  boolean phoneValidate(String phoneNo)
	{
		if (phoneNo.matches("\\d{10}")) return true;		
		else if(phoneNo.matches("\\d{3}[-\\.\\s]\\d{3}[-\\.\\s]\\d{4}")) return true;
		
		else if(phoneNo.matches("\\d{3}-\\d{3}-\\d{4}\\s(x|(ext))\\d{3,5}")) return true;
		else if(phoneNo.matches("\\(\\d{3}\\)-\\d{3}-\\d{4}")) return true;

		else return false;
		
	}
	
	public static boolean nameValidate(String name)
	{
		String pattern="[\\s]*[a-zA-Z]{3,}([\\s]+[a-zA-Z]{3,}){0,2}[\\s]*";
		if(name.matches(pattern))
			return true;
		return false; 
	}

}
