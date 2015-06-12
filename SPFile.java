package com.spudi.sp.restful.client;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.spudi.sp.restful.client.utils.Encoder;
import com.spudi.sp.restful.client.utils.SPHttpClient;

public class SPFile {

	public static String createFile(byte[] content,String targetURI,String username,String password,String workstation,String domain){
		String responseString=null;
		CloseableHttpClient httpclient = SPHttpClient.getCloseableNTLMHttpClient(username, password, workstation, domain);
		if(httpclient!=null){
			System.out.println("Before Encoding: "+targetURI);
			targetURI=Encoder.encodeUrl(targetURI);
			System.out.println("After Encoding: "+targetURI);
			HttpPut httpPut=new HttpPut(targetURI);
			//HttpPost httpPost=new HttpPost(targetURI);
			HttpEntity entity=new ByteArrayEntity(content);
			httpPut.setEntity(entity);

			CloseableHttpResponse response=null;
			try {
				response=httpclient.execute(httpPut);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//after getting response
			if(response!=null){
				System.out.println(response.getStatusLine());
				try {
					responseString = EntityUtils.toString(response.getEntity());
					EntityUtils.consume(response.getEntity());
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}


		}


		return responseString;
	}

}
