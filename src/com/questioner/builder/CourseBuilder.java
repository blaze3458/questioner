package com.questioner.builder;

import com.questioner.model.Courses;
import com.questioner.model.Teachers;

public class CourseBuilder {
	private Courses course;
	
	public CourseBuilder() {
		this.course = new Courses();
	}

	public CourseBuilder setId(long id) {
		course.setId(id);
		
		return this;
	}

	public CourseBuilder setName(String name) {
		course.setName(name);
		
		return this;
	}

	public CourseBuilder setDescription(String description) {
		course.setDescription(description);
		
		return this;
	}

	public CourseBuilder setTeacher(Teachers teacher) {
		course.setTeacher(teacher);
		
		return this;
	}
	
	public CourseBuilder setCreatedAt(long createdAt) {
		course.setCreatedAt(createdAt);
		
		return this;
	}

	public CourseBuilder setUpdatedAt(long updatedAt) {
		course.setUpdatedAt(updatedAt);
		
		return this;
	}

	public Courses getBuild() {
		return course;
	}
}
