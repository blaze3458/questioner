package com.questioner.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.questioner.back.DatabaseManager;
import com.questioner.back.Utils;
import com.questioner.dictionary.EExamStatus;
import com.questioner.model.ExamQuestions;
import com.questioner.model.Exams;
import com.questioner.model.StudentExams;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Exam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int ERROR_INVALID_EXAM_ID = 1;
	private static final int ERROR_QUESTIONS_NOT_READY = 2;
	private static final int ERROR_START_EXAM_ALREADY = 3;
	private static final int ERROR_FINISHED_EXAM_ALREADY = 4;
	private DatabaseManager dbManager;
	private Exams exam;
	private ArrayList<ExamQuestions> exam_questions;
	
    public Exam() {
        super();
        
        dbManager = new DatabaseManager();
    }
    
    private String getErrorMsg(int errorCode) {
    	switch(errorCode){
    	
    	case ERROR_INVALID_EXAM_ID:
    		return "Geçersiz sýnav belirttiniz. Hata olduðunu düþünüyorsanýz iletiþime geçin.";
    	case ERROR_QUESTIONS_NOT_READY:
    		return "Sýnavýnýz hazýrlýk aþamasýndadýr. Öðretmeniniz yayýnladýðýnda sýnava baþlayabilirsiniz.";
    	case ERROR_START_EXAM_ALREADY:
    		return "Zaten bir sýnavý baþlatmýþsýnýz. Lütfen sýnavýn tamamlanmasýný bekleyin yada geçerli sýnava dönün.";
    	case ERROR_FINISHED_EXAM_ALREADY:
    		return "Zaten bu sýnavý tamamladýnýz. Lütfen baþka bir sýnava giriþi deneyin.";
    	default:
    		return "Bilinmeyen bir hata ile karþýlaþýldý.Lütfen iletiþime geçin.";
    	}
    }
    
    private void getExamInformations(long id) {
    	exam = dbManager.getExamById(id);
    	exam_questions = dbManager.getExamQuestionsByExamId(id);
    }
    
    private int checkExamError(long id, long startedExam, ArrayList<StudentExams> exams) {	
    	if(id == 0 || exam == null)
			return ERROR_INVALID_EXAM_ID;
    	else if(id != startedExam && startedExam != 0)
    		return ERROR_START_EXAM_ALREADY;
    	else if(!Utils.hasExamById(exams,id))
    		return ERROR_FINISHED_EXAM_ALREADY;
    	else if(exam_questions.size() <= 0)
    		return ERROR_QUESTIONS_NOT_READY;

    	return 0;
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		long id = Long.parseLong(request.getParameter("id"));
		Long startedExam = Utils.sessionAttributeDefault(session, "started_exam",0L);
		ArrayList<StudentExams> exams = Utils.sessionAttributeDefault(session, "exams", null);
		Users user = Utils.sessionAttributeDefault(session, "user", null);
		
		getExamInformations(id);
		int error = checkExamError(id,startedExam,exams);
	
		if(error > 0) {
			request.setAttribute("response", true);
			request.setAttribute("errorCode", error);
			request.setAttribute("errorMsg", getErrorMsg(error));
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}

		if(startedExam > 0) {
			int lastQuestNumber = Utils.sessionAttributeDefault(session,"last_question_number",1);
			request.setAttribute("last_question_number",lastQuestNumber);
		}
		else {
			session.setAttribute("started_exam", id);
			session.setAttribute("last_question_number",1);
			session.setAttribute("exam", exam);
			session.setAttribute("questions", exam_questions);
			dbManager.setStudentExamStatus(user.getId(),id, EExamStatus.STARTED);
		}

		request.getRequestDispatcher("/exam.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
