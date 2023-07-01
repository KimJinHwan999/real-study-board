package com.example.board.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.dto.MemberDTO;
import com.example.board.mapper.MemberMapper;
import com.example.board.util.SHA256;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberMapper memberMapper;
	
	
	/* 회원가입 전체 과정 */
	@Transactional
	public int insertMemberProcess(MemberDTO memberdto,
								   MultipartFile file) throws IOException {
		/* 프로필 이미지 경로 설정 메소드 */
		memberImgPath(memberdto, file);
		
		/* 암호 해쉬화 매소드 */
		hashPassWord(memberdto);
		
        return memberMapper.insertMember(memberdto);
    }
	
	/* 아이디 중복체크 ajax */
	@Transactional
	public int idAjax(String member_id) {
		return memberMapper.idAjax(member_id);
	}
	
	/* 회원 정보 업데이트 전체 과정 */
	@Transactional
	public int updateMemberProcess(MemberDTO loginUserSession,
								   MemberDTO member,
								   MultipartFile file) throws IOException {
		Long member_no = loginUserSession.getMember_no();
		
		member.setMember_no(member_no);
		
		/* 프로필 이미지 경로 설정 메소드 */
		memberImgPath(member, file);
		
		/* 암호 해쉬화 메소드 */
		hashPassWord(member);
		
		return memberMapper.updateMember(member);
	}
	
	/* 프로필 이미지 경로 설정 메소드 */
	public MemberDTO memberImgPath(MemberDTO memberdto,
							  	   MultipartFile file) throws IOException {
		
		// 0. 뒤에서 정의할 UUID로 수정한 파일 이름 (DB에 저장될 이름)
		String savedFileName = "";		
		
		// 1. 파일 저장 경로 (프로젝트 외부에 저장)
		String img_path = "C:\\uploads\\member_profile\\";
		
		// 2. 원본 파일 이름
		String original_member_img = file.getOriginalFilename();
		
		// 3. UUID를 이용해 파일이름 변경 (중복되지 않도록)
		UUID uuid = UUID.randomUUID();
		savedFileName = uuid.toString() + "_" + original_member_img;
		
		// 4. 파일 저장 (해당 경로에 폴더가 없다면 폴더 자동 생성)
		File uploadPath = new File(img_path, savedFileName);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}

		// 5. 서버로 전송 (이 단계에서 DB에 UUID로 수정한 파일명, 외부 경로에 이미지 파일 저장은 끝남)
		file.transferTo(uploadPath);
		
		// 6. 이후 session에 담아 로그인 상태를 확인시켜 줄 memberdto에 저장된 사진이름 담아주기
		memberdto.setMember_img(savedFileName);
				
		return memberdto;
	}
	
	/* 암호 해쉬화 매소드 */
	public MemberDTO hashPassWord(MemberDTO memberdto) {
		// 암호 해쉬화
		String rawPassword = memberdto.getMember_pw();
		String password = SHA256.encSha256(rawPassword);
		
		memberdto.setMember_pw(password);
		
		return memberdto;
	}
	
	/* 로그인 */
	public List<MemberDTO> signIn(MemberDTO memberdto){
		hashPassWord(memberdto);
		return memberMapper.signIn(memberdto);
	}
	
	/* 로그인 실패 원인 찾는 메소드 */
	public String loginFail(MemberDTO memberdto){
		String member_id = memberdto.getMember_id();
		MemberDTO memberData = memberMapper.findById(member_id);
		
		// 1-1. 아이디는 DB에 존재하지만, 비밀번호가 틀린 경우
		String msg = "비밀번호가 일치하지 않습니다.";
		// 1-2. 아이디 값이 아예 DB에 없는 경우
		if(memberData == null) {
			msg = "아이디가 존재하지 않습니다.";
		}
		return msg;
	}
	
	/* 회원 아이디로 회원정보 찾기 */
	public MemberDTO findById(String member_id) {
		return memberMapper.findById(member_id);
	}
	
	/* PK로 회원 데이터 불러오기 */
	public MemberDTO findByNo(Long member_no){
		return memberMapper.findByNo(member_no);
	}
	
	/* PK로 회원 아이디 불러오기 */
	public String findIdByNo(Long member_no){
		return memberMapper.findIdByNo(member_no);
	}

}


