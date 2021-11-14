package back.questioner;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.*;
import javax.naming.*;

import db.questioner.Users;
import db.questioner.Department;
import db.questioner.Exams;
import db.questioner.Faculty;
import db.questioner.Teachers;
import db.questioner.University;

public class DatabaseManager
{
	private Connection con;
	private Statement st;
	private SQL sql;
	String dbURL = "jdbc:mysql://localhost";
	String user = "root";
	String pass = "123456";

	public DatabaseManager() {
		connectDB();
		sql = new SQL();
		sql.showLog(true);
	}
	
	private boolean connectDB() {
		Context ctx = null;	
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/questioner");
			con = ds.getConnection();
			st = con.createStatement();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private void disconnectDB() {
		try {
			con.close();
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean executeSQL() {
		boolean ret;
		try {
			ret = st.execute(sql.getSQL());
			ret = !ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}
		
		return ret;
	}
	
	private ResultSet executeSQLResulted() {
		ResultSet set = null;
		try {
			set = st.executeQuery(sql.getSQL());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return set;
	}
	
	public void testConnection() {
		boolean ret = connectDB();
		
		System.out.println("Test connection was: "+ret);
	}
	
	
	public boolean checkEmail(String email) {
		try {
			ResultSet set = st.executeQuery("SELECT email FROM users WHERE email = '"+email+"'");
			
			if(set != null)
				return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean insertUser(String email, String password, String name, String surname, String fullname, String type, String university, String faculty, String department) {
		sql.setSQL("INSERT INTO users(email,name,surname,fullname,password,role,university_id,faculty_id,department_id,created_at) VALUES('"+email+"','"+name+"','"+surname+"','"+password+"','"+type+"','"+university+"','"+faculty+"','"+department+"',NOW());");
		return executeSQL();
	}
	
	public boolean insertTeacher(int id, String name, String surname) {
		sql.setSQL("INSERT INTO teachers(user_id,name,surname,created_at) VALUES('"+id+"','"+name+"','"+surname+"',NOW());");
		return executeSQL();
	}
	
	public Users getUser(String email, String hashedPassword) {
		sql.setSQL("SELECT * FROM Users WHERE email = '"+email+"' AND password = '"+hashedPassword+"';");
		ResultSet result = executeSQLResulted();
		
		if(result == null)
			return null;
		
		try {
			result.next();
			int _id = result.getInt("id");
			String _email = result.getString("email");
			String _name = result.getString("name");
			String _surname = result.getString("surname");
			String _fullname = result.getString("fullname");
			String _role = result.getString("role");
			String _profile = result.getString("profile_url");
			String _bio = result.getString("bio");
			int _uni = result.getInt("university_id");
			int _fac = result.getInt("faculty_id");
			int _dep = result.getInt("department_id");
			long _created_at = result.getTimestamp("created_at").getTime();
			Users user = new Users(_id,_email,_name,_surname,_fullname,_role,_profile,_bio,_uni,_fac,_dep,_created_at);
			
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean updateUser(Users user) {
		sql.setSQL("UPDATE Users SET email = '"+user.getEmail()+"', name = '"+user.getName()+"', surname = '"+user.getSurname()+"', profile_url ='"+user.getProfileUrl()+"', bio = '"+user.getBio()+"', updated_at = NOW() WHERE id = "+user.getId()+";");
		return executeSQL();
	}
	
	public ArrayList<Exams> getStudentExams(int user_id){
		sql.setSQL("SELECT e.*,t.name as teacher_name, t.surname as teacher_surname FROM student_exams s INNER JOIN exams e ON s.exam_id = e.id INNER JOIN teachers t ON e.teacher_id = t.id WHERE s.student_id = "+user_id+" AND NOW() < e.end_date;");
		
		ResultSet result = executeSQLResulted();
		
		if(result == null)
			return null;
		
		try {
			ArrayList<Exams> exams = new ArrayList<Exams>();
			while(result.next()) {
				int _id = result.getInt("id");
				int _question_count = result.getInt("question_count");
				int _time = result.getInt("time");
				int _teacher_id = result.getInt("teacher_id");
				Timestamp _start_date = result.getTimestamp("start_date");
				Timestamp _end_date = result.getTimestamp("end_date");
				Timestamp _created_at = result.getTimestamp("created_at");
				String _header = result.getString("header");
				String _information = result.getString("information");
				String _teacher_name = result.getString("teacher_name");
				String _teacher_surname = result.getString("teacher_surname");
				
				exams.add(new Exams(_id,_question_count,_start_date,_end_date,_time,_header,_information, _teacher_id, _teacher_name, _teacher_surname, _created_at));
			}
			
			return exams;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Exams> getStudentExamsOld(int user_id){
		sql.setSQL("SELECT e.*,t.name as teacher_name, t.surname as teacher_surname FROM student_exams s INNER JOIN exams e ON s.exam_id = e.id INNER JOIN teachers t ON e.teacher_id = t.id WHERE s.student_id = "+user_id+" AND NOW() > e.end_date;");
		
		ResultSet result = executeSQLResulted();
		
		if(result == null)
			return null;
		
		try {
			ArrayList<Exams> exams = new ArrayList<Exams>();
			while(result.next()) {
				int _id = result.getInt("id");
				int _question_count = result.getInt("question_count");
				int _time = result.getInt("time");
				int _teacher_id = result.getInt("teacher_id");
				Timestamp _start_date = result.getTimestamp("start_date");
				Timestamp _end_date = result.getTimestamp("end_date");
				Timestamp _created_at = result.getTimestamp("created_at");
				String _header = result.getString("header");
				String _information = result.getString("information");
				String _teacher_name = result.getString("teacher_name");
				String _teacher_surname = result.getString("teacher_surname");
				
				exams.add(new Exams(_id,_question_count,_start_date,_end_date,_time,_header,_information, _teacher_id, _teacher_name, _teacher_surname, _created_at));
			}
			
			return exams;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Teachers> getStudentTeachers(int user_id){
		sql.setSQL("SELECT t.id AS teacher_id, u.`name` AS teacher_name, u.surname AS teacher_surname, u.fullname AS teacher_fullname, u.email, u.bio, u.profile_url, "
				+ "un.`name` AS university, f.`name` AS faculty, d.`name` AS department"
				+ " FROM student_teachers s INNER JOIN teachers t ON t.id = s.teacher_id INNER JOIN users u ON t.user_id = u.id"
				+ " INNER JOIN university un ON u.university_id = un.id INNER JOIN faculty f ON u.faculty_id = f.id"
				+ " INNER JOIN department d ON u.department_id = d.id WHERE s.student_id = "+user_id+";");
		
		ResultSet result = executeSQLResulted();
		
		if(result == null)
			return null;
		
		try {
			ArrayList<Teachers> teachers = new ArrayList<Teachers>();
			while(result.next()) {
				int _id = result.getInt("teacher_id");
				String _name = result.getString("teacher_name");
				String _surname = result.getString("teacher_surname");
				String _fullname = result.getString("teacher_fullname");
				String _email = result.getString("email");
				String _bio = result.getString("bio");
				String _profile = result.getString("profile_url");
				String _uni = result.getString("university");
				String _fac = result.getString("faculty");
				String _dep = result.getString("department");
 				teachers.add(new Teachers(_id,_name,_surname,_fullname,_email,_bio,_profile,_uni,_fac,_dep));
			}
			
			return teachers;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Faculty> getFacultyByUniversityId(int uni) {
		sql.setSQL("SELECT * FROM faculty WHERE university_id = "+uni+";");
		
		ResultSet result = executeSQLResulted();
		
		if(result ==  null)
			return null;
		
		try {
			ArrayList<Faculty> faculty = new ArrayList<Faculty>();
			while(result.next()) {
				int _id = result.getInt("id");
				String _name = result.getString("name");
				int _university_id = result.getInt("university_id");
				long _created_at = result.getTimestamp("created_at").getTime();
				faculty.add(new Faculty(_id,_name,_university_id,_created_at));
			}

			return faculty;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Department> getDepartmentByFacultyId(int fac) {
		sql.setSQL("SELECT * FROM department WHERE faculty_id = "+fac+";");
		
		ResultSet result = executeSQLResulted();
		
		if(result ==  null)
			return null;
		
		try {
			ArrayList<Department> department = new ArrayList<Department>();
			while(result.next()) {
				int _id = result.getInt("id");
				String _name = result.getString("name");
				int _faculty_id = result.getInt("faculty_id");
				long _created_at = result.getTimestamp("created_at").getTime();
				department.add(new Department(_id,_name,_faculty_id,_created_at));
			}

			return department;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Object> getStudentUniversityInfo(int uni_id, int fac_id, int dep_id){
		sql.setSQL("SELECT u.name AS university,f.name AS faculty, d.name AS department FROM university u, faculty f, department d WHERE u.id = "+uni_id+" AND f.id = "+fac_id+" AND d.id = "+dep_id+";");
		
		ResultSet result = executeSQLResulted();
		
		try {
			ArrayList<Object> university_info = new ArrayList<Object>();
			
			if(result.next()) {
				university_info.add(new University(uni_id,result.getString("university"),0));
				university_info.add(new Faculty(fac_id,result.getString("faculty"),uni_id,0));
				university_info.add(new Department(dep_id,result.getString("department"),fac_id,0));
			}
			
			return university_info;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
