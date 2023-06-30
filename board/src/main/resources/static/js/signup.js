$(document).ready(function(){



/*========== 프로필 사진 썸네일 처리 ==========*/	
	$("#member_img").on("change", function(e){
		let file = e.target.files[0];
		let reader = new FileReader();
		let maxSize = 1 * 1024 * 1024;
		
		reader.onload = function(e)	{
			$("#img-file").attr("src", e.target.result)
						  .attr("width", "300px")
						  .attr("hight", "300px");
		}
		
		if(file.size > maxSize){
			alert("1MB보다 작은 사이즈의 사진을 넣어주세요");
			return false;
		}
		
		reader.readAsDataURL(file);
		
	});


/*========== 이메일 인증번호 발송 ajax ==========*/
	let emailKey = "";
	
	$("#send_email_verify").click(function(){
    
	    let member_email = $("#member_email").val();
	    
	     
	    $.ajax({
	        type:'post', 
	        url:"/emailCheck", 
	        data: {"member_email" : member_email}, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
	        success: function(data){ 
	            if(data != null){ // 만약 성공할시
	            	alert('인증번호를 확인하세요');
	            	emailKey = data;
	             
	         	}else{ // 만약 실패할시
	             	alert('메일주소를 확인하세요');
				}
	     	},
	        	error : function(error){alert(error);}
	    });
        
	});
	
/*========== 이메일 인증번호 일치여부확인 ==========*/
	$("#chk_email_verify").click(function(){
		if(emailKey == ""){
			result = "인증번호를 입력하세요";
			$("#result_checkEmail").html(result).css("color","red");
			$("#member_email_chk").val("").trigger("focus");	
		}else{
			if(emailKey == $("#member_email_chk").val()){
				result="인증이 완료되었습니다.";
				$("#result_checkEmail").html(result).css("color","white");
				$("#member_email_chk").prop('readonly',true);
				$("#member_email").prop('readonly',true);
			}else{
				result="인증번호가 일치하지 않습니다.";
				$("#result_checkEmail").html(result).css("color","red");
				$("#member_email_chk").val("").trigger("focus");	
			}
		}
	})
	
	
	
	
	
	
	
	
/*========== 아이디 중복 여부 ajax 검사 ==========*/	
	$("#member_id_chk").click(function(){
    
	    let member_id = $("#member_id").val();
	     
	    $.ajax({
	        type:'post', 
	        url:"/idcheck", 
	        data: {"member_id" : member_id}, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
	        success: function(data){ 
				
						
	            if(data == 0){ // 만약 성공할시 (중복된 아이디가 없다면)
	            
	            	if($("#member_id").val() == ""){	// 사용자가 아이디를 입력하지 않고 중복검사를 한 경우
						result="아이디를 입력하세요.";
						$("#result_checkId").html(result).css("color","red");
						$("#member_id").val("").trigger("focus");
					}else{
	                    result = "사용 가능한 아이디입니다.";
	                    $(".member_id").prop('readonly',true);
	                    $("#result_checkId").html(result).css("color", "white");
	                    
	                    $("#member_pw").trigger("focus");
					}
	             
	         	}else{ // 만약 실패할시 (중복된 아이디가 있다면)
	             	result="이미 사용중인 아이디입니다.";
	                $("#result_checkId").html(result).css("color","red");
	                $("#member_id").val("").trigger("focus");
				}
	             
	     	},
	        	error : function(error){alert(error);}
	    });
        
	});
    

/*========== 아이디 중복체크 후 아이디 변경 막기 ==========*/
	$("#member_id_chk_cancel").click(function(){
		 $(".member_id").prop('readonly',false);
		 $("#member_id").val("").trigger("focus");
	});
 
    
	$("#submit").click(function(){
		
		let idRegExp = /^[a-zA-Z0-9]{4,12}$/;	// id 유효성 검사 정규식
		let pwRegExp = /^[A-Za-z0-9`~!@#\$%\^&\*\(\)\{\}\[\]\-_=\+\\|;:'"<>,\./\?]{8,20}$/; // 비밀번호 유효성 검사 정규식
		let nRegExp = /^[가-힣]{2,15}$/; 		// 이름 유효성검사 정규식
		let eRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i; // 이메일 유효성 검사 정규식
		let pRegExp = /^[0-9]{2,3}[0-9]{3,4}[0-9]{3,4}$/; // 전화번호 유효성 검사 정규식
	
/*========== 아이디 유효성 검사 ==========*/
/* 아이디 입력여부 검사 */		
		/*console.log($("#member_id").val());*/
		if($("#member_id").val() == ""){
			alert("아이디를 입력하세요");
			$("#member_id").focus();
			return false;
		}
		
/* 아이디 중복체크 검사 */		
		if(!$(".member_id").is('[readonly]')){
			alert("아이디 중복체크를 완료하세요");
			return false;
		}

/* 아이디 형식 검사 */		
		if(!idRegExp.test($("#member_id").val())){
			alert("ID는 4~12자의 영문 대소문자와 숫자로만 입력하여 주세요.");
			return false;
		}
		
/*========== 비밀번호 유효성 검사 ==========*/
/* 비밀번호 입력여부 검사 */		
		if($("#member_pw").val() == ""){ 
			alert("비밀번호를 입력하세요");
			$("#member_pw").focus();
			return false;
		}
		
		if($("#member_pw_chk").val() == ""){
			alert("비밀번호 확인을 입력하세요");
			$("#member_pw_chk").focus();
			return false;
		}
		
/* 비밀번호 확인 일치 여부 */
		if($("#member_pw").val() != $("#member_pw_chk").val()){ 
			alert("비밀번호가 일치하지 않습니다")
			$("#member_pw_chk").focus();
			return false;
		}
		
/* 비밀번호 형식 검사 */
		if(!pwRegExp.test($("#member_pw").val())){
            alert("8~20자 영문 대소문자, 숫자, 특수문자를 사용하세요.");
            return false;
        }
		

/*========== 이름 유효성 검사 ==========*/
/* 이름 입력여부 검사 */			
		if($("#member_name").val() == ""){
			alert("이름을 입력하세요");
			$("#member_name").focus();
			return false;
		}

/* 이름 형식 검사 */		
		if(!nRegExp.test($("#member_name").val())){
            alert("한글로 2글자 이상 입력하여주세요.");
            return false;
        }
        
        
/*========== 성별 유효성 검사 ==========*/     
/* 성별 입력여부 검사 */   
		if($("#member_gender option:selected").val() == "gender"){
			alert("성별을 선택하세요");
			$("#member_gender").focus();
			return false;
		}
		
/*========== 전화번호 유효성 검사 ==========*/
/* 전화번호 입력여부 검사 */		
		if($("#member_phone").val() == ""){
			alert("전화번호를 입력하세요");
			$("#member_phone").focus();
			return false;
		}
		
		
		if(!pRegExp.test($("#member_phone").val())){ 
            alert("올바른 전화번호 형식이 아닙니다. 01000000000 형식으로 입력해주세요");
            return false;
        }
		
/*========== 이메일 유효성 검사 ==========*/
/* 이메일 입력여부 검사 */		
		if($("#member_email").val() == ""){
			alert("이메일을 입력하세요");
			$("#member_email").focus();
			return false;
		}
		
/* 이메일 인증여부 검사 */		
		if($("#member_email_chk").val() == ""){
			alert("이메일 인증을 완료해주세요.");
			$("#member_email_chk").focus();
			return false;
		}
		
		if($("#result_checkEmail").html() != "인증이 완료되었습니다."){
			alert("이메일 인증을 완료해주세요.");
			$("#member_email_chk").focus();
			return false;
		}

/* 이메일 입력여부 검사 */			
		if(!eRegExp.test($("#member_email").val())){ 
            alert("올바른 이메일 형식이 아닙니다. Gmail만 입력 가능합니다.");
            return false;
        }
	
/*========== 사진 유효성 검사 ==========*/
/* 사진 입력여부 검사 */	
		if($("#member_img").val() == ""){
			alert("프로필 사진을 설정해주세요.");
			$("#member_img").focus();
			return false;
		}
	})
	
	
	
	$("form").submit(function(){
		alert("완료되었습니다.")
	})
	
});
	