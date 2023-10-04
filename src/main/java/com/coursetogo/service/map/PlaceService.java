package com.coursetogo.service.map;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.mapper.map.PlaceMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaceService {
	
	@Autowired
	PlaceMapper mapper;
	
	public PlaceDTO getPlaceByPlaceId(int placeId) throws Exception { 
		PlaceDTO placedto = mapper.getPlaceByPlaceId(placeId);
		return placedto; 
	}

	
	public List<PlaceDTO> getAllPlaces() throws SQLException{
		return mapper.getAllPlaces();
	}

	public List<PlaceDTO> searchPlacesByPlaceName(String placeName) throws SQLException{		
		return mapper.searchPlacesByPlaceName(placeName);
	}


	public List<PlaceDTO> searchPlaces(String placeName) throws SQLException{
		return mapper.searchPlaces(placeName);
	}


	public List<PlaceDTO> searchPlacesByArea(String areaName) throws SQLException{
		return mapper.searchPlacesByArea(areaName);
	}
	
	public List<PlaceDTO> searchPlacesByAreaWithPages(int pageNum,
													  String areaName) throws SQLException {
		List<PlaceDTO> res = new ArrayList<PlaceDTO>();
		
		int startRow = ((pageNum - 1) * 200) + 1;
		int endRow = ((pageNum - 1) * 200) + 200;
		
		res = mapper.searchPlacesByAreaWithPages(areaName, startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 장소(+페이지네이션) 검색 실패");
		}
		return res;
	}


	public List<PlaceDTO> searchPlacesByCategory(String categoryName) throws SQLException{
		return mapper.searchPlacesByCategory(categoryName);
	}


	public List<PlaceDTO> searchPlacesByAreaOrCategory(String areaName, String categoryName) throws SQLException{
		Map<String, String> params = new HashMap<>();
		params.put("categoryName", categoryName);
		params.put("areaName", areaName);
		return mapper.searchPlacesByAreaOrCategory(params);
	}


}