package com.example.board.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.BoardDTO;
import com.example.board.mapper.BoardMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PagingService {

    public final BoardMapper boardMapper;
		
    /* 페이징 로직 */
    public HashMap<String, Object> pagingLogic(int pgNum) {
    	
    	HashMap<String, Object> page = new HashMap<>();
    	
    	// 전체 페이지 리스트
		List<BoardDTO> board = boardMapper.findAllBoard();
    	
    	// 전체 페이지 갯수
    	int totalPageCnt = findAllPageCnt(board);
    	
    	// 이전, 다음 페이지에 넣어줄 변수 만드는 메소드
    	int nextPrevPage = nextPrevPage(pgNum, totalPageCnt);
		
		// 1개의 페이지에 담길 전체 데이터 뽑아오기 (10개씩)
		List<BoardDTO> findPageData = findPageData(nextPrevPage);
		
		// 전체 페이징 메소드
		List<Integer> totalPageList = totalPageList(nextPrevPage, totalPageCnt);
		
		
		page.put("board", board);
		page.put("nextPrevPage", nextPrevPage);
		page.put("findPageData", findPageData);
		page.put("totalPageList", totalPageList);
		
		return page;
    	
    }
	
    // DB에 저장된 데이터 양을 파악하여, 전체 설정할 페이지의 수를 뽑아옴(134개 저장 -> 페이지 14개)
    public int findAllPageCnt(List<BoardDTO> board) {
	    // pageTotalCnt는 전체 페이지의 갯수
	    int pageTotalCnt; 
	   
	    if(board.size() % 10 == 0) {
		    pageTotalCnt = board.size() / 10;
	    }else {
		    pageTotalCnt = board.size() / 10 + 1;
	    }
	   
	    return pageTotalCnt;
    }
	   
    // 이전, 다음 버튼 메소드
    public int nextPrevPage(int pgNum, int totalPageCnt) {
		   
	    // 현재 페이지 1페이지에서 << 누르면 1로 유지
		// 현재 페이지 최대 페이지에서 >> 누르면 최대페이지 유지
        if(pgNum <= 1) {
		    pgNum = 1;
		}else if(pgNum > totalPageCnt) {
		    pgNum = totalPageCnt;
		}
		return pgNum; 
	}
	   
    // 한 페이지에 담길 데이터 조회 (10개)
    public List<BoardDTO> findPageData(int nextPrevPage){
	   
	    Long nextPrevPageL = Long.valueOf(nextPrevPage);
	    Long firstPost = nextPrevPageL * 10 - 10;
	    Long postingCnt = 10L;
	   
	    // nowPage는 현재 내가 위치해있는 페이지 위치
	    return boardMapper.findPageData(firstPost, postingCnt);
    }
	   
    public List<Integer> totalPageList(int nextPrevPage, int totalPageCnt) {
    	
	    // 페이지 총 갯수를 리스트로 담아주기
		List<Integer> totalPageList = new ArrayList<Integer>();
		int i = nextPrevPage / 5;
		int j = totalPageCnt / 5;
		int k = 0;
		
		// 전체 페이지 6보다 작을 때 (이 조건만 타면 끝)
		if(totalPageCnt < 6) {
			for(k = 1; k <= totalPageCnt; k++) {
				totalPageList.add(k);
			}
			return totalPageList;
		}
		
		// 전체 페이지 6보다 클 때
		// 현재 페이지가 5의 배수가 아닐 때
		if(nextPrevPage % 5 != 0) {
			// 현재 페이지가 마지막 페이지와 다른 줄에 있을 때
			if(i < j) {
				for(k = i * 5 + 1 ; k < i * 5 + 6; k++) {
					totalPageList.add(k);
				}
			}
			// 현재 페이지가 마지막 페이지와 같은 줄에 있을 때
			if(i == j) {
				for(k = i * 5 + 1 ; k <= totalPageCnt; k++) {
					totalPageList.add(k);
				}
			}
			return totalPageList;
		}
		
		
		// 전체 페이지 6보다 클 때
		// 현재 페이지가 5의 배수일 때
		// 현재 페이지가 6보다 작을 때
		if(nextPrevPage < 6) {
			for(k = 1; k < 6; k++) {
				totalPageList.add(k);
			}
			return totalPageList;
		}
		
		// 전체 페이지 6보다 클 때
		// 현재 페이지가 5의 배수일 때
		// 현재 페이지가 6보다 클 때
		// 현재 페이지가 마지막 페이지와 다른 줄에 있을 때
		if(i < j) {
			for(k = (i - 1) * 5 + 1 ; k < i * 5 + 6; k++) {
				totalPageList.add(k);
			}
			return totalPageList;
		}
		
		// 전체 페이지 6보다 클 때
		// 현재 페이지가 5의 배수일 때
		// 현재 페이지가 6보다 클 때
		// 현재 페이지가 마지막 페이지와 같은 줄에 있을 때
		// 현재 페이지가 마지막 페이지와 같은 줄에 있지만, 현재페이지가 마지막 페이지는 아닐 때
		if(nextPrevPage < totalPageCnt) {
			for(k = (i - 1) * 5 + 1 ; k <= i * 5; k++) {
				totalPageList.add(k);
			}
		}
				
		// 전체 페이지 6보다 클 때
		// 현재 페이지가 5의 배수일 때
		// 현재 페이지가 6보다 클 때
		// 현재 페이지가 마지막 페이지와 같은 줄에 있을 때
		// 현재 페이지가 마지막 페이지일 때
		if(nextPrevPage == totalPageCnt) {
			for(k = (i - 1) * 5 + 1 ; k <= totalPageCnt; k++) {
				totalPageList.add(k);
			}
		}
		
		return totalPageList;
   }
}
