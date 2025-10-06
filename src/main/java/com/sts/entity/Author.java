package com.sts.entity;

import org.hibernate.annotations.Collate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Author {
 @Id
 @GeneratedValue (strategy=GenerationType.AUTO)
 private int authorId;
// @Column(name="first_name")
 private String authorFirstName;
 private String authorLastName;
 private String address;
 @OneToOne(mappedBy = "author")
 @JsonBackReference
 private Book book;
 public Book getBook() {
	return book;
}
 public void setBook(Book book) {
	this.book = book;
 }
 public String getAddress() {
	return address;
}
 public int getAuthorId() {
	return authorId;
}
 public void setAuthorId(int authorId) {
	this.authorId = authorId;
 }
 public String getAuthorFirstName() {
	return authorFirstName;
 }
 public void setAuthorFirstName(String authorFirstName) {
	this.authorFirstName = authorFirstName;
 }
 public String getAuthorLastName() {
	return authorLastName;
 }
 public void setAuthorLastName(String authorLastName) {
	this.authorLastName = authorLastName;
 }
 public void setAddress(String address) {
	this.address = address;
 }
 @Override
public String toString() {
	return "Author [authorId=" + authorId + ", authorFirstName=" + authorFirstName + ", authorLastName="
			+ authorLastName + ", address=" + address + "]";
}
 public Author(int authorId, String authorFirstName, String authorLastName, String address) {
	super();
	this.authorId = authorId;
	this.authorFirstName = authorFirstName;
	this.authorLastName = authorLastName;
	this.address = address;
}
 public Author() {
	super();
	// TODO Auto-generated constructor stub
 }
	
}
