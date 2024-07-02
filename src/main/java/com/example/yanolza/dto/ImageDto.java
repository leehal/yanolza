package com.example.yanolza.dto;


import com.example.yanolza.entity.Image;
import lombok.*;

import java.security.PrivateKey;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ImageDto {
    private Long ino;
    private int rno;
    private String image;

    public static ImageDto of (Image image) {
        return ImageDto.builder()
                .ino(image.getIno())
                .rno(image.getReview().getRno().intValue())
                .image(image.getIimage())
                .build();
    }
}
