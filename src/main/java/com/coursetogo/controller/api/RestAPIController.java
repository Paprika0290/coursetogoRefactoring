package com.coursetogo.controller.api;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.coursetogo.controller.map.CourseController;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.map.PlaceDTO;
import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CoursePlaceService;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.map.PlaceService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;
import com.coursetogo.service.user.CtgUserService;
import com.coursetogo.service.user.UserBookmarkCourseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class RestAPIController {
	
	@Autowired
	private CtgUserService userService; 
	
	@Autowired
	private CourseReviewService courseReviewService; 
	
	@Autowired
	private PlaceReviewService placeReviewService;

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private CoursePlaceService coursePlaceService;
	
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
	
	@GetMapping("/user/course/check/{courseId}")
	public int getCourseReviewCount(@PathVariable int courseId) {
		int reviewCount = 0;
		
		try {
			reviewCount = courseReviewService.getCourseReviewCountByCourseId(courseId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reviewCount;
	}	
	
	@DeleteMapping("/user/course/delete/{courseId}/{checked}")
	public int deleteCourse(@PathVariable int courseId, @PathVariable int checked) {
		int res = 0;
		
		// 작성된 리뷰가 없고 삭제 요청
		if(checked == 21) {
		// 작성된 리뷰가 있고 삭제 요청 + 작성자가 삭제를 재확인
		}else if(checked == 11) {
			try {
				courseReviewService.deleteCourseReviewByCourseId(courseId);
			} catch (SQLException e) {
				log.warn("코스 아이디를 통한 코스 리뷰 삭제에 실패하였습니다.");
				e.printStackTrace();
			}	
		}
		
		try {
			bookmarkService.deleteBookmarkByCourseId(courseId);
			coursePlaceService.deleteCoursePlace(courseId);
		} catch (SQLException e) {
			log.warn("코스 즐겨찾기 내역, 코스-장소 내역 삭제에 실패하였습니다.");
			e.printStackTrace();
		}
		
		try {
			res = courseService.deleteCourse(courseId);
		} catch (SQLException e) {
			log.warn("코스 삭제에 실패하였습니다.");
			e.printStackTrace();
		}

		return res;
	}
	
	
	@GetMapping("/course/getCourseList/{areaName}/{consonant}")
	public List<PlaceDTO> getCourseListByConsonant(@PathVariable String areaName, @PathVariable String consonant) {
		List<PlaceDTO> placeList = new ArrayList<PlaceDTO>();
		
		try {
			placeList = placeService.searchPlacesByAreaAndConsonant(areaName, consonant);
		} catch (SQLException e) {
			log.warn("area + 자음 장소 검색에 실패했습니다.");
			e.printStackTrace();
		}
		
		return placeList;
	}
	
	// 코스 삭제 (관리자)
	@PostMapping("/admin/course/delete")
	public String deleteCourseByAdmin(@RequestBody int[] courseIdArray) {
		int res = 0;
		
		for(Integer courseId : courseIdArray) {
			try {
				courseReviewService.deleteCourseReviewByCourseId(courseId);
				bookmarkService.deleteBookmarkByCourseId(courseId);
				coursePlaceService.deleteCoursePlace(courseId);
			} catch (SQLException e) {
				log.warn("admin- 코스 관련 테이블 삭제에 실패하였습니다.");
				e.printStackTrace();
			}
			
			try {
				res = courseService.deleteCourse(courseId);
			} catch (SQLException e) {
				log.warn("admin- 코스 삭제에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		if (res != 0) {
			return "/admin/course";
		}
		return "/admin/error";
	}

	// 장소 삭제 (관리자)
	@PostMapping("/admin/place/delete")
	public void deletePlaceByAdmin(@RequestBody int[] placeIdArray) {	
		for(Integer placeId : placeIdArray) {
			try {
				placeService.deletePlace(placeId);
			} catch (SQLException e) {
				log.warn("admin- 장소 삭제에 실패하였습니다.");
				e.printStackTrace();
			}
		}
	}
	
	// 코스리뷰 삭제 (관리자)
	@PostMapping("/admin/courseReview/delete")
	public void deleteCourseReviewByAdmin(@RequestBody int[] reviewIdArray) {
		
		for(int i = 1; i <= reviewIdArray.length; i++) {
			if(i%2 == 1) {
				try {
					courseReviewService.deleteCourseReviewByReviewId(reviewIdArray[i-1]);
				} catch (Exception e) {
					log.warn("admin- 코스리뷰 삭제에 실패하였습니다.");
					e.printStackTrace();
				}
			}else if(i%2 == 0){
				try {
					courseService.updateCourseAvgScore(reviewIdArray[i-1]);
					courseService.updateEntireAvgScore();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}	
	}

}