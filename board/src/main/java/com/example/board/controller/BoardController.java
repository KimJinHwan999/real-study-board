package com.example.board.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

		String path = "/board/write";

		// board/write 의 form action 경로 조정
		model.addAttribute("path", path);
		return "board/write";
	}

	/* 글 작성 처리 */
	@PostMapping("/board/write")
	public String writeBoardAction(HttpSession session, Model model, BoardDTO board, List<MultipartFile> file)
			throws IOException {
		String msg = "";

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

		// 세션에 저장된 아이디 불러오기 + 불러온 아이디의 pk번호 추출
		MemberDTO member = (MemberDTO) session.getAttribute("loginUserSession");
		Long writeMemberNo = member.getMember_no();

		// insert할 board의 member_no값 채워넣어주기
		board.setMember_no(writeMemberNo);
		// insert할 board의 post_date 채워주기
		board.setPost_date(Timestamp.valueOf(LocalDateTime.now().withNano(0)));

		/* 글 등록 */
		boardService.insertBoard(board);

		/* 만약 사진 첨부파일이 있다면, 아래 로직 통과시키기 */
		if (file.get(0).getSize() > 0) {
			Long post_id = board.getPost_id();
			if (post_id == null) {
				return "board/write";
			}

			/* img파일 경로 잡아주기 & DB에 첨부 사진 등록 */
			boardService.postImgPath(post_id, file);
		}

		return "redirect:/";
	}

	/* 글 읽기 */
	@GetMapping("/board/post/{post_id}")
	public String readPost(@PathVariable("post_id") Long post_id, Model model, HttpSession session) {

		/* 로그인 한 계정 정보 찾아오기 */
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");

		/* 게시글 번호로 게시글 찾아오기 */
		BoardDTO post = boardService.findById(post_id);

		/* 조회수 로직 */
		Long post_views = post.getPost_views();
		++post_views;
		boardService.updateViews(post_id, post_views);
		// 내가 글을 읽어서 하나 증가된 조회수를 다시 담아주기
		post.setPost_views(post_views);

		/* PK로 회원 데이터 찾아오기 */
		MemberDTO postMember = memberService.findByNo(post.getMember_no());

		/* 글 PK로 사진 이름 찾아오기 */
		List<String> postPhoto = new ArrayList<>();
		postPhoto = (List<String>) photoService.findPhotoByNo(post_id);

		/* 댓글 불러오기 */
		List<ReplyDTO> reply = new ArrayList<>();
		reply = replyService.findReplyByPostId(post_id);

		for (int i = 0; i < reply.size(); i++) {
			reply.get(i).setMember_id(memberService.findIdByNo(reply.get(i).getMember_no()));
		}

		// 현재 세션에 로그인되어있는 계정 정보
		model.addAttribute("loginUserSession", loginUserSession);
		// 보고있는 게시글 정보
		model.addAttribute("post", post);
		// 보고있는 게시글 쓴 사람 정보
		model.addAttribute("postMember", postMember);
		model.addAttribute("postPhoto", postPhoto);
		model.addAttribute("reply", reply);

		return "board/post";
	}

	/* 글 삭제 */
	@GetMapping("/board/delete/{post_id}")
	public String deletePost(@PathVariable("post_id") Long post_id) {

		photoService.deleteImage(post_id);
		boardService.deletePost(post_id);

		return "redirect:/";
	}

	/* 글 수정 페이지 이동 */
	@GetMapping("/board/update/{post_id}")
	public String updatePost(@PathVariable("post_id") Long post_id, Model model) {
		String path = "/board/update/" + post_id;

		/* 해당 글의 데이터 가져오기 */
		BoardDTO board = boardService.findById(post_id);

		// board/write 의 form action 경로 조정
		model.addAttribute("path", path);
		model.addAttribute("board", board);

		return "board/write";
	}

	/* 글 수정 처리 */
	@PostMapping("/board/update/{post_id}")
	public String updatePostAction(@PathVariable("post_id") Long post_id, Model model, HttpSession session,
			List<MultipartFile> file, BoardDTO board) throws IOException {

		// 로그인 세션에서 멤버PK가져오기위해 세션불러오기
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");

		// 비어있는 값들 채워넣어주기
		board.setMember_no(loginUserSession.getMember_no());
		board.setPost_date(Timestamp.valueOf(LocalDateTime.now().withNano(0)));
		board.setPost_views(boardService.findById(post_id).getPost_views());

		boardService.updatePost(board);

		/* 기존 저장된 사진은 삭제 */
		photoService.deleteImage(post_id);

		/* 만약 사진 첨부파일이 있다면, 아래 로직 통과시키기 */
		if (file.get(0).getSize() > 0) {
			/* img파일 경로 잡아주기 & DB에 첨부 사진 등록 */
			boardService.postImgPath(post_id, file);
		}

		/* 새로 업데이트된 내용 찾아오기 */
		BoardDTO updateBoard = boardService.findById(post_id);

		model.addAttribute("board", updateBoard);

		return "redirect:/";

	}


}
