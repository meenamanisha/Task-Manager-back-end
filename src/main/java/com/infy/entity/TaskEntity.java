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
	private Integer tId;
	private String tDesc;
	
	public String gettDesc() {
		return tDesc;
	}

	public void settDesc(String tDesc) {
		this.tDesc = tDesc;
	}

	private Integer tOwner;
	
	
	private String tName;
	
	private Integer tExpEff;
	
	private Integer tActEff;
	
	@Column(columnDefinition="timestamp")
	private LocalDateTime tAllDate;
	@Column(columnDefinition="timestamp")
	private LocalDateTime tCompDate;
	
	
	
	@Enumerated(EnumType.STRING)
	private TaskStatus tStatus;

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	



	public Integer gettOwner() {
		return tOwner;
	}

	public void settOwner(Integer tOwner) {
		this.tOwner = tOwner;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}


	public void settExpEff(Integer tExpEff) {
		this.tExpEff = tExpEff;
	}

	public void settActEff(Integer tActEff) {
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

	public Integer gettExpEff() {
		return tExpEff;
	}

	public Integer gettActEff() {
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
		t.settDesc(this.tDesc);
		return t;
	}
}
