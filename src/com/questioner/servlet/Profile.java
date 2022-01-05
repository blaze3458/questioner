package com.questioner.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.questioner.back.DatabaseManager;
import com.questioner.model.StudentExams;
import com.questioner.model.StudentTeachers;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DatabaseManager dbManager;   
	
    public Profile() {
        super();
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Users user = (Users) request.getSession().getAttribute("user");
		
		ArrayList<StudentExams> examsOld = dbManager.getStudentExamsByUserIdAndThen(user.getId());
		ArrayList<StudentTeachers> teachers = dbManager.getStudentTeachersByStudentId(user.getId());
		request.getSession().setAttribute("exams_old", examsOld);
		request.getSession().setAttribute("teachers",teachers);
		
		request.getRequestDispatcher("/profile.jsp").forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
