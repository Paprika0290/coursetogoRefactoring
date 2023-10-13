package com.coursetogo.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CtgUserDTO {

    private int userId;	
	
	// 네이버API에서 받아오는 값----------------
    @JsonProperty("id")
    private String naverId;
    
    @JsonProperty("name")
    private String userName;
    
    // 회원가입시 수정 가능.
    @JsonProperty("nickname")
    private String userNickname;
    
    @JsonProperty("email")
    private String userEmail;    
    // -----------------------------------
    
    // 0: Admin / 1: User
    private int userAdmin;   
    
    // 회원가입시 기본값으로 insert
    private String userPhoto; 
    
    // 회원가입시 입력받은 값으로 insert
    private String userIntroduce; 
    
    @Builder // 세션 부여용 builder
    public CtgUserDTO(int userId, String userNickname, String userEmail, String userPhoto, int userAdmin, String userIntroduce) {
    	this.userId = userId;
    	this.userNickname = userNickname;
		this.userEmail = userEmail;
		this.userPhoto = userPhoto;
		this.userIntroduce = userIntroduce;
		this.userAdmin = userAdmin;
    }
    
}
