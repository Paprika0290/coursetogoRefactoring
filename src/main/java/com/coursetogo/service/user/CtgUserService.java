package com.coursetogo.service.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coursetogo.dto.course.CourseInformDTO;
import com.coursetogo.dto.user.CtgUserDTO;
import com.coursetogo.mapper.user.CtgUserMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
			log.info("유저 정보 업데이트 성공");
		} else {
			log.warn("유저 정보 업데이트 실패");
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
			log.info("유저 탈퇴 처리 완료");
			result = true;
		} else {
			log.warn("유저 탈퇴 처리 실패");
		}

		return result;
	}
	
	// 닉네임 중복 검증
	public int nicknameCheck(String userNickname) throws SQLException{
		return mapper.nicknameCheck(userNickname);
	}
	
	// 전체 유저 수 확인 (관리자 페이지)
	public int getAllUserCount() throws SQLException {
		return mapper.getAllUserCount();
	}
	
	// 탈퇴한 유저 수 확인 (관리자 페이지)
	public int getUnsignedUserCount() throws SQLException {
		return mapper.getUnsignedUserCount();
	}
	
	// 전체 유저 정보 확인 (관리자 페이지)
	public List<CtgUserDTO> getAllUserList() throws SQLException {
		return mapper.getAllUserList();
	}
	
	// 전체 유저 정보 확인 / 페이지네이션(관리자 페이지)
	public List<CtgUserDTO> getAllUserListByPage(int pageNum, int pageSize) throws SQLException {
		List<CtgUserDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		res= mapper.getAllUserListByPage(startRow, endRow);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 유저(+페이지네이션) 검색 실패");
		}
		return res;
	}

	// 검색된 유저 정보 확인 / 페이지네이션(관리자 페이지)
	public List<CtgUserDTO> getUserListByKeywordWithPage(int pageNum, int pageSize, String category, String keyword) throws SQLException {
		List<CtgUserDTO> res = new ArrayList<>();
		
		int startRow = ((pageNum-1) * pageSize) + 1;
		int endRow = ((pageNum-1) * pageSize) + pageSize;
		
		res= mapper.getUserListByKeywordWithPage(startRow, endRow, category, keyword);
		
		if(!res.isEmpty()) {
		} else {
			log.warn("전체 유저(+페이지네이션) 검색 실패");
		}
		return res;
	}

	// 검색된 유저 수 확인(관리자 페이지)
	public int getSearchedUserCount(String category, String keyword) throws SQLException {
		return mapper.getSearchedUserCount(category, keyword);
	}
	
	// 유저 ID로 유저 닉네임 검색
	public String getUserNicknameByUserId(int userId) throws SQLException {
		return mapper.getUserNicknameByUserId(userId);
	}
	
}