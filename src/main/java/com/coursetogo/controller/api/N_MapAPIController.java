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

import com.coursetogo.controller.map.NaverMapController;
import com.coursetogo.dto.course.CourseDirectionDTO;
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
	
	@Autowired
	private NaverMapController mapController;
	
	// 출발지 -도착지의 [위도,경도]를 입력받으면 경로를 출력하는 메서드
	public double[][] getDirectionOf2points(double[] startPoint, double[] endPoint) {
		
		try {
            // 요청 URL 설정 : location 위도,경도 순서 - [위도, 경도] / URL 순서 - [경도, 위도]
            String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving?start="+ startPoint[1] + "," + startPoint[0] +
            				"&goal="+ endPoint[1] + "," + endPoint[0] + "&option=trafast";

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

                    // PATH 값 (경로를 이루는 위도,경도값) 추출
                    JsonNode pathNode = nodes.at("/route/trafast/0/path");
                    
                    double[][] pathArray = new double[pathNode.size()][2];

                    for(int i = 0; i < pathNode.size(); i++) {
                    	pathArray[i][1] = pathNode.get(i).get(0).asDouble();
                    	pathArray[i][0] = pathNode.get(i).get(1).asDouble();
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
	
	
	// 코스 MAP 화면에 direction을 표시하기 위해 필요한 CourseDirectionDTO를 반환하는 메서드
	@ResponseBody
	@GetMapping("/map/getDirection")
	public CourseDirectionDTO getDirectionOfCourseByObject(@RequestParam("places") String places) {// placeId의 나열 
		// 최종적으로 반환할 courseDirectionDTO
		CourseDirectionDTO courseDirection = null;
		
		String[] placeIdArray = places.split(",");
		PlaceDTO[] placeDTOArray = new PlaceDTO[2];
		PlaceDTO place = null;
		
		if(placeIdArray.length == 1) {
							// 장소가 1개일 때 : startLocation(double[]) 뿐인 객체 전달
							try {
								placeDTOArray[0] = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[0]));
							} catch (Exception e) {
								log.warn("장소 1개 - 장소조회 실패");
								e.printStackTrace();
							}
							courseDirection = new CourseDirectionDTO(new double[] {placeDTOArray[0].getLatitude(), placeDTOArray[0].getLongitude()});
							System.out.println(courseDirection);
			
		}else if(placeIdArray.length == 2) {
							// 장소가 2개일 때 : stopOverLocations 필드만 빠짐
							for(int i = 0; i < 2; i++) {
								try {
									placeDTOArray[i] = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i]));
								
								} catch (Exception e) {
									log.warn("장소 2개 - 장소조회 실패");
									e.printStackTrace();
								}
							}
				
							courseDirection = new CourseDirectionDTO(new double[] {placeDTOArray[0].getLatitude(), placeDTOArray[0].getLongitude()},
																	 new double[] {placeDTOArray[1].getLatitude(), placeDTOArray[1].getLongitude()});
							System.out.println(courseDirection);
		}else {
							// 장소가 3개 이상일 때
							double[][] stopOverLocations = new double[placeIdArray.length-2][2];
							double[][] totalLocations = new double[placeIdArray.length][2];
							PlaceDTO stopOverPlace = null;
							PlaceDTO startPlace = null;
							PlaceDTO endPlace = null;
							
							for(int i = 0; i < placeIdArray.length; i++) {
								if(i == 0) {
									try {
										startPlace = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i]));
										totalLocations[0] = new double[] {startPlace.getLatitude(), startPlace.getLongitude()};
									} catch (Exception e) {
										log.warn("장소 3개이상 - 출발지조회 실패");
										e.printStackTrace();
									}
								} else if(i == placeIdArray.length-1) {
									try {
										endPlace = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i]));
										totalLocations[placeIdArray.length-1] = new double[] {endPlace.getLatitude(), endPlace.getLongitude()};
									} catch (Exception e) {
										log.warn("장소 3개이상 - 도착지조회 실패");
										e.printStackTrace();
									}
								} else {
									try {
										stopOverPlace = placeService.getPlaceByPlaceId(Integer.parseInt(placeIdArray[i]));
									} catch (Exception e) {
										log.warn("장소 3개이상 - 경유지조회 실패");
										e.printStackTrace();
									}
									stopOverLocations[i-1][0] = stopOverPlace.getLatitude();
									stopOverLocations[i-1][1] = stopOverPlace.getLongitude();
									
									totalLocations[i][0] = stopOverPlace.getLatitude();
									totalLocations[i][1] = stopOverPlace.getLongitude();
									
								}	
							}
							
							courseDirection = new CourseDirectionDTO(new double[] {startPlace.getLatitude(), startPlace.getLongitude()},
																	 new double[] {endPlace.getLatitude(), endPlace.getLongitude()},
																	 stopOverLocations, totalLocations);
							
							courseDirection.setTotalLocations(totalLocations);
		}
			
			// totlaPath 만들기
			if(placeIdArray.length == 1) {
				// 코스 내 장소가 1개일 시
				
			}else if(placeIdArray.length == 2) {
				// 코스 내 장소가 2개일 시
				courseDirection.setTotalPath(getDirectionOf2points(courseDirection.getStartLocation(), courseDirection.getEndLocation()));

			}else {
				// 코스 내 장소가 3개 이상일 시			
				if(placeIdArray.length == 3) {
					courseDirection.setTotalPath(getDirectionOf2points(courseDirection.getStartLocation(), courseDirection.getStopOverLocations()[0]));
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[0],
																	   							   courseDirection.getEndLocation()))
															   ).toArray(double[][]::new)
												);

				}else if(placeIdArray.length == 4) {
					courseDirection.setTotalPath(getDirectionOf2points(courseDirection.getStartLocation(), courseDirection.getStopOverLocations()[0]));
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[0],
																	   							   courseDirection.getStopOverLocations()[1]))
															   ).toArray(double[][]::new)
												);
					
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[1],
																	   							   courseDirection.getEndLocation()))
															   ).toArray(double[][]::new)
												);		
				}else {
					courseDirection.setTotalPath(getDirectionOf2points(courseDirection.getStartLocation(), courseDirection.getStopOverLocations()[0]));
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[0],
																	   							   courseDirection.getStopOverLocations()[1]))
															   ).toArray(double[][]::new)
												);
					
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[1],
																	   							   courseDirection.getStopOverLocations()[2]))
															   ).toArray(double[][]::new)
												);		
					courseDirection.setTotalPath(Stream.concat(Arrays.stream(courseDirection.getTotalPath()),
															   Arrays.stream(getDirectionOf2points(courseDirection.getStopOverLocations()[2],
																	   							   courseDirection.getEndLocation()))
															   ).toArray(double[][]::new)
												);		
				}
				
				
			}
			
			courseDirection.setBorderLocations(mapController.getBorderLocations(courseDirection.getStartLocation(),
																				courseDirection.getEndLocation(),
																				courseDirection.getStopOverLocations()));
			
		return courseDirection;
    }
	

	
	
}
