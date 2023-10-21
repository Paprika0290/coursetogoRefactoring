package com.coursetogo.mapper.map;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.map.PlaceInformDTO;

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
	
	public List<PlaceDTO> searchPlacesByAreaAndConsonant(@Param("areaName") String areaName,
														 @Param("con1") String con1, @Param("con2") String con2) throws SQLException;
	
	public List<PlaceDTO> searchPlacesByCategory(String categoryName) throws SQLException;

	public List<PlaceDTO> searchPlacesByAreaOrCategory(String categoryName, String areaName) throws SQLException;

	public List<PlaceDTO> searchPlacesByAreaOrCategory(Map<String, String> params) throws SQLException;
	
	// 전체 장소수 조회
	public int getAllPlacesCount() throws SQLException;

	// 전체 장소리스트 조회 (관리자)
	public List<PlaceInformDTO> getAllPlaceInformListForAdminByPage(@Param("startRow") int startRow,
													  				@Param("endRow") int endRow) throws SQLException;
	// 키워드로 검색된 장소리스트 조회 (관리자)
	public List<PlaceInformDTO> getAllPlaceInformListByKeywordForAdminByPage
														   (@Param("category") String category, @Param("keyword") String keyword,
															@Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;

	// 키워드로 검색된 장소 개수 (관리자)	
	public int getSearchedPlaceCount(@Param("category") String category, @Param("keyword") String keyword) throws SQLException;

	// 장소리뷰가 새로 등록될 때, 해당 장소의 AvgScore가 update되는 메서드
	public int updatePlaceAvgScore(@Param("placeId") int placeId) throws SQLException;

	// 장소 삭제 (관리자 페이지)
	public int deletePlace(int placeId) throws SQLException;
	public int deleteAreaPlace(int placeId) throws SQLException;
	public int deleteCategoryPlace(int placeId) throws SQLException;

	// 장소ID로 장소이름 조회
	public String getPlaceNameByPlaceId(int placeId) throws SQLException;
	

	

}