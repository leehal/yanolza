package com.example.yanolza.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Table(name = "Chatting_Room")

public class ChattingRoom {
    @Id // UUID로 String 타입 RoomID 반환
    @Column(name="room_id")
    private String roomId;
    @Column(name="room_name")
    private String roomName;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @Id
//    @Column(name = "room_id", length = 50)
//    private String roomId;
//    @Column(name = "room_name")
//    private String roomName;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<Chat> chats = new ArrayList<>();
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    private Member owner;
}
