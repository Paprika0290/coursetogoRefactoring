<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<style>
		.menu div {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
		
		
		.navigationBar {
		  position: absolute;
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
		}
		
		.menuItem a:hover {
		  text-decoration: underline;
		}
		
		.dropdown {
		  position: relative;
		  display: inline-block;
		}
		
		.dropbtn {
		  background: none;
		  border: none;
		  cursor: pointer;
		}
		
		.dropbtn img {
		  width: 30px;
		  height: 30px; 
		}
		
		.dropdown-content {
		  display: none;
		  position: absolute;
		  background-color: #f1f1f1;
		  min-width: 100px;
		  width: 120px;
		  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
		  z-index: 1;
		}
		
		.dropdown-content a, .dropdown-content span, .dropdown-content input {
		  padding: 15px 20px;
		  text-decoration: none;
		  display: block;
		  color: #333;
		  font-size: 0.85rem;
		  font-family: 'TheJamsil3Regular', sans-serif;
		}
		
		.dropdown-content a:hover {
		  background-color: #ddd;
		}
		
		.dropdown:hover .dropdown-content {
		  display: block;
		}
		
		.dropdown:hover .dropbtn {
		  background-color: #EBEBEB;
		}
		
		
	</style>
	
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">	
</head>
<body>
	<!-- navigation bar -->
    <nav class = "navigationBar">
    	<div class="menu">	
	    	<div class="menuItem"><a href = "/"><img src="/images/logo.png" width = "50px"></a></div>	
		    <div class="menuItem"><a href="/course/courseList"><span>코스 찾기</span></a></div>
		    <div class="menuItem"><a href="/course/courseMake"><span>코스 만들기</span></a></div>
		    <div class="menuItem"><a href="/user/myPage"><span>마이 페이지</span></a></div>
		    
		    <div class="menuItem" style="margin-top:5px; margin-left:25px;">
		    	<c:if test="${empty sessionScope.user.userId}">
   					<div class="loginbtn" >
		       			<a href="${sessionScope.loginApiURL}" ><img height="40" src="/images/naverLoginBtn.png"/></a>
		    		</div>
				</c:if>
  		    	<c:if test="${not empty sessionScope.user.userId}">
   					<div class="dropdown">
	  		    		<button class="dropbtn" style= "display: flex; align-items: center;">
						  	<img src="${sessionScope.user.userPhoto}" alt = "프로필 사진">&nbsp;&nbsp;&nbsp;
						  	<span style= "font-family: 'TheJamsil5Bold', sans-serif;">${sessionScope.user.userNickname} 님</span> 
						</button>
						  
					  <div class="dropdown-content">
					    <span>랄랄랄라</span>
					    <input type="button" class="edit-profile-btn" value="회원정보 수정" onclick="location.href='/user/updateUserInfo'"
					    	   style= "border: 0px solid; padding-left: 20px; cursor: pointer;">
					    <input type="button" class="logout-btn" value="로그아웃" onclick="location.href='/logout'"
					    	   style= "border: 0px solid; padding-left: 35px; cursor: pointer;">
					  </div>
				  	</div>	  		    	
	    		</c:if>
			
			</div>	    
		</div>
		

    </nav>

</body>
</html>