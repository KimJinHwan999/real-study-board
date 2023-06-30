<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

   <body class="form-v10">
	<div class="page-content">
		<div class="form-v10-content">
			<c:set var="purpose" value="${purpose }" />
			
			
			
				<form 	<c:if test="${purpose eq 'update'}">action="/member/update"</c:if>
						<c:if test="${purpose eq 'signup'}">action="/member/signupprocess"</c:if> 
						class="form-detail" id="myform" method="post" encType="multipart/form-data">
			
				<div class="form-left">
					<h2>프로필 사진을 설정하세요</h2>
					<div class="form-row-img">
						
						<c:if test="${purpose eq 'update'}">
							<img id="img-file" src="/imgPath/member_profile/${loginUserSession.member_img }"/>
						</c:if>
						<c:if test="${purpose eq 'signup'}">
							<img id="img-file" src="/images/img-01.png"/>
						</c:if>
					</div>
					<div class="form-row-img-label">
						<label class="img-label" for="member_img" >프로필 사진 선택</label>
						<input type="file" name="file" id="member_img" accept="image/*" style="display:none;"/>
					</div>
				</div>
				<div class="form-right">
					<h2>회원 가입 정보를 입력하세요</h2>
					<div class="form-row">
						<input type="text" name="member_id" class="member_id" id="member_id" placeholder="아이디" value="${loginUserSession.member_id}" <c:if test="${purpose eq 'update'}">readonly</c:if>>
					</div>
					<div class="form-row">
						<span id="result_checkId"></span>
					</div>
					<div class="form-row">
						<input type="button" name="register" class="register" id="member_id_chk" value="중복검사" <c:if test="${purpose eq 'update'}">style="display:none;"</c:if>>
						<input type="button" name="register" class="register" id="member_id_chk_cancel" value="아이디 재설정" <c:if test="${purpose eq 'update'}">style="display:none;"</c:if>>
					</div>
					
					
					<div class="form-row">
						<input type="password" name="member_pw" class="member_pw" id="member_pw" placeholder="비밀번호" >
					</div>
					<div class="form-row">
						<input type="password" name="member_pw_chk" class="member_pw_chk" id="member_pw_chk" placeholder="비밀번호 확인">
					</div>
					<div class="form-group">
						<div class="form-row form-row-1">
							<input type="text" name="member_name" class="member_name" id="member_name" placeholder="이름" value="${loginUserSession.member_name} "  style="width:300px;">
						</div>
						<div class="form-row form-row-2">
								<select name="member_gender" id="member_gender">
									    <option value="gender">성별</option>
									    <option value="m">남성</option>
									    <option value="f">여성</option>
								</select>
							<span class="select-btn">
							  	<i class="zmdi zmdi-chevron-down"></i>
							</span>
						</div>
					</div>
			
				
					<div class="form-row">
						<input type="text" name="member_phone" id="member_phone" class="input-text"  placeholder="휴대폰번호" value="${loginUserSession.member_phone}">
					</div>
					
						
					
					<div class="form-group">
						<div class="form-row">
							<input type="text" name="member_email" id="member_email" class="input-text"  placeholder="이메일"  value="${loginUserSession.member_email}" style="width:300px;">
						</div>
						<div class="form-row">
							<input type="button" name="register" class="register" id="send_email_verify" value="인증번호발송">
						</div>
					</div>
					<div class="form-group">
						<div class="form-row">
							<input type="text" name="member_email_chk" id="member_email_chk" class="input-text"  placeholder="인증번호 입력"  style="width:300px;">
						</div>
						<div class="form-row">
							<input type="button" name="register" class="register" id="chk_email_verify" value="이메일 인증">
						</div>
					</div>
					<div class="form-row">
						<span id="result_checkEmail"></span>
					</div>
					
					<div class="form-row-last">
						<input type="submit" name="register" class="register" id="submit" value="회원가입">
					</div>
					
					
				</div>
			</form>
		</div>
	</div>
</body>
	
