package com.questioner.servlet;

import java.io.IOException;

import com.questioner.back.DatabaseManager;
import com.questioner.model.Users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DatabaseManager dbManager;
	
    public Logout() {
        super();
        
        dbManager = new DatabaseManager();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Users user = (Users) request.getSession().getAttribute("user");
		
		if(dbManager.updateUser(user)) {
			request.getSession().invalidate();
		}
		
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
