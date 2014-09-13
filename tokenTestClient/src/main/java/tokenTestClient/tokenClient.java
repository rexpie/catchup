package tokenTestClient;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

public class tokenClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token = "";
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> urlVariables = new HashMap<String, Object>();
		urlVariables.put("nickName", "peach");
		urlVariables.put("passWord", "123456");
		LoginResponse response = restTemplate.getForObject("http://localhost:8080/tokenTest/login",
				LoginResponse.class, urlVariables);
		if(response==null){
			System.out.println("login failed.");
		}else{
			token=response.getToken();
			System.out.println("login successed. Token is:"+token);
		}
		
	}

}
