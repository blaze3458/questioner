package questioner;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.questioner.DatabaseManager;
import db.questioner.Exams;
import db.questioner.Users;

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
				ArrayList<Exams> examList = dbManager.getStudentExams(user.getId());
				
				request.getSession().setAttribute("exams", examList);
				request.getSession().setAttribute("exam_count", examList.size());
			}	
		}
		
		 request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
