<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="sub-header">
			<c:set var="post.board_num" value="${post.board_num}" />
			<c:if test="${post.board_num eq 1}">
				<span>유머 게시판</span>
			</c:if>
			<c:if test="${post.board_num eq 2}">
				<span>스포츠 게시판</span>
			</c:if>
			<c:if test="${post.board_num eq 3}">
				<span>게임 게시판</span>
			</c:if>
			
			<span class="pull-right">작성일 : ${post.post_date }</span>
			<h2>${post.post_name }</h2>
		</div>
		
	    <div class="table-responsive">
	    	<div class="pull-right" >
		    	<div>
					<span>작성자 : ${postMember.member_id } </span>
					<span>조회수 : ${post.post_views }</span>
					
		    	</div>
		    	
		    	<!-- 세션에 등록된 계정과 글을 쓴 주인이 같으면 수정, 삭제 버튼 보이기 -->
		    	<c:set var="loginUserSession.member_no" value="${loginUserSession.member_no}" />
		    	<c:set var="post.member_no" value="${post.member_no}" />
				<c:if test="${loginUserSession.member_no eq post.member_no}">
			    	<div>
						<a href="/board/update/${post_id }"><button>글 수정</button></a>
						<button id="deleteButton">글 삭제</button>
			    	</div>
		    	</c:if>
	    	</div>
						
						
		<div class="modal fade" id="Modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">글 삭제</h5>
					<button class="close" type="button" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">X</span>
					</button>
				</div>
				<div class="modal-body">해당 글을 삭제하시겠습니까?</div>
				<div class="modal-footer">
					<a class="btn" id="modalY" href="/board/delete/${post_id }">예</a>
					<button class="btn" type="button" data-dismiss="modal">아니요</button>
				</div>
			</div>
		</div>
		</div>
				
				
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
					
					<tbody>
						<tr>
							<td>
								<div class="form-control" id="post_text" style="height: 1000px; text-align:left;">
									${post.post_text }
									
									<c:forEach var="postPhoto" items="${postPhoto}" >
										<div>
											<img src="/imgPath/post_img/<c:out value="${postPhoto }"/>"  style="width:300px; margin-top:20px; margin-bottom:20px;">
										</div>
									</c:forEach>
									
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				
				<!-- 댓글 -->
					<table class="table table-striped">
			            <thead>
			            <tr>
			                <th>댓글작성자</th>
			                <th>댓글내용</th>
			                <th>작성일자</th>
			            </tr>
			            </thead>
			            <tbody>
			            <c:forEach var="reply" items="${reply }" >
				            <tr>
				                <td id="test">${reply.member_id }</td>
				                <td>${reply.reply_content }</td>
				                <td>${reply.reply_date }</td>
				                <c:set var="reply.member_no" value="${reply.member_no}" />
				                <c:if test="${loginUserSession.member_no eq reply.member_no}">
				                	<td><button id="replyDelete">삭제</button></td>
				                	<td><input id="replyDeleteIdValue" value="${reply.reply_id }" style="display:none;"></td>
				                	<td><input id="replyDeleteAjaxPath" value="/board/reply/delete/${reply.reply_id }" style="display:none;"></td>
				                </c:if>
				            </tr>
			            </c:forEach>
			            
			            </tbody>
			        </table>
		           
	            	<textarea id="reply_content" name="reply_content" placeholder="댓글을 입력하세요" style="width:100%; margin-bottom:20px;"></textarea>
		            <input id="member_no" value="${loginUserSession.member_no }" style="display:none;"/>
		            <input id="member_id" value="${loginUserSession.member_id }" style="display:none;"/>
		            <input id="replyAjaxPath" value="/board/reply/${post.post_id}" style="display:none;"/>
	            	<input type="button" class="btn btn-success pull-right" id="replyAjaxButton" value="댓글쓰기"/>
		        <!-- 댓글 끝 -->
		</div>
		
	</div>
</body>

