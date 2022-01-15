package com.test.userservice.constants;

public class Constants {

	public static final String COP = "COP";
	public static final String USD = "USD";
	public static final double TRM_USD = 3979.17;
	public static final String EUR = "EUR";
	public static final double TRM_EUR = 4554.04;
	
	
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
		INSUFFICIENT_FOUNDS("Insufficient founds"),
		EQUALS_ACCOUNTS("Equals accounts"),
		ACCOUNT_NOT_ASSOCIATE("Account not associated"),
		LIMIT_TRANSACTIONS("Limit transactions");
		
        private String errorType;

        ErrorsTypes(String errorType) {
            this.errorType = errorType;
        }

        public String getErrorsType() {
            return this.errorType;
        }
	}
	
	
	
}
