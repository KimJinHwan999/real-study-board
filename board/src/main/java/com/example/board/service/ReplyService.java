package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.board.dto.ReplyDTO;
import com.example.board.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

	private final ReplyMapper replyMapper;
	
	
	public List<ReplyDTO> findReplyByPostId(Long post_id) {
		return replyMapper.findReplyByPostId(post_id);
	}
	
	public int insertReply(ReplyDTO reply) {
		return replyMapper.insertReply(reply);
	}
	
	public int deleteReply(Long reply_id) {
		return replyMapper.deleteReply(reply_id);
	}
	
	
}
