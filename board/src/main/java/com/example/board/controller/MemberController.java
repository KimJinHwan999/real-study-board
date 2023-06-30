package com.example.board.controller;


import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.MemberDTO;
import com.example.board.dto.RSA;
import com.example.board.service.BoardService;
import com.example.board.service.EmailService;
import com.example.board.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberController {
	
	
	private final MemberService memberService;
	private final EmailService emailService;
	private final BoardService boardService;
	
	
	
	/* 회원가입 페이지 이동 */
	@GetMapping("/member/signup")
	public String signUp(Model model) {
		// 해당 페이지가 회원가입을 위한 것임을 알려주기 위해 signup보냄
		model.addAttribute("purpose", "signup");
		return "member/signup";
	}
	
	/* 이메일 인증번호 발송 ajax */
	// key를 그대로 출력해 넘겨주기 위해 ResponseBody 사용 (안 썼으면 key.jsp 찾았을것)
	@ResponseBody
	@PostMapping("/emailCheck")
	public String signUpSendEmail(String member_email) {
		return emailService.mailCheck(member_email);
	}
	
	/* 회원가입 중 아이디 중복체크 ajax */
	@ResponseBody
	@PostMapping("/idcheck")
	public int signUpIdChk(String member_id) {
		return memberService.idAjax(member_id);
	}
	
	/* 회원가입 처리 */
	@PostMapping("/member/signupprocess")
	public String signUpProcess(MemberDTO memberdto,
								MultipartFile file) throws IOException{
		
		/* 프로필 이미지 경로 설정 메소드 */
		memberService.memberImgPath(memberdto, file);
		
		/* 암호 해쉬화 메소드 */
		memberService.hashPassWord(memberdto);
		
		
		if(memberService.insertMember(memberdto) > 0) {
			return "redirect:/member/signin";
		}
		return "member/signup";
	}

	
	
	/* 로그인 페이지 이동 */
	@GetMapping("/member/signin")
	public String signIn(HttpSession session,
						 Model model) {
		
		/* 0. RSA privateKey, publicKey 생성 메소드 */
		RSA rsa = memberService.rsaKey(session);
		
		// 2-3. 그렇게 RSAUtil.createRSA(); 메소드를 통해 만들어진 3형제 (privateKey, modulus, exponent)를 model과 session에 담아줌
		// privateKey는 로그인 처리과정에서 넘겨받아 보안검사를 할 예정
		// modulus와 exponent는 publicKey(공개키)로, 이후 HTML -> 자바스크립트단으로 넘어가 보안검사를 할 예정
		model.addAttribute("modulus", rsa.getModulus());
        model.addAttribute("exponent", rsa.getExponent());
        session.setAttribute("RSAprivateKey", rsa.getPrivateKey());

		return "member/signin";
	}
	
	
	/* 로그인 처리 */
	@PostMapping("/member/signinprocess")
	public String signInProcess(MemberDTO memberdto,
								HttpSession session,
								Model model,
								HttpServletResponse response,
								RedirectAttributes redirectAttributes) throws Exception {
		// 0. 출력될 문구 미리 선언
		String msg = ""; 
		
		// 1. 로그인 페이지 이동단에서 세션에 담겨 넘어온 개인키 취득
        PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
        
        // 1-1. 로그인 페이지로 이동할 때 private 키가 session에 담겨 넘어오지 않았다면 오류로 넘김
        if (key == null) {
        	redirectAttributes.addFlashAttribute("msg", "비정상 적인 접근 입니다.");
            return "redirect:/member/signin";
        }
        
        // 2. 확인 했으면, session에 저장된 개인키 초기화
        session.removeAttribute("RSAprivateKey");
        
		/* 3. 아이디 비밀번호 복호화 */
        memberService.decryptText(memberdto, key);
		
        /* 4. 암호 해쉬화 메소드 */
		memberService.hashPassWord(memberdto);
		
		/* 5. 아이디와 비밀번호를 확인하여 분기처리 */
		
		// 5-1. DB에 아이디와 비밀번호를 대조했는데, 일치하는 값이 나오지 않은 경우
		if(memberService.signIn(memberdto).size() == 0) {
			
			// 5-1-1. 아이디 값이 아예 DB에 없는 경우
			if(memberService.findById(memberdto.getMember_id()).getMember_id() == null) {			
				msg = "아이디가 존재하지 않습니다.";
				// msg에 원하는 메시지를 담고, redirect해서 페이지를 이동시킬 것이므로 addFlashAttribute 사용해준다 (url에 흔적 안남게)
				redirectAttributes.addFlashAttribute("msg", msg);
				return "redirect:/member/signin";
			}
			
			// 5-1-2. 아이디는 DB에 존재하지만, 비밀번호가 틀린 경우
			msg = "비밀번호가 일치하지 않습니다.";					
			redirectAttributes.addFlashAttribute("msg", msg);		
			return "redirect:/member/signin";
		}
		
		// 5-2. 로그인이 성공한 경우
		if(memberService.signIn(memberdto).size() != 0) {		
			
			// 5-2-0. 회원정보 전체 DB에서 긁어오기
			MemberDTO loginUserSession = memberService.findById(memberdto.getMember_id());
			
			// 5-2-1. 세션에 아이디 등록
			session.setAttribute("loginUserSession", loginUserSession);

			// 5-2-2. 쿠키생성 후 사용자에게 배포 (HomeController로 넘어가게 됨)
			Cookie idCookie = new Cookie("loginUserIdCookie", String.valueOf(loginUserSession.getMember_id()));
			response.addCookie(idCookie);
			
			List<BoardDTO> board = boardService.findAllBoard();
			
			redirectAttributes.addFlashAttribute("board", board);
			
			return "redirect:/";
		}
		
		return "redirect:/member/signin";
	}
	
	/* 아이디 찾기 */
	@GetMapping("/member/findid")
	public String findId() {
		return "member/findid";
	}
	
	/* 비밀번호 찾기 */
	@GetMapping("/member/findpw")
	public String findPw() {
		return "member/findpw";
	}
	
	/* 로그아웃 */
	@GetMapping("/member/logout")
	public String logout(HttpSession session,
						 HttpServletResponse response) {
		//session 만료
		session.invalidate();
		
		// 쿠키삭제
		Cookie cookie = new Cookie("loginUserIdCookie",null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		
		return "redirect:/";
	}
	
	/* 마이페이지 */
	@GetMapping("/member/mypage")
	public String myPage(HttpSession session,
						 Model model) {
		
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");
		
		model.addAttribute("loginUserSession", loginUserSession);
		
		return "member/mypage";
	}
	
	/* 개인 정보 수정 */
	@GetMapping("/member/update")
	public String updateMember(HttpSession session,
							   Model model) {
		
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");
		
		model.addAttribute("purpose", "update");
		model.addAttribute("loginUserSession", loginUserSession);
		
		return "member/signup";
	}
	
	/* 개인 정보 수정 처리 */
	@PostMapping("/member/update")
	public String updateMemberAction(HttpSession session,
									 MemberDTO member,
									 MultipartFile file,
									 Model model) throws IOException {
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");
		Long member_no = loginUserSession.getMember_no();
		
		member.setMember_no(member_no);
		
		/* 프로필 이미지 경로 설정 메소드 */
		memberService.memberImgPath(member, file);
		
		/* 암호 해쉬화 메소드 */
		memberService.hashPassWord(member);
		
		
		if(memberService.updateMember(member) > 0) {
			return "redirect:/";
		}
		
		
		return "redirect:/";
	}
	
	
	

}
