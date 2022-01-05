package com.questioner.builder;

import com.questioner.model.Faculty;
import com.questioner.model.University;

public class FacultyBuilder {
	private Faculty faculty;
	
	public FacultyBuilder() {
		this.faculty = new Faculty();
	}

	public FacultyBuilder setId(long id) {
		faculty.setId(id);
		
		return this;
	}

	public FacultyBuilder setName(String name) {
		faculty.setName(name);

		return this;
	}

	public FacultyBuilder setCreatedAt(long createdAt) {
		faculty.setCreatedAt(createdAt);

		return this;
	}

	public FacultyBuilder setUniversity(University university) {
		faculty.setUniversity(university);

		return this;
	}
	
	public Faculty getBuild() {
		return faculty;
	}
}
