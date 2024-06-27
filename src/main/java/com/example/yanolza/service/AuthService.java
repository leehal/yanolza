package com.example.yanolza.service;

import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.TokenDto;
import com.example.yanolza.entity.Member;
import com.example.yanolza.jwt.TokenProvider;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final JavaMailSender mailSender;
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    public Boolean existInfo(String info, int type){
        Boolean res = true;
        switch (type){
            case 1: return memberRepository.existsByMid(info);
            case 2:  return memberRepository.existsByEmail(info);
            case 3: return memberRepository.existsByNick(info);
            default: return res;
        }
    }
    public String findInfo(String email){
            Optional<Member> member = memberRepository.findByEmail(email);
            if(member.isPresent()){
                Member mem = member.get();
                return mem.getMid();
            }
            else return null;
        }

    public String certEmail(String email) {

        Random random = new Random();
        int min = 111111;
        int max = 999999;
        String certification = String.valueOf(random.nextInt(max - min + 1) + min);
        System.out.println("인증 번호 : " + certification);

        String htmlContent = "<div style=\"width: 100%; background-color: #f9f9f9; padding: 20px;\">"
                + "<div style=\"max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; border: 1px solid #eaeaea; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">"
                + "<div style=\"padding: 20px; text-align: center; background-color: #ffeb3b;\">"
                + "<h1 style=\"margin: 0; font-size: 24px; color: #333333;\">안나와아이들계정</h1>"
                + "</div>"
                + "<div style=\"padding: 20px; color: #333333;\">"
                + "<p style=\"margin: 0 0 20px; font-weight: bold;\">안나와아이들계정 연락처 등록을 위한 인증번호입니다.</p>"
                + "<p style=\"margin: 0 0 20px; font-size: 15px\">아래 인증번호를 확인하여 이메일 주소 인증을 완료해 주세요.</p>"
                + "<div style=\"width: 100%; border-collapse: collapse; margin: 20px 0;\">"
                + "<div style=\"display: flex; gap:15px; padding: 15px; border-top: 1px solid #eaeaea; \">"
                + "<strong style=\"width:120px;\">연락처 이메일</strong>"
                + "<span>"+email+"</span>"
                + "</div>"
                + "<div style=\"display: flex; gap:15px; padding: 15px; border-bottom: 1px solid #eaeaea;\">"
                + "<strong style=\"width:120px;\">인증번호</strong>"
                + "<span>"+certification+"</span>"
                + "</div>"
                + "</div>"
                + "<p style=\"margin: 0 0 20px; font-size: 12px; color: #666666;\">본 메일은 발신전용입니다.</p>"
                + "</div>"
                + "<div style=\"padding: 20px; text-align: center; background-color: #f1f1f1; font-size: 12px; color: #666666;\">"
                + "Copyright © 안나와아이들. All rights reserved."
                + "</div>"
                + "</div>"
                + "</div>";

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            helper.setFrom("y971030@naver.com");
            helper.setTo(email);
            helper.setSubject("안나와 아이들들 인증");
            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        mailSender.send(mimeMessage);
        return certification.toString();
    }

    public MemberResDto signup(MemberReqDto requestDto) {
        Member member = requestDto.toEntity(passwordEncoder);
        return MemberResDto.of(memberRepository.save(member));
    }

    public TokenDto login(MemberReqDto requestDto) {
        try{
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto token = tokenProvider.generateTokenDto(authentication);
        log.info(String.valueOf(token));

        Member member = memberRepository.findByMid(requestDto.getMid()).get();
        String encodedToken = token.getRefreshToken();
        member.setRefreshToken(encodedToken.concat("="));
        member.setRefreshTokenExpiresIn(token.getRefreshTokenExpiresIn());

        memberRepository.save(member);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            log.info("현재 인증 정보: " + auth);

        return token;

        } catch (Exception e) {
            log.error("로그인 중 에러 발생 : ", e);
            throw new RuntimeException("로그인 중 에러 발생", e);
        }
    }

    public TokenDto refreshAccessToken(String refreshToken) {
        log.info("refreshToken : {}", refreshToken);
        log.info("일반refreshExist : {}", memberRepository.existsByRefreshToken(refreshToken));

        //DB에 일치하는 refreshToken이 있으면
        if(memberRepository.existsByRefreshToken(refreshToken)){
            // refreshToken 검증
            try {
                if(tokenProvider.validateToken(refreshToken)) {
                    return tokenProvider.generateTokenDto(tokenProvider.getAuthentication(refreshToken));
                }
            }catch (RuntimeException e) {
                log.error("토큰 유효성 검증 중 예외 발생 : {}", e.getMessage());
            }
        }
        return null;
    }
    public Boolean resetPw(MemberReqDto memberReqDto){
        if (memberRepository.findByMid(memberReqDto.getMid()).isEmpty()) {
            return false;
        }
        Member member = memberRepository.findByMid(memberReqDto.getMid()).get();
        member.setPwd(passwordEncoder.encode(memberReqDto.getPwd()));
        memberRepository.save(member);
        return true;
    }
}