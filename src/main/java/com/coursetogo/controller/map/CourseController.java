package com.coursetogo.controller.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CoursePlaceDTO;
import com.coursetogo.service.course.CoursePlaceService;
import com.coursetogo.service.course.CourseService;
import com.coursetogo.service.course.RankingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CoursePlaceService coursePlaceService;
	
	@Autowired
	private RankingService rankingService;
	
	
	public int insertCourse(CourseDTO course, String[] placeIds) {
		int result = -1;
		
		try {
			result = courseService.insertCourse(course);
		} catch (Exception e) {
			log.warn("새로운 course 만들기 실패");
			e.printStackTrace();
		}
		
		CoursePlaceDTO coursePlace = null;
		
		if(result != -1) {			
			for(int i = 0; i < placeIds.length; i++) {
				coursePlace = CoursePlaceDTO.builder().courseId(result).placeId(Integer.parseInt(placeIds[i])).selectionOrder(i+1).build();
				try {
					coursePlaceService.insertCoursePlace(coursePlace);
				} catch (Exception e) {
					log.warn("coursePlace 관계 생성 실패");
					e.printStackTrace();
				}
			}
		}
		
		return result; 
	}
	

}
