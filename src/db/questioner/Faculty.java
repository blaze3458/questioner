package db.questioner;

public class Faculty {
	
	private int id, universityId;
	private String name;
	private long createdAt;
	
	public Faculty() {}
	
	public Faculty(int id, String name, int universityId, long createdAt) {
		this.id = id;
		this.name = name;
		this.universityId = universityId;
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUniversityId() {
		return universityId;
	}

	public void setUniversityId(int universityId) {
		this.universityId = universityId;
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
