package com.example.yanolza.controller;

import com.example.yanolza.dto.*;
import com.example.yanolza.service.PartyService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
//    @GetMapping("/plan")
//    public ResponseEntity<PartyResponseDto> mypartyInfo(@RequestParam("pno") Long pno, @RequestParam("date") String date){
//        List<MemberResDto > memberList = partyService.partyMemberList(pno);
//        List<CalendarDto> calendarDtos = partyService.partyCalendarOneDay(pno,date);
//        PartyResponseDto dto = new PartyResponseDto(calendarDtos,memberList);
//        return ResponseEntity.ok(dto);
//    }
//    모든 회원 전체 조회 // 나중에 준영님 주고 지우고 그거 가져다 axios로 가져다 쓰기..
    @GetMapping("/alluser")
    public ResponseEntity<List<MemberResDto>> allMemberList(){
        List<MemberResDto> list = partyService.selectAllUsers();
        return ResponseEntity.ok(list);
    }

    // pno에 해당하는 calendar 보여주기
    @GetMapping("/calendar")
    public ResponseEntity<List<String >> pnoCalendar(@RequestParam("pno") Long pno){
        List<String > list = partyService.selectCalendarPnoAll(pno);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/calendarOne/{pno}/{date}")
    public ResponseEntity<List<CalendarDto>> calendarOneDay(@PathVariable("pno")Long pno, @PathVariable("date") String date){
        List<CalendarDto> list = partyService.selectPnoCalendarOneDay(pno,date);
        return ResponseEntity.ok(list);
    }

    // 특정 파티의 계획 저장하기(하루단위)
    @PostMapping("/csave")
    public ResponseEntity<Boolean> calendarSave(@RequestBody CalendarSaveDto calendarSaveDto){
        boolean isTrue = false;
        isTrue = partyService.calendarInsert(calendarSaveDto);
        return ResponseEntity.ok(isTrue);
    }
    // pno에 따른 member List 반환
    @GetMapping("/pmember/{pno}")
    public ResponseEntity<List<MemberResDto>> partyMemberLsit(@PathVariable("pno") Long pno){
        return ResponseEntity.ok(partyService.partyMemberList(pno));
    }
//    파티의 찜 모음 리스트
    @PostMapping("/memDibs")
    public ResponseEntity<List<TravelDto>> pMemberDibsList(@RequestBody PmemberDibsReqDto dtos){
        return ResponseEntity.ok(partyService.membersDibsList(dtos.getMemberResDtos()));
    }
    // pname update
    @PostMapping("pnameup/{pno}/{pname}")
    public ResponseEntity<Boolean> pnameUpdateOne(@PathVariable("pno") Long pno, @PathVariable("pname") String  pname){
        return ResponseEntity.ok(partyService.pnameUpdate(pno,pname));
    }
    @PostMapping("/cosup")
    public ResponseEntity<Boolean> cosUpDate(@RequestBody CalendarDto calendarDto){
        log.info(calendarDto.getCplace());
        return ResponseEntity.ok(partyService.cosUpdate(calendarDto));
    }
    @PostMapping("/onesave")
    public ResponseEntity<Boolean> cosSaveOne(@RequestBody CalendarDto calendarDto){
        return ResponseEntity.ok(partyService.cosOneInsert(calendarDto));
    }
    @PostMapping("cosdelete/{cano}")
    public ResponseEntity<Boolean> cosDelete(@PathVariable("cano") Long cano){
        return ResponseEntity.ok(partyService.cosOneDelete(cano));
    }
}
