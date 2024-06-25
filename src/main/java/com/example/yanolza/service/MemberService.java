package com.example.yanolza.service;

import com.example.yanolza.entity.Member;
import com.example.yanolza.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.yanolza.security.SecurityUtil.getCurrentMemberId;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member memberIdFindMember(){
        String id = getCurrentMemberId();
        log.info("userId:{}",String.valueOf(id));
        Member mem = new Member();
        Optional<Member> member = memberRepository.findByMid(id);
        if(member.isPresent()){
            mem = member.get();
        }
        return mem;
    }
}
