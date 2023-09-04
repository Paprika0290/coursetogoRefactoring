<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<style>
		.menu div {
		    font-family: 'Noto Sans KR';
		}
		
		.navigationBar {
		  position: fixed;
		  top: 0;
		  left: 0;
		  right: 0;
		  background-color: #ffffff;
		  color: #fff;
		  padding: 10px 0;
		  z-index: 1000;
 		  text-align: center; /* 가로 정렬을 중앙으로 설정 */
 		  font-family: 'Noto Sans KR';
 		  box-shadow: 0px 0px 8px 2px rgba(0, 0, 0, 0.1);
 		  
		}
		
		.menuItem {
		  display: inline-block; /* 요소들을 가로로 나열 */
	      vertical-align: middle; /* 세로 정렬 */ 
		  margin-right: 20px;
		}
		
		.menuItem:last-child {
		  margin-right: 0;
		}
		
		.menuItem a {
		  text-decoration: none;
		  color: #000000;
		  font-weight: bold;
		}
		
		.menuItem a:hover {
		  text-decoration: underline;
		}
		
		/* 드롭다운 버튼 컨테이너 스타일 */
		.dropdown {
		  position: relative;
		  display: inline-block;
		}
		
		/* 드롭다운 버튼 스타일 */
		.dropbtn {
		  background: none; /* 배경 제거 */
		  border: none;
		  cursor: pointer;
		}
		
		/* 이미지 크기 및 스타일 조정 */
		.dropbtn img {
		  width: 30px; /* 이미지 너비 조정 */
		  height: 30px; /* 이미지 높이 조정 */
		  /* 원하는 스타일 추가 */
		}
		
		/* 드롭다운 내용 (기본적으로 숨겨져 있음) */
		.dropdown-content {
		  display: none;
		  position: absolute;
		  background-color: #f1f1f1;
		  min-width: 100px;
		  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
		  z-index: 1;
		}
		
		/* 드롭다운 내용의 링크 스타일 */
		.dropdown-content a {
		  padding: 12px 16px;
		  text-decoration: none;
		  display: block;
		  color: #333;
		}
		
		/* 드롭다운 내용의 링크에 호버 스타일 추가 */
		.dropdown-content a:hover {
		  background-color: #ddd;
		}
		
		/* 드롭다운 내용이 보일 때 */
		.dropdown:hover .dropdown-content {
		  display: block;
		}
		
		/* 드롭다운 버튼에 호버 스타일 추가 */
		.dropdown:hover .dropbtn {
		  background-color: #2980b9;
		}
		
		
	</style>
	
</head>
<body>
	<!-- navigation bar -->
    <nav class = "navigationBar">
    	<div class="menu">	
	    	<div class="menuItem"><a href = "/"><img src="/images/logo.png" width = "50px"></a></div>	
		    <div class="menuItem"><a href="/"><span>코스 찾기</span></a></div>
		    <div class="menuItem"><a href="/"><span>코스 만들기</span></a></div>
		    <div class="menuItem"><a href="/"><span>마이 페이지</span></a></div>
		    
		    <div class="menuItem" style="margin-top:5px; margin-left:25px;">
		    	<c:if test="${empty sessionScope.user.userId}">
   					<div class="loginbtn" >
		       			<a href="${sessionScope.loginApiURL}" ><img height="40" src="/images/naverLoginBtn.png"/></a>
		    		</div>
				</c:if>
  		    	<c:if test="${not empty sessionScope.user.userId}">
   					<div class="dropdown">
	  		    		<button class="dropbtn">
						  	<img src="${sessionScope.user.userPhoto}" alt = "프로필 사진">
						  	<span class = "userName">${sessionScope.user.userNickname} 님</span> 
						</button>
						  
					  <div class="dropdown-content">
					    <a href="#">뱃지 시스템</a>
					    <a href="#">개인정보 수정</a>
					    <a href="#">로그아웃</a>
					  </div>
				  	</div>	  		    	
	    		</c:if>
			
			</div>	    
		</div>
		

    </nav>
    
</body>
</html>