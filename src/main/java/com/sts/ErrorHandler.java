package com.sts;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sts.entity.ResponseOutDTO;

@RestControllerAdvice
public class ErrorHandler {

	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ResponseOutDTO> handleValidationErrors(MethodArgumentNotValidException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getBindingResult().getFieldErrors().forEach(error ->
	            errors.put(error.getField(), error.getDefaultMessage()));

	        ResponseOutDTO response = new ResponseOutDTO();
	        response.setValidationFlag(true);
	        response.setMessageString("Please Enter Valid Detais");
	        response.setValidationError(errors);

	        return ResponseEntity.badRequest().body(response);
	    }
}
