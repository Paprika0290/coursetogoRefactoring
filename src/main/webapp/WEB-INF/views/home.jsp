<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.coursetogo.dto.user.CtgUserDTO" %>

<!DOCTYPE html>
<html>
<head>
    <title>CourseToGo</title>
    
	<c:if test="${not empty sessionScope.newUser}">
	    <script>
	        var sessionValue = "${sessionScope.newUser}";
	        
	        alert("가입되지 않은 사용자입니다. 회원가입 페이지로 이동합니다.");
	        window.location.href = "user/sign_up";
	    </script>
	</c:if>



    <style>	
		.mainContent {
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    height: calc(100vh - 100px);
		    text-align: center;
		}
		
		.recommendsContainer {
			display: flex;
			align-items: center;
			justify-content: center;
		    flex-direction: row;
		}
		
		.fontTheJamsil {
			font-family: 'TheJamsil5Bold', sans-serif;
		}
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">

</head>
<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent">	
		<div class = "textContents">
			<span class ="fontTheJamsil" style = "font-size: 2rem; color: #FF962B;">
				오늘을 함께하고 싶은 코스, 
			</span>
			<span class ="fontTheJamsil" style = "font-size: 2rem; color:#00008b; ">
				CourseToGo
			</span>
			
			<div class = "recommendsContainer" style = "margin-top: 80px;">
				<div class = "courseRecommend">
					<div class="fontTheJamsil"  style = "border: 1px solid #E5E5E5;
														 padding: 20px;">
						추천 코스
					</div>				
				</div>	
				<div class = "placeRecommend"  style = " margin-left: 50px;
														 border: 1px solid #E5E5E5;
														 padding: 20px;">
					<div class="fontTheJamsil">
						추천 장소
					</div>	
				</div>	
			</div>
			
			<div style = "margin-top: 80px;">
				<div class = "rankingContainer" style = "border: 1px solid #E5E5E5;
														 padding: 20px;">
					<span class ="fontTheJamsil" style = "font-size: 1.5rem; color:#3C3C3C;">
						CourseToGo의 인싸피플 (이 아래에 top3~ 들)
					</span>
				</div>					
			</div>
		</div>
	</div>
	
	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
</body>
</html>