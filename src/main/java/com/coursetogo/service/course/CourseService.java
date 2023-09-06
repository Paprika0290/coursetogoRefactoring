package com.coursetogo.service.course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.course.CourseDTO;
import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.course.PageRequestDTO;
import com.coursetogo.mapper.course.CourseMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	

	public int insertCourse(CourseDTO course) throws Exception {
		int courseId = -1;
				
		 courseMapper.insertCourse(course);
		 courseId = course.getCourseId();	
			if(courseId == -1) {
				throw new Exception("no sequence");
			} else {
			
			}
				
			return courseId;
		
	}
	public List<CourseInformDTO>  getAllCourses() throws Exception {
	
		List<CourseInformDTO> res = new ArrayList<>();
				 res= courseMapper.getAllCourses();
				if(!res.isEmpty()) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	public CourseInformDTO  getCourseInformByCourseId(int courseId) throws Exception {
		
		CourseInformDTO res=null;
				 res= courseMapper.getCourseInformByCourseId(courseId);
				if(res!=null) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	public List<CourseInformDTO> getCourseInformByUserId(int userId) throws Exception {
		
		List<CourseInformDTO> res=null;
				 res= courseMapper.getCourseInformByUserId(userId);
				if(res!=null) {
					
				} else {
					throw new Exception("no data");
				}
				
				return res;
		
	}
	
	public CourseDTO getCourseById(int courseId) throws SQLException {
		CourseDTO res;
		 res= courseMapper.getCourseById(courseId);
		if(res == null) {
			log.warn("no data - getCourseById");
		} else {
			
		}
		
		return res;
	}

	public int getTotalCount(PageRequestDTO pageRequest) throws SQLException {
		return courseMapper.getTotalCount(pageRequest);
	}
	
	public List<CourseInformDTO> getCourseWithPageRequest(PageRequestDTO pageRequest) throws Exception {
		List<CourseInformDTO> res;
	    res= courseMapper.getCourseWithPageRequest(pageRequest);	
		return res;
	}



	
	
	// 코스작성왕
	public List<Integer> getCourseTop3() throws SQLException	{
		return courseMapper.getCourseTop3();
	}
}