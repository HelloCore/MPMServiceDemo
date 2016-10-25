package io.github.hellocore.MPMService.Service.Impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.springframework.stereotype.Service;

import io.github.hellocore.MPMService.Bean.AuthenticationBean;
import io.github.hellocore.MPMService.Bean.MPMSession;
import io.github.hellocore.MPMService.Description.MPMURLDescription;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;
import io.github.hellocore.MPMService.Service.AuthenticationService;
import io.github.hellocore.MPMService.Service.BasicHttpService;
import io.github.hellocore.MPMService.Util.BeanUtils;
import io.github.hellocore.MPMService.Util.MPMUtils;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

//	@Autowired
	private BasicHttpService basicHttpService;	
	public void setBasicHttpService(BasicHttpService basicHttpService) {
		this.basicHttpService = basicHttpService;
	}
	
	@Override
	public Map<String,Object> validate(){
		Map<String,Object> resultMap = new HashMap<String, Object>();	
		String source = basicHttpService.executeHttpGet(MPMURLDescription.VALIDATE_PROFILE,true);
		this.findUserNameFromString(source, resultMap);
		return resultMap;
	}

	@Override

	public Map<String,Object> login(AuthenticationBean bean){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
				
		HttpClientContext context = HttpClientContext.create();
		BasicCookieStore cookieStore = new BasicCookieStore();		
		context.setCookieStore(cookieStore);	
		
		String result = basicHttpService.executeHttpGetWithoutSession(MPMURLDescription.LOGIN_PAGE, context, true, "");
		
		MPMSession session = MPMUtils.getMPMSessionFromCookieStore(cookieStore);
		params.add(new BasicNameValuePair("authen[account]", bean.getUsername()));
		params.add(new BasicNameValuePair("authen[password]", bean.getPassword()));
		params.add(new BasicNameValuePair("commit", "Login"));
		params.add(new BasicNameValuePair("utf8", "✓"));
		this.setCSRFToken(result, params);
		result = basicHttpService.executeHttpPostWithoutSession(MPMURLDescription.LOGIN_EXE, context, params, true, session.getMpmSessionId());
			
		if(BeanUtils.isEmpty(context.getRedirectLocations())){
//				throw new MPMServiceException(new MPMServiceExceptionMessage(MPMServiceExceptionMessage.ErrorCode.AUTHORIZE_FAILED, errorMessage));			
		}else{
			if(context.getRedirectLocations().size() == 1){
				URI lastURI = context.getRedirectLocations().get(0);
				if(lastURI.toASCIIString().equals(MPMURLDescription.LOGIN_PAGE)){
					String errorMessage = this.findAuthenMessageFromString(result);
					if(BeanUtils.isEmpty(errorMessage)){
						throw new MPMServiceException(MPMServiceExceptionMessage.AUTORIZE_FAILED);
					}else{
						throw new MPMServiceException(new MPMServiceExceptionMessage(MPMServiceExceptionMessage.ErrorCode.AUTHORIZE_FAILED, errorMessage));
					}
				}else{

				}
			}					
		}
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
			
		result = basicHttpService.executeHttpGetWithoutSession(MPMURLDescription.VALIDATE_PROFILE, context, true, session.getMpmSessionId());
		session = MPMUtils.getMPMSessionFromCookieStore(cookieStore);

		this.findUserNameFromString(result, resultMap);
		resultMap.put("sessionId", session.getMpmSessionId());
		resultMap.put("signfrontSessionId", session.getSignFrontSessionId());
				
		return resultMap;
	}
	
	private void findUserNameFromString(String source,Map<String,Object> obj){
		Document document = Parser.htmlParser().parseInput(source, "");	

		obj.put("name", BeanUtils.getDefaultValueIfNull(document.select("div#banner table tbody tr td b u").last().text().toString(), "UNKNOW"));
		obj.put("csrfToken", BeanUtils.getDefaultValueIfNull(document.select("meta[name=csrf-token]").last().attr("content").toString(), ""));
		
	}
	private String findAuthenMessageFromString(String source){
		Document document = Parser.htmlParser().parseInput(source, "");		
		return document.select("div.error.w300").last().text();
	}
	
	private void setCSRFToken(String source, List<NameValuePair> params){
		Document document = Parser.htmlParser().parseInput(source, "");	
		String param = document.select("meta[name=csrf-param]").last().attr("content");
		String token = document.select("meta[name=csrf-token]").last().attr("content");
		params.add(new BasicNameValuePair(param,token));
	}
	
	
	@Override
	public void logout(){
		basicHttpService.executeHttpGet(MPMURLDescription.LOGOUT,false);
	}
}
