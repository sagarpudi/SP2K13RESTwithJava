package com.spudi.sp.restful.client.utils;

import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

public class Encoder{

	
	public static String encodeUrl(String siteUrl) {

		try {
			URL url = new URL(siteUrl);
			StringBuffer sb = new StringBuffer();
			int port = url.getPort();
			boolean appendPort = true;
			if (port == -1) {
				appendPort = false;
			}
			sb.append(url.getProtocol() + "://" + url.getHost());
			if (appendPort) {
				sb.append(":" + port);
			}
			if (StringUtils.isNotBlank(url.getPath())) {
				String encodedPath = URLEncoder.encode(
						StringUtils.removeStart(url.getPath(), "/"), "UTF-8");
				sb.append("/" + encodedPath);
			}

			String encodedUrl = sb.toString();
			encodedUrl = StringUtils.replace(encodedUrl, "+", "%20");

			return encodedUrl;
		} catch (Exception ex) {
			String exception = "Could not encode given siteUrl - " + siteUrl
					+ ". The exception is - " + ex.getMessage();
		System.out.println(exception);
			throw new RuntimeException(exception);
		}
	}
}
