package questioner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import back.questioner.DatabaseManager;
import back.questioner.Utils;
import db.questioner.Users;
import response.questioner.RegisterResponse;

public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int REGISTER_SUCCESS = 0;
	private static final int REGISTER_ERROR_EMAIL = 1;
	private static final int REGISTER_ERROR_PASSWORD_REGEX = 2;
	private static final int REGISTER_ERROR_SAVE_USER = 3;
	private static final int REGISTER_ERROR_UNKNOWN_USER = 4;
	private String errorMsg;
	
	private DatabaseManager dbManager;
	
    public Register() {
        super();
        
        dbManager = new DatabaseManager();
        errorMsg = "";
    }

    
    public int validateRegister(String email, String hashed_pass,String name, String surname, String fullname, String password, String type, String university, String faculty, String department) {
    	int ret = REGISTER_SUCCESS;
    	this.errorMsg = "";
    	
    	if(dbManager.checkEmail(email)) {
			System.out.println("This email already registered.");
			ret = REGISTER_ERROR_EMAIL;
			this.errorMsg = "This email already registered.";
		}
		
    	else if(!Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", password)) {
			System.out.println("Password does not matched by regex.");
			ret = REGISTER_ERROR_PASSWORD_REGEX;
			this.errorMsg = "Please enter at least 8 character and number.";
		}
		
    	else if(!type.equals("stu") && !type.equals("tea")) {
			System.out.println("The user type is unknown.");
			ret = REGISTER_ERROR_UNKNOWN_USER;
			this.errorMsg = "Wrong user type please refresh the page.";
		}
    	
    	else if(!dbManager.insertUser(email,hashed_pass,name,surname,fullname,type,university,faculty,department)) {
			System.out.println("The new user didn't save database.");
			ret = REGISTER_ERROR_SAVE_USER;
			this.errorMsg = "The system not complete the registeration. Please try again later.";
		}
    	
    	return ret;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String fullname = name +" "+ surname;
		String password = request.getParameter("password");
		String type = request.getParameter("type");
		String uni = request.getParameter("university");
		String fac = request.getParameter("faculty");
		String dep = request.getParameter("department");
		String hashed_pass = Utils.generateHashPassword(password);
		System.out.println("REGISTER-> "+email+":"+password+"["+type+"] --> "+name+":"+surname);
		System.out.println("Hashed: "+hashed_pass);
		
		int ret = validateRegister(email,hashed_pass,name,surname,fullname,password,type,uni,fac,dep);
		
		if(ret == REGISTER_SUCCESS) {
			Users user = dbManager.getUser(email, hashed_pass);
			
			if(user.getRole().equals("tea")) {		
				dbManager.insertTeacher(user.getId(),user.getName(),user.getSurname());
			}
			
			request.getSession().setAttribute("user", user);
		}

			
		System.out.println("Ret: "+ret);
		
		RegisterResponse rs = new RegisterResponse(ret, this.errorMsg);
		String obj = new Gson().toJson(rs);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		out.print(obj);
		out.flush();
	}

}
