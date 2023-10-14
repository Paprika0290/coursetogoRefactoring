package com.coursetogo.mapper.user;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBookmarkCourseMapper {
	
	// 해당 코스를 북마크 처리하는 메서드
	public int insertNewBookmark(@Param("userId") int userId, @Param("courseId") int courseId) throws SQLException;

	public int deleteBookmark(@Param("userId") int userId, @Param("courseId") int courseId) throws SQLException;

	public int deleteBookmarkByCourseId(@Param("courseId") int courseId) throws SQLException;

	// 유저가 찜한 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getUserBookmarkCount(int userId) throws SQLException;

	
}
