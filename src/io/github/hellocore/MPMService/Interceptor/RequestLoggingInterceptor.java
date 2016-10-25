package io.github.hellocore.MPMService.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.github.hellocore.MPMService.Util.LoggerAspect;
import io.github.hellocore.MPMService.Util.MPMUtils;

public class RequestLoggingInterceptor extends HandlerInterceptorAdapter
{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);

	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
	{
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);

        StringBuilder sb = new StringBuilder();
        sb.append("Request IP [").append(MPMUtils.getCurrentRequest().getRemoteAddr()).append("] ")
        .append("SessionId [").append(MPMUtils.getCurrentSession().getId()).append("] ")
//        .append("MPMSessionId [").append(BeanUtils.getDefaultValueIfNull(MPMUtils.getMPMSession(), "null").toString()).append("] ")
        .append(" URL [").append(request.getRequestURI()).append("]");

        LOGGER.info(sb.toString());
        
		return true;
	}
	
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView){	
		long startTime = (Long)request.getAttribute("startTime");
 
		long endTime = System.currentTimeMillis();
 
		long executeTime = endTime - startTime;

 
        StringBuilder sb = new StringBuilder();
        sb.append("Response IP [").append(MPMUtils.getCurrentRequest().getRemoteAddr()).append("] ")
        .append("SessionId [").append(MPMUtils.getCurrentSession().getId()).append("] ")
//        .append("MPMSessionId [").append(BeanUtils.getDefaultValueIfNull(MPMUtils.getMPMSession(), "null").toString()).append("] ")
        .append(" URL [").append(request.getRequestURI()).append("]")
        .append(" ExecuteTime [").append(executeTime).append("ms]");
        
        LOGGER.info(sb.toString());
	}
}
