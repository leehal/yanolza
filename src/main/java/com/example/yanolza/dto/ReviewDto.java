package com.example.yanolza.dto;

import com.example.yanolza.constant.Authority;
import com.example.yanolza.entity.Member;
import com.example.yanolza.entity.Review;
import com.example.yanolza.entity.Travel;
import com.example.yanolza.repository.TravelRepository;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ReviewDto {
    private String title;
    private int tno;
    private String tcategory;
    private String rcategory;
    private String rcontent;
    private LocalDateTime rdate;
    private int rate;
    private String rnick;
    private boolean identify;

    public static ReviewDto of (Review review, Boolean identify) {
        return ReviewDto.builder()
                .title(review.getTitle())
                .tno(review.getTravel().getTno().intValue())
                .tcategory(review.getTravel().getTcategory())
                .rcontent(review.getRcontent())
                .rdate(review.getRdate())
                .rate(review.getRate())
                .rnick(review.getRnick().getNick())
                .identify(identify)
                .build();
    }
}
