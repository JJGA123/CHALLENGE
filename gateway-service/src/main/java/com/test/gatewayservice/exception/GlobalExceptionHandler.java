package com.test.gatewayservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.gatewayservice.constants.ConstantType.ErrorsTypes;
import com.test.gatewayservice.dto.ResponseDto;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseDto existException(UnauthorizedException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.UNAUTHORIZED.getErrorsType(),ex.getMessage());
		return response;
	}
	
	
		
}
