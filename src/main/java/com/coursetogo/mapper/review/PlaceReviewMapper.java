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

    // UserId로 장소리뷰 검색하기
	public List<PlaceReviewDTO> getPlaceReviewByUserId(int userId) throws SQLException;
    
    // UserId + PlaceId로 장소리뷰 검색하기
    public PlaceReviewDTO getPlaceReviewByUserIdAndPlaceId(@Param("userId") int userId, @Param("placeId") int placeId) throws SQLException;

	// 장소리뷰 등록
	public int insertPlaceReview(PlaceReviewDTO placereview) throws SQLException;
	
	// 장소리뷰 수정
    public int updatePlaceReview(PlaceReviewDTO placereview) throws SQLException;
    
	// 장소리뷰 삭제 / 리뷰아이디를 통해
	public int deletePlaceReviewByReviewId(int placeReviewId) throws SQLException;
	
	// 장소리뷰 삭제 / 장소아이디 + 유저아이디를 통해
	public int deletePlaceReviewByPlaceIdAndUserId(@Param("userId") int userId, @Param("placeId") int placeId) throws SQLException;;
	
    // 장소리뷰왕   
	public List<Integer> getReviewTop3() throws SQLException;

	// 전체 장소리뷰 개수 조회
	public int getAllPlaceReviewCount() throws SQLException;

	// 검색된 장소리뷰 개수 조회
	public int getSearchedPlaceReviewCount(@Param("category") String category, @Param("keyword") String keyword) throws SQLException;

	// 전체 장소리뷰리스트 조회 (관리자)
	public List<PlaceReviewDTO> getAllPlaceReviewByPage(@Param("startRow") int startRow, @Param("endRow") int endRow);

	// 검색된 장소리뷰리스트 조회 (관리자)
	public List<PlaceReviewDTO> getAllPlaceReviewByKeywordWithPage(@Param("startRow") int startRow, @Param("endRow") int endRow,
																   @Param("category") String category, @Param("keyword") String keyword);


	
}
