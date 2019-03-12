package com.shorturl.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.shorturl.view.bean.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ShortUrlExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * Generic handler if nothing matches.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
		log.error("Error occurred check the trace ",ex);
		ErrorResponse err = new ErrorResponse(new Date(), "UnKnown error has occurred, please retry later", request.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handles Data Not found scenario.
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException(NotFoundException ex, WebRequest request) {
		log.error("Error occurred check the trace ",ex);
		ErrorResponse err = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Validation error has occurred" );
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ErrorResponse> errorRspList = new ArrayList<>();
		for (FieldError fieldError : fieldErrors) {
			ErrorResponse err = new ErrorResponse(new Date(), "Validation Failed",
					fieldError.getField() + " :: "
							+ fieldError.getDefaultMessage());
			errorRspList.add(err);
		}
		
		return new ResponseEntity<>(errorRspList, HttpStatus.BAD_REQUEST);
	}
	


}
