package com.example.board.service;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.board.dto.MemberDTO;
import com.example.board.dto.RSA;
import com.example.board.mapper.MemberMapper;
import com.example.board.util.RSAUtil;
import com.example.board.util.SHA256;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberMapper memberMapper;
	
	
	/* 회원가입 */
	@Transactional
	public int insertMember(MemberDTO memberdto) {
        return memberMapper.insertMember(memberdto);
    }
	
	/* 아이디 중복체크 ajax */
	@Transactional
	public int idAjax(String member_id) {
		return memberMapper.idAjax(member_id);
	}
	
	/* 프로필 이미지 경로 설정 메소드 */
	@Transactional
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
		return memberMapper.signIn(memberdto);
	}
	
	/* 3. 아이디 비밀번호 복호화 */
	public MemberDTO decryptText(MemberDTO memberdto,
								 PrivateKey key) throws Exception{
		
		RSAUtil rsaUtil = new RSAUtil();
		
		// 3-1. form에 담겨온 id와 pw를 decryptText메소드를 이용해 복호화하여 원래의 평문으로 되돌림
		String id = rsaUtil.getDecryptText(key, memberdto.getMember_id());
        String password = rsaUtil.getDecryptText(key, memberdto.getMember_pw());

		// 3-2. 복호화된 평문을 memberdto에 재설정
        memberdto.setMember_id(id);
        memberdto.setMember_pw(password);
        
		return memberdto;
	}
	
	/* RSA privateKey, publicKey 생성 메소드 */
	public RSA rsaKey(HttpSession session) {
		
		// 1. RSAUtil 생성자 만들어줌
		RSAUtil rsaUtil = new RSAUtil();
		
		// 2. 세션에 "RSAprivateKey"라는 이름으로 private키가 존재하는지 확인
		PrivateKey key = (PrivateKey) session.getAttribute("RSAprivateKey");
		
		// 2-1. 기존에 키가 있다면 파기시켜 버리기
		if(key != null) { // 기존 key 파기
			session.removeAttribute("RSAprivateKey");
		}
		
		// 2-2. 기존에 키가 없는걸 확인하고, 새로운 키 값을 가진 RSA르 만들어 줌
		RSA rsa = rsaUtil.createRSA();
		
		return rsa;
	}
	
	/* 로그인 실패 원인 찾는 메소드 */
	public MemberDTO findById(String member_id){
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
	
	public int updateMember(MemberDTO member) {
		return memberMapper.updateMember(member);
	}
	
}


