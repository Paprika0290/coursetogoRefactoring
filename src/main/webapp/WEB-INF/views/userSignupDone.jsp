<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.coursetogo.dto.user.CtgUserDTO" %>

<!DOCTYPE html>
<html>
<head>
    <title>CourseToGo / 회원가입</title>
    
	<c:if test="${not empty sessionScope.newUser}">
	    <script>
	        var sessionValue = "${sessionScope.newUser}";
	        
	        alert("가입되지 않은 사용자입니다. 회원가입 페이지로 이동합니다.");
	        window.location.href = "user/sign_up";
	    </script>
	</c:if>



    <style>	
		.mainContent {
			margin-top: 150px;
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    height: 100vh-100px;
		    text-align: center;
		}
		
		.startButton {
			background-color: #FF962B;
			padding: 10px;
			padding-left: 20px;
			padding-right: 20px;
			color: white;
   		    font-weight: bold;
  		    border-radius: 5px;
  		    border: 1px solid #FF962B;
  		    cursor: pointer;
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">

</head>
<body>

	<jsp:include page="components/navigation.jsp" />
	
	<div class = "mainContent">	
		<div class = "textContents" style = "border: 1px solid #E5E5E5; padding: 20px;">
			<p class = "fontTheJamsil" style= "color: #3C3C3C; margin: 50px 100px; margin-bottom: 100px;">
				<img src="/images/logo.png" width="100px"><br><br><br>
				<span style= "color: #FF962B;">${sessionScope.user.userNickname}</span> 님, 회원가입이 완료되었습니다. <br>
				<br>
				이제 <span style="color: #00008b;">코스 만들기 / 마이 페이지 기능</span>을<br>사용하실 수 있습니다.<br><br>
				감사합니다. 
				<br><br><br>
				<input type="button" class = "startButton" onclick="location.href='/'" value= "CourseToGo 시작하기!">
			</p>
			
			
		</div>
	</div>
	
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
</body>
</html>