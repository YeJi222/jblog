package com.poscodx.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLSyntaxErrorException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice // 스프링 컨트롤러에서 발생하는 예외를 처리하는 어노테이션 
public class GlobalExceptionHandler {
	private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class); 

	@ExceptionHandler(Exception.class)
	public String handlerException(Model model, Exception e) {
		
		// 1. 404 Error 처리 
		if(e instanceof NoHandlerFoundException) {
			return "error/404";
		}
		
		// 2. 로깅(Logging)
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		// System.out.println(errors.toString());
		logger.error(errors.toString());
		
		// 3. 404 에러 제외한 나머지 에러 처리(500 에러포함)
		model.addAttribute("errors", errors.toString());
		return "error/exception";
	}
}