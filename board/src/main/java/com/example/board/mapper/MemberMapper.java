package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.dto.MemberDTO;

@Mapper
public interface MemberMapper {
	
	/* 회원가입 */
	public int insertMember(MemberDTO memberdto);
	
	/* 아이디 중복체크 ajax */
	public int idAjax(String member_id);
	
	/* 로그인 */
	public List<MemberDTO> signIn(MemberDTO memberdto);
	
	/* 로그인 실패 시 아이디 존재 여부 확인 */
	/* 쿠키 아이디 값으로 회원 정보 검색 */
	public MemberDTO findById(String member_id);
	
	/* PK로 회원 데이터 불러오기 */
	public MemberDTO findByNo(Long member_no);
	
	/* PK로 회원 아이디 불러오기 */
	public String findIdByNo(Long member_no);
	
	/* 회원 정보 수정 */
	public int updateMember(MemberDTO member);
	
}

