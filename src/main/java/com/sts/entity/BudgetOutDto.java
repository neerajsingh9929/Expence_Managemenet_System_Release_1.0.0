package com.sts.entity;

import java.util.List;
import java.util.Map;

public class BudgetOutDto {
	private String messageString;
	private Budget budget;
	public List<Budget> getBudgetList() {
		return budgetList;
	}

	public void setBudgetList(List<Budget> budgetList) {
		this.budgetList = budgetList;
	}
	private boolean errorflag;
    private Map<String, String> validationErrors;
	private boolean validationFlag;
    private List<Budget> budgetList; // <-- Add this line
	public BudgetOutDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BudgetOutDto(String messageString, Budget budget, boolean errorflag, Map<String, String> validationErrorString,
			boolean validationFlag,List<Budget>budgetList) {
		super();
		this.messageString = messageString;
		this.budget = budget;
		this.errorflag = errorflag;
		this.validationErrors = validationErrorString;
		this.validationFlag = validationFlag;
		this.budgetList=budgetList;
	}

	@Override
	public String toString() {
		return "BudgetOutDto [messageString=" + messageString + ", budget=" + budget + ", errorflag=" + errorflag
				+ ", validationErrors=" + validationErrors + ", validationFlag=" + validationFlag + ", budgetList="
				+ budgetList + "]";
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
	public Budget getbuBudget() {
		return budget;
	}
	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	public boolean isErrorflag() {
		return errorflag;
	}
	public void setErrorflag(boolean errorflag) {
		this.errorflag = errorflag;
	}
}
