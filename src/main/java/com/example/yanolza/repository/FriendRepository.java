package com.example.yanolza.repository;

import com.example.yanolza.entity.Friend;
import com.example.yanolza.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend,Long> {
    List<Friend> findByFrom(Member member);
    List<Friend> findByTo(Member member);
    List<Friend> findByToAndAccept(Member member,String accept);
    List<Friend> findByFromAndAccept(Member member,String accept);
}
