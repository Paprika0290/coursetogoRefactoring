<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.coursetogo.dto.user.CtgUserDTO" %>

<!DOCTYPE html>
<html>
<head>
    <title>CourseToGo</title>
    <link rel="stylesheet" type="text/css" href="/css/starScore.css">
         
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
    </style>
    
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">

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
                                     <c:forEach items="${courseDetailPageList}" var="coursePage" varStatus="pageSt">
				                        <c:if test="${courseSt.index eq pageSt.index}">
				                           <c:set var="query" value="${coursePage}" scope="request" />
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
										< <a href="/course/courseDetail?${query}" style= "text-decoration: none; color: #FF962B;">${course.courseName}</a> >
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
							<img src="/images/goldMedal.png"><br>${kingNicknameList[0]}<br><br>
							<img src="/images/silverMedal.png"><br>${kingNicknameList[1]}<br><br>
							<img src="/images/bronzeMedal.png"><br>${kingNicknameList[2]}	
						</div>
						<div class= "top3Container" id= "courseReviewKing">
							<img src="/images/goldMedal.png"><br>${kingNicknameList[3]}<br><br>
							<img src="/images/silverMedal.png"><br>${kingNicknameList[4]}<br><br>
							<img src="/images/bronzeMedal.png"><br>${kingNicknameList[5]}
						</div>
						<div class= "top3Container" id= "placeReviewKing">
							<img src="/images/goldMedal.png"><br>${kingNicknameList[6]}<br><br>
							<img src="/images/silverMedal.png"><br>${kingNicknameList[7]}<br><br>
							<img src="/images/bronzeMedal.png"><br>${kingNicknameList[8]}	
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