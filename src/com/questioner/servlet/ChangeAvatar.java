package com.questioner.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.questioner.back.DatabaseManager;
import com.questioner.model.Users;

public class ChangeAvatar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DatabaseManager dbManager;
       
    public ChangeAvatar() {
        super();
       dbManager = new DatabaseManager(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("file");
		InputStream fileContent = filePart.getInputStream();
		String absoluteDiskPath = getServletContext().getRealPath("/media");
	
		Users user = (Users)request.getSession().getAttribute("user");
		String fileName = "profile_"+user.getId();
		File file = new File(absoluteDiskPath,fileName);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		
		if(file.exists()) {
			file.delete();
		}
		
		HashMap<String,Object> element = new HashMap<>();
		
		if(Files.copy(fileContent, file.toPath()) > 0) {
			user.setProfileUrl("/media/"+fileName);
			dbManager.updateUser(user);
			
			element.put("errorCode", 1);
			element.put("errorMsg", "Profil resmin güncellendi.");
		}
		else {
			element.put("errorCode", 0);
			element.put("errorMsg", "Profil resmin güncellenirken bir hata ile karþýlaþýldý.");
		}
		
		Gson gson = new Gson();
        Type gsonType = new TypeToken<>(){}.getType();
        String gsonString = gson.toJson(element,gsonType);
        
        PrintWriter out = response.getWriter();
		
		out.print(gsonString);
		out.flush();
	}

}
