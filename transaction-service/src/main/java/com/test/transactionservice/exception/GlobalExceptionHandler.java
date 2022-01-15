package com.test.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.transactionservice.constants.Constants.ErrorsTypes;
import com.test.transactionservice.dto.ResponseDto;
import com.test.transactionservice.errors.NotExistException;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(NotExistException.class)
	public ResponseDto notExist(NotExistException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.INFORMATION_NOT_EXIST.getErrorsType(),ex.getMessage());
		return response;
	}
	
}
