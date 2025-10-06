package com.sts.entity;

import java.util.Map;

public class ResponseOutDTO {

	private String messageString;
	private Employee employee;
	private boolean errorflag;
    private Map<String, String> validationErrors;
	private boolean validationFlag;
	public ResponseOutDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseOutDTO(String messageString, Employee employee, boolean errorflag, Map<String, String> validationErrorString,
			boolean validationFlag) {
		super();
		this.messageString = messageString;
		this.employee = employee;
		this.errorflag = errorflag;
		this.validationErrors = validationErrorString;
		this.validationFlag = validationFlag;
	}

	public Map<String, String> getValidationError() {
		return validationErrors;
	}

	public void setValidationError(Map<String, String> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public boolean isValidationFlag() {
		return validationFlag;
	}

	public void setValidationFlag(boolean validationFlag) {
		this.validationFlag = validationFlag;
	}

	public String getMessageString() {
		return messageString;
	}
	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public boolean isErrorflag() {
		return errorflag;
	}
	public void setErrorflag(boolean errorflag) {
		this.errorflag = errorflag;
	}
}
