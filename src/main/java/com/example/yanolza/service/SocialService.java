package com.example.yanolza.service;

import com.example.yanolza.dto.KakaoDto;
import com.example.yanolza.entity.Social;
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

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class SocialService {
    private final RestTemplate restTemplate;
    private  final SocialRepository socialRepository;

    public Social kakaoUserInfo(String kakaoToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        headers.set("Authorization", "Bearer " + kakaoToken);
        String url = "https://kapi.kakao.com/v2/user/me";

        try {
            ResponseEntity<KakaoDto> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    KakaoDto.class
            );
            KakaoDto kakaoDto = responseEntity.getBody();
            String mid = kakaoDto.getKakaoAccount().getEmail();
            if(!socialRepository.findByMid(mid).isPresent())
            {saveSocialEntity(kakaoDto);}
            return socialRepository.findByMid(mid).get();
        }catch(Exception e) {
            log.error("카카오 가입 시도 중 오류 발생(카카오 서비스)");
            return null;
        }
    }

    private void saveSocialEntity(KakaoDto kakaoDto) {
        Social social = kakaoDto.toEntity();
        socialRepository.save(social);
    }
}
