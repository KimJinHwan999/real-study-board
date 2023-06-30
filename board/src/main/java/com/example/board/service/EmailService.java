package com.example.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Transactional
    public String mailCheck(String email){
        Random random = new Random(); //난수 생성
        String key=""; // 인증번호

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("wlsghks5438@gamil.com");

        for(int i = 0;i<3;i++){
            int index = random.nextInt(25)+65;

            key+=(char)index;
        }
        int numIndex = random.nextInt(9999)+1000;

        key +=numIndex;
        message.setSubject("인증번호 입력을 위한 메일 전송");
        message.setText("인증 번호 : " + key);

        javaMailSender.send(message);
        return key;
    }
}