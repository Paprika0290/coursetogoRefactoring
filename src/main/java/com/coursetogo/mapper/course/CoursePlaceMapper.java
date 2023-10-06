package com.coursetogo.mapper.course;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.coursetogo.dto.course.CoursePlaceDTO;

@Mapper
public interface CoursePlaceMapper {

	public int insertCoursePlace(CoursePlaceDTO coursePlace) throws SQLException;

	public int deleteCoursePlace(int courseId) throws SQLException;
	
}
