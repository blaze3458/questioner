package com.questioner.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.questioner.back.DatabaseManager;
import com.questioner.back.Utils;
import com.questioner.model.StudentExams;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatabaseManager dbManager;
	
	
    public Index() {
        super();
        dbManager = new DatabaseManager();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users user = (Users)request.getSession().getAttribute("user");
		if(user != null) {
			
			if(user.getRole().equals("stu")) {
				ArrayList<StudentExams> examList = dbManager.getStudentExamsByUserIdAndNow(user.getId());
				
				request.getSession().setAttribute("exams", examList);
				request.getSession().setAttribute("has_exam", Utils.hasAnyExam(examList));
				request.getSession().setAttribute("exam_count", examList.size());
			}	
		}
		 request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
