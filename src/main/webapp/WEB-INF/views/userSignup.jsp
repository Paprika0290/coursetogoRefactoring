<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 회원가입</title>
	
	<script>
	    var sessionValue = "<%= session.getAttribute("newUser") %>";
		console.log("세션 값은" + sessionValue);
	</script>
	
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
</head>
<body>
	<jsp:include page="components/navigation.jsp" />
	
	<div class= "mainContent" >
		<form action= "/signupDone" method = "POST" >
			<div style= "border: 1px solid #E5E5E5;
						 padding: 50px;
						 display: flex;
						 flex-direction:column;
					     align-items: center;">
				<span class= "fontTheJamsil" style= "font-size: 1.3rem; color:#3C3C3C;
													 margin-bottom: 30px;">
					회원가입을 진행합니다.
				</span>
				<img src="/images/profile(0).png" width= "50px" style= "margin-bottom: 30px;">
				<span class= "fontTheJamsil">
					님 안녕하세요!
					${sessionScope.newUser}
				</span>
			</div>
		</form>
	</div>
	
   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
</body>
</html>