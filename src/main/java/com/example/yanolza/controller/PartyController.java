package com.example.yanolza.controller;

import com.example.yanolza.dto.PartyRequestDto;
import com.example.yanolza.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

    @PostMapping("save")
    public ResponseEntity<Boolean> createParty(@RequestBody PartyRequestDto requestDto){
        return ResponseEntity.ok(partyService.partyInsert(requestDto));
    }
}
