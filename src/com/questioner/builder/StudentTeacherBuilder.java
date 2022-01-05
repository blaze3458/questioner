package com.questioner.builder;

import com.questioner.model.StudentTeachers;
import com.questioner.model.Teachers;
import com.questioner.model.Users;

public class StudentTeacherBuilder {
	private StudentTeachers studentTeacher;
	
	public StudentTeacherBuilder() {
		this.studentTeacher = new StudentTeachers();
	}

	public StudentTeacherBuilder setId(long id) {
		studentTeacher.setId(id);
		
		return this;
	}

	public StudentTeacherBuilder setStudent(Users student) {
		studentTeacher.setStudent(student);

		return this;
	}

	public StudentTeacherBuilder setTeacher(Teachers teacher) {
		studentTeacher.setTeacher(teacher);

		return this;
	}

	public StudentTeacherBuilder setCreatedAt(long createdAt) {
		studentTeacher.setCreatedAt(createdAt);

		return this;
	}

	public StudentTeacherBuilder setUpdatedAt(long updatedAt) {
		studentTeacher.setUpdatedAt(updatedAt);

		return this;
	}
	
	public StudentTeachers getBuild() {
		return studentTeacher;
	}
}
