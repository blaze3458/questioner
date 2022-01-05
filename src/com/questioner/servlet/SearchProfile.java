package com.questioner.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.questioner.back.DatabaseManager;
import com.questioner.model.Users;

public class SearchProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private DatabaseManager dbManager;

    public SearchProfile() {
        super();
        
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String section = request.getParameter("section");
		String value = request.getParameter("value");
		Users user = (Users)request.getSession().getAttribute("user");
	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		
		HashMap<String,Object> elements = new HashMap<>();
		
		elements.put("errorCode", true);
		elements.put("errorMsg", "success");
		elements.put("section",section);
		
		if(section.equals("exams")) {
			elements.put("values",dbManager.getExamsByNameAndStudentId(user.getId(),value));
		}
		else if(section.equals("teachers")) {
			
			elements.put("values",dbManager.getTeachersByName(value));
		}
		else {
			elements.put("errorCode", false);
			elements.put("errorMsg", "Missing section or value.");
		}
		
		Gson gson = new Gson();
        Type gsonType = new TypeToken<>(){}.getType();
        String gsonString = gson.toJson(elements,gsonType);
        
        PrintWriter out = response.getWriter();
		
		out.print(gsonString);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
