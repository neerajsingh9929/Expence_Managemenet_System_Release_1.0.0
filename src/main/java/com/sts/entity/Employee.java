package com.sts.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Employee {

	@Column(name = "Employee_Name")
    @Size(max = 50, message = "Name must not exceed 50 characters")
	@NotBlank(message = "Name is required")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only alphabets and spaces")
	private String employeeName;

	
	@Column(name = "Employee_Id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@NotBlank(message = "EmpID is required")
//	@Pattern(regexp = "^\\d{1,10}$", message = "Must contain only numbers (max 10 digits)")
	private int employeeId;
	@Temporal(value = TemporalType.DATE)
	private Date employeeDOB;
	private String employeeProject;
	@Pattern(
		    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$",
		    message = "Password must be 8–16 characters long and include uppercase, lowercase, digit, and special character"
		)
	private String password;

	public Employee(String employeeName, int employeeId, Date employeeDOB, String employeeProject, String password) {
		super();
		this.employeeName = employeeName;
		this.employeeId = employeeId;
		this.employeeDOB = employeeDOB;
		this.employeeProject = employeeProject;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getEmployeeDOB() {
		return employeeDOB;
	}

	public void setEmployeeDOB(Date employeeDOB) {
		this.employeeDOB = employeeDOB;
	}

	public String getEmployeeProject() {
		return employeeProject;
	}

	public void setEmployeeProject(String employeeProject) {
		this.employeeProject = employeeProject;
	}

	@Override
	public String toString() {
		return "Employee [employeeName=" + employeeName + ", employeeId=" + employeeId + ", employeeDOB=" + employeeDOB
				+ ", employeeProject=" + employeeProject + "]";
	}

	public Employee() {
		super();
	}

}
