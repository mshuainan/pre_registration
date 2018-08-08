package com.elementspeed.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class UrlRequestUtil {
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String urlAddr = "http://10.6.10.158:8082/shopxxb2b2c/cacheReload/clear/clearConsultation";
		String json = UrlRequestUtil.send(urlAddr, null);
		System.out.println("result=" + json);
	}
	
	public static String send(String urlAddr, Map<String, String> paraMap) {
		StringBuffer params = new StringBuffer();
		if (paraMap != null && !paraMap.isEmpty()) {
			if (!urlAddr.contains("?")) {
				urlAddr += "?timeLong=" + new Date().getTime();
			}
			Iterator<Entry<String, String>> it = paraMap.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, String> element = (Entry<String, String>) it
						.next();
				params.append("&");
				params.append(element.getKey());
				params.append("=");
				params.append(element.getValue());
			}
		}
		String reqUrl = urlAddr + params.toString();
//		System.out.println(reqUrl);
		HttpURLConnection conn = null;
		try {
			URL url = new URL(reqUrl);
			conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-type", "text/html");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("contentType", "utf-8"); 
			// conn.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length",
					String.valueOf(params.length()));
			conn.setDoInput(true);
			conn.connect();

			OutputStream out = conn.getOutputStream();
			out.write(params.toString().getBytes());    // 写出数据

			out.flush();
			out.close();

			int code = conn.getResponseCode();
//			System.out.println("code===" + code);
			if (code != 200) {
				System.out.println("ERROR===" + code);
			} else {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader in = new BufferedReader(isr);
				String line = null;
				StringBuffer content = new StringBuffer();
				while ((line = in.readLine()) != null) {// line为返回值，这就可以判断是否成功、
					content.append(line);
					content.append("\n");
				}
				in.close();
				isr.close();
				is.close();
				return content.toString();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (null != conn) {
				conn.disconnect();
			}
		}
		return "";
	}
	
}
