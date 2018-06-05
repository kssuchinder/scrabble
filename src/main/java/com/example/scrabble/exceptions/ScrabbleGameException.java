package com.example.scrabble.exceptions;

public class ScrabbleGameException extends Exception{

	private final String exceptionMessage;
	
	public ScrabbleGameException(String message) {
        super(message);
        this.exceptionMessage = message;
    }
}
