package com.sts.dao;



import org.springframework.data.repository.CrudRepository;

import com.sts.entity.Employee;

public interface LoginRepo extends CrudRepository<Employee, Integer> {
Employee findByEmployeeIdAndPassword(Integer employeeId, String password);
}
