package com.example.yanolza.controller;

import com.example.yanolza.dto.MemberReqDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.entity.Dibs;
import com.example.yanolza.repository.DibsRepository;
import com.example.yanolza.service.DibsService;
import com.example.yanolza.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class MyController {
    public final MemberService memberService;
    public final DibsService dibsService;

    @GetMapping("/detail")
    public ResponseEntity<MemberResDto> memberDetail() {
        MemberResDto memberDto = MemberResDto.of(memberService.memberIdFindMember());
        return ResponseEntity.ok(memberDto);
    }

    @GetMapping("/dibs")
    public ResponseEntity<Boolean> dibs (@RequestParam Long tno){
        return ResponseEntity.ok(dibsService.dibs(tno));
    }
    @GetMapping("/undibs")
    public ResponseEntity<Boolean> undibs (@RequestParam Long tno){
        return ResponseEntity.ok(dibsService.undibs(tno));
    }
    @GetMapping("/dibslist")
    public ResponseEntity<List<TravelDto>> dibsList (){
        return ResponseEntity.ok(dibsService.dibsList());
    }
}
