package com.questioner.model;

public class StudentTeachers {
	private long id;
	private Users student;
	private Teachers teacher;
	private long createdAt,updatedAt;
	
	public StudentTeachers() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Users getStudent() {
		return student;
	}

	public void setStudent(Users student) {
		this.student = student;
	}

	public Teachers getTeacher() {
		return teacher;
	}

	public void setTeacher(Teachers teacher) {
		this.teacher = teacher;
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
	
}
