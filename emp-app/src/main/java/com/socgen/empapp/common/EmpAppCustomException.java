package com.socgen.empapp.common;

public class EmpAppCustomException extends RuntimeException {

	private static final long serialVersionUID = -7214458159477737441L;
	
	public EmpAppCustomException(String message) {
		super(message);
	}
	
	public EmpAppCustomException(String message, Throwable e) {
		super(message, e);
	}
	


}
