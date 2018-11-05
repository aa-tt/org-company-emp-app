package com.socgen.empapp.common;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class EmpAppExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	EmpAppExceptionLoggerAndResponseEnhancerAspect loggerAspect;

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		return buildAppCustomResponseEntity(new ApiErrorResponse(BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		return buildAppCustomResponseEntity(new ApiErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
				builder.substring(0, builder.length() - 2), ex));
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST, "Key field(s) of object missing/invalid", ex);
		return buildAppCustomResponseEntity(apiError);
	}

	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		ApiErrorResponse apiError = new ApiErrorResponse(BAD_REQUEST, "Missing/Invalid key data", ex);
		return buildAppCustomResponseEntity(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildAppCustomResponseEntity(new ApiErrorResponse(HttpStatus.BAD_REQUEST, error, ex));
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error writing JSON output";
		return buildAppCustomResponseEntity(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@ExceptionHandler(RuntimeException.class)
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
		String error = "internal error, contact support";
		return buildAppCustomResponseEntity(new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
	}

	@ExceptionHandler(EmpAppCustomException.class)
	private ResponseEntity<Object> handleCustomException(Exception ex) {
		// no need to log this exception here as it will be log by Loggerspect
		return buildAppCustomResponseEntity(
				new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex));
	}

	private ResponseEntity<Object> buildAppCustomResponseEntity(ApiErrorResponse apiError) {
		EmpAppCustomResponseEntity appResponseEntity = new EmpAppCustomResponseEntity();
		ResponseMeta responseMeta = new ResponseMeta();
		responseMeta.setTimestamp(loggerAspect.getStartTime());
		//responseMeta.setResponseTime(ChronoUnit.MILLIS.between(loggerAspect.getStartTime(), loggerAspect.getEndTime()));
		responseMeta.setResponseTime(0);

		appResponseEntity.setError(apiError); // 3. set error
		appResponseEntity.setMeta(responseMeta);
		responseMeta.setStatusMessage(apiError.getDisplayMessage());
		return ResponseEntity.status(apiError.getStatus()).body(appResponseEntity);
	}
}
