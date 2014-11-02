/*
 * Create Author  : xiaopeng.li
 * Create Date     : 2013-1-23
 * Project            : dianping-java-samples
 * File Name        : DemoApiTool.java
 *
 * Copyright (c) 2010-2015 by Shanghai HanTao Information Co., Ltd.
 * All rights reserved.
 *
 */
package tokenTest.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tokenTest.model.Shop;

/**
 * Java鐗堟湰绀轰緥浠ｇ爜锛屼娇鐢ㄨ{@link DemoApiToolTest.java}
 * <p>
 * 
 * @author : xiaopeng.li
 *         <p>
 * @version 1.0 2013-1-23
 * @since dianping-java-samples 1.0
 */
public class DPApiTool {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DPApiTool.class);
	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
	private static final String OK = "OK";

	public static String sign(String appKey, String secret,
			Map<String, String> paramMap) {
		// 瀵瑰弬鏁板悕杩涜瀛楀吀鎺掑簭
		String[] keyArray = paramMap.keySet().toArray(new String[0]);
		Arrays.sort(keyArray);

		// 鎷兼帴鏈夊簭鐨勫弬鏁板悕-鍊间覆
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(appKey);
		for (String key : keyArray) {
			stringBuilder.append(key).append(paramMap.get(key));
		}

		stringBuilder.append(secret);
		String codes = stringBuilder.toString();

		// SHA-1缂栫爜锛�杩欓噷浣跨敤鐨勬槸Apache
		// codec锛屽嵆鍙幏寰楃鍚�shaHex()浼氶鍏堝皢涓枃杞崲涓篣TF8缂栫爜鐒跺悗杩涜sha1璁＄畻锛屼娇鐢ㄥ叾浠栫殑宸ュ叿鍖呰娉ㄦ剰UTF8缂栫爜杞崲)
		/*
		 * 浠ヤ笅sha1绛惧悕浠ｇ爜鏁堟灉绛夊悓 byte[] sha =
		 * org.apache.commons.codec.digest.DigestUtils
		 * .sha(org.apache.commons.codec
		 * .binary.StringUtils.getBytesUtf8(codes)); String sign =
		 * org.apache.commons
		 * .codec.binary.Hex.encodeHexString(sha).toUpperCase();
		 */
		String sign = org.apache.commons.codec.digest.DigestUtils.shaHex(codes)
				.toUpperCase();

		return sign;
	}

	public static String getQueryString(String appKey, String secret,
			Map<String, String> paramMap) {
		String sign = sign(appKey, secret, paramMap);

		// 娣诲姞绛惧悕
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("appkey=").append(appKey).append("&sign=")
				.append(sign);
		for (Entry<String, String> entry : paramMap.entrySet()) {
			stringBuilder.append('&').append(entry.getKey()).append('=')
					.append(entry.getValue());
		}
		String queryString = stringBuilder.toString();
		return queryString;
	}

	public static String requestApi(String apiUrl, String appKey,
			String secret, Map<String, String> paramMap) {
		String queryString = getQueryString(appKey, secret, paramMap);

		StringBuffer responseStr = new StringBuffer();
//		HttpClientParams httpConnectionParams = new HttpClientParams();
//		httpConnectionParams.setConnectionManagerTimeout(1000);
//		HttpClient client = new HttpClient(httpConnectionParams);
//		HttpMethod method = new GetMethod(apiUrl);
//		
		HttpGet httpget = null;
		try {
			if (StringUtils.isNotBlank(queryString)) {
				// Encode query string with UTF-8
				String encodeQuery = URIUtil.encodeQuery(queryString, "UTF-8");
				httpget = new HttpGet(apiUrl + "?" + encodeQuery);
				System.out.println("URL:" + httpget.getURI());
				httpget.setHeader("accept", "application/json");
			}

			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				responseStr.append(line).append(
						System.getProperty("line.separator"));
			}
			reader.close();
		} catch (URIException e) {
			LOGGER.error("Can not encode query: " + queryString
					+ " with charset UTF-8. ", e);
		} catch (IOException e) {
			LOGGER.error("Request URL: " + apiUrl + " failed. ", e);
		} finally {
			httpget.completed();
		}
		return responseStr.toString();

	}

	/*public static String requestPostApi(String apiUrl, String appKey,
			String secret, Map<String, String> paramMap) {
		StringBuffer response = new StringBuffer();
		HttpClientParams httpConnectionParams = new HttpClientParams();
		httpConnectionParams.setConnectionManagerTimeout(1000);
		HttpClient client = new HttpClient(httpConnectionParams);
		PostMethod method = new PostMethod(apiUrl);

		try {
			String sign = sign(appKey, secret, paramMap);
			paramMap.put("sign", sign);
			paramMap.put("appkey", appKey);
			// 璁剧疆HTTP Post鏁版嵁
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				method.addParameter(entry.getKey(), entry.getValue());
			}
			method.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			client.executeMethod(method);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					method.getResponseBodyAsStream(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				response.append(line).append(
						System.getProperty("line.separator"));
			}
			reader.close();
		} catch (IOException e) {
			LOGGER.error("Request URL: " + apiUrl + " failed. ", e);
		} finally {
			method.releaseConnection();
		}
		return response.toString();

	}*/

	
	public static Shop getBusiness(Long id) {
		return getBusiness(String.valueOf(id));
	}
	
	public static Shop getBusiness(String id) {
		/*
		 * http://api.dianping.com/v1/business/get_single_business?
		 * appkey=5912365198&sign=7B4B6D5F21A7A2340B6073CAD6302EB0986EFA47
		 * &business_id=5912365198&out_offset_type=1&platform=2
		 */

		String apiUrl = "http://api.dianping.com/v1/business/get_single_business";

		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("business_id", id);
		paramMap.put("out_offset_type", "1");
		paramMap.put("platform", "2");

		String requestResult = requestApi(apiUrl, Constants.DP_API_KEY,
				Constants.DP_API_SEC, paramMap);
		System.out.println(requestResult);

		Shop shop = null;
		try {
			JsonNode tree = JSON_MAPPER.readTree(requestResult);
			
			String status = tree.get("status").asText();
			int count = tree.get("count").asInt();
			
			if (!StringUtils.equals(status, OK) || count <= 0){
				return shop;
			}
			
			JsonNode business = tree.get("businesses").get(0);
			
			shop = new Shop();
			
			shop.setId(business.get("business_id").asLong());
			shop.setName(business.get("name").asText());
			shop.setDianping_id(business.get("business_id").asText());
			shop.setCity(business.get("city").asText());
			shop.setPhotoUrl(business.get("s_photo_url").asText());
			shop.setPrice(business.get("avg_price").asDouble());
			shop.setLongitude(business.get("longitude").asDouble());
			shop.setLatitude(business.get("latitude").asDouble());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return shop;
		}

		return shop;
	}
}
