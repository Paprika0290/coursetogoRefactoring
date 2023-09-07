package com.coursetogo.mapper.review;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.review.CourseReviewDTO;

@Mapper
public interface ReviewMapper {
	
   public List<CourseReviewDTO> getCourseReviewByCourseId (@Param("courseId") int courseReviewId) throws SQLException;


}
