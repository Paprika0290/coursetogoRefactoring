package com.coursetogo.controller.review;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.coursetogo.dto.review.CourseReviewDTO;
import com.coursetogo.dto.review.PlaceReviewDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.service.course.CoursePlaceService;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.map.PlaceService;
import com.coursetogo.service.review.CourseReviewService;
import com.coursetogo.service.review.PlaceReviewService;
import com.coursetogo.service.user.CtgUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReviewController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private PlaceService placeService;
	
	@Autowired
	private CourseReviewService courseReviewService;

	@Autowired
	private PlaceReviewService placeReviewService;
	
	@Autowired
	private CoursePlaceService coursePlaceService;
	
	@Autowired
	private CtgUserService userService;
	
	
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
                            
                      try {
						placeService.updatePlaceAvgScore(Integer.parseInt(placeIds[i]));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}     
				}
        }
				
        // 코스리뷰 남기기-------------------------------------------------------------------
        try {
            courseReviewService.insertCourseReview(courseReview);
            courseService.updateCourseAvgScore(courseReview.getCourseId());
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
                    
                    try {
						placeService.updatePlaceAvgScore(Integer.parseInt(placeIds[i]));
					} catch (NumberFormatException | SQLException e) {
						e.printStackTrace();
					}  

				}
        }
				
        // 코스리뷰 수정하기-------------------------------------------------------------------
        try {
            courseReviewService.updateCourseReview(courseReview);
            courseService.updateCourseAvgScore(courseReview.getCourseId());
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
		
		try {
			courseService.updateCourseAvgScore(Integer.parseInt(courseId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "redirect:/course/courseDetail?courseId=" + courseId;
	}
	
	// 관리자 페이지 - courseReviewList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드.
	public HashMap<String, Object> getCourseReviewListValuesForAdmin(int pageNum, int pageSize, int groupNum,
			 												 		 String category, String keyword) {
		int allCourseReviewCount = 0;
		int searchedCourseReviewCount = 0;
		
		try {
			allCourseReviewCount = courseReviewService.getAllCourseReviewCount();
			if(category != null) {
				searchedCourseReviewCount = courseReviewService.getSearchedCourseReviewCount(category, keyword);
			}
		} catch (SQLException e) {
			log.warn("admin- 코스리뷰 수 조회에 실패하였습니다.");
			e.printStackTrace();
		}
		
		List<CourseReviewDTO> courseReviewList = new ArrayList<CourseReviewDTO>();
		
		if(category == null) {
			try {
				courseReviewList = courseReviewService.getAllCourseReviewByPage(pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 전체 코스리뷰리스트 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}else {
			try {
				courseReviewList = courseReviewService.getAllCourseReviewByKeywordWithPage(category, keyword, pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 검색된 코스리뷰리스트 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		
		HashMap<Integer, String> userNicknameList = new HashMap<Integer, String>();
		HashMap<Integer, String> courseNameList = new HashMap<Integer, String>();
		
		for(CourseReviewDTO courseReview : courseReviewList) {
			try {
				userNicknameList.put(courseReview.getUserId(), userService.getUserNicknameByUserId(courseReview.getUserId()));
				courseNameList.put(courseReview.getCourseId(), courseService.getCourseNameByCourseId(courseReview.getCourseId()));
			} catch (SQLException e) {
				log.warn("admin- 코스리뷰 리스트의 유저닉네임 / 코스이름 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		
		HashMap<String, Object> listValues = new HashMap<String, Object>();		
		listValues.put("allCourseReviewCount", allCourseReviewCount);
		listValues.put("courseReviewList", courseReviewList);
		listValues.put("userNicknameList", userNicknameList);
		listValues.put("courseNameList", courseNameList);
		
		
		int totalPages = 0;
		if(category == null) {
			if( (allCourseReviewCount / pageSize) < ((double)allCourseReviewCount / (double)pageSize) &&
				((double)allCourseReviewCount / (double)pageSize) < (allCourseReviewCount / pageSize) + 1 ) {
				totalPages = (allCourseReviewCount / pageSize) + 1;
			} else {
				totalPages = (allCourseReviewCount / pageSize);
			}
	
			int totalGroups = 0;
			if( (totalPages / 10) < ((double)totalPages / 10) &&
				((double)totalPages / 10) < (totalPages / 10) + 1 ) {
				totalGroups = (totalPages / 10) + 1;
			} else {
				totalGroups = (totalPages / 10);
			}
			
			
			for(int i = 1; i <= totalGroups; i++) {
				if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
					groupNum = i;
					break;
				}
			}
			
			listValues.put("pageNum", pageNum);
			listValues.put("pageSize", pageSize);
			listValues.put("groupNum", groupNum);
			listValues.put("totalPages", totalPages);
			listValues.put("totalGroups", totalGroups);
			
		}else {
			if( (searchedCourseReviewCount / pageSize) < ((double)searchedCourseReviewCount / (double)pageSize) &&
					((double)searchedCourseReviewCount / (double)pageSize) < (searchedCourseReviewCount / pageSize) + 1 ) {
					totalPages = (searchedCourseReviewCount / pageSize) + 1;
				} else {
					totalPages = (searchedCourseReviewCount / pageSize);
				}
		
				int totalGroups = 0;
				if( (totalPages / 10) < ((double)totalPages / 10) &&
					((double)totalPages / 10) < (totalPages / 10) + 1 ) {
					totalGroups = (totalPages / 10) + 1;
				} else {
					totalGroups = (totalPages / 10);
				}
				
				
				for(int i = 1; i <= totalGroups; i++) {
					if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
						groupNum = i;
						break;
					}
				}
				
				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
				listValues.put("category", category);
				listValues.put("keyword", keyword);
		}
		
		return listValues;
	}
	
	
	
	// 관리자 페이지 - placeReviewList 페이지를 구성하는 데에 필요한 정보들을 담아 돌려주는 메서드.
	public HashMap<String, Object> getPlaceReviewListValuesForAdmin(int pageNum, int pageSize, int groupNum,
			 												 		String category, String keyword) {
		int allPlaceReviewCount = 0;
		int searchedPlaceReviewCount = 0;
		
		try {
			allPlaceReviewCount = placeReviewService.getAllPlaceReviewCount();
			if(category != null) {
				searchedPlaceReviewCount = placeReviewService.getSearchedPlaceReviewCount(category, keyword);
			}
		} catch (SQLException e) {
			log.warn("admin- 장소리뷰 수 조회에 실패하였습니다.");
			e.printStackTrace();
		}
		
		List<PlaceReviewDTO> placeReviewList = new ArrayList<PlaceReviewDTO>();
		
		if(category == null) {
			try {
				placeReviewList = placeReviewService.getAllPlaceReviewByPage(pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 전체 코스리뷰리스트 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}else {
			try {
				placeReviewList = placeReviewService.getAllPlaceReviewByKeywordWithPage(category, keyword, pageNum, pageSize);
			} catch (SQLException e) {
				log.warn("admin- 검색된 코스리뷰리스트 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		
		HashMap<Integer, String> userNicknameList = new HashMap<Integer, String>();
		HashMap<Integer, String> placeNameList = new HashMap<Integer, String>();
		
		for(PlaceReviewDTO placeReview : placeReviewList) {
			try {
				userNicknameList.put(placeReview.getUserId(), userService.getUserNicknameByUserId(placeReview.getUserId()));
				placeNameList.put(placeReview.getPlaceId(), placeService.getPlaceNameByPlaceId(placeReview.getPlaceId()));
			} catch (SQLException e) {
				log.warn("admin- 장소리뷰 리스트의 유저닉네임 / 장소이름 조회에 실패하였습니다.");
				e.printStackTrace();
			}
		}
		
		HashMap<String, Object> listValues = new HashMap<String, Object>();		
		listValues.put("allPlaceReviewCount", allPlaceReviewCount);
		listValues.put("placeReviewList", placeReviewList);
		listValues.put("userNicknameList", userNicknameList);
		listValues.put("placeNameList", placeNameList);
		
		
		int totalPages = 0;
		if(category == null) {
			if( (allPlaceReviewCount / pageSize) < ((double)allPlaceReviewCount / (double)pageSize) &&
				((double)allPlaceReviewCount / (double)pageSize) < (allPlaceReviewCount / pageSize) + 1 ) {
				totalPages = (allPlaceReviewCount / pageSize) + 1;
			} else {
				totalPages = (allPlaceReviewCount / pageSize);
			}
	
			int totalGroups = 0;
			if( (totalPages / 10) < ((double)totalPages / 10) &&
				((double)totalPages / 10) < (totalPages / 10) + 1 ) {
				totalGroups = (totalPages / 10) + 1;
			} else {
				totalGroups = (totalPages / 10);
			}
			
			
			for(int i = 1; i <= totalGroups; i++) {
				if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
					groupNum = i;
					break;
				}
			}
			
			listValues.put("pageNum", pageNum);
			listValues.put("pageSize", pageSize);
			listValues.put("groupNum", groupNum);
			listValues.put("totalPages", totalPages);
			listValues.put("totalGroups", totalGroups);
			
		}else {
			
			if( (searchedPlaceReviewCount / pageSize) < ((double)searchedPlaceReviewCount / (double)pageSize) &&
					((double)searchedPlaceReviewCount / (double)pageSize) < (searchedPlaceReviewCount / pageSize) + 1 ) {
					totalPages = (searchedPlaceReviewCount / pageSize) + 1;
				} else {
					totalPages = (searchedPlaceReviewCount / pageSize);
				}
		
				int totalGroups = 0;
				if( (totalPages / 10) < ((double)totalPages / 10) &&
					((double)totalPages / 10) < (totalPages / 10) + 1 ) {
					totalGroups = (totalPages / 10) + 1;
				} else {
					totalGroups = (totalPages / 10);
				}
				
				
				for(int i = 1; i <= totalGroups; i++) {
					if( (i-1) < ((double)pageNum / 10) && ((double)pageNum / 10) < i) {
						groupNum = i;
						break;
					}
				}
				
				listValues.put("pageNum", pageNum);
				listValues.put("pageSize", pageSize);
				listValues.put("groupNum", groupNum);
				listValues.put("totalPages", totalPages);
				listValues.put("totalGroups", totalGroups);
				listValues.put("category", category);
				listValues.put("keyword", keyword);
		}
		
		return listValues;
	}	
	
	
}
