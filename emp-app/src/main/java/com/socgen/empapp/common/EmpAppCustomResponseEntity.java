package com.socgen.empapp.common;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
public class EmpAppCustomResponseEntity {	
	private ResponseMeta meta;
	private Object data;
	private ApiErrorResponse error;
}

@Data
class ResponseMeta {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.n")
	private LocalDateTime timestamp;
	private String statusMessage;
	private long responseTime;
	private int total;
}

@Data
@JsonIgnoreProperties(value = { "status", "ex" })
class ApiErrorResponse {
	private transient HttpStatus status;
	private String displayMessage;
	private transient Throwable ex;
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
