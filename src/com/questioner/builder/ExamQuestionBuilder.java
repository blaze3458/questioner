package com.questioner.builder;

import com.questioner.model.ExamQuestions;
import com.questioner.model.Exams;

public class ExamQuestionBuilder {
	private ExamQuestions examQuestion;
	
	public ExamQuestionBuilder() {
		this.examQuestion = new ExamQuestions();
	}

	public ExamQuestionBuilder setId(long id) {
		examQuestion.setId(id);
		
		return this;
	}

	public ExamQuestionBuilder setExamId(Exams exam) {
		examQuestion.setExamId(exam);

		return this;
	}

	public ExamQuestionBuilder setType(String type) {
		examQuestion.setType(type);

		return this;
	}

	public ExamQuestionBuilder setQuestion(String question) {
		examQuestion.setQuestion(question);

		return this;
	}

	public ExamQuestionBuilder setAnswers(String answers) {
		examQuestion.setAnswers(answers);

		return this;
	}

	public ExamQuestionBuilder setPoint(float point) {
		examQuestion.setPoint(point);

		return this;
	}

	public ExamQuestionBuilder setCreatedAt(long createdAt) {
		examQuestion.setCreatedAt(createdAt);

		return this;
	}

	public ExamQuestionBuilder setUpdatedAt(long updatedAt) {
		examQuestion.setUpdatedAt(updatedAt);

		return this;
	}
	
	public ExamQuestions getBuild() {
		return examQuestion;
	}
}
