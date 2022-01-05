package com.questioner.builder;

import com.questioner.model.University;

public class UniversityBuilder {
	private University university;
	
	public UniversityBuilder() {
		this.university = new University();
	}

	public UniversityBuilder setId(long id) {
		university.setId(id);
		
		return this;
	}

	public UniversityBuilder setName(String name) {
		university.setName(name);
		
		return this;
	}

	public UniversityBuilder setCreatedAt(long createdAt) {
		university.setCreatedAt(createdAt);
		
		return this;
	}

	public UniversityBuilder setUpdatedAt(long updatedAt) {
		university.setUpdatedAt(updatedAt);
		
		return this;
	}
	
	public University getBuild() {
		return university;
	}
}
