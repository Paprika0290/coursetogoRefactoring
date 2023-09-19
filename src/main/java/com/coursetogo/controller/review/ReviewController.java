package com.coursetogo.controller.review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReviewController {

	@Autowired
	private CourseReviewService courseReviewService;

	@Autowired
	private PlaceReviewService placeReviewService;
	
	// course당 입력될 수 있는 place수가 가변적이기 때문에 만든 메서드
	// models 배열에서 null 이거나 빈 문자열인 요소들을 걸러낸 새로운 문자열 배열을 얻을수 있음 
	public String[] filterNullValues(String... models) {
	    List<String> filteredValues = new ArrayList<>();
	    for (String value : models) {
	        if (value != null && !value.isEmpty()) {
	            filteredValues.add(value);
	        }
	    }
	    return filteredValues.toArray(new String[filteredValues.size()]);
	}
	
	// 리뷰 작성 메서드
	@PostMapping("/review/reviewWrite")
	public String insertNewCourseReview(@ModelAttribute CourseReviewDTO courseReview,
									  @ModelAttribute("placeScore1") String placeScore1,
									  @ModelAttribute("placeScore2") String placeScore2,
									  @ModelAttribute("placeScore3") String placeScore3,
									  @ModelAttribute("placeScore4") String placeScore4,
									  @ModelAttribute("placeScore5") String placeScore5,
									  @ModelAttribute("place1") String placeId1,
									  @ModelAttribute("place2") String placeId2,
									  @ModelAttribute("place3") String placeId3,
									  @ModelAttribute("place4") String placeId4,
									  @ModelAttribute("place5") String placeId5,
									  HttpSession session) {
		
		int userId = 0;
		
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}
        
		// filterNullValues는 주어진 문자열 배열에서 null이 아니고 빈 문자열이 아닌 값들만 걸러내는 메서드
		String[] placeScores = filterNullValues(placeScore1, placeScore2, placeScore3, placeScore4, placeScore5);
		String[] placeIds = filterNullValues(placeId1, placeId2, placeId3, placeId4, placeId5);
        
        // 장소리뷰 남기기-------------------------------------------------------------------
        for (int i = 0; i < placeIds.length; i++) {
                
                if (placeScores[i] != null && placeIds[i] != null) {
                    PlaceReviewDTO insertedPlaceReview = new PlaceReviewDTO(userId, Integer.parseInt(placeIds[i]), Integer.parseInt(placeScores[i]));
                    PlaceReviewDTO alreadyInsertedPlaceReview = null; 
                    
                            //이미 별점을 매긴 장소인지 확인-----------------------
                            try {
                                alreadyInsertedPlaceReview = placeReviewService.getPlaceReviewByUserIdAndPlaceId(insertedPlaceReview.getUserId(), insertedPlaceReview.getPlaceId());
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        
                            // 매긴 적 없다면---------------------------------
                            if(alreadyInsertedPlaceReview == null) {
                                        try {
                                            placeReviewService.insertPlaceReview(insertedPlaceReview);
                                        } catch (Exception e) {
                                           	log.warn("장소리뷰 신규 등록 실패");
                                        }
                                        
                            // 매긴 적 있다면---------------------------------    
                            } else {            
                                        try {
                                            placeReviewService.updatePlaceReview(insertedPlaceReview);
                                        } catch (Exception e) {
                                        	log.warn("장소리뷰 수정 실패");
                                        }
                            }    
				}
        }
				
        // 코스리뷰 남기기-------------------------------------------------------------------
        try {
            courseReviewService.insertCourseReview(courseReview);
        } catch (Exception e) {
            log.warn("코스리뷰 등록 실패");
        }
 
        return "redirect:/course/courseDetail?courseId=" + courseReview.getCourseId();
	}
	
	// 리뷰 수정 메서드
	@PostMapping("/review/reviewUpdate")
	public String updateCourseReview(@ModelAttribute CourseReviewDTO courseReview,
										@ModelAttribute("placeScore1") String placeScore1,
										@ModelAttribute("placeScore2") String placeScore2,
									    @ModelAttribute("placeScore3") String placeScore3,
									 	@ModelAttribute("placeScore4") String placeScore4,
										@ModelAttribute("placeScore5") String placeScore5,
										@ModelAttribute("place1") String placeId1,
										@ModelAttribute("place2") String placeId2,
										@ModelAttribute("place3") String placeId3,
										@ModelAttribute("place4") String placeId4,
										@ModelAttribute("place5") String placeId5,
										HttpSession session) {
		
		int userId = 0;
		
		if(session.getAttribute("user") != null) {
			userId = ((CtgUserDTO) session.getAttribute("user")).getUserId();
		}
        
		// filterNullValues는 주어진 문자열 배열에서 null이 아니고 빈 문자열이 아닌 값들만 걸러내는 메서드
		String[] placeScores = filterNullValues(placeScore1, placeScore2, placeScore3, placeScore4, placeScore5);
		String[] placeIds = filterNullValues(placeId1, placeId2, placeId3, placeId4, placeId5);
        
        // 장소리뷰 수정하기-------------------------------------------------------------------
        for (int i = 0; i < placeIds.length; i++) {
                
                if (placeScores[i] != null && placeIds[i] != null) {
                    PlaceReviewDTO insertedPlaceReview = new PlaceReviewDTO(userId, Integer.parseInt(placeIds[i]), Integer.parseInt(placeScores[i]));
       
                    try {
                        placeReviewService.updatePlaceReview(insertedPlaceReview);
                    } catch (Exception e) {
                    	log.warn("장소리뷰 수정 실패");
                    }

				}
        }
				
        // 코스리뷰 수정하기-------------------------------------------------------------------
        try {
            courseReviewService.updateCourseReview(courseReview);
        } catch (Exception e) {
            log.warn("코스리뷰 수정 실패");
            e.printStackTrace();
        }
 
        return "redirect:/course/courseDetail?courseId=" + courseReview.getCourseId();
	}
	
	// 리뷰 삭제 메서드
	@GetMapping("/review/reviewDelete")
	public String deleteCourseReview(@RequestParam("userId") String userId,
									  @RequestParam("courseId") String courseId) {
		CourseReviewDTO courseReview = null;
		try {
			courseReview = courseReviewService.getCourseReviewByUserIdAndCourseId(Integer.parseInt(userId), Integer.parseInt(courseId));
			courseReviewService.deleteCourseReviewByReviewId(courseReview.getCourseReviewId());
		} catch (Exception e) {
			log.warn("코스리뷰 삭제 실패");
			e.printStackTrace();
		}
		
		System.out.println("/course/courseDetail?courseId=" + courseId);
		return "redirect:/course/courseDetail?courseId=" + courseId;
	}
	
}
