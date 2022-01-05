package com.questioner.response;

public class RegisterResponse {
	private int errorCode;
	private String errorMsg;
	
	public RegisterResponse(int errorCode, String errorMsg) {
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
