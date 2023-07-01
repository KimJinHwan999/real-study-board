package com.example.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.MemberDTO;
import com.example.board.dto.PhotoDTO;
import com.example.board.dto.ReplyDTO;
import com.example.board.service.BoardService;
import com.example.board.service.MemberService;
import com.example.board.service.PhotoService;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final MemberService memberService;
	private final BoardService boardService;
	private final PhotoService photoService;
	private final ReplyService replyService;

	/* 글 작성 페이지로 이동 */
	@GetMapping("/board/write")
	public String writeBoard(Model model) {
		// board/write 의 form action 경로 조정
		model.addAttribute("path", "/board/writeprocess");
		return "board/write";
	}

	/* 글 작성 처리 */
	@PostMapping("/board/writeprocess")
	public String writeBoardAction(HttpSession session, 
								   Model model,
								   BoardDTO board, 
								   List<MultipartFile> file) throws IOException {
		String msg = "";
		
		MemberDTO member = (MemberDTO) session.getAttribute("loginUserSession");
		
		/* 유효성 검사 */
		if (board.getPost_name() == null || board.getPost_name().trim().isEmpty()) {
			msg = "제목을 입력해주세요";
			model.addAttribute("msg", msg);
			model.addAttribute("board", board);
			return "board/write";
		}
		if (board.getPost_text() == null || board.getPost_text().trim().isEmpty()) {
			msg = "내용을 입력해주세요";
			model.addAttribute("msg", msg);
			model.addAttribute("board", board);
			return "board/write";
		}
		if (board.getBoard_num() == null) {
			msg = "게시판을 선택해주세요";
			model.addAttribute("msg", msg);
			model.addAttribute("board", board);
			return "board/write";
		}

		/* 글 등록 */
		if(boardService.insertBoardProcess(member, board) > 0) {
			
			/* 만약 사진 첨부파일이 있다면, 아래 로직 통과시키기 */
			if (file.get(0).getSize() > 0) {
				/* img파일 경로 잡아주기 & DB에 첨부 사진 등록 */
				boardService.postImgPath(board, file);
			}
			
			return "redirect:/";
		};
		
		msg = "오류가 발생했습니다.";
		model.addAttribute("msg", msg);
		model.addAttribute("board", board);
		return "board/write";
	}

	/* 글 읽기 */
	@GetMapping("/board/post/{post_id}")
	public String readPost(@PathVariable("post_id") Long post_id, 
						   Model model, 
						   HttpSession session) {

		/* 로그인 한 계정 정보 찾아오기 */
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");

		/* 게시글 번호로 게시글 찾아오기 */
		BoardDTO post = boardService.findById(post_id);

		/* 조회수 로직 */
		boardService.updateViews(post,post_id);
		
		/* PK로 회원 데이터 찾아오기 */
		MemberDTO postMember = memberService.findByNo(post.getMember_no());

		/* 글 PK로 사진 이름 찾아오기 */
		List<PhotoDTO> postPhoto = new ArrayList<>();
		postPhoto = photoService.findPhotoByNo(post_id);

		/* 댓글 불러오기 */
		List<ReplyDTO> reply = new ArrayList<>();
		reply = replyService.findReplyByPostId(post_id);

		model.addAttribute("loginUserSession", loginUserSession);
		model.addAttribute("post", post);
		model.addAttribute("postMember", postMember);
		model.addAttribute("postPhoto", postPhoto);
		model.addAttribute("reply", reply);

		return "board/post";
	}

	/* 글 삭제 */
	@GetMapping("/board/delete/{post_id}")
	public String deletePost(@PathVariable("post_id") Long post_id) {

		replyService.deleteAllReply(post_id);
		photoService.deleteImage(post_id);
		boardService.deletePost(post_id);

		return "redirect:/";
	}

	/* 글 수정 페이지 이동 */
	@GetMapping("/board/update/{post_id}")
	public String updatePost(@PathVariable("post_id") Long post_id, 
							 Model model) {
		String path = "/board/updateprocess/" + post_id;

		/* 해당 글의 데이터 가져오기 */
		BoardDTO board = boardService.findById(post_id);
		
		/* 해당 글의 사진 데이터 가져오기 */
		List<PhotoDTO> photo = new ArrayList<>();
		photo = photoService.findPhotoByNo(post_id);

		// board/write 의 form action 경로 조정
		model.addAttribute("path", path);
		model.addAttribute("board", board);
		model.addAttribute("photo", photo);

		return "board/write";
	}

	/* 글 수정 처리 */
	@PostMapping("/board/updateprocess/{post_id}")
	public String updatePostAction(@PathVariable("post_id") Long post_id, 
								   Model model, 
								   HttpSession session,
								   List<MultipartFile> file, 
								   BoardDTO board) throws IOException {

		// 로그인 세션에서 멤버PK가져오기위해 세션불러오기
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");

		boardService.updatePost(board, loginUserSession, post_id);


		/* 만약 사진 첨부파일이 있다면, 아래 로직 통과시키기 */
		if (file.get(0).getSize() > 0) {
			/* 기존 저장된 사진은 삭제 */
			photoService.deleteImage(post_id);
			/* img파일 경로 잡아주기 & DB에 첨부 사진 등록 */
			boardService.postImgPath(board, file);
		}

		/* 새로 업데이트된 내용 찾아오기 */
		BoardDTO updateBoard = boardService.findById(post_id);

		model.addAttribute("board", updateBoard);

		return "redirect:/board/post/{post_id}";

	}


}
