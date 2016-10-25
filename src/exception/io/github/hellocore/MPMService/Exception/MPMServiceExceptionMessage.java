package io.github.hellocore.MPMService.Exception;


public class MPMServiceExceptionMessage {
	private String message;
	private int errorCode;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public MPMServiceExceptionMessage(int errorCode,String message){
		this.message = message;
		this.errorCode = errorCode;
	}
	
	public MPMServiceExceptionMessage(String message){
		this.message = message;
		this.errorCode = ErrorCode.COMMON;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public class ErrorCode {
		public static final int COMMON 				= 1000;
		public static final int AUTHORIZE_FAILED 	= 1001;
		public static final int BAD_REQUEST			= 1002;
		
		public static final int UNAUTHORIZED 				= 2001;
		public static final int PERMISSION_DENIED 			= 2002;
		public static final int VALIDATE_PARAMETER_FAILED 	= 2003;
		public static final int PARSE_ERROR 				= 2004;
		
	}
	
	public static final MPMServiceExceptionMessage PERMISSION_DENIED;
	public static final MPMServiceExceptionMessage UNAUTHORIZED;
	public static final MPMServiceExceptionMessage AUTORIZE_FAILED;
	public static final MPMServiceExceptionMessage VALIDATE_PARAMETER_FAILED;
	public static final MPMServiceExceptionMessage PARSE_ERROR;
	public static final MPMServiceExceptionMessage SESSION_EXPIRED;
	
	static
	{	
		AUTORIZE_FAILED = new MPMServiceExceptionMessage(ErrorCode.AUTHORIZE_FAILED, "AUTHORIZE FAILED");
		SESSION_EXPIRED = new MPMServiceExceptionMessage(ErrorCode.UNAUTHORIZED,"SESSION EXPIRED");
		
		UNAUTHORIZED				= new MPMServiceExceptionMessage(ErrorCode.UNAUTHORIZED,"UNAUTHORIZED");
		PERMISSION_DENIED 			= new MPMServiceExceptionMessage(ErrorCode.PERMISSION_DENIED,"PERMISSION DENIED");
		VALIDATE_PARAMETER_FAILED 	= new MPMServiceExceptionMessage(ErrorCode.VALIDATE_PARAMETER_FAILED,"VALIDATE PARAMETER FAILED");
		PARSE_ERROR 				= new MPMServiceExceptionMessage(ErrorCode.PARSE_ERROR, "PARSE ERROR");
	}
}
