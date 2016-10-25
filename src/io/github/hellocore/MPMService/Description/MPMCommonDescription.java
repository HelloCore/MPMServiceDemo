package io.github.hellocore.MPMService.Description;

public class MPMCommonDescription 
{
	public static final String HEADER_SESSION_ID_KEY = "x-mpm-session-id";
	public static final String HEADER_SESSION_ID_KEY_SIGN_FRONT = "x-sign-front-session-id";
	public static final String HEADER_CSRF_TOKEN_KEY = "csrf-token";
	
	public static final String HTTP_SESSION_MPM_SESSION_ID_REAL_KEY = "_session_id";	
	public static final String HTTP_SESSION_MPM_SESSION_ID_REAL_KEY_SIGN_FRONT = "_signfront_session";
	
	public static final String ACCEPT_HEADER = "*/*;q=0.5, text/javascript, application/javascript, application/ecmascript, application/x-ecmascript";
}