package com.questioner.builder;

import com.questioner.model.Department;
import com.questioner.model.Faculty;

public class DepartmentBuilder {

	private Department department;
	
	public DepartmentBuilder() {
		this.department = new Department();
	}

	public DepartmentBuilder setId(long id) {
		department.setId(id);
		
		return this;
	}

	public DepartmentBuilder setFaculty(Faculty faculty) {
		department.setFaculty(faculty);
		
		return this;
	}

	public DepartmentBuilder setName(String name) {
		department.setName(name);
		
		return this;
	}

	public DepartmentBuilder setCreatedAt(long createdAt) {
		department.setCreatedAt(createdAt);
		
		return this;
	}
	
	public Department getBuild() {
		return department;
	}
}
