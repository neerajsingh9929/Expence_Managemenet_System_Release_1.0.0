package com.sts.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Budget {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	   
	    private Long id;
	    private Integer month;
	    private Double amount;
	    private Double budgetLimit;
	    private String category;
	    private String userId;
	    private Date created_at;
	    private Date updated_at;
		public Budget(Long id, Integer month, Double amount, Double budgetLimit, String category, String userId, Date created_at,
				Date updated_at) {
			super();
			this.id = id;
			this.month = month;
			this.amount = amount;
			this.budgetLimit = budgetLimit;
			this.category = category;
			this.userId = userId;
			this.created_at = created_at;
			this.updated_at = updated_at;
		}
		public Budget() {
			super();
			// TODO Auto-generated constructor stub
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Integer getMonth() {
			return month;
		}
		public void setMonth(Integer month) {
			this.month = month;
		}
		public Double getAmount() {
			return amount;
		}
		public void setAmount(Double amount) {
			this.amount = amount;
		}
		public Double getBudgetLimit() {
			return budgetLimit;
		}
		public void setBudgetLimit(Double budgetLimit) {
			this.budgetLimit = budgetLimit;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public Date getCreated_at() {
			return created_at;
		}
		public void setCreated_at(Date created_at) {
			this.created_at = created_at;
		}
		public Date getUpdated_at() {
			return updated_at;
		}
		public void setUpdated_at(Date updated_at) {
			this.updated_at = updated_at;
		}
	    
	    

}
