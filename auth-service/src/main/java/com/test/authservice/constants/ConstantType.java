package com.test.authservice.constants;

public class ConstantType {
		
	public enum ErrorsTypes{
		UNAUTHORIZED("User unauthorized");
		
        private String errorType;

        ErrorsTypes(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorsType() {
            return this.errorType;
        }
	}
}
