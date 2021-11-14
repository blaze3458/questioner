package db.questioner;

public class Teachers extends Users {
	private int id;
	private University university;
	private Faculty faculty;
	private Department department;
	
	public Teachers() {}
	
	public Teachers(int id, String name, String surname) {
		
		this.id = id;
		this.setName(name);
		this.setSurname(surname);
	}
	
	public Teachers(int id, String name, String surname, String fullname, String email, String bio, String profile, String university, String faculty, String department) {
		this.id = id;
		this.setName(name);
		this.setSurname(surname);
		this.setFullname(fullname);
		
		this.setEmail(email);
		this.setBio(bio);
		this.setProfileUrl(profile);		
		this.university = new University(0,university,0);
		this.faculty = new Faculty(0,faculty,0,0);
		this.department = new Department(0,department,0,0);
	}

	public int getTeacherId() {
		return id;
	}

	public void setTeacherId(int id) {
		this.id = id;
	}
	
	public String getTeacherName() {
		return getName();
	}
	
	public String getTeacherSurname() {
		return getSurname();
	}
	
	public String getTeacherEmail() {
		return getEmail();
	}
	
	public String getTeacherBio() {
		return getBio();
	}
	
	public String getUniversity() {
		return university.getName();
	}
	
	public String getFaculty() {
		return faculty.getName();
	}
	
	public String getDepartment() {
		return department.getName();
	}
}
