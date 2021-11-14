package questioner;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.questioner.DatabaseManager;
import back.questioner.Utils;
import db.questioner.Users;
import db.questioner.Exams;
import db.questioner.Teachers;

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
				ArrayList<Exams> examList = dbManager.getStudentExams(user.getId());
				ArrayList<Teachers> teacherList = dbManager.getStudentTeachers(user.getId());
				ArrayList<Object> universityInfo = dbManager.getStudentUniversityInfo(user.getUniversityId(),user.getFacultyId(),user.getDepartmentId());
				
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
