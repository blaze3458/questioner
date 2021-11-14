package db.questioner;

public class Department extends Faculty {
	private int id,facultyId;
	private String name;
	private long createdAt;
	
	public Department() {}
	
	public Department(int id, String name, int facultyId, long createdAt) {
		this.id = id;
		this.facultyId = facultyId;
		this.name = name;
		this.createdAt = createdAt;
		
		super.setId(facultyId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFacultyId() {
		return facultyId;
	}

	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}
}
