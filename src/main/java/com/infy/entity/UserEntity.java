package com.infy.entity;


import java.util.List;
 
import javax.persistence.CascadeType;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn; 
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany; 
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infy.model.User;

@Entity
@Table(name="user1")
public class UserEntity {
	
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private int usrId;	
	private String usrCurrentAdd;
	private String usrEmail;
	private String usrName;
	private String usrPermanentAdd;
	private String usrPhno;
	private String password;
	private Integer usrMId;
	 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="roleId")	
	private RoleEntity role;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<TaskEntity> task;
	
	
	
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




	public RoleEntity getRole() {
		return role;
	}



	public void setRole(RoleEntity role) {
		this.role = role;
	}



	public List<TaskEntity> getTask() {
		return task;
	}



	public void setTask(List<TaskEntity> task) {
		this.task = task;
	}



	public User userEntityToModel()
	{
		User e = new User();
		e.setUsrCurrentAdd(this.usrCurrentAdd);
		e.setUsrEmail(this.usrEmail);
		e.setUsrId(this.usrId);
		e.setUsrMId(this.usrMId);
		e.setUsrName(this.usrName);
		e.setUsrPermanentAdd(this.usrPermanentAdd);
		e.setUsrPhno(this.usrPhno);	
		e.setPassword(null);
		return e;
	}
	
}
