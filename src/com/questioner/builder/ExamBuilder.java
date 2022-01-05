package com.questioner.builder;

import com.questioner.model.Courses;
import com.questioner.model.Exams;

public class ExamBuilder {
	private Exams exam;
	
	public ExamBuilder() {
		this.exam = new Exams();
	}

	public ExamBuilder setId(long id) {
		exam.setId(id);
		
		return this;
	}

	public ExamBuilder setHeader(String header) {
		exam.setHeader(header);

		return this;
	}

	public ExamBuilder setInformation(String information) {
		exam.setInformation(information);

		return this;
	}

	public ExamBuilder setExamCode(String examCode) {
		exam.setExamCode(examCode);

		return this;
	}

	public ExamBuilder setQuestionCount(int questionCount) {
		exam.setQuestionCount(questionCount);

		return this;
	}

	public ExamBuilder setTime(int time) {
		exam.setTime(time);

		return this;
	}

	public ExamBuilder setStartDate(long startDate) {
		exam.setStartDate(startDate);

		return this;
	}

	public ExamBuilder setEndDate(long endDate) {
		exam.setEndDate(endDate);

		return this;
	}

	public ExamBuilder setCreatedAt(long createdAt) {
		exam.setCreatedAt(createdAt);

		return this;
	}

	public ExamBuilder setUpdatedAt(long updatedAt) {
		exam.setUpdatedAt(updatedAt);

		return this;
	}

	public ExamBuilder setResult(float result) {
		exam.setResult(result);

		return this;
	}

	public ExamBuilder setCourse(Courses course) {
		exam.setCourse(course);

		return this;
	}
	
	public Exams getBuild() {
		return exam;
	}
}
