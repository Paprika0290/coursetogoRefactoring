package com.coursetogo.service.map;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.map.PlaceInformDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
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

	public List<PlaceDTO> searchPlacesByAreaAndConsonant(String areaName, String consonant) throws SQLException {
		List<PlaceDTO> res = new ArrayList<PlaceDTO>();
		String con1 = "";
		String con2 = "";

		if(consonant.equals("etc")) {
			con1 = "^[가-힣]";
			con2 = "한글 외";
		}else if(consonant.equals("ㄱ")) {
			con1 = "가";
			con2 = "나";
		}else if(consonant.equals("ㄴ")) {
			con1 = "나";
			con2 = "다";
		}else if(consonant.equals("ㄷ")) {
			con1 = "다";
			con2 = "라";
		}else if(consonant.equals("ㄹ")) {
			con1 = "라";
			con2 = "마";
		}else if(consonant.equals("ㅁ")) {
			con1 = "마";
			con2 = "바";
		}else if(consonant.equals("ㅂ")) {
			con1 = "바";
			con2 = "사";
		}else if(consonant.equals("ㅅ")) {
			con1 = "사";
			con2 = "아";
		}else if(consonant.equals("ㅇ")) {
			con1 = "아";
			con2 = "자";
		}else if(consonant.equals("ㅈ")) {
			con1 = "자";
			con2 = "차";
		}else if(consonant.equals("ㅊ")) {
			con1 = "차";
			con2 = "카";
		}else if(consonant.equals("ㅋ")) {
			con1 = "카";
			con2 = "타";
		}else if(consonant.equals("ㅌ")) {
			con1 = "타";
			con2 = "파";
		}else if(consonant.equals("ㅍ")) {
			con1 = "파";
			con2 = "하";
		}else if(consonant.equals("ㅎ")) {
			con1 = "하";
			con2 = "힣";
		}
		
		res = mapper.searchPlacesByAreaAndConsonant(areaName, con1, con2);
		
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

	// 전체 장소수 조회
	public int getAllPlacesCount() throws SQLException{
		return mapper.getAllPlacesCount();
	}

	// 전체 장소리스트 조회 (관리자)
	public List<PlaceInformDTO> getAllPlaceInformListForAdminByPage(int pageNum, int pageSize) throws SQLException {
		
		List<PlaceInformDTO> res = new ArrayList<PlaceInformDTO>();
				
		int startRow = ((pageNum - 1) * pageSize) + 1;
		int endRow = ((pageNum - 1) * pageSize) + pageSize;
		
		res = mapper.getAllPlaceInformListForAdminByPage(startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 장소(+페이지네이션) 검색 실패");
		}
		return res;
	}


	public List<PlaceInformDTO> getAllPlaceInformListByKeywordForAdminByPage(String category, String keyword,
																			 int pageNum, int pageSize) throws SQLException {
		List<PlaceInformDTO> res = new ArrayList<PlaceInformDTO>();
		
		int startRow = ((pageNum - 1) * pageSize) + 1;
		int endRow = ((pageNum - 1) * pageSize) + pageSize;
		
		res = mapper.getAllPlaceInformListByKeywordForAdminByPage(category, keyword, startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 장소(+페이지네이션) 키워드 검색 실패");
		}
		return res;
	}

	// 검색된 장소 수 확인(관리자 페이지)
	public int getSearchedPlaceCount(String category, String keyword) throws SQLException {
		return mapper.getSearchedPlaceCount(category, keyword);
	}
	
	// 장소리뷰가 새로 등록될 때, 해당 장소의 AvgScore가 update되는 메서드
	public int updatePlaceAvgScore(int placeId) throws SQLException {
		int res = 0;
		res = mapper.updatePlaceAvgScore(placeId);
		
		return res;
	}

	// 장소 삭제 (관리자 페이지)
	public int deletePlace(int placeId) throws SQLException {
		int res= 0;
		mapper.deleteAreaPlace(placeId);
		mapper.deleteCategoryPlace(placeId);
		
		res = mapper.deletePlace(placeId);
		
		return res;
	}

	// 장소ID로 장소이름 조회
	public String getPlaceNameByPlaceId(int placeId) throws SQLException {
		return mapper.getPlaceNameByPlaceId(placeId);
	}

}