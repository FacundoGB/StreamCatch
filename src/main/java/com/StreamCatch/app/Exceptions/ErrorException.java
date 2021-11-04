package com.StreamCatch.app.Exceptions;

public class ErrorException extends Exception{

	private static final long serialVersionUID = 7883636384872015753L;

	public ErrorException(String msn) {
        super(msn);
	}
}