package com.coursetogo.controller.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coursetogo.dto.course.CourseDirectionDTO;
import com.coursetogo.dto.course.Direction15ResultDTO;
import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.service.map.PlaceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class N_MapAPIController {
	
	@Value("${naver.api.map.client.id}")
	private String clientId;

	@Value("${naver.api.map.client.secret}")
	private String clientSecret;
	
	@Autowired
	private PlaceService placeService;
	
	// 출발지 -도착지의 [위도,경도]를 입력받으면 경로를 출력하는 메서드
	public double[][] getDirectionOf2points(double[] startPoint, double[] endPoint) {
		
		try {
            // 요청 URL 설정 : location 위도,경도 순서 - [위도, 경도] / URL 순서 - [경도, 위도]
            String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start="+ startPoint[1] + "," + startPoint[0] +
            				"&goal="+ endPoint[1] + "," + endPoint[0] + "&option=trafast";
            System.out.println(apiUrl);
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
                
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode nodes = objectMapper.readTree(response.toString());

                    // BBOX 값 (경로 총 너비) 추출
//                    JsonNode bboxNode = nodes.at("/route/trafast/0/summary/bbox");
                    // PATH 값 (경로를 이루는 위도,경도값) 추출
                    JsonNode pathNode = nodes.at("/route/trafast/0/path");
                    
//                    double[][] bboxArray = new double[bboxNode.size()][2];
                    double[][] pathArray = new double[pathNode.size()][2];
                    
//                    for(int i = 0; i < bboxNode.size(); i++) {
//                    	bboxArray[i][0] = bboxNode.get(i).get(0).asDouble();
//                    	bboxArray[i][1] = bboxNode.get(i).get(1).asDouble();
//                    }

                    for(int i = 0; i < pathNode.size(); i++) {
                    	pathArray[i][0] = pathNode.get(i).get(0).asDouble();
                    	pathArray[i][1] = pathNode.get(i).get(1).asDouble();
                    }
                    
                    return pathArray;
            
                } catch (Exception e) {
                    e.printStackTrace();
                }
      
            } else {
                System.out.println("HTTP 요청 실패: " + responseCode);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return null;
	}
	
	
	
	@ResponseBody
	@GetMapping("/map/getDirection")
	public Direction15ResultDTO getDirectionOfCourseByObject(@RequestParam("places") String places, // placeId의 나열 
															 @RequestParam("placeCount") String placeCount) { // place의 총갯수
		String[] placeIdArray = places.split(",");
		CourseDirectionDTO courseDirection = null;
		PlaceDTO[] placeDTOArray = new PlaceDTO[2];
		
		if(Integer.parseInt(placeCount) == 1) {
			// 장소가 1개일 때 : startLocation(double[]) 뿐인 객체 전달
			placeDTOArray[0] = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[0]));
			courseDirection = CourseDirectionDTO.builder().oneLocation(new double[] {placeDTOArray[0].getLatitude(), placeDTOArray[0].getLongitude()}).build();
			
		}else if(Integer.parseInt(placeCount) == 2) {
			// 장소가 2개일 때 : stopOverLocations 필드만 빠짐
			for(int i = 0; i < 2; i++) {
				placeDTOArray[i] = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i]));
			}
			
			courseDirection = CourseDirectionDTO.builder().startLocation(new double[] {placeDTOArray[0].getLatitude(), placeDTOArray[0].getLongitude()})
														  .endLocation(new double[] {placeDTOArray[1].getLatitude(), placeDTOArray[1].getLongitude()})
														  .build();
			
		}else {
			// 장소가 3개 이상일 때
			double[][] stopOverLocations = new double[placeIdArray.length-2][2];
			PlaceDTO place = null;

			for(int i = 1; i < Integer.parseInt(placeCount)-1; i++) {
				try {
					place = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i])); 
					stopOverLocations[i][0] = place.getLatitude();
					stopOverLocations[i][1] = place.getLongitude();			
				} catch (Exception e) {
					log.warn("getDirection - 장소 조회 실패");
					e.printStackTrace();
				}
			}
				
				CourseDirectionDTO courseDirection = CourseDirectionDTO.builder().startLocation(new double[] {Double.parseDouble(startEndLocation[1]),Double.parseDouble(startEndLocation[0])})
																				 .endLocation(new double[] {Double.parseDouble(startEndLocation[3]),Double.parseDouble(startEndLocation[2])})
																				 .stopOverLocation(stopOverLocations)
																				 .build();
				
				for(int i = 0; i < placeIdArray.length; i++) {
					// 출발지 ->2번째 path
					if (i == 0) {
						courseDirection.setTotalPath(getDirectionOf2points(courseDirection.getStartLocation(), stopOverLocations[i]));
					}else {
						courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()), Arrays.stream(getDirectionOf2points(courseDirection.getStartLocation(), stopOverLocations[i])))
         					   							.toArray(int[][]::new);); 
					}
					
				}
		}
		}

		
		
		
		
		
		
		
		
		
