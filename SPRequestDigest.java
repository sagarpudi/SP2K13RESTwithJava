package com.spudi.sp.restful.client.utils;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class SPRequestDigest {

	public static String getDigest(String spSite,String username,String password,String workstation,String domain) {
		String formDigestValue=null;

		CloseableHttpClient httpclient = SPHttpClient.getCloseableNTLMHttpClient(username, password, workstation, domain);

		if(httpclient!=null){

			String requestURI=spSite+"/_api/contextinfo";
			System.out.println("requestURI:"+requestURI);
			HttpPost httppost = new HttpPost(requestURI); 
			httppost.addHeader("Accept", "application/json;odata=verbose");

			CloseableHttpResponse response=null;

			try {
				System.out.println("Executing request " + httppost.getRequestLine());
				response = httpclient.execute(httppost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 


			//after getting response
			if(response!=null){

				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				System.out.println("----------------------------------------");


				try {
					byte[] content = EntityUtils.toByteArray(response.getEntity());
					String jsonString = new String(content, "UTF-8");
					JSONObject json = new JSONObject(jsonString);
					formDigestValue = json.getJSONObject("d").getJSONObject("GetContextWebInformation").getString("FormDigestValue").trim();
					EntityUtils.consume(response.getEntity());
				} catch (IOException e) {
					e.printStackTrace();
				}finally{

					if(response!=null){
						try {
							response.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if(httpclient!=null){
						try {
							httpclient.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				return formDigestValue;

			}

		}
		return formDigestValue;
	} //end of getDigest()
}

