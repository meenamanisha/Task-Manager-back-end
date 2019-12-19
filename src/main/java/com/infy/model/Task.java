package com.infy.model;

import java.time.LocalDateTime; 

public class Task {
	private Integer tId;

	private String tName;

	private Integer tOwner;

	private Integer tExpEff;
	private String tDesc;
	

	public String gettDesc() {
		return tDesc;
	}
	public void settDesc(String tDesc) {
		this.tDesc = tDesc;
	}
	private Integer tActEff;	
	private LocalDateTime tAllDate;
	private LocalDateTime tCompDate;	
	private TaskStatus tStatus;
	public Integer gettId() {
		return tId;
	}
	public void settId(Integer tId) {
		this.tId = tId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}

	
	public Integer gettExpEff() {
		return tExpEff;
	}
	public void settExpEff(Integer tExpEff) {
		this.tExpEff = tExpEff;
	}
	public Integer gettActEff() {
		return tActEff;
	}
	public void settActEff(Integer tActEff) {
		this.tActEff = tActEff;
	}
	
	
	
	
	public Integer gettOwner() {
		return tOwner;
	}
	public void settOwner(Integer tOwner) {
		this.tOwner = tOwner;
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
	public TaskStatus gettStatus() {
		return tStatus;
	}
	public void settStatus(TaskStatus tStatus) {
		this.tStatus = tStatus;
	}
	
	


}
