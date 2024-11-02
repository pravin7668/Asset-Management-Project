package com.empasset.exception;

public class InternalServerErrorException extends RuntimeException{
	public InternalServerErrorException(String error) {
		super(error);
	}
}
