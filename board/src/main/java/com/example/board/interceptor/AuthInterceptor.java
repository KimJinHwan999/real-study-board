package com.example.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

// Slf4j = 로깅 프레임 워크에 대한 추상화 라이브러리
// @component = 자동 빈 등록
// @Component로 Bean으로 등록하여 AutoInterceptor를 WebConfig로 념겨 registry에 등록
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, 
							 HttpServletResponse response, 
							 Object handler) throws Exception {
		// Controller로 보내기 전 이벤트 작동
		// false일 경우 실행 종료 (Controller 진입 안함)
		log.info("pre");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("loginUserSession") == null) {
			response.sendRedirect("/member/signin");
		}
		return true;
		
	}
	
	
	@Override
    public void postHandle(HttpServletRequest request, 
                           HttpServletResponse response, 
                           Object handler, 
                           ModelAndView modelAndView) throws Exception {

		// - Controller 진입 후 View가 Rendering 되기 전 수행
        // - ModelAndView modelAndView를 통해 화면 단에 들어가는 Data 등의 조작이 가능
		
		log.info("post");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, 
                                HttpServletResponse response, 
                                Object handler, 
                                Exception ex) throws Exception {

    	// - Controller 진입 후 View가 정상적으로 Rendering 된 후 수행
    	
    	log.info("after");
    }
	
	
}
