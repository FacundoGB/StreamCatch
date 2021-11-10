package com.StreamCatch.app.Intefaces;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

@Component
public interface ErrorHandler {
	public String errorHandler(Exception e, ModelMap model);

}
