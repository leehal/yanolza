package com.example.yanolza.dto;


import com.example.yanolza.entity.Image;
import com.example.yanolza.entity.Review;
import lombok.*;

import java.security.PrivateKey;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ImageDto {
    private Long ino;
    private Long rno;
    private String image;

    public static ImageDto of (Image image) {
        return ImageDto.builder()
                .ino(image.getIno())
                .rno(image.getRno())
                .image(image.getIimage())
                .build();
    }

    public Image toEntity (){
        return Image.builder()
                .iimage(this.image)
                .rno(this.rno)
                .build();
    }
}
