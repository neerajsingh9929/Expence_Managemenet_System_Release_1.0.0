package com.sts;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.sts.controller.BookController;
import com.sts.dao.BooksRepo;
import com.sts.dao.UserRepo;
import com.sts.entity.Book;
import com.sts.entity.Students;
import com.sts.entity.User;


@SpringBootApplication
public class FirststsprojectApplication {

    private final BookController bookController;

      private final BooksRepo booksDao;
	
	  private final UserRepo userRepo;
	  
	   FirststsprojectApplication(UserRepo userRepo ,BooksRepo booksDao, BookController bookController) {
	        this.userRepo = userRepo;
	        this.booksDao = booksDao;
	        this.bookController = bookController;
	    }
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context= SpringApplication.run(FirststsprojectApplication.class, args);
		UserRepo userRepo=context.getBean(UserRepo.class);
		BooksRepo booksDao=context.getBean(BooksRepo.class);
		
//		User user=new User();
//
//		user.setName("sonu");
//		user.setCity("bhopal");
//		user.setStatus("inActive");
//		
//		User user2=new User();
//		
//		user2.setName("Neeraj");
//		user2.setCity("Indore");
//		user2.setStatus("Active");
//		
//	        Saving single user
//		
//			User userA=userRepo.save(user);
//			System.out.println(userA);
//			
//		saving multiple user
//			
//			ArrayList<User> userlist=new ArrayList<>();
//			userlist.add(user);
//			userlist.add(user2);
//		ArrayList<User>	userResult=(ArrayList<User>) userRepo.saveAll(userlist);
//			userResult.forEach(x->{
//			System.out.println(x);
//			});
		
		
//		Get the data	
		
//		Optional<User>optional=userRepo.findById(1);
//		User resultUser=optional.get();
//		System.out.println(resultUser);
//		resultUser.setName("nikhil");
//		userRepo.save(resultUser);	
//		Iterable<User>itr=userRepo.findAll();
//		itr.forEach(data->{
//			System.out.println(data);
//
//		});
		
		
//		Delete user
		
//		userRepo.deleteById(1);
//		System.out.println(userRepo.findAll());		
//		System.out.println(userRepo.findById(1));	
//		System.out.println(userRepo.findByName("neeraj"));	
//		System.out.println(userRepo.findByNameAndCity("Neeraj","Indore"));	
//		System.out.println(userRepo.findByNameLike("n%"));
		
//		1. JPQL
//		ArrayList<User> resultList=userRepo.getAllUser();
//		resultList.forEach(result->{
//		System.out.println(result);
//		});
//		System.out.println("____________________________________________");
//		2.ParameterizedType JPQL
//		ArrayList<User> resultList2=userRepo.getUserByName("neeraj","Indore");
//		resultList2.forEach(result->{
//			System.out.println(result);
//			});
//		System.out.println("____________________________________________");
//
//     3. NativeQuery
//		ArrayList<User> resultList3=userRepo.getUser();
//		resultList3.forEach(result->{
//			System.out.println(result);
//			});
		
		
//		Adding Data in Book
		
		
//		Book book=new Book();
//		Book book1=new Book();
//		Book book2=new Book();
//		
//		book.setNameString("Python");
//		book.setAuthorString("Neeraj");
//		book1.setNameString("java");
//		book1.setAuthorString("karan");
//		book2.setNameString("CPP");
//		book2.setAuthorString("Sonu");
//		
//		ArrayList<Book> bookDtoList=new ArrayList<>();
//		bookDtoList.add(book);
//		bookDtoList.add(book2);
//		bookDtoList.add(book1);
//		ArrayList<Book>	bookResult=(ArrayList<Book>) booksDao.saveAll(bookDtoList);
//		bookResult.forEach(x->{
//		System.out.println(x);
//		});
//	
		
	
		//Hibernate Tutorial
		
//		Configuration cfg=new Configuration();	
//		cfg.configure("hybernate.cfg.xml");
//		SessionFactory sessionFactory= cfg.buildSessionFactory();
//		
//		Students students=new Students();
//		students.setStudentName("Neeraj");
//		students.setBatch("T2");
//		students.setAddress("Indore");
//		students.setDob(java.sql.Date.valueOf("2001-09-12"));
//		students.setPhoneNumber("1234567890");
//		try {
//			FileInputStream fileInputStream= new FileInputStream("src/main/resources/images/pic.jpeg");
//			byte[]data=new byte[fileInputStream.available()];
//			fileInputStream.read(data);
//			students.setImg(data);
//			fileInputStream.close();
//			
//			
//
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		catch (IOException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		Session session=sessionFactory.getCurrentSession();
//		Transaction transaction=session.beginTransaction();
//	session.save(students);
//		session.persist(students);
//		transaction.commit();
//		session.close();
//		System.out.println("Done.............");
//		
//		
//	
	
	
	}
}
