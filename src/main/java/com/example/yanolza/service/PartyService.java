package com.example.yanolza.service;

import com.example.yanolza.dto.CalendarDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.dto.PartyNameListDto;
import com.example.yanolza.dto.PartyRequestDto;
import com.example.yanolza.entity.Calendar;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Party;
import com.example.yanolza.entity.PartyPeople;
import com.example.yanolza.repository.CalendarRepository;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.PartyPeopleReRepository;
import com.example.yanolza.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;
    private final PartyPeopleReRepository partyPeopleReRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final CalendarRepository calendarRepository;

    //    party save
    public boolean partyInsert(PartyRequestDto reqDto) {
        boolean isTrue = false;
        List<String> nickName = reqDto.getNick();
        String pname1 = reqDto.getPname();

        Party party = Party.builder()
                .pname(pname1)
                .pdate(LocalDateTime.now())
                .build();

        partyRepository.save(party);

        Optional<Party> partyPname = partyRepository.findByPname(pname1);
        if (partyPname.isPresent()) {
            for (String s : nickName) {
                Optional<Member> member = memberRepository.findByNick(s);
                if (member.isPresent()) {
                    partyPeopleReRepository.save(
                            PartyPeople.builder()
                                    .partyPeopleNick(member.get())
                                    .partyPeoplePno(partyPname.get())
                                    .build()
                    );
                    isTrue = true;
                }
            }
        }
        return isTrue;
    }

    //    내 모임 파티명, pno 리스트 리턴
    public List<PartyNameListDto> partyView() {
        Member member = memberService.memberIdFindMember();
        List<PartyNameListDto> list = new ArrayList<>();
//         유저가 참가한 모임 리스트 나오기
        List<PartyPeople> pnoList = partyPeopleReRepository.findByPartyPeopleNick(member);
        for (PartyPeople pno : pnoList) {
//            모임의 pno에 따른 pname을 list에 추가
            Optional<Party> pnameParty = partyRepository.findById(pno.getPartyPeoplePno().getPno());
            if (pnameParty.isPresent()) {
                PartyNameListDto dto = new PartyNameListDto();
                dto.setPname(pnameParty.get().getPname());
                dto.setPno(pnameParty.get().getPno());
                list.add(dto);
            }
        }
        return list;
    }

    //    Pno에 따른 멤버 리스트 조회
    public List<String> partyMemberList(Long pno) {
        List<String> list = new ArrayList<>();
        Optional<Party> party = partyRepository.findById(pno);
        if (party.isPresent()) {
            list.add(party.get().getPname());
        }
        return list;
    }

    //    String으로 날짜와 Party 타입으로 pno 받아서 하루 일정 모두 보여주기
    public List<CalendarDto> partyCalendarOneDay(Long pno, String date) {
        List<CalendarDto> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();

        Optional<Party> partyPno = partyRepository.findById(pno);
        if (partyPno.isPresent()) {
            List<Calendar> calendar = calendarRepository.findByCaDateAndCalenderPno(dateTime, partyPno.get());
            if (!calendar.isEmpty()) {
                for (Calendar c : calendar) {
                    CalendarDto dto = CalendarDto.of(c);
                    list.add(dto);
                }
            }
        }
        return list;
    }

    //    calendar insert
    public boolean calendarInsert(CalendarDto calendarDto) {
        boolean isTrue = false;
        Optional<Party> party = partyRepository.findById(calendarDto.getCalenderPno());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime dateTime = LocalDate.parse(calendarDto.getCaDate(), formatter).atStartOfDay();
        Optional<Member> member = memberRepository.findByNick(calendarDto.getCalenderNick());

        if (party.isPresent()) {

            if (member.isPresent()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime dateTime = LocalDate.parse(calendarDto.getCaDate(), formatter).atStartOfDay();

                Calendar calendar = calendarRepository.save(
                        Calendar.builder()
                                .calenderPno(party.get())
                                .caContent(calendarDto.getCaContent())
                                .calenderNick(member.get())
                                .caDate(dateTime)
                                .build()
                );
                calendarRepository.save(calendar);
                isTrue = true;
            }
        }
        return isTrue;
    }

    // 모든 회원 전체 조회 // 나중에 준영님 주고 지운후 거기서 가져다 쓰기...
    public List<MemberResDto> selectAllUsers() {

        List<Member> members = memberRepository.findAll();
        List<MemberResDto> list = new ArrayList<>();
        for (Member member : members) {
            MemberResDto mem = MemberResDto.of(member);
            list.add(mem);
        }
        return list;
    }
}
