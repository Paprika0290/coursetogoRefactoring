package com.coursetogo.service.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.mapper.map.PlaceMapper;

@Service
public class PlaceService {
	
	@Autowired
	PlaceMapper mapper;
	
	public PlaceDTO getPlaceByPlaceId(int placeId) throws Exception { 
		PlaceDTO placedto = mapper.getPlaceByPlaceId(placeId);
		return placedto; 
	}

	
	public List<PlaceDTO> getAllPlaces() {
		return mapper.getAllPlaces();
	}

	public List<PlaceDTO> searchPlacesByPlaceName(String placeName) {		
		return mapper.searchPlacesByPlaceName(placeName);
	}


	public List<PlaceDTO> searchPlaces(String placeName) {
		return mapper.searchPlaces(placeName);
	}


	public List<PlaceDTO> searchPlacesByArea(String areaName) {
		return mapper.searchPlacesByArea(areaName);
	}


	public List<PlaceDTO> searchPlacesByCategory(String categoryName) {
		return mapper.searchPlacesByCategory(categoryName);
	}


	public List<PlaceDTO> searchPlacesByAreaOrCategory(String areaName, String categoryName) {
		Map<String, String> params = new HashMap<>();
		params.put("categoryName", categoryName);
		params.put("areaName", areaName);
		return mapper.searchPlacesByAreaOrCategory(params);
	}


}