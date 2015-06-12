package com.spudi.sp.restful.client.utils;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class SPHttpClient {

	public static CloseableHttpClient getCloseableNTLMHttpClient(String username, String password, String workstation,String domain){
		CloseableHttpClient closeableHttpClient=null;
		Credentials credentials=new NTCredentials(username, password, workstation, domain);
		AuthScope authScope=new AuthScope(AuthScope.ANY);
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(authScope,credentials);
		closeableHttpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
		return closeableHttpClient;
	}

}
