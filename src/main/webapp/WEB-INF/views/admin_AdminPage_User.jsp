<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지 - 유저</title>
 	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
 	 
	<style>
		body {
			margin: 0;
		}
		
		.mainContent {
			font-family: 'TheJamsil3Regular', sans-serif;
		}
		
		table{
			border-collapse : collapse;
			font-size: 10pt;
			margin-left: 3%;
			margin-top: 5px;
		} 
		
		th,td{
		    height: 30px;
		    text-align: center;
		    border: 1px solid #CDCDCD;
		      
		    vertical-align: top;	
		    vertical-align: bottom; 
		    vertical-align: middle; 
		    }
		 
		 th {
		 	background-color: #F7F7F7;
		 }
		 
		 tr {
			background-color: #FFFFFF;
		 }
		 
		 input[type="text"] {
		 	text-align: center;
		 	background-color: #F5F5F5;
		 	height: 90%;
		 	width: 90%;
		 	border: 0;
		 }
		 
		 .userAdminButton {
		 	background-color: #7474D9;
		 	color: #ffffff;
		 	border: 0;
		 	border-radius: 3px;
		 	padding: 4px 10px;
		 	cursor: pointer;
		 }
		 
	</style>
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">	
	
<body>	
	<c:set var = "pageNum" value= "${adminUserInfo['pageNum']}" />
	<c:set var = "pageSize" value= "${adminUserInfo['pageSize']}" />
	<c:set var = "groupNum" value= "${adminUserInfo['groupNum']}" />
	<c:set var = "totalPages" value= "${adminUserInfo['totalPages']}" />
	<c:set var = "totalGroups" value= "${adminUserInfo['totalGroups']}" />

	<div class = "mainContent">
		<div id= subject style= "background-color: #F7F7F7; padding: 2%; margin-bottom: 10px;">
			<span style= "font-size: 15pt; margin-left: 20px;"><b>회원 관리 페이지</b></span>
		</div>
		<span style= "font-size: 11pt; margin-left: 20px;">
			&nbsp;&nbsp;&nbsp; 전체 회원 수 : <span style= "color: #4242D0;"><b>${adminUserInfo.allUserCount}</b></span> 명&nbsp;&nbsp;/&nbsp;&nbsp;
			탈퇴 회원 : <span style= "color: #4242D0;"><b>${adminUserInfo.unsignedUserCount}</b></span> 명&nbsp;&nbsp;/&nbsp;&nbsp;
			제재 회원 : <span style= "color: #4242D0;"><b></b></span> 명
		</span>
		<br><br><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 회원 닉네임 클릭 시 정보 수정이 가능합니다.</span>
		<table id= "allUserList" style= "position: absolute; width: 95%;">
				<tr>
					<th scope="col" style= "width: 60px;">회원ID</th>
					<th scope="col" style= "width: 150px;">닉네임</th>
					<th scope="col" style= "width: 80px;">성명</th>
					<th scope="col" style= "width: 180px;">이메일</th>
					<th scope="col" style= "width: 80px;">권한</th>
					<th scope="col" style= "width: 100px;">가입일자 </th>
					<th scope="col" style= "width: 100px;">탈퇴일자 </th>
					<th scope="col" style= "width: 500px;">자기소개 </th>					
					<th scope="col" style= "width: 70px;">코스 수 </th>					
					<th scope="col" style= "width: 70px;">코스<br>리뷰 수 </th>					
				</tr>
			<c:forEach items="${adminUserInfo.allUserList}" var= "user" varStatus= "userSt">
				<tr>
					<td>${user.userId}</td>	
					<td id= "userNickname${user.userId}" style= "color: #4242D0; cursor: pointer;">${user.userNickname}</td>	
					<td id= "userName${user.userId}">${user.userName}</td>	
					<td id= "userEmail${user.userId}">${user.userEmail}</td>	
					<td id= "userAdmin${user.userId}">
						<c:choose>
							<c:when test="${user.userAdmin == 0}">관리자</c:when>
							<c:when test="${user.userAdmin == 1}">일반회원</c:when>
							<c:when test="${user.userAdmin == 5}">탈퇴회원</c:when>
						</c:choose>
					</td>	
					<td id= "userSignDate${user.userId}">${user.userSignDate}</td>	
					<td id= "userUnsignDate${user.userId}">${user.userUnsignDate}</td>	
					<td id= "userIntroduce${user.userId}">${user.userIntroduce}</td>	
					
					<td id= "userCourseCount${user.userId}">${adminUserInfo.userCourseCountList.get(userSt.index)}</td>	
					<td id= "userCourseReviewCount${user.userId}">${adminUserInfo.userCourseReviewCountList.get(userSt.index)}</td>	
				</tr>
			</c:forEach>
		</table>
		
		<div id= "pageNumbersContainer" style= "font-family: 'TheJamsil3Regular', sans-serif; position: absolute; bottom: 5%; left: 50%;">
			<br>
			<c:if test= "${(groupNum * 10) <= totalPages}">
				<c:set var= "endPage" value= "${groupNum * 10}" />
			</c:if>
			<c:if test= "${(groupNum * 10) > totalPages}">
				<c:set var= "endPage" value= "${totalPages}" />
			</c:if>
			
			
			<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/user?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/user?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/user?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
			</c:if>
			
		</div>
		
		<br><br>
		<form id= "userUpdate" method= "POST" action= "/admin/user/update">
			<table id= "updateInfoTable" style= "display: none; position: absolute; left: 5%; top: 30%; width: 85%;
												 border: 2px solid #616161; border-radius: 10px; box-shadow: 5px 5px 5px 2px rgba(0, 0, 0, 0.15);
												 padding: 0px 50px;">
				<tr>
					<td colspan= "4" style= "font-size: 13pt; background-color: #F7F7F7; padding: 5px;"><b>회원 정보 수정</b>
						<input type= "hidden" id= "updateUserId" name= "userId">
					</td>
				</tr>
				<tr>
					<th scope="col" style= "background-color: #FFFFFF; width: 150px;">닉네임</th>
					<th scope="col" style= "background-color: #FFFFFF; width: 180px;">이메일</th>
					<th scope="col" style= "background-color: #FFFFFF; width: 80px;">권한</th>
					<th scope="col" style= "background-color: #FFFFFF; width: 500px;">자기소개</th>
				</tr>				
				<tr>
					<td><input type= "text" id= "updateUserNickname" name= "userNickname" style= "width: 95%;"></td>
					<td><input type= "text" id= "updateUserEmail" name= "userEmail" style= "width: 95%;"></td>
					<td>
						<select id= "updateUserAdmin" name= "userAdmin">
							<option value= "0">관리자</option>
							<option value= "1">일반회원</option>
							<option value= "5">탈퇴회원</option>
						</select>
					</td>
					<td><input type= "text" id= "updateUserIntroduce" name= "userIntroduce" style= "width: 98%;"></input>
				</tr>
				<tr style= "height: 50px;">
					<td colspan= "4">
						<button id= "updateUserButton" type= "submit" class= "userAdminButton"><b>확인</b></button>					
						<button id= "cancleUpdate" type= "button" class= "userAdminButton"><b>취소</b></button>					
					</td>
				</tr>
			</table>
		</form>
		
	</div>
	
	<script>
		var userAdmins = document.querySelectorAll('[id^="userAdmin"]');
		
			for (var i = 0; i < userAdmins.length; i++) {
			  if (userAdmins[i].textContent.trim() === '일반회원') {
			    userAdmins[i].style.backgroundColor = '#FFBD45';
			  }else if (userAdmins[i].textContent.trim() === '탈퇴회원') {
			    userAdmins[i].style.backgroundColor = '#E4E4E4';
			  }else if (userAdmins[i].textContent.trim() === '관리자') {
			    userAdmins[i].style.backgroundColor = '#7474D9';
			    userAdmins[i].style.color = '#ffffff';
			  }
			}
		
		var userCourseCounts = document.querySelectorAll('[id^="userCourseCount"]');
		var userCourseReviewCounts = document.querySelectorAll('[id^="userCourseReviewCount"]');
		
			for (var i = 0; i < userAdmins.length; i++) {
				  if (userCourseCounts[i].textContent == 0) {
					  userCourseCounts[i].style.color = '#B4B4B4';
					  userCourseCounts[i].style.backgroundColor = '#F7F7F7';
				  }
				}
			for (var i = 0; i < userAdmins.length; i++) {
				  if (userCourseReviewCounts[i].textContent == 0) {
					  userCourseReviewCounts[i].style.color = '#B4B4B4';
					  userCourseReviewCounts[i].style.backgroundColor = '#F7F7F7';
				  }
				}
			
		var userNicknames = document.querySelectorAll('[id^="userNickname"]');
		userNicknames.forEach(function(userNickname) {
			userNickname.addEventListener('click', function() {
				document.getElementById('updateInfoTable').style.display = '';
				
				var userId = userNickname.getAttribute('id').replace("userNickname", "");
				var userAdmin = document.getElementById('userAdmin' + userId).textContent.trim();
				
				document.getElementById('updateUserId').value = userId;
				document.getElementById('updateUserNickname').value = document.getElementById('userNickname' + userId).textContent;
				document.getElementById('updateUserEmail').value = document.getElementById('userEmail' + userId).textContent;
				
				if(userAdmin == "관리자") {
					document.getElementById('updateUserAdmin').value = 0;	
				}else if(userAdmin == "일반회원") {
					document.getElementById('updateUserAdmin').value = 1;	
				}else if(userAdmin == "탈퇴회원") {
					document.getElementById('updateUserAdmin').value = 5;	
				}

				document.getElementById('updateUserIntroduce').value = document.getElementById('userIntroduce' + userId).textContent;
			});
		});
	
		document.getElementById('cancleUpdate').addEventListener('click', function() {
			document.getElementById('updateInfoTable').style.display = 'none';
		});
		
	</script>
	
</body>
</html>