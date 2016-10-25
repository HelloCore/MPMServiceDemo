package io.github.hellocore.MPMService.Exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import io.github.hellocore.MPMService.Util.MPMUtils;

public class MPMServiceExceptionResolver implements HandlerExceptionResolver 
{
	private static Logger LOGGER = LoggerFactory.getLogger(MPMServiceExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		exception.printStackTrace();
		StringBuilder sb = new StringBuilder();
        sb.append("Request IP [").append(MPMUtils.getCurrentRequest().getRemoteAddr()).append("] ")
        .append("SessionId [").append(MPMUtils.getCurrentSession().getId()).append("] ")
//        .append("MPMSessionId [").append(BeanUtils.getDefaultValueIfNull(MPMUtils.getMPMSession(), "null").toString()).append("] ")
        .append(" Get Exception [").append(exception.getLocalizedMessage()).append("]");
        
        LOGGER.error(sb.toString());
		ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
		
		if(exception instanceof MPMServiceException){
			if(((MPMServiceException) exception).getErrorCode() == MPMServiceExceptionMessage.ErrorCode.COMMON){
				exception.printStackTrace();
			}
			mv.addObject("message", exception.getMessage());
			mv.addObject("errorCode",((MPMServiceException) exception).getErrorCode());
		}else{
			exception.printStackTrace();
			mv.addObject("message", exception.toString());
			mv.addObject("errorCode",MPMServiceExceptionMessage.ErrorCode.COMMON);
		}
		
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		return mv;
	}

}
