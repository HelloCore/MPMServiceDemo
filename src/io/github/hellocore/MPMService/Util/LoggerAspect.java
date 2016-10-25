package io.github.hellocore.MPMService.Util;

//@Aspect
public class LoggerAspect {

//	private final static Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
//	
//	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
//	public Object logWebRequest(ProceedingJoinPoint pjp) throws Throwable {
//		Object obj;
//        Long startTime = System.currentTimeMillis();
//        
//        
//        StringBuilder sb = new StringBuilder("");
//        sb.append("IP [").append(MPMUtils.getCurrentRequest().getRemoteAddr()).append("] ")
//        .append("SessionId [").append(MPMUtils.getCurrentSession().getId()).append("] ")
////        .append("MPMSessionId [").append(BeanUtils.getDefaultValueIfNull(MPMUtils.getMPMSession(), "null").toString()).append("] ")
//        .append(" Class [").append(pjp.getSignature().getDeclaringTypeName()).append("]")
//        .append(" Method [").append(pjp.getSignature().getName()).append("]");
//
//        obj=pjp.proceed();
//        
//        sb.append(" Total Time [").append((System.currentTimeMillis() - startTime)).append(" ms]");
//        
//        LOGGER.info(sb.toString());
//        
//		return obj;
//	}
	
}
