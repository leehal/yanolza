package com.example.yanolza.service;

import com.example.yanolza.dto.*;
import com.example.yanolza.entity.*;
import com.example.yanolza.entity.Calendar;
import com.example.yanolza.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private final DibsRepository dibsRepository;
    private final TravelRepository travelRepository;
    private final ChattingRoomRepository chatRoomRepository;
    private final ChatService chatService;

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

//        String randomId = UUID.randomUUID().toString(); // roomId 생성 PK, String 타입 // 반환이 UUID 타입 객체라 toString()을 사용해 문자열로 만들어줌.
//        ChattingRoom room = ChattingRoom.builder()
//                .roomId(randomId)
//                .chatPno(party)
//                .createdAt(LocalDateTime.now())
//                .build();
//        chatRoomRepository.save(room);

        chatService.createRoom(party);

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
    public List<MemberResDto> partyMemberList(Long pno) {
        List<MemberResDto> list = new ArrayList<>();
        Optional<Party> party = partyRepository.findById(pno);
        if (party.isPresent()) {
            List<PartyPeople> partyPeople = partyPeopleReRepository.findByPartyPeoplePno(party.get());
            for (PartyPeople p : partyPeople) {
                Optional<Member> member = memberRepository.findById(p.getPartyPeopleNick().getUno());
                if (member.isPresent()) {
                    MemberResDto dto = MemberResDto.of(member.get());
                    list.add(dto);
                }
            }
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
    public boolean calendarInsert(CalendarSaveDto calendarDtos) {
        boolean isTrue = false;
        Optional<Party> party = partyRepository.findById(calendarDtos.getPno());
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDateTime dateTime = LocalDate.parse(calendarDto.getCaDate(), formatter).atStartOfDay();
        Optional<Member> member = memberRepository.findByNick(calendarDtos.getNick());

        if (party.isPresent()) {

            if (member.isPresent()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREAN);
                LocalDateTime dateTime = LocalDate.parse(calendarDtos.getDate(), formatter).atStartOfDay();

                for (CalendarDto dto : calendarDtos.getDtos()) {
                    Calendar calendar = calendarRepository.save(
                            Calendar.builder()
                                    .caddr(dto.getCaddr())
                                    .calenderPno(party.get())
                                    .caContent(dto.getCaContent())
                                    .calenderNick(member.get())
                                    .caDate(dateTime)
                                    .cplace(dto.getCplace())
                                    .build()
                    );
                    calendarRepository.save(calendar);
                    isTrue = true;
                }
            }
        }
        return isTrue;
    }

    // 모든 회원 전체 조회 // 나중에 준영님 주고 지운후 거기서 가져다 쓰기...
    public List<MemberResDto> selectAllUsers() {
        String mid = memberService.memberIdFindMember().getMid();
        List<Member> members = memberRepository.findAllExceptMine(mid);
        List<MemberResDto> list = new ArrayList<>();
        for (Member member : members) {
            MemberResDto mem = MemberResDto.of(member);
            list.add(mem);
        }
        return list;
    }

    //    모임에 따른 일정 전체 조회
    public PDateChatDto selectCalendarPnoAll(Long pno) {
        Optional<Party> party = partyRepository.findById(pno);
        PDateChatDto pdto = new PDateChatDto();
        List<String > list = new ArrayList<>();

        if (party.isPresent()) {
            List<Calendar> calendars = calendarRepository.findByCalenderPno(party.get());
            Optional<ChattingRoom> room = chatRoomRepository.findByChatPno(party.get());
            if (room.isPresent()){
                pdto.setRoomId(room.get().getRoomId());
            }

            for (Calendar c : calendars) {
                CalendarDto dto = CalendarDto.of(c);
                list.add(dto.getCaDate()); // CalendarDto에서 caDate만 추출해 추가
            }
        }

        // 중복 제거를 위해 Set으로 변환 후 다시 List로 변환
        Set<String> dateSet = new HashSet<>(list); // 중복 제거
        list = new ArrayList<>(dateSet); // Set을 List로 변환
        pdto.setDates(list);

        return pdto;
    }

    public List<CalendarDto> selectPnoCalendarOneDay(Long pno, String date) {
        List<CalendarDto> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREAN);
        LocalDateTime dateTime = LocalDate.parse(date, formatter).atStartOfDay();

        Optional<Party> party = partyRepository.findById(pno);
        if(party.isPresent()){
            List<Calendar> calendars = calendarRepository.findByCaDateAndCalenderPno(dateTime,party.get());
            for (Calendar calendar : calendars) {
                CalendarDto dto = CalendarDto.of(calendar);
                list.add(dto);
            }
        }
        return list;
    }

    public List<TravelDto> membersDibsList(List<MemberResDto> memList) {
        List<TravelDto> list = new ArrayList<>();
        for (MemberResDto dto : memList) {
            Optional<Member> member = memberRepository.findByNick(dto.getNick());
            if (member.isPresent()) {
                List<Dibs> dibs = dibsRepository.findByDnick(member.get());
                for (Dibs dib : dibs) {
                    Travel travel = travelRepository.findById(dib.getTno())
                            .orElseThrow(() -> new NoSuchElementException(dib.getTno() + "에대해 찾을수 없음"));
                    list.add(TravelDto.of(travel));
                }
            }
        }
        return list;
    }

    public boolean pnameUpdate(Long pno, String pname){
        boolean isTrue= false;
        Optional<Party> oriParty = partyRepository.findById(pno);
        if (oriParty.isPresent()){
            Party party = Party.builder()
                    .pno(pno)
                    .pname(pname)
                    .pdate(oriParty.get().getPdate())
                    .build();
            partyRepository.save(party);
            isTrue = true;
        }
        return isTrue;
    }

    public boolean cosUpdate(CalendarDto dto){
        boolean isTrue= false;
        Optional<Calendar> oriCalendar = calendarRepository.findById(dto.getCano());
        if (oriCalendar.isPresent()){
            Member member = memberService.memberIdFindMember();
            Calendar calendar = Calendar.builder()
                    .cano(dto.getCano())
                    .caddr(dto.getCaddr())
                    .caDate(oriCalendar.get().getCaDate())
                    .calenderPno(oriCalendar.get().getCalenderPno())
                    .caContent(dto.getCaContent())
                    .calenderNick(member)
                    .cplace(dto.getCplace())
                    .build();
            calendarRepository.save(calendar);
            isTrue = true;
        }
        return isTrue;
    }

    public boolean cosOneInsert(CalendarDto dto){
        boolean isTrue= false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분", Locale.KOREAN);
        LocalDateTime dateTime = LocalDate.parse(dto.getCaDate(), formatter).atStartOfDay();
        Optional<Party> party = partyRepository.findById(dto.getCalenderPno());
        if (party.isPresent()) {
            Member member = memberService.memberIdFindMember();
            Calendar calendar = Calendar.builder()
                    .caddr(dto.getCaddr())
                    .caDate(dateTime)
                    .calenderPno(party.get())
                    .caContent(dto.getCaContent())
                    .calenderNick(member)
                    .cplace(dto.getCplace())
                    .build();
            calendarRepository.save(calendar);
            isTrue = true;
        }
        return isTrue;
    }
    public boolean cosOneDelete(Long cano){
        boolean isTrue = false;
        calendarRepository.deleteById(cano);
        isTrue = true;
        return isTrue;
    }

    public boolean memberUpdate(PartyRequestDto reqDto){
        boolean isTrue= false;
        Long pno = reqDto.getPno();
        List<String> nickList = reqDto.getNick();
        Optional<Party> party = partyRepository.findById(pno);
        if (party.isPresent()) {
            for (String s : nickList) {
                Optional<Member> member = memberRepository.findByNick(s);
                if (member.isPresent()) {
                    partyPeopleReRepository.save(
                            PartyPeople.builder()
                                    .partyPeopleNick(member.get())
                                    .partyPeoplePno(party.get())
                                    .build()
                    );
                    isTrue = true;
                }
            }
        }
        return isTrue;
    }

    public boolean deletePartyPeople(Long pno){
        boolean isTrue= false;
        Member member = memberService.memberIdFindMember();
        Optional<Party> party = partyRepository.findById(pno);
        if(party.isPresent()){
            partyPeopleReRepository.deleteByPartyPeopleNickAndPartyPeoplePno(member,party.get());
            isTrue = true;
        }
        return isTrue;
    }
}
