package com.example.yanolza.service;

import com.example.yanolza.constant.TF;
import com.example.yanolza.dto.FriendDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import com.example.yanolza.repository.FriendRepository;
import com.example.yanolza.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Boolean friendApplication(FriendDto friendDto) {
        Member from = memberRepository.findByNick(friendDto.getFrom())
                .orElseThrow(() -> new NoSuchElementException("맴버를 찾을수없습니다: " + friendDto.getFrom()));
        Member to = memberRepository.findByNick(friendDto.getTo())
                .orElseThrow(() -> new NoSuchElementException("맴버를 찾을수없습니다: " + friendDto.getTo()));
        Friend friend = friendRepository.save(
                Friend.builder()
                        .from(from)
                        .to(to)
                        .accept(TF.FALSE)
                        .build());
        return friend != null;
    }
    public List<FriendDto> selectAllFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> from = friendRepository.findByFrom(member);

        for (Friend friend : from) {
            FriendDto fnd = FriendDto.of(friend);
            list.add(fnd);
        }
        return list;
    }

}
