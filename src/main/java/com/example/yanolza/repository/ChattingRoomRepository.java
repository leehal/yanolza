package com.example.yanolza.repository;

import com.example.yanolza.entity.ChattingRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, String > {
}
