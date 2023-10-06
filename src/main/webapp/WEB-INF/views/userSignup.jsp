<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 회원가입</title>
	
	<style>
		.mainContent {
			margin-top: 150px;
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    height: 100vh-100px;
		    text-align: center;
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
	</style>
	
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">

	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

</head>
<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class= "mainContent" >
		<form action= "/user/sign_up_done" id= "signUpForm" method = "POST" >
			<div style= "border: 1px solid #E5E5E5;
						 padding: 50px;
						 padding-left: 150px;
						 padding-right: 150px;
						 display: flex;
						 flex-direction:column;
					     align-items: center;">
				<span class= "fontTheJamsil" style= "font-size: 1.3rem; color:#3C3C3C;
													 margin-bottom: 30px;">
					회원가입을 진행합니다.
				</span>
				
				<img src="/images/profile(0).png" width= "50px" style= "margin-bottom: 10px;">
				<p class= "fontTheJamsil" style = "color: #3C3C3C; font-size: 1rem;">
					<span style = "color: #FF962B;">${sessionScope.newUser.userName}</span> 님 안녕하세요!
					<br><br>
					<span style = "background-color: #F1F1F1; padding: 5px; padding-left: 20px; padding-right: 20px;
								   border-radius: 10px;">
						${sessionScope.newUser.userEmail}
					</span>
					<br><br><br><br>
					닉네임 &nbsp;&nbsp;
					<input type= "text" id= "userNickname" name= "userNickname" value="${sessionScope.newUser.userNickname}" maxlength="8"
						   style= "background-color: #F1F1F1; border: 1px solid #F1F1F1;
						   		   padding: 3px; width: 180px; height: 15px;">&nbsp;&nbsp;&nbsp;
				 	<span id= "userNicknameCheck" style="display:block; font-size: 2px; width:150px; height:15px; margin-left: 50px; margin-top: 5px;"></span>
					<br>
					자기소개 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<br><br>
					<textarea rows= "4" cols= "30" name= "userIntroduce" id= "userIntroduceArea"
							  style= "background-color: #F1F1F1; border: 1px solid #F1F1F1;
						   		   padding: 3px; width: 250px; height: 80px;" maxlength="50"></textarea>				
				</p>
				<input type= "hidden" name= "naverId" value= "${sessionScope.newUser.naverId}">
				<input type= "hidden" name= "userName" value= "${sessionScope.newUser.userName}">
				<input type= "hidden" name= "userEmail" value= "${sessionScope.newUser.userEmail}">
				
				<input type= "submit" value= "회원가입" id= "signUpButton"
					   style= "background-color: #FF962B; padding: 10px; padding-left: 20px; padding-right: 20px; color: white;
					   		   font-weight: bold; border-radius: 5px; border: 1px solid #FF962B;">
				
			</div>
		</form>
	</div>
	
	<script>
	<!-- 닉네임 중복 확인 start-->
		var userNicknameInput = document.getElementById('userNickname');
		
	    userNicknameInput.addEventListener('change', function() {
		    var updatedValue = userNicknameInput.value;	
		    
 		      axios.get('/user/userNicknameCheck', {
				  params: {
					    userNickname: updatedValue
					  }
					})
			  .then(function (response) {
				  var res = response.data;
				  var userNicknameCheckSpan = document.getElementById('userNicknameCheck');

				  if(res === 1) {
					  userNicknameCheckSpan.innerHTML = '이미 사용중인 닉네임입니다.';
					  userNicknameCheckSpan.style.color = '#E74C3C';
				  }else if (res === 0) {
					  userNicknameCheckSpan.innerHTML = '사용 가능한 닉네임입니다.';
					  userNicknameCheckSpan.style.color = '#138D75';
				  } else if (res === -1) {
					  userNicknameCheckSpan.innerHTML = '닉네임은 비어있을 수 없습니다.';
					  userNicknameCheckSpan.style.color = '#E74C3C';
				    }
				  
			    console.log(response.data);
			  })
			  .catch(function (error) {
				  console.log("닉네임 검증 불가");
			  });
	    })
	<!-- 닉네임 중복 확인 end-->    
	    
	<!-- 자기소개 내용 확인 start-->
		document.getElementById('signUpButton').addEventListener('click', function(event) {
			var nicknameCheck = document.getElementById('userNicknameCheck').textContent;
			if(nicknameCheck !== "사용 가능한 닉네임입니다.") {
				alert('닉네임을 확인해주세요.');
				event.preventDefault();
			}
			
			var submitContent = document.getElementById('userIntroduceArea').value;
			if(submitContent.trim() === '') {
				alert('자기소개를 입력해주세요.');
				event.preventDefault();
			}
			
		});
	<!-- 자기소개 내용 확인 end-->    
    </script>
    
	<c:if test="${not empty sessionScope.newUser}">
		<c:remove var="newUser" scope="session" />
	</c:if>
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
</body>
</html>