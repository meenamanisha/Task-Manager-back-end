package com.taskmanager.validators;

import com.taskmanager.exception.TaskManagerException;
import com.taskmanager.model.Task;

public class TaskValidators {
	public static void taskValidate(Task task) throws TaskManagerException
	{	
		if(!expectedEffortValidate(task.gettExpEff()))
			throw new TaskManagerException("Task.Validator.IN_VALID_EXPECTED_EFFORT");
		if(!nameValidate(task.gettName()))
			throw new TaskManagerException("Task.Validator.TASK_NOT_PRESENT");
		
	}
	
	public static boolean expectedEffortValidate(Double ExpEff)
	{
		if(ExpEff>0)
			return true;
		return false;
	
	}


	public static boolean nameValidate(String name)
	{
		if(name!=null)
			return true;
		return false; 
	}

}
