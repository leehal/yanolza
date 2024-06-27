package com.example.yanolza.dto;

import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private String title;
    private String taddr;
    private String tcategory;
    private String rcategory;
    private String rcontent;
    private LocalDateTime rdate;
    private int rate;
    private String rnick;

    public static ReviewDto of (Review review)
    {
        return ReviewDto.builder()
                .title(review.getTitle())
                .taddr(review.getTravel().getTaddr())
                .tcategory(review.getTravel().getTcategory())
                .rcontent(review.getRcontent())
                .rdate(review.getRdate())
                .rate(review.getRate())
                .rnick(review.getRnick().getNick())
                .build();

    }

}
