package io.github.hellocore.MPMService.Service.Impl;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import io.github.hellocore.MPMService.Bean.MPMSession;
import io.github.hellocore.MPMService.Description.MPMCommonDescription;
import io.github.hellocore.MPMService.Description.MPMURLDescription;
import io.github.hellocore.MPMService.Exception.MPMServiceException;
import io.github.hellocore.MPMService.Exception.MPMServiceExceptionMessage;
import io.github.hellocore.MPMService.Service.BasicHttpService;
import io.github.hellocore.MPMService.Util.BeanUtils;
import io.github.hellocore.MPMService.Util.MPMUtils;

@Service
public class BasicHttpServiceImpl implements BasicHttpService{
	
	final static PoolingHttpClientConnectionManager connectionManager;
    final static HttpClientBuilder clientBuilder;
    
	static {
//		SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
//		Registry<ConnectionSocketFactory> socketFactoryRegistry = null;
//		try {
////			sslContextBuilder.loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustSelfSignedStrategy());
//			sslContextBuilder.loadTrustMaterial(KeyStore.getInstance(KeyStore.getDefaultType()), new TrustStrategy() {
//		        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
//		            return true;
//		        }
//		    });
//			
////			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", new SSLConnectionSocketFactory(
////					sslContextBuilder.build(),
////	                new String[] { "TLSv1" },
////	                null,
////	                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER))
////					.register("http", PlainConnectionSocketFactory.getSocketFactory())
////					.build();
//
//			socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create().register("https", new SSLConnectionSocketFactory(
//					sslContextBuilder.build(),SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER))
//					.register("http", PlainConnectionSocketFactory.getSocketFactory())
//					.build();
//			
//		 
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (KeyStoreException e) {
//			e.printStackTrace();
//		} catch (KeyManagementException e) {
//			e.printStackTrace();
//		}
		
		connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(100);
		clientBuilder = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).setConnectionManager(connectionManager);
		
	}
	
	
	private CloseableHttpClient buildHttpClient() throws MPMServiceException
	{
		return clientBuilder.build();
	}
	
	
	private HttpGet buildRequest(String url){
		
		return new HttpGet(url);
	}
	
	private HttpPost buildPostRequest(String url){
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
		return httpPost;
	}

	private HttpClientContext buildHttpClientContext(){
		HttpClientContext context = HttpClientContext.create();

		BasicCookieStore cookieStore = new BasicCookieStore();		
		context.setCookieStore(cookieStore);		
		return context;
	}
	
	private void setSessionCookieToStore(CookieStore cookieStore,String sessionId){
		MPMSession session = MPMUtils.getMPMSession();
		if(!session.isEmptySession()){
			BasicClientCookie cookie = new BasicClientCookie(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY, sessionId);
			cookie.setDomain(MPMURLDescription.BASE_DOMAIN);
			cookie.setPath("/");
			cookieStore.addCookie(cookie);

			BasicClientCookie cookie2 = new BasicClientCookie(MPMCommonDescription.HTTP_SESSION_MPM_SESSION_ID_REAL_KEY_SIGN_FRONT, session.getSignFrontSessionId());
			cookie2.setDomain(MPMURLDescription.BASE_DOMAIN);
			cookie2.setPath("/");
			cookieStore.addCookie(cookie2);
		}
	}
	
	private String executeClient(CloseableHttpClient client,HttpUriRequest request,HttpClientContext context,Boolean needResponse,String sessionId){
		String response = null;
		
		if(BeanUtils.isNotEmpty(sessionId)){
			this.setSessionCookieToStore(context.getCookieStore(), sessionId);
		}
		
		try{
			CloseableHttpResponse resp = client.execute(request,context);
			try{
				if(BeanUtils.isNotEmpty(sessionId)){
					if(BeanUtils.isNotEmpty(context.getRedirectLocations())){
						URI lastURI = context.getRedirectLocations().get(context.getRedirectLocations().size() - 1);
						if(lastURI.toASCIIString().equals(MPMURLDescription.LOGIN_PAGE)){
							throw new MPMServiceException(MPMServiceExceptionMessage.UNAUTHORIZED);					
						}else if(lastURI.toASCIIString().equals(MPMURLDescription.SIGNFRONT_PAGE)){
							throw new MPMServiceException(MPMServiceExceptionMessage.SESSION_EXPIRED);							
						}
					} 
				}
				if(needResponse){
					response = EntityUtils.toString( resp.getEntity());
				}
				if(resp.getStatusLine().getStatusCode() != 200){					
					StatusLine line = resp.getStatusLine();
					throw new MPMServiceException(new MPMServiceExceptionMessage(MPMServiceExceptionMessage.ErrorCode.BAD_REQUEST,""+line.getStatusCode()+":"+line.getReasonPhrase()));
				}
			} catch (IOException e1) {
				throw new MPMServiceException(new MPMServiceExceptionMessage(e1.toString()));
			} finally {
				resp.close();
			}
			
		}catch (IOException e1){
			throw new MPMServiceException(new MPMServiceExceptionMessage(e1.toString()));
		}
		return response;
	}


	@Override
	public String executeHttpGet(String url, Boolean needResponse) {
		return this.executeHttpGet(url, needResponse, MPMUtils.getMPMSession().getMpmSessionId());
	}

	@Override
	public String executeHttpGet(String url, Boolean needResponse,
			String sessionId) {
		return this.executeHttpGetWithoutSession(MPMUtils.getURLByAppendSession(url), buildHttpClientContext(), needResponse, sessionId);
	}

	@Override
	public String executeHttpGetWithoutSession(String url,HttpClientContext context,Boolean needResponse,String sessionId) throws MPMServiceException{
		HttpGet request = buildRequest(url);
		CloseableHttpClient client = buildHttpClient();
		request.setHeader(HttpHeaders.ACCEPT, MPMCommonDescription.ACCEPT_HEADER);

		return this.executeClient(client, request, context, true, MPMUtils.getMPMSession().getMpmSessionId());
	}


	@Override
	public String executeHttpPost(String url, List<NameValuePair> params,
			Boolean needResponse) throws MPMServiceException {
		return this.executeHttpPost(url, params, needResponse, MPMUtils.getMPMSession().getMpmSessionId());
	}


	@Override
	public String executeHttpPost(String url, List<NameValuePair> params,
			Boolean needResponse, String sessionId) throws MPMServiceException {
		return this.executeHttpPostWithoutSession(MPMUtils.getURLByAppendSession(url), buildHttpClientContext(), params, needResponse, sessionId);
	}

	@Override
	public String executeHttpPostWithoutSession(String url,List<NameValuePair> params,Boolean needResponse) throws MPMServiceException{
		return this.executeHttpPostWithoutSession(url, buildHttpClientContext(), params, needResponse, null);
	}
	
	@Override
	public String executeHttpPostWithoutSession(String url,
			HttpClientContext context, List<NameValuePair> params,
			Boolean needResponse, String sessionId) throws MPMServiceException {
		CloseableHttpClient httpclient = this.buildHttpClient();
		HttpPost httpPost = this.buildPostRequest(url);
		try {
			if(BeanUtils.isNotEmpty(params)){
				httpPost.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.executeClient(httpclient, httpPost, context, needResponse, sessionId);		
	}

	
}
