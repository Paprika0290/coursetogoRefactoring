package com.coursetogo.controller.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestAPIController {

	@Autowired
	private CourseReviewService courseReviewService; 
	
	@Autowired
	private PlaceReviewService placeReviewService;
	
	// course/courseDetail 페이지에서 코스 리뷰 리스트 조회
	@GetMapping("/course/courseDetail/reviewList")
	public List<CourseReviewDTO> getCourseReviewList(String courseId) {
		
		List<CourseReviewDTO> courseReviewList = new ArrayList<CourseReviewDTO>();
		
		try {
			courseReviewList = courseReviewService.getCourseReviewByCourseId(Integer.parseInt(courseId));
		} catch (Exception e) {
			log.warn("코스 리뷰 리스트 조회 실패");
			e.printStackTrace();
		}

		return courseReviewList;
	}
	
	// course/courseDetail 페이지에서 리뷰 수정 페이지에서 기존 리뷰 내용 조회
	@GetMapping("/review/getCourseReview")
	public CourseReviewDTO getWrittenCourseReview(@RequestParam("userId") String userId,
								 			      @RequestParam("courseId") String courseId) {
		CourseReviewDTO courseReview = null;
		
		try {
			courseReview = courseReviewService.getCourseReviewByUserIdAndCourseId(Integer.parseInt(userId), Integer.parseInt(courseId));
		} catch (NumberFormatException | SQLException e) {
			log.warn("기존 코스리뷰 조회 실패");
		}

		return courseReview;
	}
	
	// course/courseDetail 페이지에서 리뷰 수정 페이지에서 기존 리뷰 내용(별점) 조회
	@GetMapping("/review/getPlaceReviews")
	public int[] getWrittenPlaceReviews(@RequestParam("userId") String userId,
								 	    @RequestParam("places") String places) {
		String[] placeIdList = places.split(",");
		int[] placeScores = new int[placeIdList.length];
		
		for(int i = 0; i <placeIdList.length; i++) {
			PlaceReviewDTO placeReview;
			try {
				placeReview = placeReviewService.getPlaceReviewByUserIdAndPlaceId(Integer.parseInt(userId), Integer.parseInt(placeIdList[i]));
				placeScores[i] = placeReview.getPlaceScore();
			} catch (NumberFormatException | SQLException e) {
				log.warn("기존 장소리뷰 조회 실패");
			} 
		}


		return placeScores;
	}
	
}