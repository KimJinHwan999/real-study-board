package com.example.board.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
		Long parent_id = null;
		Long child_id = null;
		
		replyService.insertReply(post_id, parent_id, child_id, member_no, member_id, reply_content);
		
		return "/board/post/{post_id}";
	}
	
	@ResponseBody
	@PostMapping("/board/reply/replyadd/{post_id}/{reply_id}")
	public String insertReplyAddAction(@PathVariable("post_id") Long post_id,
									   @PathVariable("reply_id") Long parent_id,
									   Long member_no,
									   String member_id,
									   String reply_content) {
		
		Long reply_id = replyService.insertReply(post_id, parent_id, null, member_no, member_id, reply_content);
		replyService.updateReplyChild(parent_id, reply_id);
		
		return "/board/post/{post_id}";
	}
	
	@ResponseBody
	@PostMapping("/board/reply/delete/{post_id}")
	public String deleteReplyAction(@PathVariable("post_id") Long post_id,
									Long reply_id) {
		
		replyService.deleteReply(reply_id);
		
		return "/board/post/{post_id}";
	}
}
