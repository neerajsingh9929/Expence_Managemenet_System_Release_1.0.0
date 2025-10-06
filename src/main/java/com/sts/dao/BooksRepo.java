package com.sts.dao;



import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sts.entity.Book;

import java.util.List;




public interface BooksRepo extends CrudRepository<Book, Integer> {
	
public Book findById(int id);
public  List<Book> findAll();
	
		

}
