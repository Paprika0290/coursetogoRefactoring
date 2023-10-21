<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지 - 장소리뷰</title>
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
	<c:set var = "allPlaceReviewCount" value= "${adminPlaceReviewInfo['allPlaceReviewCount']}" />
	<c:set var = "placeReviewList" value= "${adminPlaceReviewInfo['placeReviewList']}" />
	<c:set var = "userNicknameList" value= "${adminPlaceReviewInfo['userNicknameList']}" />
	<c:set var = "placeNameList" value= "${adminPlaceReviewInfo['placeNameList']}" />
	
	<c:set var = "pageNum" value= "${adminPlaceReviewInfo['pageNum']}" />
	<c:set var = "pageSize" value= "${adminPlaceReviewInfo['pageSize']}" />
	<c:set var = "groupNum" value= "${adminPlaceReviewInfo['groupNum']}" />
	<c:set var = "totalPages" value= "${adminPlaceReviewInfo['totalPages']}" />
	<c:set var = "totalGroups" value= "${adminPlaceReviewInfo['totalGroups']}" />

	<div class = "mainContent">
		<div id= subject style= "background-color: #F7F7F7; padding: 2%; margin-bottom: 10px;">
			<span style= "font-size: 15pt; margin-left: 20px;"><b>장소리뷰 관리 페이지</b></span>
		</div>
		<span style= "font-size: 11pt; margin-left: 20px;">
			&nbsp;&nbsp;&nbsp; 전체 장소리뷰 수 : <span style= "color: #4242D0;"><b>${allPlaceReviewCount}</b></span> 개
		</span>
		<br><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 별점 검색은 [검색값 이상의 값을 가진 코스리뷰]를 검색합니다.</span><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 작성일자 검색은 [검색 일자 이후에 작성된 코스리뷰]를 검색합니다.</span><br><br>		
		<div style= "display: flex; align-items: center;">
			<select id= "selectedCategory" style= "text-align: center; margin-left: 3%; border: 1px solid #ADADAD; height: 24px;">
				<option value="entireReview" selected>전체&nbsp;&nbsp;</option>
				<option value="userNickname">작성자 닉네임&nbsp;&nbsp;</option>
				<option value="placeName">장소 이름&nbsp;&nbsp;</option>
				<option value="score">별점&nbsp;&nbsp;</option>
				<option value="reviewDate">작성 일자&nbsp;&nbsp;</option>
			</select>&nbsp;
			<input type= "text" id="searchKeyword" style= "border: 1px solid #ADADAD; width: 150px; height: 20px;" value= "">&nbsp;&nbsp;
			
			<button id= "searchReviewButton" type= "button" class= "userAdminButton"><b>검색</b></button>
		</div>
		<br>
		
			<script>
				var searchButton = document.getElementById("searchReviewButton");	
				
				searchButton.addEventListener('click', function() {
					var keyword = document.getElementById("searchKeyword").value.trim();
					var category = document.getElementById("selectedCategory").value;
							if(category === "entireReview") {
								window.location.href= '/admin/placeReview';
							}else {
								if(keyword === "") {
									keyword = "none";
								}
								window.location.href= '/admin/placeReview/' + category + '/' + keyword;
							}
		        });	
				</script>
		
		
		
		<table id= "allPlaceReviewList" style= "position: absolute; width: 95%;">
				<tr>
					<th scope="col" id= "deleteCheckHeader" style= "width: 60px; display: none;">삭제여부</th>				
					<th scope="col" style= "width: 60px;">리뷰ID</th>
					<th scope="col" style= "width: 150px;">작성자 닉네임</th>
					<th scope="col" style= "width: 250px;">장소 이름</th>
					<th scope="col" style= "width: 50px;">별점</th>			
					<th scope="col" style= "width: 100px;">작성일자</th>
				</tr>
			<c:forEach items="${placeReviewList}" var= "placeReview" varStatus= "placeReviewSt">	
				<tr>
					<td style= "display: none;" class= "deleteCheckData">
						<input type="checkbox" id= "reviewIdForDelete${placeReview.placeReviewId}">
					</td>
					
					<td id= "reviewId">${placeReview.placeReviewId}</td>	
					<td id= "userNickName${placeReview.placeReviewId}">${userNicknameList.get(placeReview.userId)}</td>	
					<td id= "placeName${placeReview.placeReviewId}">${placeNameList.get(placeReview.placeId)}</td>	
					<td id= "placeScore${placeReview.placeReviewId}">${placeReview.placeScore}</td>	
					<td id= "reviewDate${placeReviewSt}">${placeReview.reviewDate}</td>	
				</tr>
			</c:forEach>
		</table>
					<script>
						// 날짜 표기법 변경
						var months = new Map([
												["Jan", 1],["Feb", 2],["Mar", 3],["Apr", 4],["May", 5],["Jun", 6],
												["Jul", 7],["Aug", 8],["Sep", 9],["Oct", 10],["Nov", 11],["Dec", 12]
											]);
						
						function formatDate(inputDate) {
							var stringParts = inputDate.split(' ');
							var date = stringParts[5] + "-" + months.get(stringParts[1]) + "-" + stringParts[2];
							return date;
						}
						
							var longDates = document.querySelectorAll('[id^="reviewDate"]');
							
							longDates.forEach(longDate => {
								longDate.textContent = formatDate(longDate.textContent);
							});
					</script>
		
		<div id= "pageNumbersContainer" style= "font-family: 'TheJamsil3Regular', sans-serif; position: absolute; bottom: 3%; left: 40%;">
			<br>
			<c:if test= "${(groupNum * 10) <= totalPages}">
				<c:set var= "endPage" value= "${groupNum * 10}" />
			</c:if>
			<c:if test= "${(groupNum * 10) > totalPages}">
				<c:set var= "endPage" value= "${totalPages}" />
			</c:if>
			
			<c:if test="${not empty adminPlaceReviewInfo['category']}">

				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/placeReview/${adminPlaceReviewInfo['category']}/${adminPlaceReviewInfo['keyword']}?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/placeReview/${adminPlaceReviewInfo['category']}/${adminPlaceReviewInfo['keyword']}?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/placeReview/${adminPlaceReviewInfo['category']}/${adminPlaceReviewInfo['keyword']}?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>

			</c:if>
			<c:if test="${empty adminPlaceReviewInfo['category']}">

				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/placeReview?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/placeReview?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/placeReview?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>

			</c:if>
			
			
			
		</div>
		
		<br><br>
		
		
	</div>

</body>
</html>