package com.coursetogo.service.user;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.mapper.user.CtgUserMapper;

@Service
public class CtgUserService {
	
	@Autowired
	private CtgUserMapper mapper;
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체를 받아와 회원 가입 진행하는 메서드---------------------------------------------------------------
	// 성공:true 실패:false
	public boolean insertCtgUser(CtgUserDTO user) throws SQLException {

		int res = mapper.insertCtgUser(user);

		if(res != 0) {
			return true;
		}
		return false;
	}
	
	
	// 네이버 회원 정보가 맵핑된 CtgUser객체에서 받아온 naverId, userName 이용하여 기존에 존재하는 회원인지 확인하는 메서드----------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null	
	public CtgUserDTO getCtgUserByNaverIdAndName(String naverIdFront, String naverIdRear, String userName) throws SQLException {	
		return mapper.getCtgUserByNaverIdAndName(naverIdFront, naverIdRear, userName);
	}
	
	
	// userId를 받아 회원DTO를 가져오는 메서드---------------------------------------------------------------------------------------
	// 존재하면 : 해당 CtgUser 객체 / 존재하지 않으면 : null	
	public CtgUserDTO getCtgUserByUserId(int userId) throws SQLException{
		return mapper.getCtgUserByUserId(userId);
	}	
	
	
	// 수정된 userDTO를 변수로 받아 업데이트하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false	
	public boolean updateCtgUser(CtgUserDTO ctgUser) throws Exception{
		boolean result = false;
		
		int res = mapper.updateCtgUser(ctgUser);
		
		if(res !=0) {
			result = true;
		} else {
			System.out.println("수정 실패");
			throw new Exception("개인 정보 수정 실패");
		}
				
		return result;
	}
	
	
	// 회원 탈퇴를 진행하는 메서드---------------------------------------------------------------------------------------
	// 성공하면 : true / 실패하면 : false
	public boolean unsignCtgUserByUserId(int userId) throws Exception{
		boolean result = false;
		
		int res = mapper.unsignCtgUserByUserId(userId);
		
		if(res != 0) {
			System.out.println("유저 삭제 완료");
			result = true;
		} else {
			throw new Exception("유저 삭제 실패");
		}

		return result;
	}
	
	// 닉네임 중복 검증
	public int nicknameCheck(String userNickname) throws SQLException{
		return mapper.nicknameCheck(userNickname);
	}

	// 나의 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyCourseCount(int userId) throws SQLException {
		return mapper.getMyCourseCount(userId);
	}
	
	// 찜한 코스 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyBookmarkCount(int userId) throws SQLException{
		return mapper.getMyBookmarkCount(userId);
	}
	
	// 나의 리뷰 개수 가져오는 메서드--------------------------------------------------------------
	public int getMyReviewCount(int userId) throws SQLException{
		return mapper.getMyReviewCount(userId);	
	}

}