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
    		return "Ge�ersiz s�nav belirttiniz. Hata oldu�unu d���n�yorsan�z ileti�ime ge�in.";
    	case ERROR_QUESTIONS_NOT_READY:
    		return "S�nav�n�z haz�rl�k a�amas�ndad�r. ��retmeniniz yay�nlad���nda s�nava ba�layabilirsiniz.";
    	case ERROR_START_EXAM_ALREADY:
    		return "Zaten bir s�nav� ba�latm��s�n�z. L�tfen s�nav�n tamamlanmas�n� bekleyin yada ge�erli s�nava d�n�n.";
    	case ERROR_FINISHED_EXAM_ALREADY:
    		return "Zaten bu s�nav� tamamlad�n�z. L�tfen ba�ka bir s�nava giri�i deneyin.";
    	default:
    		return "Bilinmeyen bir hata ile kar��la��ld�.L�tfen ileti�ime ge�in.";
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
