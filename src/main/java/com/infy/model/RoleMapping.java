package com.infy.model;

public enum RoleMapping {
	ADMIN(3),MANAGER(2),EMPLOYEE(1);
	final int val;
	RoleMapping(int val)
	{
		this.val = val;
	}
	public int getVal() {
		return val;
	}
	
}
