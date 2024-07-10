package com.example.yanolza.repository;

import com.example.yanolza.entity.ChattingRoom;
import com.example.yanolza.entity.Party;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String > {
    Optional<ChattingRoom> findByChatPno (Party pno);
}
