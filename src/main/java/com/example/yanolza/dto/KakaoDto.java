package com.example.yanolza.dto;

import com.example.yanolza.constant.Authority;
import com.example.yanolza.entity.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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

        public Member toEntity(PasswordEncoder passwordEncoder) {
            return Member.builder()
                    .pwd(passwordEncoder.encode(id))
                    .mid(kakaoAccount.getEmail())
                    .nick(kakaoAccount.getProfile().getNick())
                    .image(kakaoAccount.getProfile().getProfileImageUrl())
                    .social("KAKAO")
                    .authority(Authority.ROLL_USER)
                    .build();
        }
    }

