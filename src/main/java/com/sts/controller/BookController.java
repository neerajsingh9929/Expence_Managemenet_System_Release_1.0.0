package com.sts.controller;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sts.entity.Book;
import com.sts.service.BookService;


@RestController
public class BookController {
	@Autowired
	BookService bookService;
	
	
	@GetMapping("/book")
	public ResponseEntity<List<Book>> getAllBooks()
	{
		List<Book> bookList=bookService.getAllBooks();
		if(bookList.size()==0)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(bookList));
		
	}
	@GetMapping("/book{id}")
	
	public ResponseEntity<Book> getBookById(@PathVariable int id)
	{
		try {
			Book book=bookService.getBookById(id);
       return  ResponseEntity.ok().body(book);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
		
	}
	@PostMapping("/book")
	public Book addBook(@RequestBody Book book)
	{
	Book books=	bookService.addBook(book);
		return  books;
		
		
	}
	@DeleteMapping("/book{id}")
public List<Book> deleteBook(@PathVariable int id)	{
		
		List<Book>resultList= bookService.deleteBook(id);
		return resultList;
	}
	
	@PutMapping("/book{id}")
	public void updateBook(@RequestBody Book book, @PathVariable int id)
	{
		bookService.updateBook(book,id);
		
	}
}
