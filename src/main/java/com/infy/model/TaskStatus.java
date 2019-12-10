package com.infy.model;

public enum TaskStatus {
	COMPLETED("fv"), 
	ON_HOLD("On hold"),
	IN_PROCESS("In process"),
	CANCELLED("Cancelled"),
	NEW("New");
	private String t;
	TaskStatus  (String s)
	{
		this.t = s;
	}
	public String getT() {
		return t;
	}
	
}
