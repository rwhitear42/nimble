package com.cloupia.feature.nimble.accounts.api;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.log4j.Logger;

import com.cloupia.feature.nimble.accounts.NimbleAccount;

public class NimbleAccountAPI {

	private static final HashMap<String, NimbleAccountAPI> instances = new HashMap<String, NimbleAccountAPI>();
	private static Logger logger = Logger.getLogger(NimbleAccountAPI.class);
	private String ipAddress;
	private String username;
	private String password;
	private String protocol;
	private int port;
	private int apiVersion = 1;
	
	private String token = null;
	/**
	 * THIS CONSTRUCTOR SHOULD NEVER BE USED, IT'S PRIVATE TO PREVENT ANYONE FROM ACCESS TO IT!
	 */
	private NimbleAccountAPI() {
	}
    /**
     *	private constructor with parameters.
	 */
	
	private NimbleAccountAPI(String ipAddress, String username, String password,int port,String protocol) {
		this.ipAddress = ipAddress;
		this.username = username;
		this.password = password;
		this.port = port;
		this.protocol =protocol;
	}
	 /**
	   * This method is used to get NimbleAccountAPI object 
	   * 
	   * @param NimbleAccount having the account details.
	   * @return NimbleAccountAPI.
	   */
	public static NimbleAccountAPI getNimbleAccountAPI(NimbleAccount account) throws Exception
	{
		return getInstanceFor(account.getServerAddress(), account.getLogin(),  account.getPassword(), 5392, account.getProtocol());
	}
      /**
	   * This method is used to get NimbleAccountAPI object 
	   * 
	   * @param ipAddress of account.
	   * @param username of account.
	   * @param password of account.
	   * @param protocol of account.
	   * @return NimbleAccountAPI.
	   */
	public static NimbleAccountAPI getInstanceFor(String ipAddress, String username, String password,int port,String protocol) throws Exception {
		NimbleAccountAPI api = instances.get(ipAddress+username+password+port);
		if (api == null) 
		{
			api = new NimbleAccountAPI(ipAddress, username, password,port,protocol);
			
			instances.put(ipAddress+username+password+port, api);
		}
		else
		{
			
		}
		return api;
	}
	/**
	* This method is used to get the HttpClient Object.
	* param no need.
	* return HttpClient
	*
	*/
	private HttpClient trustEveryoneSSLHttpClient() {
		try {
			SchemeRegistry registry = new SchemeRegistry();

			SSLSocketFactory socketFactory = new SSLSocketFactory(new TrustStrategy() {

				public boolean isTrusted(final X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}

			}, org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			registry.register(new Scheme(this.protocol, port, socketFactory));
			ThreadSafeClientConnManager mgr = new ThreadSafeClientConnManager(registry);
		
			HttpParams params = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(params, 30000);
			HttpConnectionParams.setSoTimeout(params, 30000);

			DefaultHttpClient client = new DefaultHttpClient(mgr);
			client.setParams(params);
			return client;
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	public String getInventoryData(String url) throws Exception 
	{
		return url;
		
	}
	/**
	 * This method is used to get full URL
	 * @param  url.
	 * @return URL
	 * 
	 */
	private String getCompleteUrl(String url)
	{
		return this.protocol+"://" + ipAddress+":"+port +"/"+url;
	}
	
	

}
