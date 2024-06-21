package com.example.yanolza.controller;

import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    @Autowired
    MemberService memberService;

    @GetMapping("/detail")
    public ResponseEntity<MemberResDto> memberDetail() {
        MemberResDto memberDto = MemberResDto.of(memberService.memberIdFindMember());
        return ResponseEntity.ok(memberDto);
    }
}
