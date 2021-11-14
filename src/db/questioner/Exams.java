package db.questioner;

import java.sql.Timestamp;

public class Exams extends Teachers {

	private int id,questionCount,time,teacherId;
	private String header,information;
	private Timestamp startDate,endDate,createdAt;
	
	public Exams(int id, int questionCount, Timestamp startDate, Timestamp endDate, int time, String header, String information, int teacherId, String teacherName, String teacherSurname, Timestamp createdAt) {
		super(teacherId,teacherName, teacherSurname);
		this.id = id;
		this.questionCount = questionCount;
		this.startDate = startDate;
		this.endDate = endDate;
		this.time = time;
		this.header = header;
		this.information = information;
		this.teacherId = teacherId;
		this.createdAt = createdAt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionCount() {
		return questionCount;
	}

	public void setQuestionCount(int questionCount) {
		this.questionCount = questionCount;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public int getTeacherId() {
		return teacherId;
	}
	
	public String getTeacherName() {
		return super.getTeacherName();
	}
	
	public String getTeacherSurname() {
		return super.getTeacherSurname();
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Timestamp getCreatedAtTimestamp() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	
	public long getStartDateMilis() {
		return startDate.getTime();
	}
	
	public String getStartDateStr() {
		@SuppressWarnings("deprecation")
		String format = String.format("%d/%d/%d %d:%d", startDate.getDate(), (startDate.getMonth()+1), (startDate.getYear()+1900), startDate.getHours(), startDate.getMinutes());
		return format;
	}
	
	public String getEndDateStr() {
		@SuppressWarnings("deprecation")
		String format = String.format("%d/%d/%d %d:%d", endDate.getDate(), (endDate.getMonth()+1), (endDate.getYear()+1900), endDate.getHours(), endDate.getMinutes());
		return format;
	}
	
	public String getCreatedAtStr() {
		return createdAt.toString();
	}
	
	public long getStartTime() {
		return startDate.getTime() - System.currentTimeMillis();
	}
	
	public boolean isInterval() {
		 
		return (System.currentTimeMillis() < endDate.getTime() && System.currentTimeMillis() > startDate.getTime());
	}
	
	public String getTimeOutStr() {
		String format;
		long second = getStartTime() / 1000;
		
		if(second < 60) {
			format = String.format("%d Saniye", second);
		}
		else if(second >= 60 && second < 3600) {
			format = String.format("%d Dakika %d Saniye", (second / 60), (second % 60));
		}
		else if (second >= 3600 && second < 3600*24){
			format = String.format("%d Saat %d Dakika %d Saniye",(second / 3600), (second / 60)%60, (second % 60));
		}
		else {
			format = String.format("%d Gün %d Saat %d Dakika %d Saniye",(second / (3600*24)), (second / 3600)%24, (second / 60)%60, (second % 60));
		}
		
		return format;
	}
}
