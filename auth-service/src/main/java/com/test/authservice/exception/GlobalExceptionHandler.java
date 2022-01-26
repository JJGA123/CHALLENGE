package com.test.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.authservice.constants.ConstantType.ErrorsTypes;
import com.test.authservice.dto.ResponseDto;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

	/**
	 * unauthorized method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseDto unauthorized(UnauthorizedException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.UNAUTHORIZED.getErrorsType(),ex.getMessage());
		return response;
	}
		
}
