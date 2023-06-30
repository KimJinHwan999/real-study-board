package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface HchartMapper {
	
	/* 존재하는 게시판들을 리스트로 가져오기 */
	public List<Long> selectBoardNum_();
	
	/* 게시판 별로 가지고 있는 글의 갯수를 Map에 넣어주기 */
	public Long hchartBoardCnt (Long boardNum);
	
}
