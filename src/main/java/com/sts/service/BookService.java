package com.sts.service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.sts.dao.BooksRepo;
import com.sts.entity.Book;


@Service
public class BookService {

    private final BooksRepo booksRepo;
private List<Book> bookList=new ArrayList<>();


    BookService(BooksRepo booksRepo) {
        this.booksRepo = booksRepo;
    }

	
	
//	static {
//		Book book=new Book();
//		Book book1=new Book();
//		Book book2=new Book();
//		book.setId(0);
//		book.setNameString("Python");
//		book.setAuthorString("Neeraj");
//		book1.setId(1);
//		book1.setNameString("java");
//		book1.setAuthorString("karan");
//		book2.setId(2);
//		book2.setNameString("CPP");
//		book2.setAuthorString("Sonu");
//		
//		bookList.add(book);
//		bookList.add(book2);
//		bookList.add(book1);
//		
//	}
	
	public List<Book> getAllBooks()
	{
		bookList=booksRepo.findAll();
		return bookList;
	}
	public Book getBookById(int id) {
//		Book book=null;
//		book=bookList.stream().filter(e->e.getId()==id).findFirst().get();
//		return book;
		Book book=booksRepo.findById(id);
		return book;
		}
	
	public Book addBook(Book book) {
//		bookList.add(book);
		Book bookresult=booksRepo.save(book);
		System.out.println(bookresult);

		return bookresult;
	}
	public List<Book> deleteBook(int id) {
		

//		bookList=bookList.stream().filter(e->e.getId()!=id).collect(Collectors.toList());
		booksRepo.deleteById(id);
		bookList=booksRepo.findAll();
		return bookList;
	}
	public void updateBook(Book book, int id) {
		
//	bookList=	bookList.stream().map(b->{
//			if(b.getId()==id)
//			{
//				b.setNameString("Core java");
//				b.setAuthorString("Devin charls");
//				
//				
//			}
//			return b;
//		}).collect(Collectors.toList());
//	
//	System.out.println(book);
	book.setId(id);
		Book bookresult=booksRepo.save(book);
		System.out.println(bookresult);
		
		
	}

	

}
