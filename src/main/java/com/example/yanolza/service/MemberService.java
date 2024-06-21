package com.example.yanolza.service;

import com.example.yanolza.entity.Member;
import com.example.yanolza.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.example.yanolza.security.SecurityUtil.getCurrentMemberId;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member memberIdFindMember(){
        Long id = getCurrentMemberId();
        Member mem = new Member();
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
            mem = member.get();
        }
        return mem;
    }
}
