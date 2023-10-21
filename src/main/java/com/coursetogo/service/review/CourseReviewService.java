package com.coursetogo.service.review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
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
	
	// 전체 코스리뷰 개수 조회
	public int getAllCourseReviewCount() throws SQLException {
		return mapper.getAllCourseReviewCount();
	}
	
	// 검색된 코스리뷰 개수 조회
	public int getSearchedCourseReviewCount(String category, String keyword) throws SQLException {
		return mapper.getSearchedCourseReviewCount(category, keyword);
	}
	
	// 전체 코스리뷰리스트 조회 (관리자)
	public List<CourseReviewDTO> getAllCourseReviewByPage(int pageNum, int pageSize) throws SQLException {
		List<CourseReviewDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		res= mapper.getAllCourseReviewByPage(startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 코스리뷰(+페이지네이션) 검색 실패");
		}
		return res;
	}
	
	// 검색된 코스리뷰리스트 조회 (관리자)
	public List<CourseReviewDTO> getAllCourseReviewByKeywordWithPage(String category, String keyword,
																	 int pageNum, int pageSize) throws SQLException {
		List<CourseReviewDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		
		res= mapper.getAllCourseReviewByKeywordWithPage(startRow, endRow, category, keyword);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("검색된 코스리뷰(+페이지네이션) 검색 실패");
		}
		return res;
	}

	
	
}
