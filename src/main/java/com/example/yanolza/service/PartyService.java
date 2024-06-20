package com.example.yanolza.service;

import com.example.yanolza.dto.PartyRequestDto;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Party;
import com.example.yanolza.entity.PartyPeople;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.PartyPeopleReRepository;
import com.example.yanolza.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    public boolean partyInsert(PartyRequestDto reqDto){
//        boolean isTrue = false;
        List<String > nickName = reqDto.getNick();
        String pname = reqDto.getPName();

        Party party = Party.builder()
                .pname(pname)
                .pdate(LocalDateTime.now())
                .build();

        partyRepository.save(party);

        Optional<Party> partyPname = partyRepository.findByPname(pname);
        if (partyPname.isPresent()){
            for (String s : nickName) {
                Optional<Member> member = memberRepository.findByNick(s);
                if (member.isPresent()) {
                    partyPeopleReRepository.save(
                            PartyPeople.builder()
                                    .partyPeaplenick(member.get())
                                    .partyPeaplePno(partyPname.get())
                                    .build()
                    );
                }
            }
            return true;
        }
        return false;
    }
}
