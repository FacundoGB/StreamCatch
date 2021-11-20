package com.StreamCatch.app.Exceptions;

public class ServiceError extends Exception {

	public ServiceError() {
		super("OcurriÃ³ un error inesperado");
	}

	public ServiceError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceError(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceError(String message) {
		super(message);
	}

	public ServiceError(Throwable cause) {
		super(cause);
	}
}
