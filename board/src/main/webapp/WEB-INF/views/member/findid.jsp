<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<body>
	<div class="limiter">
		<div class="container-login100" style="background: linear-gradient(-135deg, #50c8a2, #4158d0);">
			<div class="wrap-login100" style="display:flex; justify-content: center;">
				
	
				<form class="login100-form validate-form" action="/member/signin" method="post" id="loginForm">
					<span class="login100-form-title">
						아이디 찾기
					</span>
	
					
					<div class="wrap-input100 validate-input">
						<input class="input100" type="text" name="member_id" id="member_id" placeholder="이름">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
					</div>
	
					<div class="wrap-input100 validate-input">
						<input class="input100" type="password" name="member_pw" id="member_pw" placeholder="이메일">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
					</div>
					
					<div class="container-login100-form-btn">
						<button type="submit" class="login100-form-btn">
							아이디 찾기
						</button>
					</div>
	
					<div class="text-center p-t-12">
						<span class="txt1">
							Forgot
						</span>
						
						<a class="txt2" href="/member/findpw">
							Password?
						</a>
					</div>
	
					<div class="text-center p-t-136">
						<a class="txt2" href="/member/signup">
							회원가입
							<i class="fa fa-long-arrow-right m-l-5" aria-hidden="true"></i>
						</a>
						
					</div>
				</form>
				
				<!-- 실제 서버로 전송되는 form -->
				<form action="/member/signin" method="post" id="hiddenForm">
					<fieldset>
						<input type="hidden" name="member_id" />
						<input type="hidden" name="member_pw" />
					</fieldset>
				</form>
				
			</div>
		</div>
	</div>
	
</body>	