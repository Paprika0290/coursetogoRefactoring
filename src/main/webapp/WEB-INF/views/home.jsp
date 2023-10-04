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
						CourseToGo의 <span style= "font-size: 20pt; color: #FF962B;">&nbsp;&nbsp;최고 </span><span style= "font-size: 20pt; color: #00008b;">인-싸피플</span>
					</span>
					
					<div style= "text-align: center; display: flex; flex-direction: row; padding: 30px 80px; font-family: 'TheJamsil3Regular', sans-serif;
								 background-color: #ECF0F1; margin: 20px 10px;">
						<div class= "top3Container" id= "courseKing" style= "padding: 5px; border-radius: 5px; ">
							<div style= "background-color: #ffffff; padding: 20px; border-radius: 5px;">
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B;"> 코스를<br>가장 많이 만드신 </span><br>
								<span style= "font-family: 'TheJamsil5Bold', sans-serif;"> TOP3</span><br><br>
								<img src="/images/goldMedal.png"><br>${kingNicknameList[0]}<br><br>
								<img src="/images/silverMedal.png"><br>${kingNicknameList[1]}<br><br>
								<img src="/images/bronzeMedal.png"><br>${kingNicknameList[2]}	
							</div>	
						</div>					

						<div class= "top3Container" id= "courseReviewKing" style= "padding: 5px; margin-left: 30px;">
								<div style= "background-color: #ffffff; padding: 20px; border-radius: 5px;">			
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B;"> 리뷰를<br>가장 많이 남긴</span><br>
								<span style= "font-family: 'TheJamsil5Bold', sans-serif;"> TOP3</span><br><br>			
								<img src="/images/goldMedal.png"><br>${kingNicknameList[3]}<br><br>
								<img src="/images/silverMedal.png"><br>${kingNicknameList[4]}<br><br>
								<img src="/images/bronzeMedal.png"><br>${kingNicknameList[5]}	
							</div>
						</div>					
						
						<div class= "top3Container" id= "placeReviewKing" style= "padding: 5px; margin-left: 30px;">
								<div style= "background-color: #ffffff; padding: 20px; border-radius: 5px;">			
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B;"> 가장 다양한 장소에<br>리뷰를 남겨주신</span><br>		
								<span style= "font-family: 'TheJamsil5Bold', sans-serif;"> TOP3</span><br><br>							
								<img src="/images/goldMedal.png"><br>${kingNicknameList[6]}<br><br>
								<img src="/images/silverMedal.png"><br>${kingNicknameList[7]}<br><br>
								<img src="/images/bronzeMedal.png"><br>${kingNicknameList[8]}							
							</div>	
						</div>					
					</div>
				</div>					
			</div>
		</div>
	</div>
	
	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
</body>
</html>