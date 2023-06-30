package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.dto.BoardDTO;

@Mapper
public interface BoardMapper {

	/* 게시글 전체 가져오기 */
	public List<BoardDTO> findAllBoard();
	
	public List<BoardDTO> findPageData(@Param("firstPost") Long firstPost, @Param("postingCnt") Long postingCnt);
	
	/* 게시글 등록 */
	public Long insertBoard(BoardDTO board);
	
	/* 게시글 번호로 게시글 찾아오기 */
	public BoardDTO findById(Long post_id);
	
	/* 조회수 로직 */
	public int updateViews(@Param("post_id") Long post_id, @Param("post_views") Long post_views);
	
	/* 글 삭제 */
	public int deletePost(Long post_id);
	
	/* 글 수정 */
	public int updatePost(BoardDTO board);
	
	public BoardDTO findAllByBoardNum(Long board_num);

	
}
