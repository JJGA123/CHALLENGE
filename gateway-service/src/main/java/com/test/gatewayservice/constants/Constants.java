package com.test.gatewayservice.constants;

public class Constants {
	
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
		UNAUTHORIZED("Unhautorized");
		
        private String errorType;

        ErrorsTypes(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorsType() {
            return this.errorType;
        }
	}
	
	
	
}
