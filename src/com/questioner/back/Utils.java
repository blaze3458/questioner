package com.questioner.back;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

}
