package com.coursetogo.service.review;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.mapper.review.ReviewMapper;

@Service
public class ReviewService {

	@Autowired
	private ReviewMapper mapper;
	
	// 코스아이디로 코스리뷰 검색
	public List<CourseReviewDTO> getCourseReviewByCourseId(int courseId) throws Exception { 
		List<CourseReviewDTO> coursereviewList = mapper.getCourseReviewByCourseId(courseId);	
		return coursereviewList; 
	}


//	// userId로 CourseReview 객체 리스트 반환
//	public List<CourseReview> getCourseReviewByUserId(int userId) throws SQLException {
//		return mapper.getCourseReviewByUserId(userId);
//	}
//	
//
//    // userId와 courseId로 CourseReview 객체 리스트 반환
//    public CourseReview getCourseReviewByUserIdAndCourseId(int userId, int courseId) throws SQLException {
//        return mapper.getCourseReviewByUserIdAndCourseId(userId, courseId);
//    }
//
//
//    // 코스리뷰왕
//	public List<Integer> getReviewTop3() throws SQLException{
//		return mapper.getReviewTop3();
//	}		
}
