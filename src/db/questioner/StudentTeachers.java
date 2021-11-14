package db.questioner;

public class StudentTeachers {
	private int id, studentId, teacherId;
	
	public StudentTeachers(int id, int studentId, int teacherId) {
		this.id = id;
		this.studentId = studentId;
		this.teacherId = teacherId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
}
