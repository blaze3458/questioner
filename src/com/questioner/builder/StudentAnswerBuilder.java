package com.questioner.builder;

import com.questioner.model.ExamQuestions;
import com.questioner.model.StudentAnswers;
import com.questioner.model.StudentExams;

public class StudentAnswerBuilder {
	private StudentAnswers studentAnswer;
	
	public StudentAnswerBuilder() {
		this.studentAnswer = new StudentAnswers();
	}

	public StudentAnswerBuilder setId(long id) {
		studentAnswer.setId(id);
		
		return this;
	}

	public StudentAnswerBuilder setAnswer(String answer) {
		studentAnswer.setAnswer(answer);

		return this;
	}

	public StudentAnswerBuilder setExamQuestion(ExamQuestions examQuestion) {
		studentAnswer.setExamQuestion(examQuestion);

		return this;
	}

	public StudentAnswerBuilder setStudentExam(StudentExams studentExam) {
		studentAnswer.setStudentExam(studentExam);

		return this;
	}

	public StudentAnswerBuilder setCreatedAt(long createdAt) {
		studentAnswer.setCreatedAt(createdAt);

		return this;
	}

	public StudentAnswerBuilder setUpdatedAt(long updatedAt) {
		studentAnswer.setUpdatedAt(updatedAt);

		return this;
	}
	
	public StudentAnswers getBuild() {
		return studentAnswer;
	}
}
