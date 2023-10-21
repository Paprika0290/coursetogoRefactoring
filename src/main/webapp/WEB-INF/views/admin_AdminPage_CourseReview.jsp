<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지 - 코스리뷰</title>
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
	<c:set var = "allCourseReviewCount" value= "${adminCourseReviewInfo['allCourseReviewCount']}" />
	<c:set var = "courseReviewList" value= "${adminCourseReviewInfo['courseReviewList']}" />
	<c:set var = "userNicknameList" value= "${adminCourseReviewInfo['userNicknameList']}" />
	<c:set var = "courseNameList" value= "${adminCourseReviewInfo['courseNameList']}" />
	
	<c:set var = "pageNum" value= "${adminCourseReviewInfo['pageNum']}" />
	<c:set var = "pageSize" value= "${adminCourseReviewInfo['pageSize']}" />
	<c:set var = "groupNum" value= "${adminCourseReviewInfo['groupNum']}" />
	<c:set var = "totalPages" value= "${adminCourseReviewInfo['totalPages']}" />
	<c:set var = "totalGroups" value= "${adminCourseReviewInfo['totalGroups']}" />

	<div class = "mainContent">
		<div id= subject style= "background-color: #F7F7F7; padding: 2%; margin-bottom: 10px;">
			<span style= "font-size: 15pt; margin-left: 20px;"><b>코스리뷰 관리 페이지</b></span>
		</div>
		<span style= "font-size: 11pt; margin-left: 20px;">
			&nbsp;&nbsp;&nbsp; 전체 코스리뷰 수 : <span style= "color: #4242D0;"><b>${allCourseReviewCount}</b></span> 개
		</span>
		<br><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 코스리뷰 이름 클릭 시 해당 코스 상세 페이지 조회가 가능합니다.</span><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 별점 검색은 [검색값 이상의 값을 가진 코스리뷰]를 검색합니다.</span><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 작성일자 검색은 [검색 일자 이후에 작성된 코스리뷰]를 검색합니다.</span><br><br>		
		<div style= "display: flex; align-items: center;">
			<select id= "selectedCategory" style= "text-align: center; margin-left: 3%; border: 1px solid #ADADAD; height: 24px;">
				<option value="entireReview" selected>전체&nbsp;&nbsp;</option>
				<option value="userNickname">작성자 닉네임&nbsp;&nbsp;</option>
				<option value="courseName">코스 이름&nbsp;&nbsp;</option>
				<option value="score">별점&nbsp;&nbsp;</option>
				<option value="reviewDate">작성 일자&nbsp;&nbsp;</option>
			</select>&nbsp;
			<input type= "text" id="searchKeyword" style= "border: 1px solid #ADADAD; width: 150px; height: 20px;" value= "">&nbsp;&nbsp;
			
			<button id= "searchReviewButton" type= "button" class= "userAdminButton"><b>검색</b></button>
			
			<div style= "display: flex; flex-direction: row; margin-left: auto; margin-right: 3%; gap: 5%;">
				<button id= "deleteReviewButton" type= "button" class= "userAdminButton"
						style= "cursor: pointer; background-color: #E8704D; width: 90px; height: 30px;">
					<b>리뷰 삭제</b>
				</button>
				
				<button id= "deleteReviewButtonCommit" type= "button" class= "userAdminButton"
						style= "cursor: pointer; background-color: #77331F; width: 90px; height: 30px; display: none;">
					<b>삭제 확인</b>
				</button>
				
				<button id= "deleteReviewButtonCancel" type= "button" class= "userAdminButton"
						style= "cursor: pointer; width: 70px; height: 30px; display: none;">
					<b>취소</b>
				</button>
			</div>
		</div>
		<br>
		
			<script>
				var searchButton = document.getElementById("searchReviewButton");	
				
				searchButton.addEventListener('click', function() {
					var keyword = document.getElementById("searchKeyword").value.trim();
					var category = document.getElementById("selectedCategory").value;
							if(category === "entireReview") {
								window.location.href= '/admin/courseReview';
							}else {
								if(keyword === "") {
									keyword = "none";
								}
								window.location.href= '/admin/courseReview/' + category + '/' + keyword;
							}
		        });	
				
				var deleteButton = document.getElementById("deleteReviewButton");	
				
				deleteButton.addEventListener('click', function() {
					document.getElementById("deleteReviewButton").style.display = 'none';
					
					document.getElementById("deleteCheckHeader").style.display = '';
					document.getElementById("deleteReviewButtonCommit").style.display = '';
					document.getElementById("deleteReviewButtonCancel").style.display = '';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = '';
						});
						
				});
				
				var commitButton = document.getElementById("deleteReviewButtonCommit");
				var deleteReviews = [];
				
				commitButton.addEventListener('click', function() {
					var selectedReviews = document.querySelectorAll('[id^="reviewIdForDelete"]');
					var needToConfirm = false;
					
					
					for(var i = 0; i < selectedReviews.length; i++) {
						if(selectedReviews[i].checked) {
							var deleteReviewId = selectedReviews[i].id.replace("reviewIdForDelete", "");							
							deleteReviews.push(parseInt(deleteReviewId, 10));
							deleteReviews.push(parseInt(document.getElementById("courseId"+deleteReviewId).value, 10));
						}
					}
					
					if(confirm("해당 리뷰들을 정말 삭제하나요?") == true) {
						axios.post("/admin/courseReview/delete", deleteReviews)
							.then(response => {
								window.location.reload();
					        })
					        .catch(error => {
					        	window.location.href= '/error'
					        });
					}
				});
				
				
				var cancelButton = document.getElementById("deleteReviewButtonCancel");
				
				cancelButton.addEventListener('click', function() {
					document.getElementById("deleteReviewButton").style.display = '';
					
					document.getElementById("deleteCheckHeader").style.display = 'none';
					document.getElementById("deleteReviewButtonCommit").style.display = 'none';
					document.getElementById("deleteReviewButtonCancel").style.display = 'none';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = 'none';
						});
						
				});
			</script>
		
		<table id= "allCourseReviewList" style= "position: absolute; width: 95%;">
				<tr>
					<th scope="col" id= "deleteCheckHeader" style= "width: 60px; display: none;">삭제여부</th>				
					<th scope="col" style= "width: 60px;">리뷰ID</th>
					<th scope="col" style= "width: 150px;">작성자 닉네임</th>
					<th scope="col" style= "width: 250px;">코스 이름</th>
					<th scope="col" style= "width: 50px;">별점</th>
					<th scope="col" style= "width: 500px;">리뷰 내용</th>				
					<th scope="col" style= "width: 100px;">작성일자</th>
				</tr>
			<c:forEach items="${courseReviewList}" var= "courseReview" varStatus= "courseReviewSt">
				<tr>
					<td style= "display: none;" class= "deleteCheckData">
						<input type="checkbox" id= "reviewIdForDelete${courseReview.courseReviewId}">
					</td>
					
					<td>${courseReview.courseReviewId}</td>	
					<td id= "userNickName${courseReview.courseReviewId}">${userNicknameList.get(courseReview.userId)}</td>	
					<td id= "courseName${courseReview.courseReviewId}" style= "color: #4242D0; cursor: pointer;">
						<a href="/course/courseDetail?courseId=${courseReview.courseId}" style= "text-decoration: none;" target= "_blank">
							${courseNameList.get(courseReview.courseId)}
						</a>
					</td>	
					<td id= "reviewScore${courseReview.courseReviewId}">${courseReview.courseScore}</td>	
					<td id= "reviewContent${courseReview.courseReviewId}">${courseReview.content}</td>	
					<td id= "reviewDate${courseReview.courseReviewId}">${courseReview.reviewDate}</td>	
				</tr>
				<input type= "hidden" id= "courseId${courseReview.courseReviewId}" value= "${courseReview.courseId}"> 
			</c:forEach>
		</table>
		
		<div id= "pageNumbersContainer" style= "font-family: 'TheJamsil3Regular', sans-serif; position: absolute; bottom: 3%; left: 40%;">
			<br>
			<c:if test= "${(groupNum * 10) <= totalPages}">
				<c:set var= "endPage" value= "${groupNum * 10}" />
			</c:if>
			<c:if test= "${(groupNum * 10) > totalPages}">
				<c:set var= "endPage" value= "${totalPages}" />
			</c:if>
			
			<c:if test="${not empty adminCourseReviewInfo['category']}">

				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/courseReview/${adminCourseReviewInfo['category']}/${adminCourseReviewInfo['keyword']}?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/courseReview/${adminCourseReviewInfo['category']}/${adminCourseReviewInfo['keyword']}?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/courseReview/${adminCourseReviewInfo['category']}/${adminCourseReviewInfo['keyword']}?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>

			</c:if>
			<c:if test="${empty adminCourseReviewInfo['category']}">

				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/courseReview?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/courseReview?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/courseReview?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>

			</c:if>
			
			
			
		</div>
		
		<br><br>
		
		
	</div>

</body>
</html>