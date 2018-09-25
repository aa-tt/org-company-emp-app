package com.socgen.empapp.common;

import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EmpAppExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	EmpAppExceptionLoggerAndResponseEnhancerAspect loggerAspect;
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		String bodyOfResponse = "{\"message\":\"Please provide valid input\"}";
		return handleExceptionInternal(ex, bodyOfResponse, 
				headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(EmpAppCustomException.class)
	private ResponseEntity<?> handleCustomException(RuntimeException ex, WebRequest request) {
		// no need to log this exception here as it will be log by Loggerspect
		return buildAppCustomResponseEntity(ex, request);
	}
	
	private ResponseEntity<?> buildAppCustomResponseEntity(Exception ex, WebRequest request) {
    	EmpAppCustomResponseEntity appResponseEntity = new EmpAppCustomResponseEntity();
  		ResponseMeta responseMeta = new ResponseMeta();
  		responseMeta.setTimestamp(loggerAspect.getStartTime());
  		responseMeta.setResponseTime(ChronoUnit.MILLIS.between(loggerAspect.getStartTime(), loggerAspect.getEndTime()));
    	
  		appResponseEntity.setError(new ApiErrorResponse(ex.getMessage(), ex.getMessage())); // 3. set error
  		appResponseEntity.setMeta(responseMeta);
  		responseMeta.setStatusMessage(ex.getMessage());
  		return handleExceptionInternal(ex, appResponseEntity, 
				new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
