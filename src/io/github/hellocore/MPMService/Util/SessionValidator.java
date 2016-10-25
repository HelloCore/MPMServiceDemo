package io.github.hellocore.MPMService.Util;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class SessionValidator {
	
	@Before("@annotation(io.github.hellocore.MPMService.Description.RequiredSession)")
	public void doCheckSession(){
		MPMUtils.validateSessionId();
	}
}
