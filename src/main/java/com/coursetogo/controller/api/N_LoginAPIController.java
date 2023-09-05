package com.coursetogo.controller.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.controller.user.CtgUserController;
import com.coursetogo.dto.user.CtgUserDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class N_LoginAPIController {
	@Value("${naver.api.login.client.id}")
	private String clientId;	
	
	@Value("${naver.api.login.client.secret}")			
	private String clientSecret;
	
	@Value("${naver.api.login.callbackURL}")			
	private String callbackURL;	
	
	@Value("${naver.api.login.apiURL}")			
	private String apiURL;
	
	@Value("${naver.api.new.access.token.apiURL}")			
	private String newAccessTokenApiURL;
	
	@Autowired
	private N_ProfileAPIController profileController;
	
	@Autowired
	private CtgUserController userController;
	
	// naverLoginAPI를 요청하는 URL 생성하여 return (-> MainController로)
	public String getloginAPIUrl() {
		String redirectURI = "";
		
		try {
			redirectURI = URLEncoder.encode(callbackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		
		SecureRandom random = new SecureRandom();			
		String state = new BigInteger(130, random).toString();			
					
		apiURL += "&client_id=" + clientId;			
		apiURL += "&redirect_uri=" + redirectURI;			
		apiURL += "&state=" + state;	
		
		return apiURL;
	}
	
	@GetMapping("callback")
	public String getAccessToken(@RequestParam("code") String code,
							    @RequestParam("state") String state,
							    HttpSession session, Model model) {
		log.info("login 시도");
		String redirectURI = "";
		
		try {
			redirectURI = URLEncoder.encode(callbackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		  
		// 접근 토큰 발급 요청 URL
		String apiURL;
			apiURL = newAccessTokenApiURL;
			apiURL += "client_id=" + clientId;
			apiURL += "&client_secret=" + clientSecret;
			apiURL += "&redirect_uri=" + redirectURI;
			apiURL += "&code=" + code;
			apiURL += "&state=" + state;
			
		JSONObject accessToken = null;
		
		try {
		    URL url = new URL(apiURL);
		    HttpURLConnection con = (HttpURLConnection)url.openConnection();
		    con.setRequestMethod("GET");
		    int responseCode = con.getResponseCode();
		
		    BufferedReader br;           
		    	if(responseCode==200) {
		    		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		    	} else {
		    		br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		    	}
		
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
			  res.append(inputLine);
			}
			log.info("접근 토근 응답 json 확인 :" + res); // <- 응답받은 접근 '토큰 내용' 확인
		            
		    br.close();
			 
			if(responseCode==200) { // 정상 호출시 응답받은 토큰을 JSONObject로 변환    
		          accessToken = new JSONObject(res.toString());
		    }	    			            
		} catch (Exception e) {
		    System.out.println(e);
		}	
		
		CtgUserDTO user = profileController.getMemberProfile(accessToken);// 접근 토큰을 이용해 네이버에서 유저 프로필을 가져와 user 객체에 맵핑해 돌려주는 메서드		

		// 맵핑한 user객체정보로 DB접속, 기존 등록된 회원인지 확인
		CtgUserDTO searchUser = userController.getCtgUserByNaverIdAndName(user.getNaverId().substring(0, 10),
																   		  user.getNaverId().substring(user.getNaverId().length() -10),
																		  user.getUserName());
		
		// 등록된 회원이 아니면
		if (searchUser == null) {			
			session.setAttribute("newUser", user);
			log.info("비회원 접근");
			return "home";
		}else {		
		// 등록된 회원이라면	
			CtgUserDTO userForSession = CtgUserDTO.builder().userId(searchUser.getUserId())
															.userNickname(searchUser.getUserNickname())
															.userEmail(searchUser.getUserEmail())
															.userPhoto(searchUser.getUserPhoto())
															.userIntroduce(searchUser.getUserIntroduce())
															.build();
			session.setMaxInactiveInterval(3600);
			session.setAttribute("user", userForSession);	
			log.info("회원 접근");
		return "home";
	}
	
	
  }
}
