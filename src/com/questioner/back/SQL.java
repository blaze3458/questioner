package com.questioner.back;

import java.util.ArrayList;

public class SQL {
	private String sql;
	private boolean enableLog;
	private ArrayList<Object> values;
	SQL(){
		sql = "";
		enableLog = false;
		values = new ArrayList<>();
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
		clear();
	}
	
	public String getSQL() {
		if(enableLog)
			System.out.println(sql);
		
		return sql;
	}
	
	public SQL setValues(Object value) {
		values.add(value);
		
		return this;
	}
	
	public ArrayList<Object> getValues(){
		return values;
	}
	
	private void clear() {
		values.clear();
	}
}
