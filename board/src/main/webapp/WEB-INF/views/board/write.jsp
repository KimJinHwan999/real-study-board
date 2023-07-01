<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<body>
	
	<div class="container">
		<div class="row">
			<form action="${path }" method="post" name="writeForm" id="writeForm" encType="multipart/form-data">
				<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					<thead>
						<tr>
							<c:if test="${path eq '/board/writeprocess' }"><th colspan="2" style="background-color: #2e8b57; text-align:center;">게시판 글쓰기 양식</th></c:if>
							<c:if test="${path ne '/board/writeprocess' }"><th colspan="2" style="background-color: #2e8b57; text-align:center;">글 수정</th></c:if>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input type="text" class="form-control" placeholder="글 제목" name="post_name" id="post_name" maxlength="50" value="${board.post_name }"></td>
						</tr>
						<tr>
							<td><textarea class="form-control" placeholder="글 내용" name="post_text" id="post_text" maxlength="2048" style="height: 350px">${board.post_text }</textarea></td>
						</tr>
						<tr>
							<td>
								<label for="post_img" style="cursor:pointer;">
									원하는 사진을 업로드 하세요<br> 
									(한 번에 여러장 업로드가 가능합니다)
								</label>
								<input type="file" name="file" id="post_img" accept="image/*" multiple style="display:none"/>
								<div id="img-file">
									<c:forEach var="photo" items="${photo}" >
										<div>
											<img src="/imgPath/post_img/<c:out value="${photo.post_img_saved_name }"/>"  style="width:300px; margin-top:20px; margin-bottom:20px;">
										</div>
									</c:forEach>
								</div>
								<div id="img-file-name">
									<c:forEach var="photo" items="${photo}" >
										<div>
											<span><c:out value="${photo.post_img_original_name }"/></span>
										</div>
									</c:forEach>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
				
				
				
				<div class="pull-right">
				
					<select name="board_num" id="board_num">
					<c:set var="board_num" value="${board.board_num }"/>
						<option value="0">===게시판 선택===</option>
						<option value="1" <c:if test="${board_num eq 1}">selected</c:if>>유머 게시판</option>
						<option value="2" <c:if test="${board_num eq 2}">selected</c:if>>스포츠 게시판</option>
						<option value="3" <c:if test="${board_num eq 3}">selected</c:if>>게임 게시판</option>
					</select>
					
			
					
					<input type="text" name="post_views" id="post_views" value="0" style="display:none;"/>
					
					<br>
					<br>
						<c:if test="${path eq '/board/writeprocess' }"><input type="submit" class="btn btn-success pull-right" value="글 쓰기"></c:if>
						<c:if test="${path ne '/board/writeprocess' }"><input type="submit" class="btn btn-success pull-right" value="글 수정하기"></c:if>
					<br>
					<br>
					
					<div class="pull-right">
						<span id="msg" style="color:red">${msg }</span>
					</div>		
				</div>
				
			</form>
		</div>
				
	</div>
	
</body>