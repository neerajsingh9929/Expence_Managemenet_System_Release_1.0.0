package com.sts.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Menus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  int menuId;
	private  String contextRoot;
	private  String  menuName;
	
	public Menus(String contextRoot, int menuId,String menuName) {
		super();
		this.menuId = menuId;
		this.contextRoot = contextRoot;
		this.menuName = menuName;
	
	}
	public Menus() {
		super();
		// TODO Auto-generated constructor stub
	}
	public  int getMenuId() {
		return menuId;
	}
	public  void setMenuId(int menuId) {
	this.menuId = menuId;
	}
	public  String getContextRoot() {
		return contextRoot;
	}
	public  void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}
	public  String getMenuName() {
		return menuName;
	}
	public  void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	@Override
	public String toString() {
		return "Menus [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	
	
	

}
