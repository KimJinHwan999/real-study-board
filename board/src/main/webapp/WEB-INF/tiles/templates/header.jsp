<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">웃으면 복이와요 유머게시판</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
        	<c:if test="${empty loginUserSession}">
	            <ul class="nav navbar-nav navbar-right">
	                <li><a href="/member/signin">로그인</a></li>
	                <li><a href="/member/signup">회원가입</a></li>
	            </ul>
            </c:if>
        	<c:if test="${not empty loginUserSession}">
	            <ul class="nav navbar-nav navbar-right">
 					<li><a class="profile-img" href="/member/mypage" style="padding-right:0px;"><img src= "/imgPath/member_profile/${loginUserSession.member_img }" style="width:25px; height:25px"/></a></li> 
	                <li><a href="/member/mypage">마이페이지</a></li>
	                <li><a href="/member/logout">로그아웃</a></li>
	            </ul>
            </c:if>
		
        </div>
    </div>
</nav>