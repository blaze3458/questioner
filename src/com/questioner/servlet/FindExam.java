package com.questioner.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.questioner.back.DatabaseManager;
import com.questioner.model.Exams;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FindExam extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final int ERROR_MSG_REGISTER_SUCCESS = 0;
    private static final int ERROR_MSG_EXAM_NOT_EXISTS = 1;
    private static final int ERROR_MSG_EXAM_DATE_EXPIRED = 2;
    private static final int ERROR_MSG_ALREADY_REGISTERED = 3;
    private static final int ERROR_MSG_CANNOT_REGISTERED = 4;
	private DatabaseManager dbManager;
    public FindExam() {
        super();
        dbManager = new DatabaseManager();
    }
    
    private String getErrorMsg(int ret) {
    	switch(ret) {
			case ERROR_MSG_REGISTER_SUCCESS:
				return "S�nava ba�ar�yla kay�t oldun.";
			case ERROR_MSG_EXAM_NOT_EXISTS:
				return "Ge�ersiz s�nav kodu! L�tfen s�nav kodunu kontrol ederek tekrar girin.";
			case ERROR_MSG_EXAM_DATE_EXPIRED:
				return "Bu s�nav�n tarihi ge�mi�. L�tfen ba�ka bir s�nav kodu deneyin.";
			case ERROR_MSG_ALREADY_REGISTERED:
				return "Zaten bu s�nava kay�t oldun.";
			case ERROR_MSG_CANNOT_REGISTERED:
				return "Girdi�iniz veriler hatal� bilgiler i�erdi�i i�in kay�t olu�turulamad�.";
			default:
				return "��lem bilinemeyen bir hata ile kar��la�t�! L�tfen daha sonra tekrar deneyin.";
		}
		
    }
    
    @SuppressWarnings("unchecked")
	private boolean registerUserExam(String uuid, Users user, HttpServletRequest request) {
    	Exams e = dbManager.registerUserExamByUUID(uuid,user.getId());
    	
    	if(e == null) {
    		return false;
    	}
    	
		ArrayList<Exams> examList = (ArrayList<Exams>)request.getSession().getAttribute("exams");
		examList.add(e);
		
		request.getSession().setAttribute("exams", examList);
		request.getSession().setAttribute("exam_count", examList.size());
    	
    	return true;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uuid = request.getParameter("exam_uuid");
		
		Users user = (Users)request.getSession().getAttribute("user");
		
		int ret = dbManager.checkUserForExamByUUID(uuid,user.getId());
		
		if(ret == ERROR_MSG_REGISTER_SUCCESS) {
			
			ret = registerUserExam(uuid,user,request) ? ret : ERROR_MSG_CANNOT_REGISTERED;
		}
		
		request.setAttribute("response", "true");
		request.setAttribute("errorCode",ret);
		request.setAttribute("errorMsg", getErrorMsg(ret));
		
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
