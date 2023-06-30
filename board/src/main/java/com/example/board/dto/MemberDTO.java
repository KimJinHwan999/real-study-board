package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberDTO {
	
	private Long member_no;		// PK
	private String member_id;
	private String member_pw;
	private String member_pw_chk;
	private String member_name;
	private String member_gender;
	private String member_phone;
	private String member_email;
	private String member_img;
	
	

}
