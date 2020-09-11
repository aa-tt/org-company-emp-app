package com.socgen.empapp.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


public class EmpAppCustomResponseEntity {
	public ResponseMeta meta;
	public Object data;
	public ApiErrorResponse error;
}

class ResponseMeta {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.n")
	public LocalDateTime timestamp;
	public String statusMessage;
	public long responseTime;
	public int total;
}

@JsonIgnoreProperties(value = { "status", "ex" })
class ApiErrorResponse {
	public transient HttpStatus status;
	public String displayMessage;
	public transient Throwable ex;
	public ApiErrorResponse() {	}
	public ApiErrorResponse(HttpStatus status, String displayMessage, Throwable ex) {
		this.status = status;
		this.displayMessage = displayMessage;
		this.ex = ex;
	}

	@Override
	public String toString() {
		return String.format("[ApiErrorResponse message=%s]", displayMessage);
	}
}
