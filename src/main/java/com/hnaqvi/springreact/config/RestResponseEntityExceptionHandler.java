package com.hnaqvi.springreact.config;

import java.util.stream.Collectors;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends
		ResponseEntityExceptionHandler {

	@ExceptionHandler({ RepositoryConstraintViolationException.class })
	@ResponseBody
	public ResponseEntity<Error> handle(
			Exception ex, WebRequest request) {
		var nevEx =
				(RepositoryConstraintViolationException) ex;

		var errors = nevEx.getErrors().getAllErrors().stream()
				.map(ObjectError::getCode).collect(Collectors.joining("\n"));

		var error = new Error(errors);
		return new ResponseEntity<>(error, new HttpHeaders(),
				HttpStatus.BAD_REQUEST);
	}

	@AllArgsConstructor
	@Data
	private class Error {
		private String message;
	}
}