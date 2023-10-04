package com.coursetogo.service.review;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	// 장소리뷰 삭제
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
    
    // 장소리뷰왕   
	public List<Integer> getReviewTop3() throws SQLException{
		return mapper.getReviewTop3();
	}	
}