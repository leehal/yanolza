package com.example.yanolza.repository;

import com.example.yanolza.entity.Calendar;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Party;
import com.example.yanolza.entity.PartyPeople;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
@Slf4j
@TestPropertySource(locations = "classpath:application-test.properties")
class PartyPeopleReRepositoryTest {
    @Autowired
    PartyPeopleReRepository partyPeopleReRepository;
    @Autowired
    PartyRepository partyRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CalendarRepository calendarRepository;

    @PersistenceContext
    EntityManager em;
    private Party party;

    public Member createMember() {

        Member member = Member.builder()
                .nick("pp")
                .mid("pkmm")
                .email("pkmm@naver")
                .pwd("1234")
                .build();

        memberRepository.save(member);

        return member;

    }

    public Party createParty() {

        Party party = Party.builder()
                .pdate(LocalDateTime.now())
                .pname("강릉여행")
                .build();
        partyRepository.save(party);

        return party;
    }


    @Test
    @DisplayName("모임 생성 Test")
    public void createTestParty() {
        Member member = createMember();
        log.info(member.getNick());

        Party party = createParty();
        log.info(party.getPname());
        Optional<Party> partypno = partyRepository.findById(party.getPno());
        if (partypno.isPresent()) {

            PartyPeople partyPeople = PartyPeople.builder()
                    .partyPeaplenick(member)
                    .partyPeaplePno(partypno.get())
                    .build();

            partyPeopleReRepository.save(partyPeople);
            log.info("결과는 : " + partyPeople.getPartyPeaplenick());
        }

    }

    @Test
    @DisplayName("닉네임으로 해당 사람 정보 가져오기")
    public void nickMember() {
        Member member = createMember();
        Optional<Member> member1 = memberRepository.findByNick(member.getNick());
        if (member1.isPresent()) {
            log.info("성공 : " + member1.get().getEmail());
        } else {
            log.info("실패");
        }
    }

    @Test
    @DisplayName("모임이름으로 모임 정보 가져오기")
    public void partyName() {
        Party party = createParty();
        Optional<Party> party1 = partyRepository.findByPname(party.getPname());
        if (party1.isPresent()) {
            log.info("성공 : " + party1.get().getPno());
        } else {
            log.info("실패");
        }
    }

    @Test
    @DisplayName("캘린더 생성")
    public void createCalendar() {
        createTestParty();
        Optional<Party> party = partyRepository.findByPname("강릉여행");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dateTime = LocalDate.parse("2024-06-20", formatter).atStartOfDay();

        Optional<Member> member = memberRepository.findByNick("pp");
        if (party.isPresent()) {

            if (member.isPresent()) {
                Calendar calendar = calendarRepository.save(
                        Calendar.builder()
                                .calenderPno(party.get())
                                .caContent("기차시간 7시")
                                .calenderNick(member.get())
                                .color("black")
                                .caDate(dateTime)
                                .build()
                );
                calendarRepository.save(calendar);
                log.info("시간 : " + String.valueOf(LocalDate.now()));
            }
        }
    }

    @Test
    @DisplayName("캘린더 계획 조회")
    public void selectCalender() {
        createCalendar();
        Optional<Party> party = partyRepository.findByPname("강릉여행");
        if (party.isPresent()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime dateTime = LocalDate.parse("2024-06-20", formatter).atStartOfDay();
            List<Calendar> calendar = calendarRepository.findByCaDateAndCalenderPno(dateTime, party.get());
            if (!calendar.isEmpty()) {
                for (Calendar c : calendar) {
                    log.info("캘린더 : " + c.getColor());
                }
            } else {
                log.info("실패");
            }
            ;
        }
    }


}