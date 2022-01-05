package com.questioner.model;

import java.util.Date;

public class Exams {

	private long id;
	private String header,information,examCode;
	private int questionCount,time;
	private long startDate,endDate,createdAt,updatedAt;
	private float result;
	private Courses course;
	
	public Exams() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public String getExamCode() {
		return examCode;
	}

	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}

	public Courses getCourse() {
		return course;
	}

	public void setCourse(Courses course) {
		this.course = course;
	}
	
	public boolean isInterval() {
		 
		return (System.currentTimeMillis() < endDate && System.currentTimeMillis() > startDate);
	}
	
	@SuppressWarnings("deprecation")
	public String getStartDateStr() {
		return new Date(startDate).toLocaleString();
	}
	
	@SuppressWarnings("deprecation")
	public String getEndDateStr() {
		return new Date(endDate).toLocaleString();
	}
}
