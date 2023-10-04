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
     <title>CourseToGo / 코스 찾기</title> 
     <link rel="stylesheet" type="text/css" href="/css/starScore.css">
 	 <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

	 <style>
		body {
			background-color: #F7F9F9;
		}
	
		.mainContent {
			margin-top: 30px;
		    display: absolute;
		    justify-content: center;
		    align-items: center;
		    height: 80vh;
		    text-align: center;
		    overflow: auto;
		}
		
		.searchContainer {
			margin-top: 100px;
			text-align: center;
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
	
	<c:set var = "courseInformList" value= "${ListValues['courseInformList']}" />
	<c:set var = "userPhotoSrcList" value= "${ListValues['userPhotoSrcList']}" />
	<c:set var = "courseDetailPageList" value= "${ListValues['courseDetailPageList']}" />
	<c:set var = "areaList" value= "${ListValues['areaList']}" />
	<c:set var = "totalCourseCount" value= "${ListValues['totalCourseCount']}" />
	
	<c:set var = "pageNum" value= "${ListValues['pageNum']}" />
	<c:set var = "pageSize" value= "${ListValues['pageSize']}" />
	<c:set var = "groupNum" value= "${ListValues['groupNum']}" />
	<c:set var = "totalPages" value= "${ListValues['totalPages']}" />
	<c:set var = "totalGroups" value= "${ListValues['totalGroups']}" />
	
	
	<div class= "searchContainer">
		<div id= "areaSearch" class= "searchInput" >
			<select style= "padding: 2px;
							text-align: center;
							width: 200px;
							border: 3px solid #FF962B;
							border-radius: 3px;"
					id= "areaSelect">
				  <option value="" selected>지역별 코스 검색 </option>	
				  <option value="전체"> 전체 &nbsp;&nbsp;</option>
				  <c:forEach items = "${areaList}" var="area" varStatus="areaSt">
				  		<option value="${area}">${area} &nbsp;&nbsp;</option>
				  </c:forEach>					  							
			</select>
		</div>
		
		<script>
			// <option>으로 주어지는 area값중 어떤 값이 선택될 시, API 요청 발생
			var areaSelected = document.getElementById('areaSelect');
			
			areaSelected.addEventListener('change', function() {
				  var selectedValue = areaSelected.value;
				  console.log(selectedValue);
				  window.location.href= '/course/courseList?areaName=' + selectedValue;
			})
		</script>
		
	</div>
	
	<div class= "mainContent">
		<c:forEach items= "${courseInformList}" var= "courseInformDTO" varStatus= "courseSt">
 			<div class= "courseBox">
               <div id= "courseTitle" style= "display: flex; align-items: center; width: 90vw;">
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                  
                     <c:forEach items="${userPhotoSrcList}" var= "userPhotoSrc" varStatus= "photoSt">
                        <c:if test="${courseSt.index eq photoSt.index}">
                           <c:set var="photoSrc" value="${userPhotoSrc}" scope= "request" />
                        </c:if>
                     </c:forEach>         
                     
                  <img class="userPhotoOfCourse" src="${photoSrc}" style= "width: 30px;">            
                     <c:forEach items="${courseDetailPageList}" var="coursePage" varStatus="pageSt">
                          <c:if test="${courseSt.index eq pageSt.index}">
                             <c:set var="query" value="${coursePage}" scope="request" />
                          </c:if>
                       </c:forEach>
                  <p style= "position: flex; flex-direction: row; justify-content: center;">
                     &nbsp;&nbsp;&nbsp;
                     ${courseInformDTO.userNickname} 님의 < <a href="/course/courseDetail?${query}" style= "text-decoration: none;"><span style="color: #FF962B;">${courseInformDTO.courseName}</span></a> > 코스 &nbsp;&nbsp;
                  </p>
                  <div style = "background-color: #eeeeee; display: flex; align-items: center; border-radius: 5px; padding: 0px 10px;" >
                     <span style = "color: #636363;"> 평균 별점 : </span>
                     <div class="stars small" id= "courseAvgScore${courseSt.index}" style= "margin-left: 10px; font-size: 20px; display: flex; justify-content: flex-end; " data-score="${courseInformDTO.courseAvgScore}"></div>
                              <script>
                                 var courseStars = document.getElementById("courseAvgScore${courseSt.index}");
                                 courseStars.setAttribute("data-score", Math.floor(courseStars.getAttribute("data-score")));
                              </script>
                  </div>    
                 
                 <c:if test= "${not empty sessionScope.user}">
                 	  <c:if test= "${courseInformDTO.isBookMarked eq 0}">
                  		<img src= "/images/unBookmarked.png" width= "30px;" style= "position: flex; margin-left: auto; cursor: pointer;"
                  		 	id= "bookmarkIn${courseInformDTO.courseId}">
	                  </c:if>
	
	                  <c:if test= "${courseInformDTO.isBookMarked eq 1}">
	                  	<img src= "/images/bookmarked.png" width= "30px;" style= "position: flex; margin-left: auto; cursor: pointer;"
	                  		 id= "bookmarkOut${courseInformDTO.courseId}">
	                  </c:if>
                 </c:if>
                  
              	 
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
                  <span>"&nbsp; ${courseInformDTO.courseContent} &nbsp;"</span>
               </div>
            </div>
		</c:forEach>
		
		<div id= "pageNumbersContainer" style= "font-family: 'TheJamsil3Regular', sans-serif;">
			<br>
			<c:if test= "${(groupNum * 10) <= totalPages}">
				<c:set var= "endPage" value= "${groupNum * 10}" />
			</c:if>
			<c:if test= "${(groupNum * 10) > totalPages}">
				<c:set var= "endPage" value= "${totalPages}" />
			</c:if>
			
			
			
			<c:if test= "${totalPages > 1}">
				<c:if test= "${param.areaName == ''}">
					<c:if test= "${groupNum != 1}">
						<a href= "/course/courseList?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/course/courseList?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/course/courseList?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>
				
				<c:if test= "${param.areaName != ''}">
					<c:if test= "${groupNum != 1}">
						<a href= "/course/courseList?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}&areaName=${param.areaName}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/course/courseList?pageNum=${i}&pageSize=${pageSize}&areaName=${param.areaName}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/course/courseList?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}&areaName=${param.areaName}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>
			</c:if>
			
		</div>
	</div>
	
	<input type= "hidden" id= "userId" value= "${sessionScope.user.userId}">
	
	<script>
	var thisUserId = document.getElementById("userId").value;
	
	<!-- 회색 북마크 버튼 클릭시 북마크 추가 -->
	document.querySelectorAll('[id^="bookmarkIn"]').forEach(function(bookmarkIn) {
		bookmarkIn.addEventListener('click', function(event) {
			var targetIdIn = event.target.id;
	        var thisCourseIdIn = targetIdIn.replace("bookmarkIn", "");
			
	        console.log(targetIdIn);
	        console.log(thisCourseIdIn);
	        console.log(thisUserId);
	        
	        axios.post('/user/bookmark/insert',  null, {
	        	params: {
	        		courseId: thisCourseIdIn,
		        	userId: thisUserId	
	        	}
	        })
	        .then(function (response) {
	            if(response.data) {
	            	window.location.reload();
	            }
	        })
	        .catch(function (error) {
	            console.error("북마크 추가 요청 실패:", error);
	        });
	    });
	});

	<!-- 주황색 북마크 버튼 클릭시 북마크 추가 -->
	document.querySelectorAll('[id^="bookmarkOut"]').forEach(function(bookmarkOut) {
		bookmarkOut.addEventListener('click', function(event) {
			var targetIdOut = event.target.id;
	        var thisCourseIdOut = targetIdOut.replace("bookmarkOut", "");
			
	        console.log(targetIdOut);
	        console.log(thisCourseIdOut);
	        console.log(thisUserId);
	        
	        axios.post('/user/bookmark/delete',  null, {
	        	params: {
	        		courseId: thisCourseIdOut,
		        	userId: thisUserId	
	        	}
	        })
	        .then(function (response) {
	            if(response.data) {
	            	window.location.reload();
	            }
	        })
	        .catch(function (error) {
	            console.error("북마크 삭제 요청 실패:", error);
	        });
	    });
	});

	
	</script>
	
	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	

</body>
</html>