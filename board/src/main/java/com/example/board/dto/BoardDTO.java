package com.example.board.dto;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDTO {
	
	private Long post_id;		// PK
	private Long member_no;		// FK
	private Long board_num;		// 1 : 유머 2 : 스포츠 3 : 게임
	private String post_name;
	private String post_text;
	private Timestamp post_date;
	private Long post_views;
	
	
}
