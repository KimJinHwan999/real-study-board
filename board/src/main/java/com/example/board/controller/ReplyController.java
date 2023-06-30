package com.example.board.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.dto.ReplyDTO;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReplyController {

	private final ReplyService replyService;
	
	@ResponseBody
	@PostMapping("/board/reply/{post_id}")
	public String insertReplyAction(@PathVariable("post_id") Long post_id,
									Long member_no,
									String member_id,
									String reply_content) {
		
		Timestamp reply_date = Timestamp.valueOf(LocalDateTime.now().withNano(0));
		
		ReplyDTO reply = new ReplyDTO();
		reply.setPost_id(post_id);
		reply.setMember_no(member_no);
		reply.setMember_id(member_id);
		reply.setReply_content(reply_content);
		reply.setReply_date(reply_date);
		
		replyService.insertReply(reply);
		
		return "/board/post/" + post_id;
	}
	
	@ResponseBody
	@PostMapping("/board/reply/delete/{post_id}")
	public String deleteReplyAction(@PathVariable("post_id") Long post_id,
									Long reply_id) {
	
		
		replyService.deleteReply(reply_id);
		
		return "/board/post/" + post_id;
	}
}
