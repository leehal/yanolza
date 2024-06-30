package com.example.yanolza.controller;

import com.example.yanolza.dto.AdvertisementDto;
import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.TokenDto;
import com.example.yanolza.repository.AdvertisementRepository;
import com.example.yanolza.service.AdvertisementService;
import com.example.yanolza.service.AuthService;
import com.example.yanolza.service.SocialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final SocialService socialService;
    private final AdvertisementService advertisementService;
    private final AdvertisementRepository advertisementRepository;

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
    @PostMapping("/resetpw")
    public ResponseEntity<Boolean> resetPw(@RequestBody MemberReqDto memberReqDto){
        return ResponseEntity.ok(authService.resetPw(memberReqDto));
    }

    @GetMapping("/certemail")
    public ResponseEntity<String> certEmail(@RequestParam String email) {
        return ResponseEntity.ok(authService.certEmail(email));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> newToken(@RequestBody String refreshToken) {
        log.warn("refreshToken: {}", refreshToken);
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }
    @GetMapping("/kakao")
    public ResponseEntity<TokenDto> social(@RequestParam String token) {
        MemberReqDto memberReqDto = socialService.kakaoUserInfo(token);
        TokenDto tokenDto = authService.login(memberReqDto);
        System.out.println("token : "+tokenDto);
        return ResponseEntity.ok(tokenDto);
    }
    @PostMapping("/adpublished")
    public ResponseEntity<Boolean> adList(@RequestBody AdvertisementDto advertisementDto){
        return ResponseEntity.ok(advertisementService.adPublish(advertisementDto));
    }

    @GetMapping("/adlist")
    public ResponseEntity<List<AdvertisementDto>> adList(@RequestParam Long now){
        return ResponseEntity.ok(advertisementService.adList(now));
    }
}
