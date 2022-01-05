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


public class ChangeBio extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DatabaseManager dbManager;
	
    public ChangeBio() {
        super();
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _old = request.getParameter("old");
		String _new = request.getParameter("new");
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		
		HashMap<String,Object> element = new HashMap<>();
		
		if(!_old.equals(_new)) {
			Users user = (Users)request.getSession().getAttribute("user");
			user.setBio(_new);
			
			dbManager.updateUser(user);
			
			request.getSession().setAttribute("user", user);
			element.put("errorCode", 1);
			element.put("errorMsg", "Durumun deðiþtirilidi.");
		}
		else {
			element.put("errorCode", 0);
			element.put("errorMsg", "Sistem bir hata ile karþýlaþtý ve durumunu deðiþtiremedi.");
		}
		
		
		Gson gson = new Gson();
        Type gsonType = new TypeToken<>(){}.getType();
        String gsonString = gson.toJson(element,gsonType);
        
        PrintWriter out = response.getWriter();
		
		out.print(gsonString);
		out.flush();
	}
}