//
//		for(int i = 0; i < Integer.parseInt(placeCount)-1; i++) {
//			
//			if(i == 0) {
//				Direction15ResultDTO stopOverDirection  = getDirectionOf2points(new Double[] {placeDTOList.get(i).getLatitude(), placeDTOList.get(i).getLongitude()},
//						  														new Double[] {placeDTOList.get(i+1).getLatitude(), placeDTOList.get(i+1).getLongitude()});
//				
//				result.setPathArray(stopOverDirection.getPathArray());
//				System.out.println("++++++++++++++++++++" + "첫번째~2번째" + "++++++++++++++++++++");
//				System.out.println(result.getPathArray());
//			}else {
//				Direction15ResultDTO stopOverDirection  = getDirectionOf2points(new Double[] {placeDTOList.get(i).getLatitude(), placeDTOList.get(i).getLongitude()},
//						  new Double[] {placeDTOList.get(i+1).getLatitude(), placeDTOList.get(i+1).getLongitude()});
//				
//				result.setPathArray(Stream.concat(Arrays.stream(result.getPathArray()), Arrays.stream(stopOverDirection.getPathArray()))
//				.toArray(double[][]::new));
//				System.out.println("++++++++++++++++++++" +  (i+1) + "번째~" +  (i+2) + "번째" + "++++++++++++++++++++");
//				System.out.println(result.getPathArray());
//			}
//		}
		
		
		
//		System.out.println("start" +  + "," + startLocation[0]);
//		System.out.println("end" + endLocation[1] + "," + endLocation[0]);
		
		return result;
    }
	
	
	// 해당 코스 정보 영역 - 코스 경로를 Naver Direction15를 통해 수신, 페이지로 경로 전달 (동기 방식으로 진행하려 했으나, 비동기 방식으로 진행 시도하기로 변경)	
//	public Direction15ResultDTO getDirectionOfCourse(double[] startLocation, double[] endLocation) {
//		// 최종적으로 반환될 Direction(경로) 객체
//		Direction15ResultDTO result = null;
//		
//		try {
//            // 요청 URL 설정
//            String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start=126.92052117802075,37.55012643120129&goal=126.9218735210481,37.55010931375865&option=trafast";
//            URL url = new URL(apiUrl);
//
//            // HTTP 연결
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            // 요청 헤더 설정
//    		connection.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
//            connection.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);
//            
//            // 요청 후 응답 확인
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String line;
//                
//                while ((line = br.readLine()) != null) {
//                    response.append(line);
//                }
//                
//                br.close();
//                
//                try {
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    JsonNode nodes = objectMapper.readTree(response.toString());
//
//                    // BBOX 값 (경로 총 너비) 추출
//                    JsonNode bboxNode = nodes.at("/route/trafast/0/summary/bbox");
//                    // PATH 값 (경로를 이루는 위도,경도값) 추출
//                    JsonNode pathNode = nodes.at("/route/trafast/0/path");
//                    
//                    double[][] bboxArray = new double[bboxNode.size()][2];
//                    double[][] pathArray = new double[pathNode.size()][2];
//                    
//                    for(int i = 0; i < bboxNode.size(); i++) {
//                    	bboxArray[i][0] = bboxNode.get(i).get(0).asDouble();
//                    	bboxArray[i][1] = bboxNode.get(i).get(1).asDouble();
//                    }
//
//                    for(int i = 0; i < pathNode.size(); i++) {
//                    	pathArray[i][0] = pathNode.get(i).get(0).asDouble();
//                    	pathArray[i][1] = pathNode.get(i).get(1).asDouble();
//                    }
//                    
//                    result = new Direction15ResultDTO(bboxArray, pathArray);
//                    return result;
//            
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//      
//            } else {
//                System.out.println("HTTP 요청 실패: " + responseCode);
//            }
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//		
//		return result;
//    }
	
	
	
	
}
