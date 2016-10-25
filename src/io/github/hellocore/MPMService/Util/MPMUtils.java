package io.github.hellocore.MPMService.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.github.hellocore.MPMService.Bean.MPMSession;
import io.github.hellocore.MPMService.Description.MPMCommonDescription;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;

public class MPMUtils {
	
	public static HttpServletRequest getCurrentRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpSession getCurrentSession(){
		return MPMUtils.getCurrentRequest().getSession();
	}
	
//	public static void setMPMSession(String mpmSession){
//		MPMUtils.getCurrentSession().setAttribute(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY, mpmSession);
//	}
	
	public static MPMSession getMPMSession(){
		MPMSession session = new MPMSession();
		if(BeanUtils.isNotEmpty(MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_SESSION_ID_KEY))){
			session.setMpmSessionId(MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_SESSION_ID_KEY));
		}
		if(BeanUtils.isNotEmpty(MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_SESSION_ID_KEY_SIGN_FRONT))){
			session.setSignFrontSessionId(MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_SESSION_ID_KEY_SIGN_FRONT));
		}
		
		return session;
	}
	
	public static void validateSessionId(){
		if(BeanUtils.isNull(MPMUtils.getMPMSession().isEmptySession())){
			throw new MPMServiceException(MPMServiceExceptionMessage.UNAUTHORIZED);					
		}
	}
	
	public static String getCSRFToken(){
		String token = "";
		if(BeanUtils.isNotEmpty(MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_CSRF_TOKEN_KEY))){
			token = MPMUtils.getCurrentRequest().getHeader(MPMCommonDescription.HEADER_CSRF_TOKEN_KEY);
		}
		return token;
	}
	
//	public static void setMPMSession(CookieStore cookieStore){
//		List<Cookie> cookies = cookieStore.getCookies();
//		for(Cookie cookie : cookies){
//			if(cookie.getName().equals(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY)){
//				MPMUtils.setMPMSession(cookie.getValue());
//				break;
//			}
//		}
//	}
	
	public static MPMSession getMPMSessionFromCookieStore(CookieStore cookieStore){
		MPMSession session = new MPMSession();
		List<Cookie> cookies = cookieStore.getCookies();
		for(Cookie cookie : cookies){
			if(cookie.getName().equals(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY)){
				session.setMpmSessionId(cookie.getValue());
			}else if(cookie.getName().equals(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY_SIGN_FRONT)){
				session.setSignFrontSessionId(cookie.getValue());
			}
		}
		return session;
	}
	
	public static String getURLByAppendSession(String url){
		StringBuilder newURL = new StringBuilder(url);
//		if(BeanUtils.isNotNull(MPMUtils.getMPMSessions())){
//			newURL.append("?sessionid=").append(MPMUtils.getMPMSessions());
//		}
		return newURL.toString();
	}
	

	public static String subStringFromSource(String source,Pattern startPattern,Pattern endPattern)
	{
		int locateStart = 0;
		int locateEnd = 0;
		Matcher startMatcher = startPattern.matcher(source);
		if(startMatcher.find()){
			locateStart = startMatcher.end();
		}else{
			throw new MPMServiceException(MPMServiceExceptionMessage.PARSE_ERROR);	
		}
		Matcher endMatcher = endPattern.matcher(source);
		if(endMatcher.find()){
			locateEnd = endMatcher.start();
		}else{
			throw new MPMServiceException(MPMServiceExceptionMessage.PARSE_ERROR);	
		}
		return source.substring(locateStart, locateEnd);		
	}
//	26/May/2014 09:00
	private static final DateFormat dateFormat = new SimpleDateFormat("EEE dd-MM-yyyy HH:mm",Locale.US);
	public static Date parseDate(String source){
		Date result = null;
		try {
			result = dateFormat.parse(source);
		} catch (ParseException e) {
			throw new MPMServiceException(new MPMServiceExceptionMessage(e.toString()));
		}
		return result;
	}
}
