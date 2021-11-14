package api.questioner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import back.questioner.DatabaseManager;
import db.questioner.Department;

/**
 * Servlet implementation class DepartmentAPI
 */
@WebServlet("/department/*")
public class DepartmentAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DatabaseManager dbManager;
	 
    public DepartmentAPI() {
        super();
        dbManager = new DatabaseManager();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int fac_id = Integer.parseInt(request.getParameter("faculty_id"));

		ArrayList<Department> faculty = dbManager.getDepartmentByFacultyId(fac_id);
		
		String obj = new Gson().toJson(faculty);
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json;");
		
		PrintWriter out = response.getWriter();
		
		out.print(obj);
		out.flush();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
