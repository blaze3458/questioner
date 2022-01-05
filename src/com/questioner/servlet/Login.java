package com.questioner.servlet;

import java.io.IOException;
import java.util.ArrayList;

import com.questioner.back.DatabaseManager;
import com.questioner.back.Utils;
import com.questioner.model.StudentExams;
import com.questioner.model.StudentTeachers;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatabaseManager dbManager;
	
    public Login() {
        super();
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String hashed_pass = Utils.generateHashPassword(password);
		
		Users user = dbManager.getUser(email, hashed_pass);
		
		if(user != null) {
			
			if(user.getRole().equals("stu")) {
				ArrayList<StudentExams> examList = dbManager.getStudentExamsByUserIdAndNow(user.getId());
				ArrayList<StudentTeachers> teacherList = dbManager.getStudentTeachersByStudentId(user.getId());
				ArrayList<Object> universityInfo = dbManager.getStudentUniversityInfo(user.getUniversity().getId(),user.getFaculty().getId(),user.getDepartment().getId());
				
				request.getSession().setAttribute("exams", examList);
				request.getSession().setAttribute("teachers", teacherList);
				request.getSession().setAttribute("exam_count", examList.size());
				request.getSession().setAttribute("university_info",universityInfo);
			}
							
			request.getSession().setAttribute("user",user);
		}
		
		response.sendRedirect("/");
	}

}
