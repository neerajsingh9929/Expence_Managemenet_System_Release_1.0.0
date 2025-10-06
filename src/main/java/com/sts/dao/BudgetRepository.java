package com.sts.dao;

import java.util.List;


import org.springframework.data.repository.CrudRepository;


import com.sts.entity.Budget;

public interface BudgetRepository extends CrudRepository <Budget, Integer> {


	List<Budget> findAllByUserId(String userId);
	List<Budget> findAllByUserIdAndMonth(String userId, Integer month);
	List<Budget> findAllByUserIdAndCategory(String userId, String category);
	List<Budget> findAllByUserIdAndMonthAndCategory(String userId, Integer month, String category);
}
