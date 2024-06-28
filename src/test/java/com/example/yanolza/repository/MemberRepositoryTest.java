package com.example.yanolza.repository;

import com.example.yanolza.constant.Authority;
import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.TokenDto;
import com.example.yanolza.entity.Member;
import com.example.yanolza.jwt.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
//@Transactional
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TokenProvider tokenProvider;
    @Autowired
    AuthenticationManagerBuilder managerBuilder;

    public void createMember(){

        Member member = Member.builder()
                .nick("pp")

                .mid("pkmm")
                .email("pkmm@naver")
                .pwd("1234")
                .build();
        memberRepository.save(member);
    }

    public void createMembers(){
        for (int i = 0; i < 10; i++) {
            Member member = Member.builder()
                    .nick("pp"+i)
                    .mid("pkmm"+i)
                    .email("pkmm@naver"+i+".com")
                    .pwd("1234")
                    .authority(Authority.ROLL_USER)
                    .build();

            memberRepository.save(member);
        }
    }
    @Test
    @DisplayName("회원가입")
    public void signup() {
        createMembers();
    }
    @Test
    @DisplayName("로그인")
    public void login() {
        createMember();
        MemberReqDto requestDto = new MemberReqDto();
        requestDto.setMid("pkmm");
        requestDto.setPwd("1234");

        try {
            UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

            TokenDto token = tokenProvider.generateTokenDto(authentication);

            Member member = memberRepository.findByMid(requestDto.getMid()).get();
            String encodedToken = token.getRefreshToken();
            member.setRefreshToken(encodedToken.concat("="));
            member.setRefreshTokenExpiresIn(token.getRefreshTokenExpiresIn());

            memberRepository.save(member);

            System.out.println(token);
        }
        catch(Exception e) {
            log.error("로그인 중 에러 발생 : ", e);
        }
    }
}