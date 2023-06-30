package com.example.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.ReplyDTO;

@Mapper
public interface ReplyMapper {

	public List<ReplyDTO> findReplyByPostId(Long post_id);
	
	public int insertReply(ReplyDTO reply);
	
	public int deleteReply(Long reply_id);
	
	
	
	
}
