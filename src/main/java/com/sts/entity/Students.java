package com.sts.entity;


import java.util.Arrays;
import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
public class Students {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentId;
	@Column(name = "student_name",length = 30)
	private String studentName;
	private String batch;
	private String address;
	@Temporal(value = TemporalType.DATE)
	private Date dob;
	@Lob
	private byte[] img;
	@Transient
	private String phoneNumber;
	public Students() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Students(int studentId, String studentName, String batch, String address, Date dob, byte[] img,
			String phoneNumber) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.batch = batch;
		this.address = address;
		this.dob = dob;
		this.img = img;
		this.phoneNumber = phoneNumber;
	}
	@Override
	public String toString() {
		return "Students [studentId=" + studentId + ", studentName=" + studentName + ", batch=" + batch + ", address="
				+ address + ", dob=" + dob + ", img=" + Arrays.toString(img) + ", phoneNumber=" + phoneNumber + "]";
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
	
	

}
