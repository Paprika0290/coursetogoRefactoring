package com.coursetogo.service.review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.mapper.review.PlaceReviewMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaceReviewService {
	
	@Autowired
	private PlaceReviewMapper mapper;
	
	// 장소리뷰 Id로 장소리뷰 검색
	public PlaceReviewDTO getPlaceReviewByReviewId(int placeReviewId) throws SQLException { 
        return mapper.getPlaceReviewByReviewId(placeReviewId);
	}

    // UserId로 장소리뷰 검색하기
    public List<PlaceReviewDTO> getPlaceReviewByUserId(int userId) throws SQLException {
        return mapper.getPlaceReviewByUserId(userId);
    }
    
    // UserId + PlaceId로 장소리뷰 검색하기
    public PlaceReviewDTO getPlaceReviewByUserIdAndPlaceId(int userId, int placeId) throws SQLException {
        return mapper.getPlaceReviewByUserIdAndPlaceId(userId, placeId);
    }
    
	// 장소리뷰 등록
	public boolean insertPlaceReview(PlaceReviewDTO placereview) throws SQLException{
		boolean result = false;
		int res = mapper.insertPlaceReview(placereview);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("장소 리뷰 등록 실패");
		}
		
		return result;
	}

	// 장소리뷰 수정
	public boolean updatePlaceReview(PlaceReviewDTO placereview) throws SQLException{
		boolean result = false;
		int res = mapper.updatePlaceReview(placereview);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("장소 리뷰 수정 실패");
		}
		
		return result;
	}
	
	// 장소리뷰 삭제 / 리뷰아이디를 통해
	public boolean deletePlaceReviewByReviewId(int placeReviewId) throws SQLException{
		boolean result = false;
		int res = mapper.deletePlaceReviewByReviewId(placeReviewId);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("장소 리뷰 삭제 실패");
		}
		
		return result;
	}

	// 장소리뷰 삭제 / 장소아이디 + 유저아이디를 통해
	public boolean deletePlaceReviewByPlaceIdAndUserId(int placeId, int userId) throws SQLException{
		boolean result = false;
		int res = mapper.deletePlaceReviewByPlaceIdAndUserId(placeId, userId);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("장소 리뷰 삭제 실패");
		}
		
		return result;
	}
	
    // 장소리뷰왕   
	public List<Integer> getReviewTop3() throws SQLException{
		return mapper.getReviewTop3();
	}

	// 전체 장소리뷰 개수 조회
	public int getAllPlaceReviewCount() throws SQLException {
		return mapper.getAllPlaceReviewCount();
	}

	// 검색된 장소리뷰 개수 조회
	public int getSearchedPlaceReviewCount(String category, String keyword) throws SQLException {
		return mapper.getSearchedPlaceReviewCount(category, keyword);
	}	
	
	// 전체 장소리뷰리스트 조회 (관리자)
	public List<PlaceReviewDTO> getAllPlaceReviewByPage(int pageNum, int pageSize) throws SQLException {
		List<PlaceReviewDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		res= mapper.getAllPlaceReviewByPage(startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 코스리뷰(+페이지네이션) 검색 실패");
		}
		return res;
	}
	
	// 검색된 장소리뷰리스트 조회 (관리자)
	public List<PlaceReviewDTO> getAllPlaceReviewByKeywordWithPage(String category, String keyword,
																	int pageNum, int pageSize) throws SQLException {
		List<PlaceReviewDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		
		res= mapper.getAllPlaceReviewByKeywordWithPage(startRow, endRow, category, keyword);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("검색된 코스리뷰(+페이지네이션) 검색 실패");
		}
		return res;
	}
	
	
	
	
	
	
	
	
	
	
}