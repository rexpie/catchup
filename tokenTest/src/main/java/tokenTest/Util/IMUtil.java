package tokenTest.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.MissingNode;
import org.codehaus.jackson.node.ObjectNode;

import tokenTest.model.User;

import com.google.common.collect.Lists;

public class IMUtil {

	private static final String RONG_BASE_URL = "https://api.cn.rong.io/";
	private static final String APP_KEY = "mgb7ka" + "1nbhm7g";
	private static final String APP_SEC = "ErlBNo" + "ATzdj" + "lMf";

	private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

	private static CloseableHttpClient httpclient = HttpClients.createDefault();

	public static String getToken(Long id, String nick, String picURL) {
		return getToken(String.valueOf(id), nick, picURL);
	}

	public static String getToken(String id, String nick, String picURL) {

		final List<NameValuePair> nameValuePairs = Lists.newArrayList();
		nameValuePairs.add(new BasicNameValuePair("userId", id));
		nameValuePairs.add(new BasicNameValuePair("name", nick));
		nameValuePairs.add(new BasicNameValuePair("portraitUri", picURL));
		String URLString = RONG_BASE_URL + "user/getToken.json";

		String result = "";
		JsonNode tree = _sendRequest(URLString, nameValuePairs);

		if (tree.get("code").asInt() == 200) {
			result = tree.get("token").asText();
		}

		return result;
	}

	public static Status startSystemDelegateConversation(String fromUserName, Long toUserId,
			String content) {
		return startTextConversation(String.valueOf(Constants.MSG_SYSTEM_UID),
				String.valueOf(toUserId), String.format(content, fromUserName));
	}

	public static Status startSystemConversation(Long toUserId,
			String content) {
		return startTextConversation(String.valueOf(Constants.MSG_SYSTEM_UID),
				String.valueOf(toUserId), content);
	}

	public static Status startTextConversation(Long fromUserId, Long toUserId,
			String content) {
		return startTextConversation(String.valueOf(fromUserId),
				String.valueOf(toUserId), content);
	}

	public static Status startTextConversation(String fromUserId,
			String toUserId, String content) {
		String contentString = "{\"content\":\"" + content + "\"}";
		return startConversation(fromUserId, toUserId, "RC:TxtMsg",
				contentString);
	}

	public static Status startConversation(String fromUserId, String toUserId,
			String objectName, String content) {

		final List<NameValuePair> nameValuePairs = Lists.newArrayList();

		nameValuePairs.add(new BasicNameValuePair("fromUserId", fromUserId));
		nameValuePairs.add(new BasicNameValuePair("toUserId", toUserId));
		nameValuePairs.add(new BasicNameValuePair("objectName", objectName));
		nameValuePairs.add(new BasicNameValuePair("content", content));

		String URLString = RONG_BASE_URL + "message/publish.json";

		JsonNode tree = _sendRequest(URLString, nameValuePairs);
		return Status.get(tree.get("code").asInt());
	}

	private static JsonNode _sendRequest(String URLString,
			final List<NameValuePair> nameValuePairs) {
		try {

			StringBuilder resultBuilder = new StringBuilder();

			long time = new Date().getTime() / 1000;
			String rand = rand();

			HttpPost httpPost = new HttpPost(URLString);

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					nameValuePairs);

			httpPost.setHeader("App-Key", APP_KEY);
			httpPost.setHeader("Nonce", rand);
			httpPost.setHeader("Timestamp", String.valueOf(time));
			httpPost.setHeader("Signature", getSignature(APP_SEC, rand, time));
			httpPost.setHeader("Content-Type",
					"application/x-www-form-urlencoded");

			httpPost.setEntity(entity);

			CloseableHttpResponse response = httpclient.execute(httpPost);

			HttpEntity responseEntity = response.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					responseEntity.getContent(), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resultBuilder.append(line).append(
						System.getProperty("line.separator"));
			}
			reader.close();

			JsonNode tree = JSON_MAPPER.readTree(resultBuilder.toString());

			// do something useful with the response body
			// and ensure it is fully consumed
			EntityUtils.consume(responseEntity);

			return tree;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return MissingNode.getInstance();
	}

	private static String getSignature(String appKey, String rand, long time) {
		return new String(Hex.encodeHex(DigestUtils.sha(appKey + rand
				+ String.valueOf(time))));
	}

	private static String rand() {
		return String.valueOf(Math.abs(RandomUtils.nextInt(100000)));
	}


	public static void notifyUser(User user, Enum message){
		// TODO
	}
}
