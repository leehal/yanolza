package com.example.yanolza.service;

import com.example.yanolza.dto.CalendarDto;
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

    public boolean partyInsert(PartyRequestDto reqDto) {
        boolean isTrue = false;
        List<String> nickName = reqDto.getNick();
        String pname = reqDto.getPName();

        Party party = Party.builder()
                .pname(pname)
                .pdate(LocalDateTime.now())
                .build();

        partyRepository.save(party);

        Optional<Party> partyPname = partyRepository.findByPname(pname);
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

//    내 모임 파티명 리스트 리턴
    public List<String > partyView(String nick) {
        Member member = memberService.memberIdFindMember();
        List<String > list = new ArrayList<>();
//         유저가 참가한 모임 리스트 나오기
        List<PartyPeople> pnoList = partyPeopleReRepository.findByPartyPeopleNick(member);
        for (PartyPeople pno : pnoList) {
//            모임의 pno에 따른 pname을 list에 추가
            Optional<Party> pnameParty = partyRepository.findById(pno.getPartyPeoplePno().getPno());
            if (pnameParty.isPresent()){
                String pname = pnameParty.get().getPname();
                list.add(pname);
            }
        }
        return list;
    }

//    파티명에 따른 멤버 리스트 조회
    public List<String > partyMemberList(Long pno){
        List<String > list = new ArrayList<>();
        Optional<Party> party = partyRepository.findById(pno);
        if (party.isPresent()){
            list.add(party.get().getPname());
        }
        return list;
    }

//    String으로 날짜와 Party 타입으로 pno 받아서 하루 일정 모두 보여주기
    public List<CalendarDto> partyCalendarOneDay(Party pno, String date){
        List<CalendarDto> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();

        List<Calendar> calendar = calendarRepository.findByCaDateAndCalenderPno(dateTime, pno);
        if (!calendar.isEmpty()) {
            for (Calendar c : calendar) {
                CalendarDto dto = CalendarDto.of(c);
                list.add(dto);
            }
        }
        return list;
    }
}
