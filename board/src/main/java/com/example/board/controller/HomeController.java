package com.example.board.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.board.dto.MemberDTO;
import com.example.board.service.HchartService;
import com.example.board.service.PagingService;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final PagingService pagingService;
	private final HchartService hchartService;

	
	@GetMapping("/")
	public String home(HttpSession session,
					   HttpServletResponse response,
			  		   Model model) {
		int pgNum = 1;
		
        // 세션에 저장된 회원정보 불러오기
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginUserSession");
		
		/* 페이징 로직 */
		Map<String, Object> page = (HashMap<String, Object>) pagingService.pagingLogic(pgNum);
			
		/* 하이차트 */
		String chartJson = (String) hchartService.chartJson();
		String chartJson2 = (String) hchartService.chartJson2();
		
		model.addAttribute("loginMember", sessionMember);
		model.addAttribute("page", page);
		model.addAttribute("chartJson", chartJson);
		model.addAttribute("chartJson2", chartJson2);
		
		return "index/index";

	}
	
	@GetMapping("/{pgNum}")
	public String homePaging(@PathVariable("pgNum") int pgNum,
					   HttpSession session,
					   HttpServletResponse response,
			  		   Model model) {
		
        // 세션에 저장된 회원정보 불러오기
		MemberDTO sessionMember = (MemberDTO)session.getAttribute("loginUserSession");
		
		/* 페이징 로직 */
		Map<String, Object> page = (HashMap<String, Object>) pagingService.pagingLogic(pgNum);
			
		/* 하이차트 */
		String chartJson = (String) hchartService.chartJson();
		String chartJson2 = (String) hchartService.chartJson2();
		
		
		model.addAttribute("loginMember", sessionMember);
		model.addAttribute("page", page);
		model.addAttribute("chartJson", chartJson);
		model.addAttribute("chartJson2", chartJson2);
		
		return "index/index";

	}
}	

