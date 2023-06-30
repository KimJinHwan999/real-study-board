<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<body>
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<div class="login100-pic js-tilt" data-tilt>
					<img src="/images/img-01.png" alt="IMG">
				</div>
				
	
				<form class="login100-form validate-form" action="/member/signinprocess" method="post" id="loginForm">
					<span class="login100-form-title">
						로그인
					</span>
	
					<input value="${modulus }" type="hidden" id="modulus"/>
					<input value="${exponent }" type="hidden" id="exponent"/>
					
					<div class="wrap-input100 validate-input" data-validate = "아이디를 입력해주세요">
						<input class="input100" type="text" name="member_id" id="member_id" placeholder="아이디">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
					</div>
	
					<div class="wrap-input100 validate-input" data-validate = "비밀번호를 입력해주세요">
						<input class="input100" type="password" name="member_pw" id="member_pw" placeholder="비밀번호">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-lock" aria-hidden="true"></i>
						</span>
					</div>
					
					<div class="text-center">
						<p id="result" style=color:red>${msg}</p>
					</div>
					
					<div class="container-login100-form-btn">
						<button type="submit" class="login100-form-btn">
							로그인
						</button>
					</div>
	
					<div class="text-center p-t-12">
						<span class="txt1">
							Forgot
						</span>
						<a class="txt2" href="/member/findid">
							Username 
						</a>
						 / 
						<a class="txt2" href="#">
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