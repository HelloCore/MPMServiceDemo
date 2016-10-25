package io.github.hellocore.MPMService.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class MPMServiceListener implements ApplicationListener<ContextRefreshedEvent>{

	private final static Logger LOGGER = LoggerFactory.getLogger(Logger.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		// TODO Auto-generated method stub
		LOGGER.warn("onApplicationEvent()");
//		SSLContext
//		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory();
//		X509HostnameVerifier hostnameVerifier = new Browser?CompatHostnameVerifier();

	}

}
