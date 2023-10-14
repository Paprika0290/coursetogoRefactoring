package com.coursetogo.service.review;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.mapper.review.CourseReviewMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseReviewService {

	@Autowired
	private CourseReviewMapper mapper;
	
	// 코스리뷰 Id로 코스리뷰 검색
	public CourseReviewDTO getCourseReviewByReviewId(int courseReviewId) throws Exception { 
		  CourseReviewDTO coursereview = mapper.getCourseReviewByReviewId(courseReviewId);
		return coursereview; 
	}

	// 코스Id로 코스리뷰 검색
	public List<CourseReviewDTO> getCourseReviewByCourseId(int courseId) throws Exception { 
		List<CourseReviewDTO> coursereview = mapper.getCourseReviewByCourseId(courseId);
		return coursereview; 
	}
	
	// 코스Id로 코스리뷰 개수 확인
	public int getCourseReviewCountByCourseId(int courseId) throws SQLException {
		int courseReviewCount = mapper.getCourseReviewCountByCourseId(courseId);
		return courseReviewCount;
	}
	
	// userId로 코스리뷰 리스트 검색
	public List<CourseReviewDTO> getCourseReviewByUserId(int userId) throws SQLException {
		return mapper.getCourseReviewByUserId(userId);
	}
	
    // userId + courseId로 코스리뷰 검색
    public CourseReviewDTO getCourseReviewByUserIdAndCourseId(int userId, int courseId) throws SQLException {
        return mapper.getCourseReviewByUserIdAndCourseId(userId, courseId);
    }
	
	// 코스리뷰 등록
	public boolean insertCourseReview(CourseReviewDTO coursereview) throws SQLException, Exception {
		boolean result = false;
		int res = mapper.insertCourseReview(coursereview);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("코스 리뷰 등록 실패");
		}
		
		return result;
	}
	
	// 코스리뷰 수정
	public boolean updateCourseReview(CourseReviewDTO coursereview) throws SQLException, Exception {
		boolean result = false;
		int res = mapper.updateCourseReview(coursereview);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("코스 리뷰 수정 실패");
		}
		
		return result;
	}
	
	// 코스리뷰 삭제
	public boolean deleteCourseReviewByReviewId(int courseReviewId) throws SQLException, Exception {
		boolean result = false;
		int res = mapper.deleteCourseReviewByReviewId(courseReviewId);
		
		if(res != 0) {
			result = true;
		} else {
			log.warn("코스 리뷰 삭제 실패");
		}
		
		return result;
	}
	
	public int deleteCourseReviewByCourseId(int courseId) throws SQLException {
		int res = 0;
		res = mapper.deleteCourseReviewByCourseId(courseId);
		
		return res;
	}	

	// 유저의 리뷰 개수 가져오는 메서드--------------------------------------------------------------
	public int getUserCourseReviewCount(int userId) throws SQLException{
		return mapper.getUserCourseReviewCount(userId);	
	}	
	
    // 코스리뷰왕
	public List<Integer> getReviewTop3() throws SQLException{
		return mapper.getReviewTop3();
	}

	
	
}
