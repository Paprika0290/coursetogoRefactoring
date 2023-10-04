package com.coursetogo.controller.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coursetogo.controller.map.CourseController;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.map.PlaceService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;
import com.coursetogo.service.user.UserBookmarkCourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestAPIController {
	
	@Autowired
	private CourseReviewService courseReviewService; 
	
	@Autowired
	private PlaceReviewService placeReviewService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private UserBookmarkCourseService bookmarkService;

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
	
	// course/courseMake 페이지에서 area or area + category 검색시 장소리스트 반환
	@GetMapping("/place/getPlaceList")
	public List<PlaceDTO> getPlaceList(@RequestParam("areaName") String areaName,
									   @RequestParam(name= "categoryName", defaultValue = "none") String categoryName) {
		List<PlaceDTO> placeList = new ArrayList<PlaceDTO>();
		
		if(categoryName.equals("none")) {
			try {
				placeList = placeService.searchPlacesByArea(areaName);
			} catch (SQLException e) {
				log.warn("지역별 장소 조회 불가");
				e.printStackTrace();
			}
		}else {
			try {
				placeList = placeService.searchPlacesByAreaOrCategory(areaName, categoryName);
			} catch (SQLException e) {
				log.warn("category + 지역별 장소 조회 불가");
				e.printStackTrace();
			}
		}
		
		return placeList;
	}
	
	
	// course/courseList 페이지에서 회색 북마크 버튼 클릭시 북마크 추가 처리
	@PostMapping("/user/bookmark/insert")
	public boolean insertBookmark(@RequestParam("courseId") String courseId,
								  @RequestParam("userId") String userId) {
		boolean res = false; 
		
		try {
			res = bookmarkService.insertNewBookmark(Integer.parseInt(userId), Integer.parseInt(courseId));
		} catch (SQLException e) {
			log.warn(courseId + "코스 북마크 실패");
			e.printStackTrace();
		}
		
		if(res) {
			return true;
		}
		return false;
	}
	
	// course/courseList 페이지에서 주황 북마크 버튼 클릭시 북마크 삭제 처리
	@PostMapping("/user/bookmark/delete")
	public boolean deleteBookmark(@RequestParam("courseId") String courseId,
								  @RequestParam("userId") String userId) {
		boolean res = false; 
		
		try {
			res = bookmarkService.deleteBookmark(Integer.parseInt(userId), Integer.parseInt(courseId));
		} catch (SQLException e) {
			log.warn(courseId + "코스 북마크 삭제 실패");
			e.printStackTrace();
		}
		
		if(res) {
			return true;
		}
		return false;
	}
	
	
	
	
	
}