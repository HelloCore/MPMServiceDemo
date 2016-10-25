package io.github.hellocore.MPMService.Service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.protocol.HttpClientContext;

import io.github.hellocore.MPMService.Exception.MPMServiceException;


public interface BasicHttpService {
	
	public String executeHttpGet(String url,Boolean needResponse);
	public String executeHttpGet(String url,Boolean needResponse,String sessionId);
	
	public String executeHttpGetWithoutSession(String url,HttpClientContext context,Boolean needResponse,String sessionId) throws MPMServiceException;

	public String executeHttpPost(String url,List<NameValuePair> params,Boolean needResponse) throws MPMServiceException;
	public String executeHttpPost(String url,List<NameValuePair> params,Boolean needResponse,String sessionId) throws MPMServiceException;
	
	public String executeHttpPostWithoutSession(String url,List<NameValuePair> params,Boolean needResponse) throws MPMServiceException;
	public String executeHttpPostWithoutSession(String url,HttpClientContext context,List<NameValuePair> params,Boolean needResponse,String sessionId) throws MPMServiceException;
	
}
