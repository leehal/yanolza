package com.example.yanolza.service;

import com.example.yanolza.dto.KakaoDto;
import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.entity.Member;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {
    private final RestTemplate restTemplate;
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberReqDto kakaoUserInfo(String kakaoToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + kakaoToken);
        String url = "https://kapi.kakao.com/v2/user/me";
        MemberReqDto memberReqDto = new MemberReqDto();
        try {
            ResponseEntity<KakaoDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoDto.class
            );
            KakaoDto kakaoDto = responseEntity.getBody();
            String mid = kakaoDto.getKakaoAccount().getEmail();
            String pwd = kakaoDto.getId();
            memberReqDto.setMid(mid);
            memberReqDto.setPwd(pwd);
            if(socialRepository.existsByMidAndSocial(mid,"COMMON")||socialRepository.existsByMidAndSocial(mid,"NAVER")||socialRepository.existsByMidAndSocial(mid,"GOOGLE")) {
                return null;
            }
            else if(!socialRepository.existsByMid(mid)){
                saveKakaoEntity(kakaoDto);
                return memberReqDto;
            }
            else return memberReqDto;
        }catch(Exception e) {
            log.error("카카오 가입 시도 중 오류 발생(카카오 서비스)");
            return null;
        }
    }

    private void saveKakaoEntity(KakaoDto kakaoDto) {
        Member member = kakaoDto.toEntity(passwordEncoder);
        memberRepository.save(member);
    }
}
