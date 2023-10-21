<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>CourseToGo / 관리자페이지</title>
 	<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
 	 
	<style>
		 body {
		 	background-color: #F5EFED;
		 }
		 
		 .mainContent {	 	
		 }
		 
		 .adminPageButton {
		 	width: 130px;
		 	background-color: #4242D0;
		 	font-family: 'TheJamsil3Regular', sans-serif;
		 	color: #ffffff;
		 	border-radius: 3px;
		 	border: 0px;
		 	padding: 6px 10px;
		 	margin-right: 30px;
		 	font-size: 11pt;
		 	margin-bottom: 25px;
		 	cursor: pointer;
		 }
		 
	</style>
	<link rel="stylesheet" type="text/css" href="/css/fonts.css">	
</head>
<body>	
	<header>
		<jsp:include page="components/navigation.jsp" />	
	</header>
	
	<div class = "mainContent" style= "margin-top: 100px; width: 85%; height: 90%;
									   position: absolute; left: 5%;">		
		<div id= "buttonContainer" style= "width: 130px; position: absolute; left: 0;">
			<button id= "userAdminButton" type= "button" class= "adminPageButton" onclick= "switchContent('/admin/user')">회원 관리</button>
			<button id= "courseAdminButton" type= "button" class= "adminPageButton" onclick= "switchContent('/admin/course')">코스 관리</button>
			<button id= "placeAdminButton" type= "button" class= "adminPageButton" onclick= "switchContent('/admin/place')">장소 관리</button>
			<button id= "courseReviewAdminButton" type= "button" class= "adminPageButton" onclick= "switchContent('/admin/courseReview')">코스리뷰 관리</button>
			<button id= "placeReviewAdminButton" type= "button" class= "adminPageButton" onclick= "switchContent('/admin/placeReview')">장소리뷰 관리</button>
		</div>
		
		<iframe id= "contentsContainer" style= "border: 1px solid #D3D3D3; width: 90%; height: 95%; background-color: #ffffff;
											    position: absolute; left: 129px; border-radius: 2px;
											    text-align: center; overflow: auto;">

		</iframe>
	</div>

   	<footer>
		<jsp:include page="components/footer.jsp" />
	</footer>
	
	<script>
		var iframe = document.getElementById('contentsContainer'); 
		
		if(${empty sessionScope.user.userId}) {
			iframe.src = "/user/myPage/loginRequired";
		}
		
		if(${not empty sessionScope.user}) {
			iframe.src = "/admin/user";
		}
	
		function switchContent(page) {
			if(${not empty sessionScope.user.userId}) {		
			    iframe.src = page;	
			}
		}
		
	</script>
	
	
</body>
</html>