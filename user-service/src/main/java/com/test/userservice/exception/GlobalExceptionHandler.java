package com.test.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.userservice.constants.Constants.ErrorsTypes;
import com.test.userservice.dto.ResponseDto;
import com.test.userservice.errors.AccountNotAssociatedException;
import com.test.userservice.errors.EqualAccountsException;
import com.test.userservice.errors.ExistException;
import com.test.userservice.errors.InsufficientFoundException;
import com.test.userservice.errors.LimitTransactionsException;
import com.test.userservice.errors.NotExistException;

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
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(InsufficientFoundException.class)
	public ResponseDto insufficientFound(InsufficientFoundException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.INSUFFICIENT_FOUNDS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(EqualAccountsException.class)
	public ResponseDto equalAccounts(EqualAccountsException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.EQUALS_ACCOUNTS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(AccountNotAssociatedException.class)
	public ResponseDto accountNotAssociated(AccountNotAssociatedException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.ACCOUNT_NOT_ASSOCIATE.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(ExistException.class)
	public ResponseDto existException(ExistException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.INFORMATION_EXIST.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(LimitTransactionsException.class)
	public ResponseDto existException(LimitTransactionsException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),ErrorsTypes.LIMIT_TRANSACTIONS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	
		
}
