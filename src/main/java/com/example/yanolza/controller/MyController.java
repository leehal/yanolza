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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/checkpw")
    public ResponseEntity<Boolean> chechPw (@RequestBody MemberReqDto memberReqDto) {
        return ResponseEntity.ok(memberService.checkPw(memberReqDto));
    }
    @PostMapping("/editinfo")
    public ResponseEntity<Boolean> editInfo (@RequestBody Map<String,String> data){
        String info = data.get("info");
        int type = Integer.parseInt(data.get("type"));
        return ResponseEntity.ok(memberService.editInfo(info,type));}
}
