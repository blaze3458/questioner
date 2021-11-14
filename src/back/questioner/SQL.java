package back.questioner;

public class SQL {
	private String sql;
	private boolean enableLog;
	SQL(){
		sql = "";
		enableLog = false;
	}
	
	SQL(String sql){
		this.sql = sql;
		enableLog = false;
	}
	
	public void showLog(boolean opt) {
		enableLog = opt;
	}
	
	public void setSQL(String sql){
		this.sql = sql;
	}
	
	public String getSQL() {
		if(enableLog)
			System.out.println(sql);
		
		return sql;
	}
	
	
}
