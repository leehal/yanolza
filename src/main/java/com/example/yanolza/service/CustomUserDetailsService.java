package com.example.yanolza.service;

import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Social;
import com.example.yanolza.repository.MemberRepository;
import com.example.yanolza.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final SocialRepository socialRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (memberRepository.findByMid(username).isPresent()){
            return memberRepository.findByMid(username)
                    .map(this::createUserDetails)
                    .orElseThrow(() -> new UsernameNotFoundException(username + " 을 DB에서 찾을 수 없습니다"));}
        else {return socialRepository.findByMid(username)
                .map(this::createSocialUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " 을 DB에서 찾을 수 없습니다"));}
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(
                String.valueOf(member.getMid()),
                member.getPwd(),
                Collections.singleton(grantedAuthority)
        );
    }

    private UserDetails createSocialUserDetails(Social social) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLL_USER");

        return new User(
                String.valueOf(social.getMid()),
                "",
                Collections.singleton(grantedAuthority)
        );
    }
}