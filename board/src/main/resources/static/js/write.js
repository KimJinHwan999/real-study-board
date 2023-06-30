/**
 * 
 */
$(document).ready(function(){
	
	/*========== 추가 이미지 썸네일 ==========*/	
	$("#post_img").on("change", function(e){
		
		// 기존 선택했던 사진파일 흔적들은 삭제
		$("#img-file").empty();
		$("#img-file-name").empty();
		
		let maxSize = 1 * 1024 * 1024;
		let fileList=[];
		let href=[];
		
		
		for(let i = 0; i < e.target.files.length; i++){
			fileList[i] = e.target.files[i];			
			href[i] = window.URL.createObjectURL(fileList[i]);
			
			// 출력할 이미지 썸네일
			$("#img-file").append("<img src='"+ href[i] +"' style='width:150px';/>")
			
			// 출력할 이미지 이름
			$("#img-file-name").append("<span>"+ fileList[i].name +"</span><br>")
							   .css({"border" : "solid 1px black"})
										  
			if(fileList[i].size > maxSize){
				alert("1MB보다 작은 사이즈의 사진을 넣어주세요");
				return false;
			}
		}
		
	});
	
	
})