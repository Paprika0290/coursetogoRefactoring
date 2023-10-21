package com.coursetogo.mapper.user;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coursetogo.dto.user.CtgUserDTO;

@Mapper
public interface CtgUserMapper {
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체를 받아와 회원 가입 진행하는 메서드---------------------------------------------------------------
	public int insertCtgUser(CtgUserDTO user) throws SQLException;
	
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체에서 받아온 naverId, userName 이용하여 기존에 존재하는 회원인지 확인하는 메서드----------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null	
	public CtgUserDTO getCtgUserByNaverIdAndName(@Param("naverIdFront") String naverIdFront,
											  @Param("naverIdRear") String naverIdRear,
											  @Param("userName") String userName) throws SQLException;	
	
	
	// userId를 받아 회원DTO를 가져오는 메서드---------------------------------------------------------------------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null	
	public CtgUserDTO getCtgUserByUserId(@Param("userId") int userId) throws SQLException;
	
	
	// 수정된 userDTO를 변수로 받아 업데이트하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false	
	public int updateCtgUser(CtgUserDTO ctgUser) throws SQLException;
	
	
	// 회원 탈퇴를 진행하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false
	public int unsignCtgUserByUserId(int userId) throws SQLException;
	
	// 닉네임 중복 검증 메서드-------------------------------------------------------------------
	public int nicknameCheck(@Param("userNickname") String userNickname);
	
	// 전체 유저 수 확인 (관리자 페이지)
	public int getAllUserCount() throws SQLException;
	
	// 탈퇴한 유저 수 확인 (관리자 페이지)
	public int getUnsignedUserCount() throws SQLException;
	
	// 전체 유저 정보 확인 (관리자 페이지)
	public List<CtgUserDTO> getAllUserList() throws SQLException;	

	// 전체 유저 정보 확인 / 페이지네이션(관리자 페이지)
	public List<CtgUserDTO> getAllUserListByPage(@Param("startRow") int startRow, @Param("endRow") int endRow) throws SQLException;
	
	// 검색된 유저 정보 확인 / 페이지네이션(관리자 페이지)
	public List<CtgUserDTO> getUserListByKeywordWithPage(@Param("startRow") int startRow, @Param("endRow") int endRow,
														 @Param("category") String category, @Param("keyword") String keyword) throws SQLException;

	// 검색된 유저 수 확인(관리자 페이지)
	public int getSearchedUserCount(@Param("category") String category, @Param("keyword") String keyword) throws SQLException;
	
	// 유저 ID로 유저 닉네임 검색
	public String getUserNicknameByUserId(int userId) throws SQLException;
	
	// 나의 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getUserCourseCount(int userId) throws SQLException;


	

	








}