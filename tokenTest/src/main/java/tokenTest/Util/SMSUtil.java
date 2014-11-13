package tokenTest.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.google.common.collect.Lists;

public class SMSUtil {

	private static final String url = "http://utf8.sms.webchinese.cn";

	public static int send(String mobile_code, String phoneNum) {

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(url);

		// client.getParams().setContentCharset("GBK");
		// client.getParams().setContentCharset("UTF-8");
		method.setHeader("ContentType",
				"application/x-www-form-urlencoded;charset=UTF-8");

		// System.out.println(mobile);

		String content = new String(mobile_code + "是您的验证码，切勿随意分享。");

		NameValuePair[] data = { new BasicNameValuePair("Uid", "rexpie"),
				new BasicNameValuePair("Key", "d6603cde51c6607c2cee"),
				new BasicNameValuePair("smsMob", phoneNum),
				new BasicNameValuePair("smsText", content), };

		try {

			method.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(data),
					"UTF-8"));
			CloseableHttpResponse response = client.execute(method);

			System.out.println("Response by SMSUtil");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String line = reader.readLine();
			System.out.println(line);
			return Integer.valueOf(line);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NumberFormatException e){
			e.printStackTrace();
		}
		return 0;
	}

	public static String genCode() {
		int code = Math.abs(ran.nextInt());
		String str = String.valueOf(code % Constants.MAX_VALIDATION_CODE);
		if (code < 1000) {
			str = '0' + str;
		}
		return str;
	}

	private static Random ran = new Random();

	public static int doValidate(String phoneNum, String code) {
		int retval = send(code, phoneNum);
		return retval;
	}
}