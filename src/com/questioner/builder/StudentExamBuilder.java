package com.questioner.builder;

import com.questioner.dictionary.EExamStatus;
import com.questioner.model.Exams;
import com.questioner.model.StudentExams;
import com.questioner.model.Users;

public class StudentExamBuilder {
	private StudentExams studentExam;
	
	public StudentExamBuilder() {
		this.studentExam = new StudentExams();
	}

	public StudentExamBuilder setId(long id) {
		studentExam.setId(id);
		
		return this;
	}

	public StudentExamBuilder setResult(float result) {
		studentExam.setResult(result);

		return this;
	}

	public StudentExamBuilder setStatus(EExamStatus status) {
		studentExam.setStatus(status);
		
		return this;
	}

	public StudentExamBuilder setCreatedAt(long createdAt) {
		studentExam.setCreatedAt(createdAt);

		return this;
	}

	public StudentExamBuilder setUpdatedAt(long updatedAt) {
		studentExam.setUpdatedAt(updatedAt);

		return this;
	}

	public StudentExamBuilder setStudent(Users student) {
		studentExam.setStudent(student);

		return this;
	}

	public StudentExamBuilder setExam(Exams exam) {
		studentExam.setExam(exam);

		return this;
	}
	
	public StudentExams getBuild() {
		return studentExam;
	}
}
