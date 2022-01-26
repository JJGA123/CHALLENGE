package com.test.transactionservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.transactionservice.constants.ConstantType.ErrorsTypes;
import com.test.transactionservice.dto.ResponseDto;

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
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.INFORMATION_NOT_EXIST.getErrorsType(),ex.getMessage());
		return response;
	}
		
	/**
	 * exist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(LimitTransactionsException.class)
	public ResponseDto existException(LimitTransactionsException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.LIMIT_TRANSACTIONS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * insufficientFound method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(InsufficientFoundException.class)
	public ResponseDto insufficientFoundException(InsufficientFoundException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.INSUFFICIENT_FOUNDS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(EqualAccountsException.class)
	public ResponseDto equalAccounts(EqualAccountsException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.EQUALS_ACCOUNTS.getErrorsType(),ex.getMessage());
		return response;
	}
	
	/**
	 * notExist method to receive the exception type
	 * @param ex object that contain the exception type
	 * @return The response object with information details
	 */
	@ExceptionHandler(AccountNotAssociatedException.class)
	public ResponseDto accountNotAssociated(AccountNotAssociatedException ex) {
		ResponseDto response = new ResponseDto(HttpStatus.OK.getReasonPhrase(),ErrorsTypes.ACCOUNT_NOT_ASSOCIATE.getErrorsType(),ex.getMessage());
		return response;
	}
	
}
