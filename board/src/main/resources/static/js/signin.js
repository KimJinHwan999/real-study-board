
$(document).ready(function(){
	
	let $id = $("#hiddenForm input[name='member_id']");
	let $password = $("#hiddenForm input[name='member_pw']");
	let modulus = $("#modulus").val();
	let exponent = $("#exponent").val();

	// Server로부터 받은 공개키 입력
	let rsa = new RSAKey();
	rsa.setPublic(modulus, exponent);
	

	$("#loginForm").submit(function(e) {
		// 실제 유저 입력 form은 event 취소
		// javascript가 작동되지 않는 환경에서는 유저 입력 form이 submit 됨
		// -> Server 측에서 검증되므로 로그인 불가
		e.preventDefault();

		// 아이디/비밀번호 암호화 후 hidden form으로 submit
		let id = $(this).find("#member_id").val();
		let password = $(this).find("#member_pw").val();
		
		$id.val(rsa.encrypt(id)); // 아이디 암호화
		$password.val(rsa.encrypt(password)); // 비밀번호 암호화
		$("#hiddenForm").submit();
	});

	
});
	