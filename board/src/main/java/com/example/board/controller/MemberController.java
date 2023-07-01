package com.example.board.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.dto.MemberDTO;
import com.example.board.service.EmailService;
import com.example.board.service.MemberService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MemberController {
	
	
	private final MemberService memberService;
	private final EmailService emailService;
	
	
	
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
		
		if(memberService.insertMemberProcess(memberdto, file) > 0) {
			return "redirect:/member/signin";
		}
		return "member/signup";
	}

	
	
	/* 로그인 페이지 이동 */
	@GetMapping("/member/signin")
	public String signIn() {
		return "member/signin";
	}
	
	
	/* 로그인 처리 */
	@PostMapping("/member/signinprocess")
	public String signInProcess(MemberDTO memberdto,
								HttpSession session,
								Model model) throws Exception {
		
		// 1. DB에 아이디와 비밀번호를 대조했는데, 일치하는 값이 나오지 않은 경우 (로그인 실패 이유 msg에 담아 화면에 출력)
		if(memberService.signIn(memberdto).size() == 0) {
			model.addAttribute("msg", memberService.loginFail(memberdto));		
			return "member/signin";
		}
		
		// 2. 로그인이 성공한 경우 (세션에 등록)
		MemberDTO loginUserSession = memberService.findById(memberdto.getMember_id());
		session.setAttribute("loginUserSession", loginUserSession);
		
		return "redirect:/";
		
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
	@PostMapping("/member/updateprocess")
	public String updateMemberAction(HttpSession session,
									 MemberDTO member,
									 MultipartFile file,
									 Model model) throws IOException {
		
		MemberDTO loginUserSession = (MemberDTO) session.getAttribute("loginUserSession");
		
		if(memberService.updateMemberProcess(loginUserSession, member, file) > 0) {
			return "redirect:/";
		}
		
		model.addAttribute("purpose", "update");
		model.addAttribute("loginUserSession", loginUserSession);
		
		return "member/signup";
	}
	
	
	

}
