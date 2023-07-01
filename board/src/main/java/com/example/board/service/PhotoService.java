package com.example.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.PhotoDTO;
import com.example.board.mapper.PhotoMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PhotoService {

	private final PhotoMapper photoMapper;
	
	/* 이미지 파일 저장하는 로직 (FK로 글 번호와 묶음) */
	public int insertImage(Long post_id, String fileListOriginName ,String fileList) {
		return photoMapper.insertImage(post_id, fileListOriginName, fileList);
	}
	
	/* 이미지 파일 삭제하는 로직 */
	@Transactional
	public int deleteImage(Long post_id) {
		return photoMapper.deleteImage(post_id);
	}
	
	/* 글 PK로 사진 이름 찾아오기 */
	public List<PhotoDTO> findPhotoByNo(Long post_id) {
		return photoMapper.findPhotoByNo(post_id);
	}
	
}
