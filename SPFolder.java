package com.spudi.sp.restful.client;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.spudi.sp.restful.client.utils.SPHttpClient;
import com.spudi.sp.restful.client.utils.SPRequestDigest;

public class SPFolder {

	public static String createFolder(String spSite,String serverRelativeUrl,String username,String password,String workstation,String domain){
		String responseString=null;
		CloseableHttpClient httpclient = SPHttpClient.getCloseableNTLMHttpClient(username, password, workstation, domain);
		if(httpclient!=null){
		
		String requestURI=spSite+"/_api/web/folders";
		HttpPost httppost = new HttpPost(requestURI);
		httppost.addHeader("Accept", "application/json;odata=verbose");
		httppost.addHeader("Content-Type", "application/json;odata=verbose");
		
		CloseableHttpResponse response=null;
		try {

			final String formDigestValue=SPRequestDigest.getDigest(spSite,username,password,workstation,domain);
			httppost.addHeader("X-RequestDigest", formDigestValue);

			String jsonBodyOdata="{ \"__metadata\": { \"type\": \"SP.Folder\" }, \"ServerRelativeUrl\" :"+"\""+serverRelativeUrl+"\""+"}";
			StringEntity jsonEntity= new StringEntity(jsonBodyOdata);
			jsonEntity.setContentType("application/json;odata=verbose");
			httppost.setEntity(jsonEntity);
		
			
			System.out.println("Executing request " + httppost.getRequestLine());
			response = httpclient.execute(httppost);
			
			
			//after getting response
			if(response!=null){
				System.out.println(response.getStatusLine());
				responseString = EntityUtils.toString(response.getEntity());
				EntityUtils.consume(response.getEntity());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			//closing the httpclient
			if(httpclient!=null){
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//closing the response
			if(response!=null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		}
		return responseString;
	}

}
