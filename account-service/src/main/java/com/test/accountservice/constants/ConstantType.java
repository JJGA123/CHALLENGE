package com.test.accountservice.constants;

public class ConstantType {
	
	public enum StatusTypes {
        OK("OK");
		
        private String statusType;

        StatusTypes(String statusType) {
            this.statusType = statusType;
        }

        public String getStatusType() {
            return this.statusType;
        }
    }
	
	public enum ErrorsTypes{
		INFORMATION_EXIST("Information exists"),
		INFORMATION_NOT_EXIST("Information not exists"),
		AMOUNT_INVALID("Amount invalid");
		
        private String errorType;

        ErrorsTypes(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorsType() {
            return this.errorType;
        }
	}
}
