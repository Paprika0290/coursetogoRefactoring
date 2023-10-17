<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지 - 코스</title>
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
	<c:set var = "courseInformList" value= "${adminCourseInfo['courseInformList']}" />
	<c:set var = "courseDetailPageList" value= "${adminCourseInfo['courseDetailPageList']}" />
	<c:set var = "totalCourseCount" value= "${adminCourseInfo['totalCourseCount']}" />
	<c:set var = "reviewCountList" value= "${adminCourseInfo['reviewCountList']}" />
	
	<c:set var = "pageNum" value= "${adminCourseInfo['pageNum']}" />
	<c:set var = "pageSize" value= "${adminCourseInfo['pageSize']}" />
	<c:set var = "groupNum" value= "${adminCourseInfo['groupNum']}" />
	<c:set var = "totalPages" value= "${adminCourseInfo['totalPages']}" />
	<c:set var = "totalGroups" value= "${adminCourseInfo['totalGroups']}" />

	<div class = "mainContent">
		<div id= subject style= "background-color: #F7F7F7; padding: 2%; margin-bottom: 10px;">
			<span style= "font-size: 15pt; margin-left: 20px;"><b>코스 관리 페이지</b></span>
		</div>
		<span style= "font-size: 11pt; margin-left: 20px;">
			&nbsp;&nbsp;&nbsp; 전체 코스 수 : <span style= "color: #4242D0;"><b>${totalCourseCount}</b></span> 개
		</span>
		<br><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 코스 이름 클릭 시 코스 삭제가 가능합니다.</span><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 평균 별점, 북마크 수 검색은 [검색값 이상의 값을 가진 코스]를 검색합니다.</span><br><br>
		<div style= "display: flex; align-items: center;">
			<select id= "selectedCategory" style= "text-align: center; margin-left: 3%; border: 1px solid #ADADAD; height: 24px;">
				<option value="entireCourse" selected>전체&nbsp;&nbsp;</option>
				<option value="userNickname">작성자 닉네임&nbsp;&nbsp;</option>
				<option value="courseName">코스 이름&nbsp;&nbsp;</option>
				<option value="avgScore">평균 별점&nbsp;&nbsp;</option>
				<option value="bookmarkCount">북마크 수&nbsp;&nbsp;</option>
			</select>&nbsp;
			<input type= "text" id="searchKeyword" style= "border: 1px solid #ADADAD; width: 150px; height: 20px;">&nbsp;&nbsp;
			<button id= "searchCourseButton" type= "button" class= "userAdminButton"><b>검색</b></button>
			
			<div style= "display: flex; flex-direction: row; margin-left: auto; margin-right: 3%; gap: 5%;">
				<button id= "deleteCourseButton" type= "button" class= "userAdminButton"
						style= "cursor: pointer; background-color: #E8704D; width: 90px; height: 30px;">
					<b>코스 삭제</b>
				</button>
				
				<button id= "deleteCourseButtonCommit" type= "button" class= "userAdminButton"
						style= "cursor: pointer; background-color: #77331F; width: 90px; height: 30px; display: none;">
					<b>삭제 확인</b>
				</button>
				
				<button id= "deleteCourseButtonCancel" type= "button" class= "userAdminButton"
						style= "cursor: pointer; width: 70px; height: 30px; display: none;">
					<b>취소</b>
				</button>
			</div>
		</div>
		<br>
		
			<script>
				var searchButton = document.getElementById("searchCourseButton");	
				
				searchButton.addEventListener('click', function() {
					var keyword = document.getElementById("searchKeyword").value.trim();
					var category = document.getElementById("selectedCategory").value;
							if(category === "entireCourse") {
								window.location.href= '/admin/course';
							}else {
								window.location.href= '/admin/course/' + category + '/' + keyword;
							}
		        });	
				
				var deleteButton = document.getElementById("deleteCourseButton");	
				
				deleteButton.addEventListener('click', function() {
					document.getElementById("deleteCourseButton").style.display = 'none';
					
					document.getElementById("deleteCheckHeader").style.display = '';
					document.getElementById("deleteCourseButtonCommit").style.display = '';
					document.getElementById("deleteCourseButtonCancel").style.display = '';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = '';
						});
						
				});
				
				var commitButton = document.getElementById("deleteCourseButtonCommit");
				var deleteCourses = [];
				
				commitButton.addEventListener('click', function() {
					var selectedCourses = document.querySelectorAll('[id^="courseIdForDelete"]');
					var needToConfirm = false;
					
					
					for(var i = 0; i < selectedCourses.length; i++) {
						if(selectedCourses[i].checked) {
							var deleteCourseId = selectedCourses[i].id.replace("courseIdForDelete", "");							
							deleteCourses.push(parseInt(deleteCourseId, 10));
							
							if(document.getElementById("courseBookmarkedCount" + deleteCourseId).textContent != 0 || document.getElementById("courseReviewCount" + deleteCourseId).textContent != 0){
								if(needToConfirm == false) {
									needToConfirm = true;
								}
							}
						}
					}
					
					if(needToConfirm == true) {
						alert("삭제될 코스 중 북마크되었거나, 리뷰가 남겨진 코스가 존재합니다. 삭제 전 확인 부탁드립니다.");
					}
					
					if(confirm("해당 코스들을 정말 삭제하나요?") == true) {
						axios.post("/admin/course/delete", deleteCourses)
							.then(response => {
								window.location.reload();
					        })
					        .catch(error => {
					        	window.location.href= '/error'
					        });
					}
				});
				
				
				var cancelButton = document.getElementById("deleteCourseButtonCancel");
				
				cancelButton.addEventListener('click', function() {
					document.getElementById("deleteCourseButton").style.display = '';
					
					document.getElementById("deleteCheckHeader").style.display = 'none';
					document.getElementById("deleteCourseButtonCommit").style.display = 'none';
					document.getElementById("deleteCourseButtonCancel").style.display = 'none';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = 'none';
						});
						
				});
			</script>
		
		<table id= "allCourseList" style= "position: absolute; width: 95%;">
				<tr>
					<th scope="col" id= "deleteCheckHeader" style= "width: 60px; display: none;">삭제여부</th>				
					<th scope="col" style= "width: 60px;">코스ID</th>
					<th scope="col" style= "width: 150px;">작성자 닉네임</th>
					<th scope="col" style= "width: 250px;">코스 이름</th>
					<th scope="col" style= "width: 50px;">장소 수</th>
					<th scope="col" style= "width: 60px;">평균 별점</th>
					<th scope="col" style= "width: 500px;">코스 설명</th>				
					<th scope="col" style= "width: 50px;">북마크 수 </th>
					<th scope="col" style= "width: 50px;">리뷰 수 </th>
				</tr>
			<c:forEach items="${courseInformList}" var= "courseInform" varStatus= "courseInformSt">
				<tr>
					<td style= "display: none;" class= "deleteCheckData">
						<input type="checkbox" id= "courseIdForDelete${courseInform.courseId}">
					</td>
					
					<td>${courseInform.courseId}</td>	
					<td id= "userNickname${courseInform.courseId}">${courseInform.userNickname}</td>	
					<td id= "courseName${courseInform.courseId}" style= "color: #4242D0; cursor: pointer;">${courseInform.courseName}</td>	
					<td id= "courseNumber${courseInform.courseId}">${courseInform.courseNumber}</td>	
					<td id= "courseAvgScore${courseInform.courseId}">${courseInform.courseAvgScore}</td>	
					<td id= "courseContent${courseInform.courseId}">${courseInform.courseContent}</td>	
					<td id= "courseBookmarkedCount${courseInform.courseId}">${courseInform.isBookMarked}</td>	
					<td id= "courseReviewCount${courseInform.courseId}">${reviewCountList.get(courseInformSt.index)}</td>	
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
						<a href= "/admin/course?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/course?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/course?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
			</c:if>
			
		</div>
		
		<br><br>
		
		
	</div>
	
	<script>	
		var courseAvgScores = document.querySelectorAll('[id^="courseAvgScore"]');
		var courseBookmarkedCounts = document.querySelectorAll('[id^="courseBookmarkedCount"]');
		var courseReviewCounts = document.querySelectorAll('[id^="courseReviewCount"]');
		
		for(var i = 0; i < courseAvgScores.length; i++) {
			courseAvgScores[i].textContent = (parseFloat(courseAvgScores[i].textContent)).toFixed(1);
			if (courseBookmarkedCounts[i].textContent == 0) {
					courseBookmarkedCounts[i].style.color = '#B4B4B4';
					courseBookmarkedCounts[i].style.backgroundColor = '#F7F7F7';				
			}
			
			if (courseReviewCounts[i].textContent == 0) {
					courseReviewCounts[i].style.color = '#B4B4B4';
					courseReviewCounts[i].style.backgroundColor = '#F7F7F7';				
			}
		}
		
	</script>
	
</body>
</html>