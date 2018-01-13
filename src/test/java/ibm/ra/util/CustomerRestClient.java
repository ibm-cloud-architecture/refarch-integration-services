package ibm.ra.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import po.dto.model.CustomerAccount;
import po.dto.model.ProductDTO;




public class CustomerRestClient extends RestClient {
	protected String hostName;

	protected static Properties props = new Properties();
	protected static Gson parser = new GsonBuilder()
			   .setDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz").create();
			   
	static {
		InputStream fin=CustomerRestClient.class.getClassLoader().getResourceAsStream("env.properties");
		 try {
			props.load(fin);
		} catch (IOException e) {
			e.printStackTrace();
			props.setProperty("customerms.host", "172.16.40.131");
		}
	}

	public CustomerRestClient(){
		init();
	}

	private void init(){
		this.setPort(Integer.parseInt(props.getProperty("port")));
		this.setProtocol(props.getProperty("protocol"));
		this.setBaseUrl(props.getProperty("customerms.webcontext")+props.getProperty("customerms.baseapi"));
		setHost( new HttpHost(props.getProperty("customerms.host"),this.getPort(),props.getProperty("protocol")));
        setHttpClient(HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).build());
	}

	public CustomerRestClient(String name,int port){
		this.setPort(port);
		props.setProperty("customerms.host", name);
		init();
	}
	

	public CustomerRestClient(Properties p){
		props=p;
		init();
	}

	public  Properties getProps() {
		return props;
	}

	public  void setProps(Properties props) {
		CustomerRestClient.props = props;
	}
	
	
	 @Override
    public String executeGetMethod(String url,List<NameValuePair> nvps) throws Exception {
    	HttpGet request = new HttpGet(buildCompleteUrl(url,nvps));
    	request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    	request.setHeader(HttpHeaders.ACCEPT, "application/json");
		return executeMethod(request);
	}
	 
	
	public String executePostMethodAsJson(String url,String json) throws Exception {
		HttpPost postMethod = new HttpPost(buildCompleteUrl(url,null));
		postMethod.setHeader("Accept", "application/json");
		postMethod.setHeader("Content-type", "application/json");
		postMethod.setEntity(new StringEntity(json));
		return executeMethod(postMethod);
	}
		
	public String executeCustomerPost(String url,CustomerAccount c) throws Exception {
		String s= parser.toJson(c);
		return executePostMethodAsJson(url,s);
	}
	
	public String executeProductPost(String url,ProductDTO c) throws Exception {
		String s= parser.toJson(c);
		return executePostMethodAsJson(url,s);
	}
	
	
	public String executePutMethodAsJson(String url,String json) throws Exception {
		HttpPut postMethod = new HttpPut(buildCompleteUrl(url,null));
		postMethod.setHeader("Accept", "application/json");
		postMethod.setHeader("Content-type", "application/json");
		postMethod.setEntity(new StringEntity(json));
		return executeMethod(postMethod);
	}
	
	public String executeCustomerPut(String url,CustomerAccount c) throws Exception {
		String s= parser.toJson(c);
		return executePutMethodAsJson(url,s);
	}

	public String getHostName() {
		return hostName;
	}

	public  Gson getParser() {
		return parser;
	}
	
}
