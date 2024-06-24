package com.example.yanolza.dto;

import com.example.yanolza.entity.Social;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

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
                    .mid(id)
                    .nick(kakaoAccount.getProfile().getNick())
                    .profile(kakaoAccount.getProfile().getProfileImageUrl())
                    .build();
        }
    }

