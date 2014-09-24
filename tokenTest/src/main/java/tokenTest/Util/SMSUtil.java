package tokenTest.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.google.common.collect.Lists;

public class SMSUtil {
	
	private static final String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	public static void send(String mobile_code) {
		
        CloseableHttpClient client = HttpClients.createDefault();
		HttpPost method = new HttpPost(Url); 
			
		//client.getParams().setContentCharset("GBK");		
//		client.getParams().setContentCharset("UTF-8");
		method.setHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		
		//System.out.println(mobile);
		
	    String content = new String("������֤���ǣ�" + mobile_code + "���벻Ҫ����֤��й¶�������ˡ�"); 

		NameValuePair[] data = {//�ύ����
			    new BasicNameValuePair("account", "�û���"), 
			    new BasicNameValuePair("password", "����"), //�������ʹ�����������ʹ��32λMD5����
			    //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
			    new BasicNameValuePair("mobile", "�ֻ�����"), 
			    new BasicNameValuePair("content", content),
		};
		
		try {

			method.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(data),"UTF-8"));
			CloseableHttpResponse response = client.execute(method);	
			
			String SubmitResult = response.getEntity().toString();
			//System.out.println(SubmitResult);

			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			String code = root.elementText("code");	
			String msg = root.elementText("msg");	
			String smsid = root.elementText("smsid");	
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
						
			if(code == "2"){
				System.out.println("�����ύ�ɹ�");
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		
	}
	
	public static String genCode(){
		int code = Math.abs(ran.nextInt());
		String str = String.valueOf(code % Constants.MAX_VALIDATION_CODE);
		if (code < 1000){
			str = '0' + str;
		}
		return str;
	}
	
	private static Random ran = new Random();
	
}