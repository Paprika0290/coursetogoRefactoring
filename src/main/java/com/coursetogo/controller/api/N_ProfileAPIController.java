package com.coursetogo.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import com.coursetogo.dto.user.CtgUserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 접근 토큰을 전달받아 user 객체를 돌려주는 컨트롤러
@Controller
public class N_ProfileAPIController {

	@Value("${naver.api.member.profile.apiURL}")			
	private String memberProfileApiURL;
	
	// 1. apiURL을 통해 httpURLConnection 객체를 불러오는 static 메서드
    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }	
	
    // 2. get 메서드(4) 에서 사용할 readBody 메서드
    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }	
	
    // 3. apiURL을 이용해 서버와 연결하여 header와 body의 값을 String으로 받아옴
    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }    

    // 4. 접근 토큰 JSON객체를 받아 User테이블에 insert 후 user 객체를 돌려주는 메서드
	public CtgUserDTO getMemberProfile(JSONObject jsonOb) {
		
		String accessToken = "";
			 
		accessToken = jsonOb.getString("access_token");
		String header = "Bearer " + accessToken;
		  
		Map<String, String> requestHeaders = new HashMap<>();
		requestHeaders.put("Authorization", header);
		String responseBody = get(memberProfileApiURL,requestHeaders);
		  
		JSONObject jsonObject = new JSONObject(responseBody.toString());
		  
		ObjectMapper objMapper = new ObjectMapper();
		  
		  
		CtgUserDTO user = null;
		try {
			user = objMapper.readValue(jsonObject.get("response").toString(), CtgUserDTO.class);
		} catch (JsonProcessingException | JSONException e) {
			e.printStackTrace();
		}	
		
		return user;
	}    
}
