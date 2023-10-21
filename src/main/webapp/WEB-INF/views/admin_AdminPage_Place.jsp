<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지 - 장소</title>
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
		 
		 .placeAdminButton {
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
	<c:set var = "placeInformList" value= "${adminPlaceInfo['placeInformList']}" />
	<c:set var = "totalPlaceCount" value= "${adminPlaceInfo['totalPlaceCount']}" />
	
	<c:set var = "pageNum" value= "${adminPlaceInfo['pageNum']}" />
	<c:set var = "pageSize" value= "${adminPlaceInfo['pageSize']}" />
	<c:set var = "groupNum" value= "${adminPlaceInfo['groupNum']}" />
	<c:set var = "totalPages" value= "${adminPlaceInfo['totalPages']}" />
	<c:set var = "totalGroups" value= "${adminPlaceInfo['totalGroups']}" />

	<div class = "mainContent">
		<div id= subject style= "background-color: #F7F7F7; padding: 2%; margin-bottom: 10px;">
			<span style= "font-size: 15pt; margin-left: 20px;"><b>장소 관리 페이지</b></span>
		</div>
		<span style= "font-size: 11pt; margin-left: 20px;">
			&nbsp;&nbsp;&nbsp; 전체 장소 수 : <span style= "color: #4242D0;"><b>${totalPlaceCount}</b></span> 개
		</span>
		<br><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 장소 이름 클릭 시 장소 조회가 가능합니다.</span><br>
		<span style= "margin-left: 3%; font-size: 10pt; color: #A7A7A7;"> - 평균 별점, 수록된 코스 수 검색은 [검색값 이상의 값을 가진 장소]를 검색합니다.</span><br><br>
		<div style= "display: flex; align-items: center;">
			<select id= "selectedCategory" style= "text-align: center; margin-left: 3%; border: 1px solid #ADADAD; height: 24px;">
				<option value="entirePlace" selected>전체&nbsp;&nbsp;</option>
				<option value="areaName">장소분류&nbsp;&nbsp;</option>
				<option value="placeName">장소 이름&nbsp;&nbsp;</option>
				<option value="address">주소&nbsp;&nbsp;</option>
				<option value="placeAvgScore">평균 별점&nbsp;&nbsp;</option>
				<option value="includedCourseCount">수록된 코스 수&nbsp;&nbsp;</option>
			</select>&nbsp;
			<input type= "text" id="searchKeyword" style= "border: 1px solid #ADADAD; width: 150px; height: 20px;">&nbsp;&nbsp;
			<button id= "searchPlaceButton" type= "button" class= "placeAdminButton"><b>검색</b></button>
			
			<div style= "display: flex; flex-direction: row; margin-left: auto; margin-right: 3%; gap: 5%;">
				<button id= "deletePlaceButton" type= "button" class= "placeAdminButton"
						style= "cursor: pointer; background-color: #E8704D; width: 90px; height: 30px;">
					<b>장소 삭제</b>
				</button>
				
				<button id= "deletePlaceButtonCommit" type= "button" class= "placeAdminButton"
						style= "cursor: pointer; background-color: #77331F; width: 90px; height: 30px; display: none;">
					<b>삭제 확인</b>
				</button>
				
				<button id= "deletePlaceButtonCancel" type= "button" class= "placeAdminButton"
						style= "cursor: pointer; width: 70px; height: 30px; display: none;">
					<b>취소</b>
				</button>
			</div>
		</div>
		<br>
		
			<script>
				var searchButton = document.getElementById("searchPlaceButton");	
				
				searchButton.addEventListener('click', function() {
					var keyword = document.getElementById("searchKeyword").value.trim();
					var category = document.getElementById("selectedCategory").value;
							if(category === "entirePlace") {
								window.location.href= '/admin/place';
							}else {
								if(keyword === "") {
									keyword = "none";
								}
								window.location.href= '/admin/place/' + category + '/' + keyword;
							}
		        });	
				
				var deleteButton = document.getElementById("deletePlaceButton");	
				
				deleteButton.addEventListener('click', function() {
					document.getElementById("deletePlaceButton").style.display = 'none';
					
					document.getElementById("deleteCheckHeader").style.display = '';
					document.getElementById("deletePlaceButtonCommit").style.display = '';
					document.getElementById("deletePlaceButtonCancel").style.display = '';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = '';
						});
						
				});
				
				var commitButton = document.getElementById("deletePlaceButtonCommit");
				var deletePlaces = [];
				
				commitButton.addEventListener('click', function() {
					var selectedPlaces = document.querySelectorAll('[id^="placeIdForDelete"]');
					var needToConfirm = false;
					
					
					for(var i = 0; i < selectedPlaces.length; i++) {
						if(selectedPlaces[i].checked) {
							var deletePlaceId = selectedPlaces[i].id.replace("placeIdForDelete", "");							
							
							if(document.getElementById("placeIncludedCount" + deletePlaceId).textContent != 0){
								if(needToConfirm == false) {
									needToConfirm = true;
									break;
								}
							}
							
							deletePlaces.push(parseInt(deletePlaceId, 10));
						}
					}
					
					if(needToConfirm == true) {
						alert("이미 코스에 수록된 장소가 포함되어 있습니다. 삭제할 수 없습니다.");
					}
					
					if(needToConfirm == false) {
						axios.post("/admin/place/delete", deletePlaces)
						.then(response => {
							window.location.reload();
				        })
				        .catch(error => {
				        	window.location.href= '/error'
				        });
					}
				});
				
				
				var cancelButton = document.getElementById("deletePlaceButtonCancel");
				
				cancelButton.addEventListener('click', function() {
					document.getElementById("deletePlaceButton").style.display = '';
					
					document.getElementById("deleteCheckHeader").style.display = 'none';
					document.getElementById("deletePlaceButtonCommit").style.display = 'none';
					document.getElementById("deletePlaceButtonCancel").style.display = 'none';
					var checkData = document.querySelectorAll('.deleteCheckData');
					
						checkData.forEach((td) => {
							td.style.display = 'none';
						});
						
				});
			</script>
		
		<table id= "allPlaceList" style= "position: absolute; width: 95%;">
				<tr>
					<th scope="col" id= "deleteCheckHeader" style= "width: 60px; display: none;">삭제여부</th>				
					<th scope="col" style= "width: 50px;">장소ID</th>
					<th scope="col" style= "width: 80px;">장소분류</th>
					<th scope="col" style= "width: 150px;">장소 이름</th>
					<th scope="col" style= "width: 500px;">주소</th>
					<th scope="col" style= "width: 80px;">평균 별점</th>
					<th scope="col" style= "width: 60px;">수록된 코스 수</th>
				</tr>
			<c:forEach items="${placeInformList}" var= "placeInform" varStatus= "placeInformSt">
				<tr>
					<td style= "display: none;" class= "deleteCheckData">
						<input type="checkbox" id= "placeIdForDelete${placeInform.placeId}">
					</td>
					
					<td>${placeInform.placeId}</td>	
					<td id= "area${placeInform.placeId}">${placeInform.areaName}</td>
					<td id= "placeName${placeInform.placeId}" style= "color: #4242D0; cursor: pointer;">
						<a href="https://map.naver.com/p/search/${placeInform.placeName} ${placeInform.areaName}" style= "text-decoration: none; color: inherit;" target="_blank">
							${placeInform.placeName}
						</a>
					</td>	
					<td id= "address${placeInform.placeId}">${placeInform.address}</td>	
					<td id= "placeAvgScore${placeInform.placeId}">${placeInform.placeAvgScore}</td>	
					<td id= "placeIncludedCount${placeInform.placeId}">${placeInform.includedCourseCount}</td>	
				</tr>
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
			
			<c:if test="${not empty adminPlaceInfo['category']}">
			
				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/place/${adminPlaceInfo['category']}/${adminPlaceInfo['keyword']}?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/place/${adminPlaceInfo['category']}/${adminPlaceInfo['keyword']}?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/place/${adminPlaceInfo['category']}/${adminPlaceInfo['keyword']}?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
				</c:if>
			
			</c:if>
			<c:if test="${empty adminPlaceInfo['category']}">
			
				<c:if test= "${totalPages > 1}">
					<c:if test= "${groupNum != 1}">
						<a href= "/admin/place?pageNum=${(groupNum-1)*10}&pageSize=${pageSize}&groupNum=${groupNum-1}" style= "color: #00008b; text-decoration: none;">◀</a>&nbsp;
					</c:if>
								
					<c:forEach var= "i" begin= "${(groupNum - 1) * 10 + 1}" end= "${endPage}">
						<a href= "/admin/place?pageNum=${i}&pageSize=${pageSize}" style= "color: #00008b; text-decoration: none;"><b>${i}</b></a>&nbsp;
					</c:forEach>
					
					<c:if test= "${groupNum != totalGroups}">
						<a href= "/admin/place?pageNum=${(groupNum*10)+1}&pageSize=${pageSize}&groupNum=${groupNum+1}" style= "color: #00008b; text-decoration: none;">▶</a>&nbsp;
					</c:if>
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