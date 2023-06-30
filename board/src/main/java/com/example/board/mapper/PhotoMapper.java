package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoMapper {
	
	/* 게시글 첨부 사진 등록 */
	public int insertImage(@Param("post_id")			Long post_id, 
						   @Param("fileListOriginName")	String fileListOriginName, 
						   @Param("fileList")			String fileList);
	
	/* 게시글 첨부 사진 삭제 */
	public int deleteImage(Long post_id);
	
	/* 글 PK로 사진 이름 찾아오기 */
	public List<String> findPhotoByNo(@Param("post_id") Long post_id);

}
