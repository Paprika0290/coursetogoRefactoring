package com.coursetogo.controller.map;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.map.PlaceInformDTO;
import com.coursetogo.service.map.PlaceService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;
	
	
	// 관리자 페이지 - placeList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드.
	public HashMap<String, Object> getPlaceInformListValuesForAdmin(String category, String keyword,
															  		int pageNum, int pageSize, Integer groupNum) {
		HashMap<String, Object> listValues = new HashMap<String, Object>();
		List<PlaceInformDTO> placeInformList = new ArrayList<PlaceInformDTO>();
		
		if(category == null) {
			try {
				placeInformList = placeService.getAllPlaceInformListForAdminByPage(pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 전체 장소리스트 검색에 실패하였습니다.");
				e.printStackTrace();
			}
		}else {
			try {
				placeInformList = placeService.getAllPlaceInformListByKeywordForAdminByPage(category, keyword, pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 키워드 장소리스트 검색에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		int totalPlaceCount = 0;
		int searchedPlaceCount = 0;
		
		try {
			totalPlaceCount = placeService.getAllPlacesCount();
			if(category != null) {
				searchedPlaceCount = placeService.getSearchedPlaceCount(category, keyword);
			}
		} catch (SQLException e) {
			log.warn("전체 장소수 조회에 실패했습니다.");
			e.printStackTrace();
		}
		
		listValues.put("placeInformList", placeInformList);
		listValues.put("totalPlaceCount", totalPlaceCount);
		
		// pageNum : 기본-1, 페이지 번호 누를시 새로 입력됨 / pageSize: 기본-10
		int totalPages = 0;
		if(category == null) {
			if( (totalPlaceCount / pageSize) < ((double)totalPlaceCount / (double)pageSize) &&
					((double)totalPlaceCount / (double)pageSize) < (totalPlaceCount / pageSize) + 1 ) {
					totalPages = (totalPlaceCount / pageSize) + 1;
				} else {
					totalPages = (totalPlaceCount / pageSize);
				}

				int totalGroups = 0;
				if( (totalPages / 10) < ((double)totalPages / 10) &&
					((double)totalPages / 10) < (totalPages / 10) + 1 ) {
					totalGroups = (totalPages / 10) + 1;
				} else {
					totalGroups = (totalPages / 10);
				}
				
				
				for(int i = 1; i <= totalGroups; i++) {
					if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
						groupNum = i;
						break;
					}
				}
				
				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
		}else {
			if( (searchedPlaceCount / pageSize) < ((double)searchedPlaceCount / (double)pageSize) &&
					((double)searchedPlaceCount / (double)pageSize) < (searchedPlaceCount / pageSize) + 1 ) {
					totalPages = (searchedPlaceCount / pageSize) + 1;
				} else {
					totalPages = (searchedPlaceCount / pageSize);
				}

				int totalGroups = 0;
				if( (totalPages / 10) < ((double)totalPages / 10) &&
					((double)totalPages / 10) < (totalPages / 10) + 1 ) {
					totalGroups = (totalPages / 10) + 1;
				} else {
					totalGroups = (totalPages / 10);
				}
				
				
				for(int i = 1; i <= totalGroups; i++) {
					if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
						groupNum = i;
						break;
					}
				}
				
				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
				listValues.put("category", category);
				listValues.put("keyword", keyword);
		}
		

		return listValues;
	}
	

	
	
}
