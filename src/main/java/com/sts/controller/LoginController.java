package com.sts.controller;

import java.awt.Menu;
import java.awt.MenuShortcut;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sts.entity.Menus;
import com.sts.entity.Employee;
import com.sts.entity.ResponseOutDTO;
import com.sts.service.LoginService;

import jakarta.validation.Valid;

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginService;

	@PostMapping("/register")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<ResponseOutDTO> RegisterUser(@Valid @RequestBody Employee employee) {
		
		ResponseOutDTO responseOutDTO=loginService.saveEmployee(employee);
		return ResponseEntity.ok().body(responseOutDTO);
		
	}
	
	@PostMapping("/login")
	@CrossOrigin(origins = "http://localhost:4200")

	public ResponseEntity<ResponseOutDTO> LoginUser(@RequestBody Employee employee) {
		ResponseOutDTO responseOutDTO=new ResponseOutDTO();
		responseOutDTO=loginService.LoginEmployee(employee);
		return ResponseEntity.ok().body(responseOutDTO);
		
	}
	
	@GetMapping("/getMenus")
	@CrossOrigin(origins = "http://localhost:4200")

	public ResponseEntity<ArrayList<Menus>> Menus() {
		ArrayList<Menus> menus=new ArrayList<>();
		menus=(ArrayList<Menus>) loginService.getMenus();
		System.out.println(menus);
		return ResponseEntity.ok().body(menus);
		
	}
	

}
