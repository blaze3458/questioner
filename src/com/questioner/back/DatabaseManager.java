package com.questioner.back;

import java.sql.*;
import java.util.ArrayList;

import javax.sql.*;

import com.questioner.builder.CourseBuilder;
import com.questioner.builder.DepartmentBuilder;
import com.questioner.builder.ExamBuilder;
import com.questioner.builder.FacultyBuilder;
import com.questioner.builder.StudentExamBuilder;
import com.questioner.builder.StudentTeacherBuilder;
import com.questioner.builder.TeacherBuilder;
import com.questioner.builder.UniversityBuilder;
import com.questioner.builder.UserBuilder;
import com.questioner.model.Department;
import com.questioner.model.Exams;
import com.questioner.model.Faculty;
import com.questioner.model.StudentExams;
import com.questioner.model.StudentTeachers;
import com.questioner.model.Teachers;
import com.questioner.model.Users;

import javax.naming.*;

public class DatabaseManager
{
	private Connection con;
	private PreparedStatement st;
	private SQL sql;

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
		} catch (NamingException | SQLException e) {
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
			e.printStackTrace();
		}
	}
	
	private boolean executeSQL() {
		boolean ret;
		try {
			st = con.prepareStatement(sql.getSQL());
			ret = st.execute();
			ret = !ret;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ret = false;
		}
		
		return ret;
	}
	
	private ResultSet executeSQLResulted() {
		ResultSet set = null;
		int index = 1;
		try {
			st = con.prepareStatement(sql.getSQL());	
			for(Object o : sql.getValues()) {
				st.setObject(index++, o);
			}
			
			set = st.executeQuery();
			
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
	
	public boolean insertTeacher(long id, String name, String surname) {
		sql.setSQL("INSERT INTO teachers(user_id,name,surname,created_at) VALUES('"+id+"','"+name+"','"+surname+"',NOW());");
		return executeSQL();
	}
	
	public Users getUser(String email, String hashedPassword) {
		sql.setSQL("SELECT u.*, un.name AS university, f.name AS faculty, d.name AS department"
				+ " FROM Users u"
				+ " INNER JOIN university un ON un.id = u.university_id"
				+ " INNER JOIN faculty f ON f.id = u.faculty_id"
				+ " INNER JOIN department d ON d.id = u.department_id"
				+ " WHERE email = ? AND password = ?;");
		sql.setValues(email).setValues(hashedPassword);
		ResultSet result = executeSQLResulted();	
		
		try {
			if(result.next()) {
				UserBuilder userBuilder = new UserBuilder();
				UniversityBuilder universityBuilder = new UniversityBuilder();
				FacultyBuilder facultyBuilder = new FacultyBuilder();
				DepartmentBuilder departmentBuilder = new DepartmentBuilder();
				
				universityBuilder.setId(result.getLong("university_id")).setName(result.getString("university"));
				facultyBuilder.setId(result.getLong("faculty_id")).setName(result.getString("faculty")).setUniversity(universityBuilder.getBuild());
				departmentBuilder.setId(result.getLong("department_id")).setName(result.getString("department")).setFaculty(facultyBuilder.getBuild());
				
				userBuilder.setId(result.getInt("id")).setName(result.getString("name")).setSurname(result.getString("surname"))
				.setFullname(result.getString("fullname")).setEmail(result.getString("email")).setRole(result.getString("role"))
				.setProfileUrl(result.getString("profile_url")).setBio(result.getString("bio"))
				.setCreatedAt(result.getTimestamp("created_at").getTime()).setUpdatedAt(result.getTimestamp("updated_at").getTime())
				.setUniversity(universityBuilder.getBuild()).setFaculty(facultyBuilder.getBuild()).setDepartment(departmentBuilder.getBuild());
				
				return userBuilder.getBuild();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();		
		}
		
		return null;
	}
	
	public ArrayList<Teachers> getTeachersByName(String name){
		ArrayList<Teachers> teachers = new ArrayList<>();
		
		sql.setSQL("SELECT t.id, u.fullname, u.profile_url FROM teachers t INNER JOIN users u ON t.user_id = u.id WHERE u.fullname LIKE '%?%';");
		sql.setValues(name);
		ResultSet result = executeSQLResulted();
		
		try {
			while(result.next()) {
				TeacherBuilder teacherBuilder = new TeacherBuilder();
				UserBuilder userBuilder = new UserBuilder();
				
				userBuilder.setFullname(result.getString("fullname"));
				userBuilder.setProfileUrl(result.getString("profile_url"));
				
				teacherBuilder.setId(result.getLong("id"));
				teacherBuilder.setUser(userBuilder.getBuild());
				
				teachers.add(teacherBuilder.getBuild());
			}
			
			return teachers;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ArrayList<Exams> getExamsByNameAndStudentId(long id,String name){
		ArrayList<Exams> exams = new ArrayList<>();
		
		sql.setSQL("SELECT e.id, e.header, e.information, e.exam_code, e.question_count, e.start_date, e.end_date,"
				+ "	e.time, e.created_at, e.updated_at, c.id AS course_id, c.name AS course_name, c.description AS course_description,"
				+ "	c.created_at AS course_created_at, c.updated_at AS course_updated_at, c.teacher_id AS course_teacher_id,"
				+ " FROM exams e INNER JOIN student_exams su ON su.exam_id = e.id INNER JOIN courses c ON c.id = e.course_id"
				+ " WHERE e.header LIKE '%?%' AND su.student_id = ?;");
		sql.setValues(id).setValues(name);
		ResultSet result = executeSQLResulted();
		
		if(result != null) {
			try {
				while(result.next()) {
					ExamBuilder examBuilder = new ExamBuilder();
					CourseBuilder courseBuilder = new CourseBuilder();
					TeacherBuilder teacherBuilder = new TeacherBuilder();
					
					teacherBuilder.setId(result.getLong("course_teacher_id"));
					
					courseBuilder.setId(result.getLong("course_id")).setDescription("course_description").setName(result.getString("course_name"))
					.setCreatedAt(result.getTimestamp("course_created_at").getTime()).setUpdatedAt(result.getTimestamp("course_updated_at").getTime()).setTeacher(teacherBuilder.getBuild());
					
					examBuilder.setId(result.getLong("id")).setHeader(result.getString("header")).setInformation(result.getString("information"))
					.setExamCode(result.getString("exam_code")).setQuestionCount(result.getInt("question_count")).setStartDate(result.getTimestamp("start_date").getTime())
					.setEndDate(result.getTimestamp("end_date").getTime()).setTime(result.getInt("time")).setCreatedAt(result.getTimestamp("created_at").getTime())
					.setUpdatedAt(result.getTimestamp("updated_at").getTime()).setCourse(courseBuilder.getBuild());
					
					exams.add(examBuilder.getBuild());
				}
				
				return exams;
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public boolean updateUser(Users user) {
		sql.setSQL("UPDATE Users SET email = '"+user.getEmail()+"', name = '"+user.getName()+"', surname = '"+user.getSurname()+"', profile_url ='"+user.getProfileUrl()+"', bio = '"+user.getBio()+"', updated_at = NOW() WHERE id = "+user.getId()+";");
		return executeSQL();
	}
	
	public ArrayList<StudentExams> getStudentExamsByUserIdAndNow(long user_id){
		ArrayList<StudentExams> studentExams = new ArrayList<>();
		
		sql.setSQL("SELECT 	s.id AS s_id, s.result AS s_result, s.created_at AS s_created_at, s.updated_at AS s_updated_at,"
				+ "	e.*, c.id AS course_id, c.name AS course_name, c.description AS course_description, c.created_at AS course_created_at,"
				+ "	c.updated_at AS course_updated_at, t.id AS teacher_id, u.fullname AS teacher_name"
				+ " FROM student_exams s INNER JOIN exams e ON s.exam_id = e.id INNER JOIN courses c ON e.course_id = c.id"
				+ " INNER JOIN teachers t ON c.teacher_id = t.id INNER JOIN users u ON t.user_id = u.id"
				+ " WHERE s.student_id = ? AND NOW() < e.end_date;");
		sql.setValues(user_id);
		ResultSet result = executeSQLResulted();
		try {
			while(result.next()) {
				StudentExamBuilder studentExamBuilder = new StudentExamBuilder();
				ExamBuilder examBuilder = new ExamBuilder();
				CourseBuilder courseBuilder = new CourseBuilder();
				TeacherBuilder teacherBuilder = new TeacherBuilder();
				UserBuilder userBuilder = new UserBuilder();
				
				userBuilder.setFullname(result.getString("teacher_name"));
				
				teacherBuilder.setId(result.getLong("teacher_id")).setUser(userBuilder.getBuild());
				
				courseBuilder.setId(result.getLong("course_id")).setDescription("course_description").setName(result.getString("course_name"))
				.setCreatedAt(result.getTimestamp("course_created_at").getTime()).setUpdatedAt(result.getTimestamp("course_updated_at").getTime()).setTeacher(teacherBuilder.getBuild());
				
				examBuilder.setId(result.getLong("id")).setHeader(result.getString("header")).setInformation(result.getString("information"))
				.setExamCode(result.getString("exam_code")).setQuestionCount(result.getInt("question_count")).setStartDate(result.getTimestamp("start_date").getTime())
				.setEndDate(result.getTimestamp("end_date").getTime()).setTime(result.getInt("time")).setCreatedAt(result.getTimestamp("created_at").getTime())
				.setUpdatedAt(result.getTimestamp("updated_at").getTime()).setCourse(courseBuilder.getBuild());
				
				studentExamBuilder.setId(result.getLong("s_id")).setResult(result.getFloat("s_result")).setCreatedAt(result.getTimestamp("s_created_at").getTime())
				.setUpdatedAt(result.getTimestamp("s_updated_at").getTime()).setStudent(null).setExam(examBuilder.getBuild());
				
				studentExams.add(studentExamBuilder.getBuild());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return studentExams;
	}
	
	public ArrayList<StudentExams> getStudentExamsByUserIdAndThen(long user_id){
		ArrayList<StudentExams> studentExams = new ArrayList<>();
		
		sql.setSQL("SELECT 	s.id AS s_id, s.result AS s_result, s.created_at AS s_created_at, s.updated_at AS s_updated_at,"
				+ "	e.*, c.id AS course_id, c.name AS course_name, c.description AS course_description, c.created_at AS course_created_at,"
				+ "	c.updated_at AS course_updated_at, t.id AS teacher_id, u.fullname AS teacher_name"
				+ " FROM student_exams s INNER JOIN exams e ON s.exam_id = e.id INNER JOIN courses c ON e.course_id = c.id"
				+ " INNER JOIN teachers t ON c.teacher_id = t.id INNER JOIN users u ON t.user_id = u.id"
				+ " WHERE s.student_id = ? AND NOW() > e.end_date;");
		sql.setValues(user_id);
		ResultSet result = executeSQLResulted();
		try {
			while(result.next()) {
				StudentExamBuilder studentExamBuilder = new StudentExamBuilder();
				ExamBuilder examBuilder = new ExamBuilder();
				CourseBuilder courseBuilder = new CourseBuilder();
				TeacherBuilder teacherBuilder = new TeacherBuilder();
				UserBuilder userBuilder = new UserBuilder();
				
				userBuilder.setFullname(result.getString("teacher_name"));
				
				teacherBuilder.setId(result.getLong("teacher_id")).setUser(userBuilder.getBuild());
				
				courseBuilder.setId(result.getLong("course_id")).setDescription("course_description").setName(result.getString("course_name"))
				.setCreatedAt(result.getTimestamp("course_created_at").getTime()).setUpdatedAt(result.getTimestamp("course_updated_at").getTime()).setTeacher(teacherBuilder.getBuild());
				
				examBuilder.setId(result.getLong("id")).setHeader(result.getString("header")).setInformation(result.getString("information"))
				.setExamCode(result.getString("exam_code")).setQuestionCount(result.getInt("question_count")).setStartDate(result.getTimestamp("start_date").getTime())
				.setEndDate(result.getTimestamp("end_date").getTime()).setTime(result.getInt("time")).setCreatedAt(result.getTimestamp("created_at").getTime())
				.setUpdatedAt(result.getTimestamp("updated_at").getTime()).setCourse(courseBuilder.getBuild());
				
				studentExamBuilder.setId(result.getLong("s_id")).setResult(result.getFloat("s_result")).setCreatedAt(result.getTimestamp("s_created_at").getTime())
				.setUpdatedAt(result.getTimestamp("s_updated_at").getTime()).setStudent(null).setExam(examBuilder.getBuild());
				
				studentExams.add(studentExamBuilder.getBuild());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return studentExams;
	}
	
	public ArrayList<StudentTeachers> getStudentTeachersByStudentId(long user_id){
		ArrayList<StudentTeachers> studentTeachers = new ArrayList<>();
		
		sql.setSQL("SELECT s.id AS s_id, s.created_at AS s_created_at, s.updated_at AS s_updated_at,"
				+ "	t.id AS teacher_id, u.fullname AS teacher_name, u.email, u.bio, u.profile_url,"
				+ " un.`name` AS university, f.`name` AS faculty, d.`name` AS department"
				+ " FROM student_teachers s " 
				+ " INNER JOIN teachers t ON t.id = s.teacher_id"
				+ " INNER JOIN users u ON t.user_id = u.id"
				+ " INNER JOIN university un ON u.university_id = un.id"
				+ " INNER JOIN faculty f ON u.faculty_id = f.id"
				+ " INNER JOIN department d ON u.department_id = d.id"
				+ " WHERE"
				+ "	s.student_id = ?;");
		sql.setValues(user_id);
		ResultSet result = executeSQLResulted();

		try {
			while(result.next()) {
				StudentTeacherBuilder studentTeacherBuilder = new StudentTeacherBuilder();
				TeacherBuilder teacherBuilder = new TeacherBuilder();
				UserBuilder userBuilder = new UserBuilder();
				UniversityBuilder universityBuilder = new UniversityBuilder();
				FacultyBuilder facultyBuilder = new FacultyBuilder();
				DepartmentBuilder departmentBuilder = new DepartmentBuilder();
				
				universityBuilder.setName(result.getString("university"));
				facultyBuilder.setName(result.getString("faculty")).setUniversity(universityBuilder.getBuild());
				departmentBuilder.setName(result.getString("department")).setFaculty(facultyBuilder.getBuild());
				
				userBuilder.setFullname(result.getString("teacher_name")).setEmail(result.getString("email")).setBio(result.getString("bio"))
				.setProfileUrl(result.getString("profile_url"))
				.setUniversity(universityBuilder.getBuild()).setFaculty(facultyBuilder.getBuild()).setDepartment(departmentBuilder.getBuild());
				
				teacherBuilder.setId(result.getLong("teacher_id")).setUser(userBuilder.getBuild());
				
				studentTeacherBuilder.setStudent(null).setTeacher(teacherBuilder.getBuild())
				.setCreatedAt(result.getTimestamp("s_created_at").getTime()).setUpdatedAt(result.getTimestamp("s_updated_at").getTime()).setId(result.getLong("s_id"));
				
				studentTeachers.add(studentTeacherBuilder.getBuild());
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return studentTeachers;
	}
	
	public ArrayList<Faculty> getFacultyByUniversityId(int uni) {
		ArrayList<Faculty> faculty = new ArrayList<>();
		sql.setSQL("SELECT * FROM faculty WHERE university_id = ?;");
		sql.setValues(uni);
		ResultSet result = executeSQLResulted();	
		try {
			while(result.next()) {
				FacultyBuilder facultyBuilder = new FacultyBuilder();
				
				facultyBuilder.setId(result.getLong("id")).setName(result.getString("name"))
				.setCreatedAt(result.getTimestamp("created_at").getTime()).setUniversity(null);
				
				faculty.add(facultyBuilder.getBuild());
			}	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return faculty;
	}
	
	public ArrayList<Department> getDepartmentByFacultyId(int fac) {
		ArrayList<Department> department = new ArrayList<>();
		
		sql.setSQL("SELECT * FROM department WHERE faculty_id = ?;");
		sql.setValues(fac);
		ResultSet result = executeSQLResulted();
		
		try {
			
			while(result.next()) {
				DepartmentBuilder departmentBuilder = new DepartmentBuilder();
				
				departmentBuilder.setId(result.getLong("id")).setCreatedAt(result.getTimestamp("created_at").getTime())
				.setName(result.getString("name")).setFaculty(null);
				
				department.add(departmentBuilder.getBuild());
			}			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return department;
	}
	
	public ArrayList<Object> getStudentUniversityInfo(long uni_id, long fac_id, long dep_id){
		ArrayList<Object> university_info = new ArrayList<Object>();
		
		sql.setSQL("SELECT u.name AS university,f.name AS faculty, d.name AS department FROM university u, faculty f, department d WHERE u.id = ? AND f.id = ? AND d.id = ?;");
		sql.setValues(uni_id).setValues(fac_id).setValues(dep_id);
		
		ResultSet result = executeSQLResulted();
		
		try {
			
			
			if(result.next()) {
				UniversityBuilder universityBuilder = new UniversityBuilder();
				FacultyBuilder facultyBuilder = new FacultyBuilder();
				DepartmentBuilder departmentBuilder = new DepartmentBuilder();
				
				universityBuilder.setId(uni_id).setName(result.getString("university"));
				facultyBuilder.setId(fac_id).setName(result.getString("faculty")).setUniversity(null);
				departmentBuilder.setId(dep_id).setName(result.getString("department")).setFaculty(null);
				
				university_info.add(universityBuilder.getBuild());
				university_info.add(facultyBuilder.getBuild());
				university_info.add(departmentBuilder.getBuild());
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		return university_info;
	}
	
	public int checkUserForExamByUUID(String uuid, long user_id) {
		sql.setSQL("SELECT e.* FROM exams e WHERE e.exam_code = ?;");
		sql.setValues(uuid);
		
		ResultSet result = executeSQLResulted();
		try {
			if(result.next()) {
				
				int exam_id = result.getInt("id");
				
				//tarihe bak eskiyse 2
				//return 2;

				sql.setSQL("SELECT * FROM student_exams st WHERE st.exam_id = ? AND st.student_id = ?;");
				sql.setValues(exam_id);
				sql.setValues(user_id);
				ResultSet result2 = executeSQLResulted();
				
				if(result2.next()) {
					//Daha önce bu sýnava kayýt oldunuz
					return 3;
				}
				
				return 0;
			}
			else {
				//Böyle bir sýnav yok
				return 1;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			return 99;
		}
		
	}

	public Exams registerUserExamByUUID(String uuid, long user_id) {
		Exams exam = new Exams();
		boolean found = false;
		sql.setSQL("SELECT e.id,e.header, e.information, e.question_count, e.time, e.course_id, e.start_date, e.end_date, e.created_at, e.updated_at, "
				+ "c.name as course_name, c.description as course_description, "
				+ "t.id as teacher_id, u.fullname AS teacher_name "
				+ "FROM exams e "
				+ "INNER JOIN courses c ON e.course_id = c.id "
				+ "INNER JOIN teachers t ON c.teacher_id = t.id "
				+ "INNER JOIN users u ON t.user_id = u.id "
				+ "WHERE e.exam_code = ?;");
		sql.setValues(uuid);
		ResultSet result = executeSQLResulted();
		
		try {
			if(result.next()) {
				found = true;
				
				ExamBuilder examBuilder = new ExamBuilder();
				CourseBuilder courseBuilder = new CourseBuilder();
				TeacherBuilder teacherBuilder = new TeacherBuilder();
				UserBuilder userBuilder = new UserBuilder();
				
				userBuilder.setFullname(result.getString("teacher_name"));
				teacherBuilder.setId(result.getLong("teacher_id")).setUser(userBuilder.getBuild());
				
				courseBuilder.setId(result.getLong("course_id")).setName(result.getString("course_name"))
				.setDescription(result.getString("course_description")).setTeacher(teacherBuilder.getBuild());
				
				
				examBuilder.setId(result.getInt("id")).setQuestionCount(result.getInt("question_count")).setResult(0.0f)
				.setTime(result.getInt("time")).setStartDate(result.getTimestamp("start_date").getTime()).setEndDate(result.getTimestamp("end_date").getTime())
				.setUpdatedAt(result.getTimestamp("updated_at").getTime()).setCreatedAt(result.getTimestamp("created_at").getTime()).setHeader(result.getString("header"))
				.setInformation(result.getString("information")).setCourse(courseBuilder.getBuild());
				exam = examBuilder.getBuild();
					
				sql.setSQL("INSERT INTO student_exams(student_id,exam_id,created_at) VALUES("+user_id+","+result.getInt("id")+",NOW())");
			}
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return found && executeSQL() ? exam : null;
	}
}
