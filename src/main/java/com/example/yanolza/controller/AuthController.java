package com.example.yanolza.controller;

import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.TokenDto;
import com.example.yanolza.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

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

    @GetMapping("/existid")
    public ResponseEntity<Boolean> conId(@RequestParam String id) {
        return ResponseEntity.ok(authService.conId(id));
    }

    @GetMapping("/existemail")
    public ResponseEntity<Boolean> conEmail(@RequestParam String email) {
        return ResponseEntity.ok(authService.conEmail(email));
    }

    @GetMapping("/existnick")
    public ResponseEntity<Boolean> conNick(@RequestParam String nick) {
        return ResponseEntity.ok(authService.conNick(nick));
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
}
