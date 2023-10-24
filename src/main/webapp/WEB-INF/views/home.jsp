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
			flex-direction: column;
		    height: calc(100vh - 100px);
		    text-align: center;
   		    overflow: auto;
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
		
		.courseBox {
			padding: 5px;
			background-color: #FFFFFF;
			border-radius:10px;
			border: 1px solid #E2E2E2;
	     	font-family: 'TheJamsil3Regular', sans-serif;
	     	color: #3C3C3C;
	     	margin-bottom: 10px;
		}
		
		.top3Container {
			background-color: #F5F5F5;
			border-radius: 2px;
			background-color: #ECF0F1;
			padding: 10px;
			width: 150px;
		}
		
		a {
		    text-decoration: none; 
		    color: inherit; 
		}
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">
    <link rel="stylesheet" type="text/css" href="/css/starScore.css">

</head>
<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent" style= "display: flex; align-items: center;">	
		<div class = "textContents" style= "margin-top: 100px;">
			<span class ="fontTheJamsil" style = "font-size: 2rem; color: #FF962B;">
				오늘을 함께하고 싶은 코스, 
			</span>
			<span class ="fontTheJamsil" style = "font-size: 2rem; color:#00008b; ">
				CourseToGo
			</span>
		</div>	
			<div class = "recommendsContainer" style = "margin-top: 80px; width: 100%;">
				<div class = "courseRecommend" style= "border: 1px solid #E5E5E5; padding: 20px; ">
					<div style = "font-size: 20pt; color: #00008b; font-family: 'TheJamsil5Bold', sans-serif;">
						추천 코스<br><br>
					</div>
					
					<div id= "recommendCourseContainer">
						<c:forEach items= "${recommendCourseList}" var= "course" varStatus= "courseSt">
							<div class= "courseBox">
								<div class= "courseTitle" style= "display: flex; flex-direction: row; align-items: center;">
									<c:forEach items="${userPhotoSrcList}" var= "userPhotoSrc" varStatus= "photoSt">
				                        <c:if test="${courseSt.index eq photoSt.index}">
				                           <c:set var="photoSrc" value="${userPhotoSrc}" scope= "request" />
				                        </c:if>
				                     </c:forEach>         
                                     <c:forEach items="${courseIdList}" var="courseId" varStatus="courseIdSt">
				                        <c:if test="${courseSt.index eq courseIdSt.index}">
				                           <c:set var="courseId" value="${courseId}" scope="request" />
				                        </c:if>
				                     </c:forEach>
                                     <c:forEach items="${userNicknameList}" var="userNickname" varStatus="userNicknameSt">
				                        <c:if test="${courseSt.index eq userNicknameSt.index}">
				                           <c:set var="nickName" value="${userNickname}" scope="request" />
				                        </c:if>
				                     </c:forEach>
				                     
				                     
				                  <img class="userPhotoOfCourse" src="${photoSrc}" style= "width: 30px; margin-left: 20px; margin-top: 5px;">&nbsp;&nbsp;&nbsp;&nbsp;   
									<div class= "userNickname"> ${nickName} 님의</div> &nbsp;&nbsp;&nbsp;
									<div class= "courseName">
										< <a href="/course/courseDetail?courseId=${courseId}" style= "text-decoration: none; color: #FF962B;">${course.courseName}</a> >
									</div>
									<div class="stars small" id= "courseAvgScore${courseSt.index}" style= "margin-left: 10px; font-size: 20px; display: flex;" data-score="${course.courseAvgScore}"></div>
		                              <script>
		                                 var courseStars = document.getElementById("courseAvgScore${courseSt.index}");
		                                 courseStars.setAttribute("data-score", Math.floor(courseStars.getAttribute("data-score")));
		                              </script>
								</div>
							    
							    <div style= "border-radius: 3px; padding: 15px; background-color: #ECF0F1; margin-top: 5px;">
							    	${course.courseContent}
							    </div>
							</div>
						</c:forEach>
					</div>				
				</div>	
			</div>
			
			<div style = "margin-top: 50px; margin-bottom: 50px; width: 700px;">
				<div class = "rankingContainer" style = "border: 1px solid #E5E5E5;
														 padding: 30px;">
					<span style = "font-size: 1.5rem; color:#3C3C3C; font-family: 'TheJamsil5Bold', sans-serif;">
						CourseToGo의 <span style= "font-size: 20pt; color: #FF962B;">&nbsp;&nbsp;최고 </span><span style= "font-size: 20pt; color: #00008b;">인-싸피플</span><br><br>
					</span>
					
					<div style= "display: flex; flex-direction: row; gap: 50px; justify-content: center; font-family: 'TheJamsil3Regular', sans-serif;">
						<div class= "top3Container" id= "courseKing">
							<div style= "background-color: #ffffff; padding: 5px;">
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B; border-radius: 10px;">코스를 가장 많이<br>만드신 분</span><br>
                            	<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #00008b;"> TOP3</span>
							</div>
							<br>
							<img src="/images/goldMedal.png"><br><a href="user/userPage?userId=${kingIdList[0]}">${kingNicknameList[0]}</a><br><br>
							<img src="/images/silverMedal.png"><br><a href="user/userPage?userId=${kingIdList[1]}">${kingNicknameList[1]}</a><br><br>
							<img src="/images/bronzeMedal.png"><br><a href="user/userPage?userId=${kingIdList[2]}">${kingNicknameList[2]}</a>	
						</div>
						<div class= "top3Container" id= "courseReviewKing">
							<div style= "background-color: #ffffff; padding: 5px;">
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B; border-radius: 10px;">가장 많은 코스를<br>다녀오신 분</span><br>
                            	<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #00008b;"> TOP3</span>
							</div>
							<br>
							<img src="/images/goldMedal.png"><br><a href="user/userPage?userId=${kingIdList[3]}">${kingNicknameList[3]}</a><br><br>
							<img src="/images/silverMedal.png"><br><a href="user/userPage?userId=${kingIdList[4]}">${kingNicknameList[4]}</a><br><br>
							<img src="/images/bronzeMedal.png"><br><a href="user/userPage?userId=${kingIdList[5]}">${kingNicknameList[5]}</a>
						</div>
						<div class= "top3Container" id= "placeReviewKing">
							<div style= "background-color: #ffffff; padding: 5px;">
								<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #FF962B; border-radius: 10px;">가장 다양한 장소를<br>다녀오신 분</span><br>
                            	<span style= "font-family: 'TheJamsil5Bold', sans-serif; color: #00008b;"> TOP3</span>
							</div>
							<br>
							<img src="/images/goldMedal.png"><br><a href="user/userPage?userId=${kingIdList[6]}">${kingNicknameList[6]}</a><br><br>
							<img src="/images/silverMedal.png"><br><a href="user/userPage?userId=${kingIdList[7]}">${kingNicknameList[7]}</a><br><br>
							<img src="/images/bronzeMedal.png"><br><a href="user/userPage?userId=${kingIdList[8]}">${kingNicknameList[8]}</a>	
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