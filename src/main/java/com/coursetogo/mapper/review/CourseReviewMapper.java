package com.coursetogo.mapper.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.review.CourseReviewDTO;

@Mapper
public interface CourseReviewMapper {
	
	// 코스리뷰 Id로 코스리뷰 검색
	public CourseReviewDTO getCourseReviewByReviewId (int courseReviewId);
	
    // 코스Id로 코스리뷰 검색
    public List<CourseReviewDTO> getCourseReviewByCourseId (int courseId) throws SQLException;
	
	// userId로 코스리뷰 리스트 검색
    public List<CourseReviewDTO> getCourseReviewByUserId(int userId) throws SQLException;

    // userId + courseId로 코스리뷰 검색
    public CourseReviewDTO getCourseReviewByUserIdAndCourseId(@Param("userId") int userId, @Param("courseId") int courseId) throws SQLException;

	// 코스리뷰 등록
	public int insertCourseReview(CourseReviewDTO coursereview) throws SQLException;
	
	// 코스리뷰 수정
    public int updateCourseReview(CourseReviewDTO coursereview) throws SQLException;
    
	// 코스리뷰 삭제
	public int deleteCourseReviewByReviewId(int courseReviewId)throws SQLException;
	
    // 코스리뷰왕
	public List<Integer> getReviewTop3() throws SQLException;

	

}
