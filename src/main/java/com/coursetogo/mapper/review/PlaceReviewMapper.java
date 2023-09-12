package com.coursetogo.mapper.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.review.PlaceReviewDTO;

@Mapper
public interface PlaceReviewMapper {
	
	// 장소리뷰 Id로 장소리뷰 검색
    public PlaceReviewDTO getPlaceReviewByReviewId (int placeReviewId) throws SQLException;

    // UserId + PlaceId로 장소리뷰 검색하기
    public PlaceReviewDTO getPlaceReviewByUserIdAndPlaceId(@Param("userId") int userId, @Param("placeId") int placeId) throws SQLException;

	// 장소리뷰 등록
	public int insertPlaceReview(PlaceReviewDTO placereview) throws SQLException;
	
	// 장소리뷰 수정
    public int updatePlaceReview(PlaceReviewDTO placereview) throws SQLException;
    
	// 장소리뷰 삭제
	public int deletePlaceReviewByReviewId(int placeReviewId)throws SQLException;
	
    // 장소리뷰왕   
	public List<Integer> getReviewTop3() throws SQLException;
}
