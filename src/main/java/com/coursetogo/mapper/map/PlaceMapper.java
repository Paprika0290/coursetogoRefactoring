package com.coursetogo.mapper.map;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.coursetogo.dto.map.PlaceDTO;

@Mapper
public interface PlaceMapper {

	public PlaceDTO getPlaceByPlaceId(int placeId);
	
	public List<PlaceDTO> getAllPlaces();

	public List<PlaceDTO> searchPlaces(String placeName);

	public List<PlaceDTO> searchPlacesByPlaceName(String placeName);

	public List<PlaceDTO> searchPlacesByArea(String areaName);

	public List<PlaceDTO> searchPlacesByCategory(String categoryName);

	public List<PlaceDTO> searchPlacesByAreaOrCategory(String categoryName, String areaName);

	public List<PlaceDTO> searchPlacesByAreaOrCategory(Map<String, String> params);

	

}