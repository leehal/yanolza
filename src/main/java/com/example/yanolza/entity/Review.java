package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Review {
    @Id
    @Column(name = "rno")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rno;
    @Column(nullable = false)
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tno")
    private Travel travel;
    @Column(nullable = false)
    private String rcontent;
    @Column(nullable = false)
    private LocalDateTime rdate;
    @Column(nullable = false)
    private String rate;
    @ManyToOne
    @JoinColumn(name = "nick")
    private Member rnick;

    @PreRemove
    private void preRemoveReview() {
        this.rnick = null;
    }
}
