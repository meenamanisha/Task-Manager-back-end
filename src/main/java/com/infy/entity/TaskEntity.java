package com.infy.entity;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.infy.model.Task;
import com.infy.model.TaskStatus;


@Entity
@Table(name="task")
public class TaskEntity {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private int tId;
	
	private String tOwner;
	
	private String tName;
	
	private int tExpEff;
	
	private int tActEff;
	
	@Column(columnDefinition="timestamp")
	private LocalDateTime tAllDate;
	@Column(columnDefinition="timestamp")
	private LocalDateTime tCompDate;
	
	
	
	@Enumerated(EnumType.STRING)
	private TaskStatus tStatus;

	public int gettId() {
		return tId;
	}

	public void settId(int tId) {
		this.tId = tId;
	}

	

	public String gettOwner() {
		return tOwner;
	}

	public void settOwner(String tOwner) {
		this.tOwner = tOwner;
	}


	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}


	public void settExpEff(int tExpEff) {
		this.tExpEff = tExpEff;
	}

	public void settActEff(int tActEff) {
		this.tActEff = tActEff;
	}

	

	public LocalDateTime gettAllDate() {
		return tAllDate;
	}

	public void settAllDate(LocalDateTime tAllDate) {
		this.tAllDate = tAllDate;
	}

	public LocalDateTime gettCompDate() {
		return tCompDate;
	}

	public void settCompDate(LocalDateTime tCompDate) {
		this.tCompDate = tCompDate;
	}

	public int gettExpEff() {
		return tExpEff;
	}

	public int gettActEff() {
		return tActEff;
	}

	public TaskStatus gettStatus() {
		return tStatus;
	}

	public void settStatus(TaskStatus tStatus) {
		this.tStatus = tStatus;
	}
	
	public Task taskEnityToModel()
	{
		Task t = new Task();
		t.settActEff(this.tActEff);
		t.settAllDate(this.tAllDate);
		t.settCompDate(this.tCompDate);
		t.settExpEff(this.tExpEff);
		t.settId(this.tId);
		t.settName(this.tName);
		t.settOwner(this.tOwner);
		t.settStatus(this.tStatus);
		return t;
	}
}
