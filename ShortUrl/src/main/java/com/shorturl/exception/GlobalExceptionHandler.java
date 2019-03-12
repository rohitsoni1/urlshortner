package com.shorturl.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.shorturl.view.bean.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final ResponseEntity<ErrorResponse> handleEmptyPayloadExceptions(HttpMessageNotReadableException ex, WebRequest request) {
		ErrorResponse err = new ErrorResponse(new Date(), "Invalid request data sent, unable to parse it.", request.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
}
