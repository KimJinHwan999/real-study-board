package com.example.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.board.mapper.HchartMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HchartService {

	private final HchartMapper hchartMapper;
	
	/* 하이차트 */
	public JsonObject chartJson() {
		
		// 존재하는 게시판들을 리스트로 가져오기
		List<Long> boardNumList = selectBoardNum_();
		
		// 게시판 별로 가지고 있는 글의 갯수를 Map에 넣어주기
        Map<String, Long> hchartBoardCnt = new HashMap<>();
		for(int i = 0; i < boardNumList.size(); i++) {
	 	    hchartBoardCnt.put(String.valueOf(boardNumList.get(i)), hchartBoardCnt(boardNumList.get(i)));
		}
		
		// Gson 라이브러리를 이용해 hchartBoardCnt 객체를 JSON 형식으로 변환하고 JsonObject 타입으로 가져오기
		Gson gson = new Gson();
		return gson.toJsonTree(hchartBoardCnt).getAsJsonObject();
	}
	
	/* 존재하는 게시판들을 리스트로 가져오기 */
	public List<Long> selectBoardNum_(){ 
		return hchartMapper.selectBoardNum_(); 
	}
	
	/* 게시판 별로 가지고 있는 글의 갯수를 Map에 넣어주기 */
	public Long hchartBoardCnt(Long boardNum){
		return hchartMapper.hchartBoardCnt(boardNum);
	}
}
