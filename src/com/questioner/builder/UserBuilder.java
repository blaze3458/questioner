package com.questioner.builder;

import com.questioner.model.Department;
import com.questioner.model.Faculty;
import com.questioner.model.University;
import com.questioner.model.Users;

public class UserBuilder {
	private Users user;
	
	public UserBuilder() {
		this.user = new Users();
	}

	public UserBuilder setId(long id) {
		user.setId(id);
		
		return this;
	}

	public UserBuilder setUniversity(University university) {
		user.setUniversity(university);
		
		return this;
	}

	public UserBuilder setFaculty(Faculty faculty) {
		user.setFaculty(faculty);
		
		return this;
	}

	public UserBuilder setDepartment(Department department) {
		user.setDepartment(department);
		
		return this;
	}

	public UserBuilder setName(String name) {
		user.setName(name);
		
		return this;
	}

	public UserBuilder setSurname(String surname) {
		user.setSurname(surname);
		
		return this;
	}

	public UserBuilder setFullname(String fullname) {
		user.setFullname(fullname);
		
		return this;
	}

	public UserBuilder setEmail(String email) {
		user.setEmail(email);
		
		return this;
	}

	public UserBuilder setRole(String role) {
		user.setRole(role);
		
		return this;
	}

	public UserBuilder setProfileUrl(String profileUrl) {
		user.setProfileUrl(profileUrl);
		
		return this;
	}

	public UserBuilder setBio(String bio) {
		user.setBio(bio);
		
		return this;
	}

	public UserBuilder setPassword(String password) {
		user.setPassword(password);
		
		return this;
	}

	public UserBuilder setCreatedAt(long createdAt) {
		user.setCreatedAt(createdAt);
		
		return this;
	}

	public UserBuilder setUpdatedAt(long updatedAt) {
		user.setUpdatedAt(updatedAt);
		
		return this;
	}
	
	public Users getBuild() {
		return user;
	}
}
