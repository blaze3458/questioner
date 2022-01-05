package com.questioner.builder;

import com.questioner.model.Teachers;
import com.questioner.model.Users;

public class TeacherBuilder {
	private Teachers teacher;
	
	public TeacherBuilder() {
		this.teacher = new Teachers();
	}

	public TeacherBuilder setId(long id) {
		teacher.setId(id);
		
		return this;
	}

	public TeacherBuilder setCreatedAt(long createdAt) {
		teacher.setCreatedAt(createdAt);
		
		return this;
	}

	public TeacherBuilder setUpdatedAt(long updatedAt) {
		teacher.setUpdatedAt(updatedAt);
		
		return this;
	}

	public TeacherBuilder setUser(Users user) {
		teacher.setUser(user);
		
		return this;
	}
	
	public Teachers getBuild() {
		return teacher;
	}
}
