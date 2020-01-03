package com.taskmanager.model;


public class Task {
	private Integer tId;

	private String tName;

	private Integer tOwner;

	private Double tExpEff;
	private String tDesc;
	
	private Double tActEff;	
	private String tAllDate;
	private String tCompDate;	
	private TaskStatus tStatus;
	private String taskOwner;
	private Integer tUserId;
	private String tUserName;
	private String tCreatDate;
	
	

	public String gettCreatDate() {
		return tCreatDate;
	}
	public void settCreatDate(String tCreatDate) {
		this.tCreatDate = tCreatDate;
	}
	public String getTaskOwner() {
		return taskOwner;
	}
	public Integer gettUserId() {
		return tUserId;
	}
	public void settUserId(Integer tUserId) {
		this.tUserId = tUserId;
	}
	public String gettUserName() {
		return tUserName;
	}
	public void settUserName(String tUserName) {
		this.tUserName = tUserName;
	}
	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	public String gettDesc() {
		return tDesc;
	}
	public void settDesc(String tDesc) {
		this.tDesc = tDesc;
	}
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

	
	public Double gettExpEff() {
		return tExpEff;
	}
	public void settExpEff(Double tExpEff) {
		this.tExpEff = tExpEff;
	}
	public Double gettActEff() {
		return tActEff;
	}
	public void settActEff(Double tActEff) {
		this.tActEff = tActEff;
	}
	public Integer gettOwner() {
		return tOwner;
	}
	public void settOwner(Integer tOwner) {
		this.tOwner = tOwner;
	}
	
	
	public String gettAllDate() {
		return tAllDate;
	}
	public void settAllDate(String tAllDate) {
		this.tAllDate = tAllDate;
	}
	public String gettCompDate() {
		return tCompDate;
	}
	public void settCompDate(String tCompDate) {
		this.tCompDate = tCompDate;
	}
	public TaskStatus gettStatus() {
		return tStatus;
	}
	public void settStatus(TaskStatus tStatus) {
		this.tStatus = tStatus;
	}
	
	


}
