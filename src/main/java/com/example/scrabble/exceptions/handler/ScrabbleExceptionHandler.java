package com.example.scrabble.exceptions.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.scrabble.exceptions.ScrabbleGameException;

@ControllerAdvice
public class ScrabbleExceptionHandler {
	
	@ExceptionHandler(ScrabbleGameException.class)
    @ResponseBody
	public ResponseEntity<String> handleScrabbleGameException(ScrabbleGameException e, HttpStatus status) {
		
		return new ResponseEntity<String>(e.getMessage(), status);
		
	}

}
