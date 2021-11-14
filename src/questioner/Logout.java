package questioner;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.questioner.DatabaseManager;
import db.questioner.Users;

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
