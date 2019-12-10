package com.infy.model;


import java.util.List;



public class User {
	private int usrId;	
	private String usrCurrentAdd;
	private String usrEmail;
	private String usrName;
	private String usrPermanentAdd;
	private String usrPhno;
	private String password;
	private Integer usrMId;
	private Role roleId;
		
	private List<Task> tasks;

	public int getUsrId() {
		return usrId;
	}

	public void setUsrId(int usrId) {
		this.usrId = usrId;
	}

	public String getUsrCurrentAdd() {
		return usrCurrentAdd;
	}

	public void setUsrCurrentAdd(String usrCurrentAdd) {
		this.usrCurrentAdd = usrCurrentAdd;
	}

	public String getUsrEmail() {
		return usrEmail;
	}

	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPermanentAdd() {
		return usrPermanentAdd;
	}

	public void setUsrPermanentAdd(String usrPermanentAdd) {
		this.usrPermanentAdd = usrPermanentAdd;
	}

	public String getUsrPhno() {
		return usrPhno;
	}

	public void setUsrPhno(String usrPhno) {
		this.usrPhno = usrPhno;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getUsrMId() {
		return usrMId;
	}

	public void setUsrMId(Integer usrMId) {
		this.usrMId = usrMId;
	}

	public Role getRoleId() {
		return roleId;
	}

	public void setRoleId(Role roleId) {
		this.roleId = roleId;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
