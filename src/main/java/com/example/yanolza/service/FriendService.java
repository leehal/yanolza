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

    //친구신청
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
    //내친구 목록
    public List<FriendDto> selectAllFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> from = friendRepository.findByFromAndAccept(member, TF.TRUE.toString());
        List<Friend> to = friendRepository.findByFromAndAccept(member, TF.TRUE.toString());

        for (Friend friend : from) {
            FriendDto fnd = FriendDto.of(friend);
            list.add(fnd);
        }
        for (Friend friend : to) {
            FriendDto fnd = FriendDto.of(friend);
            list.add(fnd);
        }
        return list;
    }
    //내가 보낸 친구신청 목록 (중복신청 막기위해)
    public List<FriendDto> allicationFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> from = friendRepository.findByFromAndAccept(member, TF.FALSE.toString());

        for (Friend friend : from) {
            FriendDto fnd = FriendDto.of(friend);
            list.add(fnd);
        }
        return list;
    }
    //내가 받은 친구신청 목록
    public List<FriendDto> acceptFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> to = friendRepository.findByToAndAccept(member, TF.FALSE.toString());

        for (Friend friend : to) {
            FriendDto fnd = FriendDto.of(friend);
            list.add(fnd);
        }
        return list;
    }
}
