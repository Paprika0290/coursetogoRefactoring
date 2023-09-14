package com.coursetogo.controller.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coursetogo.dto.course.Direction15ResultDTO;
import com.coursetogo.dto.course.LocationDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class N_MapAPIController {
	
	@Value("${naver.api.map.client.id}")
	private String clientId;

	@Value("${naver.api.map.client.secret}")
	private String clientSecret;
	
	@ResponseBody
	@GetMapping("/map/getDirection")
	public String getDirectionOfCourse(@RequestParam("userId") String userId) {
		try {
            // 요청 URL 설정
            String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=126.92052117802075,37.55012643120129&goal=126.9218735210481,37.55010931375865&option=trafast";
            URL url = new URL(apiUrl);

            // HTTP 연결
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // 요청 헤더 설정
    		connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            connection.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // 요청 후 응답 확인
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                System.out.println(jsonObject.get("route"));
                


                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode nodes = objectMapper.readTree(response.toString());

                    // BBOX 값 (경로 총 너비) 추출
                    JsonNode bboxNode = nodes.at("/route/trafast/0/summary/bbox");
                    // PATH 값 (경로를 이루는 위도,경도값) 추출
                    JsonNode pathNode = nodes.at("/route/trafast/0/path");
                    
                    System.out.println("BBOX: " + bboxNode);
                    System.out.println(bboxNode.get(0).get(0));
                    System.out.println(bboxNode.get(0).get(1));
                    System.out.println(bboxNode.get(1).get(0));
                    System.out.println(bboxNode.get(1).get(1));
                    
                    System.out.println(bboxNode.size());
                    
                    Direction15ResultDTO result = null;
                    LocationDTO location = null;
                    LocationDTO[] bbox = new LocationDTO[bboxNode.size()];
                    LocationDTO[] path = new LocationDTO[pathNode.size()];
                    
                    for(int i = 0; i < bboxNode.size(); i++) {
                    	bbox[i] = new LocationDTO(bboxNode.get(i).get(0).asDouble(),bboxNode.get(i).get(1).asDouble());
                    	System.out.println("bbox : " + bbox[i]);
                    }

                    for(int i = 0; i < pathNode.size(); i++) {
                    	path[i] = new LocationDTO(pathNode.get(i).get(0).asDouble(),pathNode.get(i).get(1).asDouble());
                    	System.out.println("path : " + path[i]);
                    }
                    

            

                } catch (Exception e) {
                    e.printStackTrace();
                }
                

                return "";
      
            } else {
                System.out.println("HTTP 요청 실패: " + responseCode);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
    }
	
}
