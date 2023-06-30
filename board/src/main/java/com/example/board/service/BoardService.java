package com.example.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.dto.BoardDTO;
import com.example.board.mapper.BoardMapper;
import com.example.board.mapper.PhotoMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardMapper boardMapper;
	private final PhotoMapper photoMapper;
	
	/* 게시글 전체 가져오기 */
	public List<BoardDTO> findAllBoard() {
		return boardMapper.findAllBoard();
	}
	
	/* 게시글 등록 */
	@Transactional
	public Long insertBoard(BoardDTO board) {
		return boardMapper.insertBoard(board);
	}
	
	/* img파일 경로 잡아주기 & DB에 첨부 사진 등록 */
	@Transactional
	public void postImgPath(Long post_id,
		  	   				List<MultipartFile> file) throws IOException {

		// 0. 뒤에서 정의할 UUID로 수정한 파일 이름 (DB에 저장될 이름)
		String savedFileName = "";		
		
		// 1. 파일 저장 경로 (프로젝트 외부에 저장)
		String img_path = "C:\\uploads\\post_img\\";
		
		// 1-1. 원본 파일 이름과 바뀔 파일 이름을 차곡차곡 리스트에 담아 줄 예정(여러장의 사진이 올 수 있으니까)
		List<String> fileListOriginName = new ArrayList<>();
		List<String> fileList = new ArrayList<>();
		
		// 2. 여러 장의 사진이 올 경우를 대비해 반복문 태워서 사진 이름을 리스트에 담아줌
		for(int i = 0; i < file.size(); i++) {
			// 2-1. 원본 파일 이름
			String original_member_img = file.get(i).getOriginalFilename();
			
			// 2-2. UUID를 이용해 파일이름 변경 (중복되지 않도록)
			UUID uuid = UUID.randomUUID();
			savedFileName = uuid.toString() + "_" + original_member_img;
			
			// 2-3. 파일의 본래 이름과, 저장될 이름을 리스트에 각각 담아줌
			fileListOriginName.add(original_member_img);
			fileList.add(savedFileName);
		}
		
		for(int i = 0; i < file.size(); i++) {
			// 3. 파일 저장 (해당 경로에 폴더가 없다면 폴더 자동 생성)
			File uploadPath = new File(img_path, fileList.get(i));
			// 3-1. 해당 경로에 폴더가 없다면 폴더 자동 생성
			if(uploadPath.exists() == false) {
				uploadPath.mkdirs();
			}
			// 4. 서버로 전송 (이 단계에서 DB에 UUID로 수정한 파일명, 외부 경로에 이미지 파일 저장은 끝남)
			file.get(i).transferTo(uploadPath);
			
			int flag = 0;
			// 5. DB에 저장된 사진 이름 담아주기 (성공하면 int 1반환)
			flag = photoMapper.insertImage(post_id, fileListOriginName.get(i) ,fileList.get(i));
			
			// 5-1. 만약 파일을 저장하다가 한 번이라도 오류가나서 저장이 안되는 상황이 생긴다면, 0을 반환하고 앞서 저장했던 파일들을 삭제
			if(flag == 0) {
				photoMapper.deleteImage(post_id);
			}
			
		}
		
	}
	
	
	/* 게시글 번호로 게시글 찾아오기 */
	public BoardDTO findById(Long post_id) {
		return boardMapper.findById(post_id);
	}
	
	/* 조회수 로직 */
	public int updateViews(Long post_id, Long post_views) {
		return boardMapper.updateViews(post_id, post_views);
	}
	
	/* 글 삭제 */
	public int deletePost(Long post_id) {
		return boardMapper.deletePost(post_id);
	}
	
	/* 글 수정 */
	public int updatePost(BoardDTO board) {
		return boardMapper.updatePost(board);
	}
	
	/* 해당 게시판 모든 글 가져오기 */
	public BoardDTO findAllByBoardNum(Long board_num) {
		return boardMapper.findAllByBoardNum(board_num);
	}
	
	
	
	
}
