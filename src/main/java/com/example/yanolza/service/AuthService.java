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
//    private final JavaMailSender mailSender;
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

//    public String certEmail(String email) {
//
//        Random random = new Random();
//        int min = 111111;
//        int max = 999999;
//        String certification = String.valueOf(random.nextInt(max - min + 1) + min);
//        System.out.println("인증 번호 : " + certification);
//
//        String htmlContent = "<div style=\"text-align: center; display:flex; flex-direction:column; justify-content:center; text-align:center;\">"
//                + "<p style=\"font-size:30px; display: block;\">안나와 아이들들 인증번호 입니다.</p>"
//                + "<p></p>"
//                + "<p style=\"font-size:16px; display: block;\">아래의 인증 번호를 입력해주세요.</p>"
//                + "<p></p>"
//                + "<div style=\"font-size:20px; font-style:bold; width: 100%; height:50px; border: 1px solid #c6c6c6; display: block;\">" + certification + "</div>"
//                + "</div>";
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
//        try {
//            helper.setFrom("anna&idle@naver.com");
//            helper.setTo(email);
//            helper.setSubject("안나와 아이들들 인증");
//            helper.setText(htmlContent, true);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        } catch (javax.mail.MessagingException e) {
//            throw new RuntimeException(e);
//        }
//        mailSender.send(mimeMessage);
//        return certification.toString();
//    }

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