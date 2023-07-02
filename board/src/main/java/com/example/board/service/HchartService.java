package com.example.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.board.mapper.HchartMapper;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HchartService {

	private final HchartMapper hchartMapper;
	
	/* 하이차트 */
	public String chartJson() {
		
		// 존재하는 게시판들을 리스트로 가져오기
		List<Long> boardNumList = selectBoardNum_();
		
		List<List<Map<String, Long>>> data = new ArrayList<>(); 
		for(int i = 0; i < boardNumList.size(); i++) {
			data.add(hchartBoardCnt(boardNumList.get(i)));
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		
		return json;
	}
	
	public String chartJson2() {
		
		List<Map<String, Long>> data = new ArrayList<>(); 
		data = hchartMapper.hchartMemberCnt();
		Gson gson = new Gson();
		String json = gson.toJson(data);
		
		return json;
	}
	
	/* 존재하는 게시판들을 리스트로 가져오기 */
	public List<Long> selectBoardNum_(){ 
		return hchartMapper.selectBoardNum_(); 
	}
	
	/* 게시판 별로 가지고 있는 글의 갯수를 Map에 넣어주기 */
	public List<Map<String, Long>> hchartBoardCnt(Long boardNum){
		return hchartMapper.hchartBoardCnt(boardNum);
	}
	
	public List<Map<String, Long>> selectMemberCnt(){
		return hchartMapper.hchartMemberCnt();
	}
}
