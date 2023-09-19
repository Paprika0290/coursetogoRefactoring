package com.coursetogo.service.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.CoursePlaceDTO;
import com.coursetogo.dto.course.PageRequestDTO;
import com.coursetogo.mapper.course.CourseMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
	
	@Autowired
	private CourseMapper mapper;
	

	public int insertCourse(CourseDTO course) throws Exception {
		int res = mapper.insertCourse(course);
		
		if(res == 0) {
			log.warn("새로운 course 만들기 실패");
		} else {
			log.info("새로운 course 만들기 성공");
		}
			
		return res;
		
	}
	public List<CourseInformDTO>  getAllCourses() throws Exception {
	
		List<CourseInformDTO> res = new ArrayList<>();
				 res= mapper.getAllCourses();
				if(!res.isEmpty()) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	public CourseInformDTO  getCourseInformByCourseId(int courseId) throws Exception {
		
		CourseInformDTO res=null;
				 res= mapper.getCourseInformByCourseId(courseId);
				if(res!=null) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	public List<CourseInformDTO> getCourseInformByUserId(int userId) throws Exception {
		
		List<CourseInformDTO> res=null;
				 res= mapper.getCourseInformByUserId(userId);
				if(res!=null) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	
	public CourseDTO getCourseById(int courseId) throws SQLException {
		CourseDTO res;
		 res= mapper.getCourseById(courseId);
		if(res == null) {
			log.warn("no data - getCourseById");
		} else {
			
		}
		
		return res;
	}

	public int getTotalCount(PageRequestDTO pageRequest) throws SQLException {
		return mapper.getTotalCount(pageRequest);
	}
	
	public List<CourseInformDTO> getCourseWithPageRequest(PageRequestDTO pageRequest) throws Exception {
		List<CourseInformDTO> res;
	    res= mapper.getCourseWithPageRequest(pageRequest);	
		return res;
	}

	

	
	// 코스작성왕
	public List<Integer> getCourseTop3() throws SQLException	{
		return mapper.getCourseTop3();
	}
}