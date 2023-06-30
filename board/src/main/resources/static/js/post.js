$(document).ready(function(){
	
	/* 글삭제 누르면 나오는 모달창 */
	$("#deleteButton").on("click", function(e){
		e.preventDefault();
		$("#Modal").modal("show");
		
	});
	
	
	/* 댓글 입력 ajax */
	$("#replyAjaxButton").click(function(){
    
    	let member_no = $("#member_no").val();
    	let member_id = $("#member_id").val();
	    let reply_content = $("#reply_content").val();
	    let replyUrl = $("#replyAjaxPath").val();
	    
	     
	    $.ajax({
	        type:'post', 
	        url: replyUrl, 
	        data: {
				"member_no" : member_no,
				"member_id" : member_id,
				"reply_content" : reply_content
				}, 
	        success: function(data){ 
	            if(data != null){ // 만약 성공할시
	            	alert('댓글작성완료');
	            	window.location.reload();
	             
	         	}else{ // 만약 실패할시
	             	alert('댓글작성실패');
				}
	     	},
	        	error : function(error){alert(error);}
	    });
        
	});
	
	
	/* 댓글 삭제 ajax */
	$("#replyDelete").click(function(){
    
    	let reply_id = $("#replyDeleteIdValue").val();
	    let replyDeleteAjaxPath = $("#replyDeleteAjaxPath").val();
	    
	     
	    $.ajax({
	        type:'post', 
	        url: replyDeleteAjaxPath, 
	        data: {"reply_id" : reply_id}, 
	        success: function(data){ 
	            if(data != null){ // 만약 성공할시
	            	alert('댓글삭제완료');
	            	window.location.reload();
	             
	         	}else{ // 만약 실패할시
	             	alert('댓글삭제실패');
				}
	     	},
	        	error : function(error){alert(error);}
	    });
        
	});
})