package db.questioner;

import java.util.Date;

public class Users {
	private String email,name,surname, fullname, role, profileUrl, bio;
	private int id,universityId,facultyId,departmentId;
	private long createdAt;
	
	public Users() {}
	
	public Users(int id, String email, String name, String surname, String fullname, String role, String profileUrl, String bio, int universityId, int facultyId, int departmentId, long createdAt) {
		this.setId(id);
		this.setEmail(email);
		this.setName(name);
		this.setSurname(surname);
		this.setRole(role);
		this.setProfileUrl(profileUrl);
		this.setBio(bio);
		this.setUniversityId(universityId);
		this.setFacultyId(facultyId);
		this.setDepartmentId(departmentId);
		this.setCreatedAt(createdAt);
		this.setFullname(fullname);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public int getUniversityId() {
		return universityId;
	}

	public void setUniversityId(int universityId) {
		this.universityId = universityId;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
	
	@SuppressWarnings("deprecation")
	public String getCreateTime() {
		Date d = new Date(this.createdAt);
		
		return String.format("%d/%d/%d",d.getDate(),(d.getMonth()+1),(d.getYear()+1900));
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
}
