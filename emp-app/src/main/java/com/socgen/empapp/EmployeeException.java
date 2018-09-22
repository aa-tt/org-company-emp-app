package com.socgen.empapp;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class EmployeeException extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		String bodyOfResponse = "{\"message\":\"Please provide valid input\"}";
		return handleExceptionInternal(ex, bodyOfResponse, 
				headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = {EmptyResultDataAccessException.class})
	private ResponseEntity<Object> handleDataError(RuntimeException ex, WebRequest request) {
		String bodyOfResponse = "{\"message\":\"Employee does not exist\"}";
		return handleExceptionInternal(ex, bodyOfResponse, 
		          new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
