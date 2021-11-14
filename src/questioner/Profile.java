package questioner;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.questioner.DatabaseManager;
import db.questioner.Exams;
import db.questioner.Teachers;
import db.questioner.Users;

public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private DatabaseManager dbManager;   
	
    public Profile() {
        super();
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Users user = (Users) request.getSession().getAttribute("user");
		
		ArrayList<Exams> examsOld = dbManager.getStudentExamsOld(user.getId());
		ArrayList<Teachers> teachers = dbManager.getStudentTeachers(user.getId());
		request.getSession().setAttribute("exams_old", examsOld);
		request.getSession().setAttribute("teachers",teachers);
		
		request.getRequestDispatcher("profile.jsp").forward(request, response); 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
