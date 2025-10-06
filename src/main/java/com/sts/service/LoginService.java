package com.sts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sts.dao.LoginRepo;
import com.sts.dao.MenusRepo;
import com.sts.entity.Employee;
import com.sts.entity.Menus;
import com.sts.entity.ResponseOutDTO;

@Service
public class LoginService {

    private final LoginRepo loginRepo;
    private final MenusRepo menusRepo;

    String messageString = null;
    boolean errorFlag = false;

    public LoginService(LoginRepo loginRepo, MenusRepo menusRepo) {
        this.loginRepo = loginRepo;
        this.menusRepo = menusRepo;
    }

    public ResponseOutDTO saveEmployee(Employee employee) {
        ResponseOutDTO responseOutDTO = new ResponseOutDTO();
        try {
            Employee e = this.loginRepo.save(employee);
            System.out.println(e);

            if (e == null || e.toString().isEmpty()) {
                errorFlag = true;
                messageString = "Something has gone wrong. Please contact Admin or helpservices@metafolic.com";
            } else {
                errorFlag = false;
                messageString = "Successfully registered. Please Login here.";
            }
            responseOutDTO.setEmployee(e);
            responseOutDTO.setMessageString(messageString);
            responseOutDTO.setErrorflag(errorFlag);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return responseOutDTO;
    }

    public ResponseOutDTO LoginEmployee(Employee employee) {
        ResponseOutDTO responseOutDTO = new ResponseOutDTO();
        try {
            Employee dbEmployee = loginRepo.findByEmployeeIdAndPassword(employee.getEmployeeId(), employee.getPassword());

            if (dbEmployee != null) {
                responseOutDTO.setEmployee(dbEmployee);
                responseOutDTO.setMessageString("Login successful.");
                responseOutDTO.setErrorflag(false);
                responseOutDTO.setValidationFlag(false);
                responseOutDTO.setValidationError(null);
            } else {
                responseOutDTO.setEmployee(null);
                responseOutDTO.setMessageString("Invalid Employee Id or Password.");
                responseOutDTO.setErrorflag(true);
                responseOutDTO.setValidationFlag(false);
                responseOutDTO.setValidationError(null);
            }
        } catch (Exception exception) {
            responseOutDTO.setEmployee(null);
            responseOutDTO.setMessageString("Something went wrong. Please contact support.");
            responseOutDTO.setErrorflag(true);
            responseOutDTO.setValidationFlag(false);
            responseOutDTO.setValidationError(null);
            exception.printStackTrace();
        }
        return responseOutDTO;
    }

    public List<Menus> getMenus() {
        List<Menus> menus = new ArrayList<>();
        try {
            menus = (List<Menus>) menusRepo.findAll();
            System.out.println(menus);

            if (menus != null && !menus.isEmpty()) {
                System.out.println("Successfully fetched menus");
            } else {
                System.out.println("No menus found");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return menus;
    }
}