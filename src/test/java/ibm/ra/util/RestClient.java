package ibm.ra.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;




public abstract class RestClient {
	Logger logger = java.util.logging.Logger.getLogger("RestClient");
	protected CloseableHttpClient httpClient;
	protected HttpHost host;
	protected int port=443;

	protected String baseUrl="";
	protected String protocol="https";
	protected HttpClientContext context;
	
	public void setCredential(String uname,String pwd) {
		Credentials defaultcreds = new UsernamePasswordCredentials(uname,pwd);
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, defaultcreds);
		context = HttpClientContext.create();
		AuthCache authCache = new BasicAuthCache();
		authCache.put(this.host, new BasicScheme());
		context.setCredentialsProvider(provider);
		context.setAuthCache(authCache);
	}

	public URI buildCompleteUrl(String url,List<NameValuePair> nvps) {
		URIBuilder builder = new URIBuilder();
		String newUrl=baseUrl; 	
		if (url!=null) {
			if (url.startsWith("/")) newUrl=baseUrl+url;
			else  newUrl= baseUrl+"/"+url;
		}
		builder.setScheme(protocol).setHost(host.getHostName()).setPort(port).setPath(newUrl);
		if (nvps != null) builder.setParameters(nvps);
		try {
			URI uri= builder.build();
			logger.info(uri.toASCIIString());
			return uri;
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected void displayResponse(String url, int statusCode, String response) {
		System.out.println(">> URL " + url);
		System.out.println("<< RESPONSE - Status code " + statusCode + "\n");
		System.out.println("<< PAYLOAD -"+response);
	}

	protected String getResponse(InputStream stream) {
		if (stream == null) return "";
		int count = 0 ;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ;
		byte[] byteArray = new byte[1024];
		try {
			while((count = stream.read(byteArray, 0, byteArray.length)) > 0) {
			  outputStream.write(byteArray, 0, count) ;
			}
			return new String(outputStream.toByteArray(), "UTF-8");
	   } catch (IOException e) {
		e.printStackTrace();
		return "";
	   }
   }
	
	public String executeMethod( HttpUriRequest method) throws Exception {
		CloseableHttpResponse httpResponse=null;
		
		try {
			httpResponse = httpClient.execute(method,context);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			InputStream stream = httpResponse.getEntity().getContent();
			String response = getResponse(stream);
			displayResponse(method.getURI().toString(), statusCode, response);
			
			if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_NO_CONTENT && statusCode != HttpStatus.SC_CREATED ) {
				throw new RuntimeException ("Method failed: " + httpResponse.getStatusLine());
			  
			}
			return response;
		}catch (ClientProtocolException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			throw e;
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			throw e;
		} finally {
			if (httpResponse!= null) httpResponse.close();
		}
	}

	
	public String executeGetMethod(String url,List<NameValuePair> nvps) throws Exception {
		return executeMethod(new HttpGet(buildCompleteUrl(url,nvps)));
	}
	
	public String executeDeleteMethod(String url,List<NameValuePair> nvps) throws Exception {
		return executeMethod(new HttpDelete(buildCompleteUrl(url,nvps)));
	}


	public String executeGetMethodAsJson(String url,List<NameValuePair> nvps)  throws Exception{
		
		return executeMethod(new HttpGet(buildCompleteUrl(url,nvps)));
	}
	
	
	public String executePostMethodAsJson(String url, List<NameValuePair> nvps) throws Exception {
		HttpPost postMethod = new HttpPost(buildCompleteUrl(url,nvps));
		return executeMethod(postMethod);
	}
	

	public String executePutMethodAsJson(String url, List<NameValuePair> nvps) throws Exception {
		HttpPut putMethod = new HttpPut(buildCompleteUrl(url,nvps));
		return executeMethod(putMethod);
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}

    public void close(){
    	try {
			httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public HttpHost getHost() {
		return host;
	}

	public void setHost(HttpHost host) {
		this.host = host;
	}

	public HttpClientContext getContext() {
		return context;
	}

	public void setContext(HttpClientContext context) {
		this.context = context;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}


}