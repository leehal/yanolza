package com.example.yanolza.repository;

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

import java.time.LocalDateTime;
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

    @PersistenceContext
    EntityManager em;
    private Party party;

    public Member createMember(){

    Member member = Member.builder()
            .nickname("pp")
            .id("pkmm")
            .email("pkmm@naver")
            .pwd("1234")
            .build();
        memberRepository.save(member);

        return member;
        
    } public Party createParty(){

        Party party = Party.builder()
                .pdate(LocalDateTime.now())
                .pname("강릉여행")
                .build();
        partyRepository.save(party);

        return party;
    }


    @Test
    @DisplayName("모임 생성 Test")
    public void createTestParty(){
        Member member = createMember();
        log.info(member.getNickname());

        Party party = createParty();
        log.info(party.getPname());
        Optional<Party> partypno = partyRepository.findById(party.getPno());
        if(partypno.isPresent()){

        PartyPeople partyPeople = PartyPeople.builder()
                .partyPeaplenick(member)
                .partyPeaplePno(partypno.get())
                .build();

        partyPeopleReRepository.save(partyPeople);
        log.info("결과는 : "+partyPeople.getPartyPeaplenick());
        }

    }
    
    @Test
    @DisplayName("닉네임으로 해당 사람 정보 가져오기")
    public void nickMember(){
        Member member = createMember();
        Optional<Member> member1 = memberRepository.findByNickname(member.getNickname());
        if(member1.isPresent()){
            log.info("성공 : "+member1.get().getEmail());
        }else {
            log.info("실패");
        }
    }

    @Test
    @DisplayName("모임이름으로 모임 정보 가져오기")
    public void partyName(){
        Party party = createParty();
        Optional<Party> party1 = partyRepository.findByPname(party.getPname());
        if(party1.isPresent()){
            log.info("성공 : "+party1.get().getPno());
        }else {
            log.info("실패");
        }
    }

}