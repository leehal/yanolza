package com.example.yanolza.dto;

import com.example.yanolza.entity.Social;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KakaoDto {
        private String id;

        @JsonProperty("kakao_account")
        private KakaoAccount kakaoAccount;

        @Data
        public static class KakaoAccount {
            private Profile profile;
            private String email;

            @Data
            public static class Profile {
                @JsonProperty("nickname")
                private String nick;
                @JsonProperty("profile_image_url")
                private String profileImageUrl;
            }
        }

        public KakaoDto() {}

        public Social toEntity() {
            return Social.builder()
                    .pwd(id)
                    .mid(kakaoAccount.getEmail())
                    .nick(kakaoAccount.getProfile().getNick())
                    .profile(kakaoAccount.getProfile().getProfileImageUrl())
                    .build();
        }
    public UsernamePasswordAuthenticationToken toAuthenticationToken(List<SimpleGrantedAuthority> authorities) {
        return new UsernamePasswordAuthenticationToken(kakaoAccount.getEmail(), "", authorities);
    }
    }

