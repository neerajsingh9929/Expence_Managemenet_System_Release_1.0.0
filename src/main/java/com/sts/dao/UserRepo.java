package com.sts.dao;


import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.sts.entity.User;

public interface UserRepo extends CrudRepository<User,Integer> {

	public ArrayList<User> findByName(String name);
	public ArrayList<User> findByNameAndCity(String name,String city);
	public ArrayList<User> findByNameLike(String name);
	
//	 using query annotation
//	1.JPQL
	@Query( "select u From User u")
	public ArrayList<User> getAllUser();
	
//	ParameterizedType query
	@Query("select u From User u Where u.name= :n And u.city= :c")
	public ArrayList<User>getUserByName(@Param("n") String name, @Param("c") String city);
	

//	2. nativeQuery
	
	@Query(value="select * From user", nativeQuery = true)
	public ArrayList<User> getUser();
	
}
