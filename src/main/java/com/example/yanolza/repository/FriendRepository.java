package com.example.yanolza.repository;

import com.example.yanolza.constant.TF;
import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend,Long> {
    List<Friend> findByFrom(Member member);
    List<Friend> findByTo(Member member);
    Optional<Friend> findByFromAndTo(Member nick,Member member);
    List<Friend> findByToAndAccept(Member member, TF accept);
    List<Friend> findByFromAndAccept(Member member,TF accept);
}
