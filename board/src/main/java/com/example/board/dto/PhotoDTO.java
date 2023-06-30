package com.example.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoDTO {

	private Long post_id; 		// fk
	private String post_img_original_name;
	private String post_img_saved_name;
}
