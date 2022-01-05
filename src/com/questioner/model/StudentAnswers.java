package com.questioner.model;

public class StudentAnswers {
	private long id;
	private String answer;
	private ExamQuestions examQuestion;
	private StudentExams studentExam;
	private long createdAt,updatedAt;
	
	public StudentAnswers() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ExamQuestions getExamQuestion() {
		return examQuestion;
	}

	public void setExamQuestion(ExamQuestions examQuestion) {
		this.examQuestion = examQuestion;
	}

	public StudentExams getStudentExam() {
		return studentExam;
	}

	public void setStudentExam(StudentExams studentExam) {
		this.studentExam = studentExam;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
