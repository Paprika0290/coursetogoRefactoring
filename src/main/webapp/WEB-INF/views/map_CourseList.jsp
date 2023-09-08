<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.coursetogo.dto.course.CourseInformDTO" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Set" %>
<!DOCTYPE html>
<html>
<head>
     <title>Searching Courses</title> 
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script> -->
 	 <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

	 <style>
		body {
			background-color: #F7F9F9;
		}
	
		.mainContent {
			margin-top: 100px;
		    display: flex;
		    justify-content: center;
		    align-items: center;
		    height: 80vh;
		    text-align: center;
		    overflow: auto;
		    padding-top: 100px;
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
	 </style>
	 
</head>

<body>
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent">	
		<div id="courseContainer" style= "width: 85%; max-height: 100vh;">
    	 	<c:forEach items="${requestScope.courseInformList}" var="courseInformDTO" varStatus="courseSt">
				<div class= "courseBox">
					<div id= "courseTitle" style= "display: flex; align-items: center;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
							<c:forEach items="${requestScope.userPhotoSrcList}" var= "userPhotoSrc" varStatus= "photoSt">
								<c:if test="${courseSt.index eq photoSt.index}">
									<c:set var="photoSrc" value="${userPhotoSrc}" scope= "request" />
								</c:if>
							</c:forEach>			
							
						<img class="userPhotoOfCourse" src="${photoSrc}" style= "width: 30px;">				
							<c:forEach items="${requestScope.courseDetailPageList}" var="coursePage" varStatus="pageSt">
			    	 			<c:if test="${courseSt.index eq pageSt.index}">
			    	 				<c:set var="query" value="${coursePage}" scope="request" />
			    	 			</c:if>
			    	 		</c:forEach>
						<p style= "position: flex; flex-direction: row; justify-content: center;">
							&nbsp;&nbsp;&nbsp;
							${courseInformDTO.userNickname} 님의 < <a href="/course/courseDetail?${query}" style= "text-decoration: none;"><span style="color: #FF962B;">${courseInformDTO.courseName}</span></a> > 코스
						</p>
					</div>	
					
					<div class="inline-items" style = "border-radius:5px;
	                								   display:flex;
	                								   align-items: center;
	                								   justify-content: center;">
	                								   
	                    <c:forEach items="${fn:split(courseInformDTO.courseList, ',')}" var="place">
	                        <div class="item" style = "padding:5px 10px;
	                        						   background-color: #7FB3D5;
	                        						   border-radius:5px;
	                        						   color: white;
	                        						   margin-right:15px;
	                        						   font-family: 'TheJamsil3Regular', sans-serif;">${place}</div>
	                    </c:forEach>
	                </div>
					
					<div style= "background-color: #F7F9F9; padding: 5px; margin-top: 10px;">
						<span>${courseInformDTO.courseContent}</span>
					</div>
				</div>
	        </c:forEach>
		</div>	
	</div>

   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
</body>
</html>