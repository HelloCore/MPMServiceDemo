package io.github.hellocore.MPMService.Exception;

public class MPMServiceException extends RuntimeException{

	private static final long serialVersionUID = -3471932563252895997L;
	protected MPMServiceExceptionMessage exceptionMessage;
	public MPMServiceException(MPMServiceExceptionMessage exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	
	public String getMessage(){
		return this.exceptionMessage.getMessage();
	}
	
	public int getErrorCode(){
		return this.exceptionMessage.getErrorCode();
	}
}
