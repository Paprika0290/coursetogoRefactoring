package com.coursetogo.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.service.review.ReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestAPIController {

	@Autowired
	private ReviewService reviewService; 
	
	// course/courseDetail 페이지에서 코스 리뷰 리스트 조회
	@GetMapping("/course/courseDetail/reviewList")
	public List<CourseReviewDTO> getCourseReviewList(String courseId) {
		
		List<CourseReviewDTO> courseReviewList = new ArrayList<CourseReviewDTO>();
		
		System.out.println(courseId);
		
		try {
			courseReviewList = reviewService.getCourseReviewByCourseId(Integer.parseInt(courseId));
		} catch (Exception e) {
			log.warn("코스 리뷰 리스트 조회 실패");
			e.printStackTrace();
		}

		return courseReviewList;
	}
	
}