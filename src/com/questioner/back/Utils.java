package com.questioner.back;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;

import com.questioner.dictionary.EExamStatus;
import com.questioner.model.StudentExams;

import jakarta.servlet.http.HttpSession;

@SuppressWarnings("unchecked")
public class Utils {
	
	public Utils() {
		
	}
	
	public static String generateHashPassword(String password) {
		String hash;
		
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());

			byte byteData[] = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
			    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			hash = sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			hash = password;
			e.printStackTrace();
		}
		
		return hash;
	}
	
	public static boolean hasExamById(ArrayList<StudentExams> exams, long id) {
		for(StudentExams e : exams)
			if(e.getExam().getId() == id && (e.getStatus() == EExamStatus.WAITING ||e.getStatus() == EExamStatus.STARTED))
				return true;
		
		return false;
	}
	
	public static boolean hasAnyExam(ArrayList<StudentExams> exams) {
		for(StudentExams e : exams)
			if(e.getStatus() == EExamStatus.WAITING || e.getStatus() == EExamStatus.STARTED)
				return true;
		
		return false;
	}
	
	public static <T> Optional<T> sessionAttribute(HttpSession session, String key){
		T attr = (T)session.getAttribute(key);
		
		return Optional.ofNullable(attr);
	}
	
	public static <T> T sessionAttributeDefault(HttpSession session, String key, T def){
		T attr = (T)session.getAttribute(key);
		
		return attr != null ? attr : (T)def;
	}

}
