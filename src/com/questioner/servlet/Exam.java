package com.questioner.servlet;

import java.io.IOException;

import com.questioner.back.DatabaseManager;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Exam extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int ERROR_INVALID_EXAM_ID = 1;
	private DatabaseManager dbManager;
	
    public Exam() {
        super();
        
        dbManager = new DatabaseManager();
    }
    
    private String getErrorMsg(int errorCode) {
    	switch(errorCode){
    	
    	case ERROR_INVALID_EXAM_ID:
    		return "Ge�ersiz s�nav belirttiniz. Hata oldu�unu d���n�yorsan�z ileti�ime ge�in.";
    	
    	default:
    		return "Bilinmeyen bir hata ile kar��la��ld�.L�tfen ileti�ime ge�in.";
    	}
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String exam = request.getParameter("id");
		
		if(exam == null) {
			request.setAttribute("response", true);
			request.setAttribute("errorCode", ERROR_INVALID_EXAM_ID);
			request.setAttribute("errorMsg", getErrorMsg(ERROR_INVALID_EXAM_ID));
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		//dbManager.findExamDetails(exam);
		
		request.getRequestDispatcher("/exam.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
