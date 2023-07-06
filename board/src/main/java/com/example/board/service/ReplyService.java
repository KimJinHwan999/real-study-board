package com.example.board.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.ReplyDTO;
import com.example.board.mapper.ReplyMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReplyService {

	private final ReplyMapper replyMapper;
	
	
	public List<ReplyDTO> findReplyByPostId(Long post_id) {
		return replyMapper.findReplyByPostId(post_id);
	}
	
	@Transactional
	public Long insertReply(Long post_id,
						   Long parent_id,
						   Long child_id,
						   Long member_no,
						   String member_id,
						   String reply_content) {
		
		Timestamp reply_date = Timestamp.valueOf(LocalDateTime.now().withNano(0));
		
		ReplyDTO reply = new ReplyDTO();
		reply.setPost_id(post_id);
		reply.setParent_id(parent_id);
		reply.setChild_id(child_id);
		reply.setMember_no(member_no);
		reply.setMember_id(member_id);
		reply.setReply_content(reply_content);
		reply.setReply_date(reply_date);
		
		replyMapper.insertReply(reply);
		
		
		return reply.getReply_id();
	}
	
	@Transactional
	public int deleteReply(Long reply_id) {
		return replyMapper.deleteReply(reply_id);
	}
	
	@Transactional
	public int deleteAllReply(Long post_id) {
		return replyMapper.deleteAllReply(post_id);
	}

	public int updateReplyChild(Long parent_id, Long reply_id) {
		return replyMapper.updateReplyChild(parent_id, reply_id);
	}
	
	
}
