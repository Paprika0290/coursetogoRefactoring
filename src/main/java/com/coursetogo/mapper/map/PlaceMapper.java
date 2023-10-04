package com.coursetogo.mapper.map;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.map.PlaceDTO;

@Mapper
public interface PlaceMapper {

	public PlaceDTO getPlaceByPlaceId(int placeId) throws SQLException;
	
	public List<PlaceDTO> getAllPlaces() throws SQLException;

	public List<PlaceDTO> searchPlaces(String placeName) throws SQLException;

	public List<PlaceDTO> searchPlacesByPlaceName(String placeName) throws SQLException;

	public List<PlaceDTO> searchPlacesByArea(String areaName) throws SQLException;

	public List<PlaceDTO> searchPlacesByAreaWithPages(@Param("areaName") String areaName,
													  @Param("startRow") int startRow,
													  @Param("endRow") int endRow) throws SQLException;
	
	public List<PlaceDTO> searchPlacesByCategory(String categoryName) throws SQLException;

	public List<PlaceDTO> searchPlacesByAreaOrCategory(String categoryName, String areaName) throws SQLException;

	public List<PlaceDTO> searchPlacesByAreaOrCategory(Map<String, String> params) throws SQLException;

	

}