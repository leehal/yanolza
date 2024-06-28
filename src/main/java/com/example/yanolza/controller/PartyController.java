package com.example.yanolza.controller;

import com.example.yanolza.dto.*;
import com.example.yanolza.service.PartyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/party")
public class PartyController {
    private final PartyService partyService;

//   save party
    @PostMapping("/save")
    public ResponseEntity<Boolean> createParty(@RequestBody PartyRequestDto requestDto){
        log.info(requestDto.getPname());
        log.info(requestDto.getNick().toString());
        return ResponseEntity.ok(partyService.partyInsert(requestDto));
    }
//    내 모임명과 해당 pno list 반환
    @GetMapping("/list")
    public ResponseEntity<List<PartyNameListDto>> myPartyList(){
       return ResponseEntity.ok(partyService.partyView());
    }

//    선택된 날짜(처음은 그 날 날짜)와 선택한 pno값을 받아 날짜에 따른 일정과 그 파티의 멤버 반환
    @GetMapping("/plan")
    public ResponseEntity<PartyResponseDto> mypartyInfo(@RequestParam("pno") Long pno, @RequestParam("date") String date){
        List<String > memberList = partyService.partyMemberList(pno);
        List<CalendarDto> calendarDtos = partyService.partyCalendarOneDay(pno,date);
        PartyResponseDto dto = new PartyResponseDto(calendarDtos,memberList);
        return ResponseEntity.ok(dto);
    }
//    모든 회원 전체 조회 // 나중에 준영님 주고 지우고 그거 가져다 axios로 가져다 쓰기..
    @GetMapping("/alluser")
    public ResponseEntity<List<MemberResDto>> allMemberList(){
        List<MemberResDto> list = partyService.selectAllUsers();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<CalendarDto>> pnoCalendar(@RequestParam("pno") int pno){
        List<CalendarDto> list = partyService.selectCalendarPnoAll(pno);
        return ResponseEntity.ok(list);
    }
}
