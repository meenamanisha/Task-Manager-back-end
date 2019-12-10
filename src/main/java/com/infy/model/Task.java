package com.infy.model;

import java.time.LocalDateTime;

public class Task {
	private int tId;

	private String tName;

	private String tOwner;

	private int tExpEff;

	private int tActEff;	
	private LocalDateTime tAllDate;
	private LocalDateTime tCompDate;	
	private TaskStatus tStatus;
	public int gettId() {
		return tId;
	}
	public void settId(int tId) {
		this.tId = tId;
	}
	public String gettName() {
		return tName;
	}
	public void settName(String tName) {
		this.tName = tName;
	}

	
	public int gettExpEff() {
		return tExpEff;
	}
	public void settExpEff(int tExpEff) {
		this.tExpEff = tExpEff;
	}
	public int gettActEff() {
		return tActEff;
	}
	public void settActEff(int tActEff) {
		this.tActEff = tActEff;
	}
	
	public LocalDateTime gettAllDate() {
		return tAllDate;
	}
	public String gettOwner() {
		return tOwner;
	}
	public void settOwner(String tOwner) {
		this.tOwner = tOwner;
	}
	
	public LocalDateTime gettCompDate() {
		return tCompDate;
	}
	public void settCompDate(LocalDateTime tCompDate) {
		this.tCompDate = tCompDate;
	}
	public void settAllDate(LocalDateTime tAllDate) {
		this.tAllDate = tAllDate;
	}
	public TaskStatus gettStatus() {
		return tStatus;
	}
	public void settStatus(TaskStatus tStatus) {
		this.tStatus = tStatus;
	}
	
	


}
