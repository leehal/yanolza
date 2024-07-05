package com.example.yanolza.service;

import com.example.yanolza.constant.TF;
import com.example.yanolza.dto.FriendDto;
import com.example.yanolza.dto.MemberResDto;
import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.repository.FriendRepository;
import com.example.yanolza.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //친구신청
    public Boolean friendApplication(String nick) {
        Member from = memberService.memberIdFindMember();
        Member to = memberRepository.findByNick(nick)
                .orElseThrow(() -> new NoSuchElementException("맴버를 찾을수없습니다: " + nick));
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
        List<Friend> from = friendRepository.findByFromAndAccept(member, TF.TRUE);
        List<Friend> to = friendRepository.findByToAndAccept(member, TF.TRUE);

        for (Friend friend : from) {
            FriendDto fnd = FriendDto.of(friend,TF.TRUE);
            list.add(fnd);
        }
        for (Friend friend : to) {
            FriendDto fnd = FriendDto.of(friend,TF.FALSE);
            list.add(fnd);
        }
        return list;
    }

    //내가 보낸 친구신청 목록 (중복신청 막기위해)
    public List<FriendDto> allicationFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> from = friendRepository.findByFromAndAccept(member, TF.FALSE);

        for (Friend friend : from) {
            FriendDto fnd = FriendDto.of(friend,TF.TRUE);
            list.add(fnd);
        }
        return list;
    }

    //내가 받은 친구신청 목록
    public List<FriendDto> acceptFriends() {
        Member member = memberService.memberIdFindMember();
        List<FriendDto> list = new ArrayList<>();
        List<Friend> to = friendRepository.findByToAndAccept(member, TF.FALSE);
        if (!to.isEmpty()) {
            for (Friend friend : to) {
                FriendDto fnd = FriendDto.of(friend,TF.FALSE);
                list.add(fnd);
            }
        }
        return list;
    }
    //친구신청 수락
    public Boolean friendsAgree(String nick) {
        Member me = memberService.memberIdFindMember();
        Member you = memberRepository.findByNick(nick).orElseThrow(() -> new RuntimeException("해당닉네임 존재안함"));
        Optional<Friend> friend = friendRepository.findByFromAndTo(you,me);
        if (!friend.isEmpty()) {
           friend.get().setAccept(TF.TRUE);
           return true;
        }
        else return false;
    }

    // 친구 삭제
    public boolean friendDelete(Long id) {
        try {
            Friend friend = friendRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("해당 친구가 존재하지 않습니다.")
            );
            friendRepository.delete(friend);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
