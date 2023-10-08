package com.poscodx.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class); 
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		//1. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		// System.out.println(errors.toString());
		logger.error(errors.toString());
		
		//2. 사과 페이지
		model.addAttribute("errors", errors.toString());
		return "error/exception";
	}
}