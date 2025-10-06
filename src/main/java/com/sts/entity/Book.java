package com.sts.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
@Entity
public class Book {
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	private int id;
	private String nameString;
	@OneToOne(cascade = CascadeType.ALL)
	@JsonManagedReference
	private Author author;
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Book(int id, String nameString, Author author) {
		super();
		this.id = id;
		this.nameString = nameString;
		this.author = author;
	}
	@Override
	public String toString() {
		return "BookDto [id=" + id + ", nameString=" + nameString + ", authorString=" + author + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNameString() {
		return nameString;
	}
	public void setNameString(String nameString) {
		this.nameString = nameString;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	

}
