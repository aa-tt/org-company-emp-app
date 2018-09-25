package com.socgen.empapp.common;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
class ApiErrorResponse {
	private String displayMessage;
	private String verboseMessage;
	public ApiErrorResponse() {	}
	public ApiErrorResponse(String displayMessage, String verboseMessage) {
		this.verboseMessage = verboseMessage;
		this.displayMessage = displayMessage;
	}

	@Override
	public String toString() {
		return String.format("[ApiErrorResponse message=%s]", displayMessage);
	}
}
