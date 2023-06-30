package com.example.board.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyDTO {

	private Long reply_id;
	private Long post_id;
	private Long member_no;
	private String member_id;
	private String reply_content;
	private Timestamp reply_date;
	
}
