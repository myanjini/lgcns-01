package com.optimagrowth.license.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.optimagrowth.license.model.utils.ErrorMessage;
import com.optimagrowth.license.model.utils.ResponseWrapper;
import com.optimagrowth.license.model.utils.RestErrorList;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseWrapper> handleException(HttpServletRequest request, Exception e) {
		ErrorMessage errorMessage = new ErrorMessage(
				e.getMessage(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Internal Server Error", 
				request.getRequestURI());
		
		RestErrorList errorList = new RestErrorList(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
		
		ResponseWrapper responseWrapper 
			= new ResponseWrapper(
					java.util.Collections.singletonMap("status", HttpStatus.INTERNAL_SERVER_ERROR), 
					errorList.getErrors());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseWrapper);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ResponseWrapper> handleRuntimeException(HttpServletRequest request, Exception e) {
		ErrorMessage errorMessage = new ErrorMessage(
				e.getMessage(), 
				HttpStatus.NOT_ACCEPTABLE.value(), 
				"Not Acceptable", 
				request.getRequestURI());
		
		RestErrorList errorList = new RestErrorList(HttpStatus.NOT_ACCEPTABLE, errorMessage);
		
		ResponseWrapper responseWrapper 
			= new ResponseWrapper(
					java.util.Collections.singletonMap("status", HttpStatus.NOT_ACCEPTABLE), 
					errorList.getErrors());
		
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(responseWrapper);
	}
}
