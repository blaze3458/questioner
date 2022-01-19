package com.questioner.model;

import com.questioner.dictionary.EExamStatus;

public class StudentExams {
	private long id;
	private float result;
	private long createdAt,updatedAt;
	private EExamStatus status;
	private long startedTime;
	private Users student;
	private Exams exam;
	
	public StudentExams() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getResult() {
		return result;
	}

	public void setResult(float result) {
		this.result = result;
	}
	
	public EExamStatus getStatus() {
		return status;
	}

	public void setStatus(EExamStatus status) {
		this.status = status;
	}

	public long getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
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

	public Users getStudent() {
		return student;
	}

	public void setStudent(Users student) {
		this.student = student;
	}

	public Exams getExam() {
		return exam;
	}

	public void setExam(Exams exam) {
		this.exam = exam;
	}
}
