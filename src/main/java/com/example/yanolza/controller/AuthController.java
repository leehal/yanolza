package com.example.yanolza.controller;

import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.TokenDto;
import com.example.yanolza.entity.Social;
import com.example.yanolza.service.AuthService;
import com.example.yanolza.service.SocialService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SocialService socialService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResDto> signup(@RequestBody MemberReqDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberReqDto requestDto) {
        TokenDto tokenDto = authService.login(requestDto);
        System.out.println("token : "+tokenDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/existinfo")
    public ResponseEntity<Boolean> existMember(@RequestBody Map<String,String > data){
        String info = data.get("info");
        int type = Integer.parseInt(data.get("type"));
        return ResponseEntity.ok(authService.existInfo(info,type));
    }

    @GetMapping("/findinfo")
    public ResponseEntity<String> findInfo(@RequestParam String email){
        return ResponseEntity.ok(authService.findInfo(email));
    }

//    @GetMapping("/certemail")
//    public ResponseEntity<String> certEmail(@RequestParam String email) {
//        return ResponseEntity.ok(authService.certEmail(email));
//    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> newToken(@RequestBody String refreshToken) {
        log.info("refreshToken: {}", refreshToken);
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }
    @GetMapping("/social")
    public ResponseEntity<TokenDto> social(@RequestParam String token) {
        Social social = socialService.kakaoUserInfo(token);
        TokenDto tokenDto = authService.login(MemberReqDto.of(social));
        System.out.println("token : "+tokenDto);
        return ResponseEntity.ok(tokenDto);
    }
}
