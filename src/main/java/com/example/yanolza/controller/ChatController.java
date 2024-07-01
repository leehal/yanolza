package com.example.yanolza.controller;

import com.example.yanolza.dto.ChatMessageDto;
import com.example.yanolza.dto.ChatRoomReqDto;
import com.example.yanolza.dto.ChatRoomResDto;
import com.example.yanolza.entity.Chatting;
import com.example.yanolza.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin( origins = "http://localhost:3000")
@RequestMapping("/chat")
// 사용자가 WebSocket 연결을 설정하기 전에 필요한 데이터를 가져오거나, 특정 작업을 수행하기 위해 사용될 수 있습니다.
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomReqDTO) {
        log.warn("chatRoomDto : {}", chatRoomReqDTO);
        ChatRoomResDto room = chatService.createRoom(chatRoomReqDTO.getName());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ChatRoomResDto>> getRooms() {
        return ResponseEntity.ok(chatService.findAllRoom());
    }
    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChatRoomResDto> findRoomById(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.findRoomById(roomId));
    }
    // 메세지 저장하기
    @PostMapping("/message")
    public ResponseEntity<Void> saveMessage(@RequestBody ChatMessageDto chatMessageDTO) {
        chatService.saveMessage(chatMessageDTO.getRoodId(), chatMessageDTO.getSender(), chatMessageDTO.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }
//     세션 수 가져오기
    @GetMapping("/room/{roomId}/sessioncount")
    public ResponseEntity<Integer> getSessionCount(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.getSessionCount(roomId));
    }
//     이전 메세지 가져오기
    @GetMapping("/message/{roomId}")
    public ResponseEntity<List<ChatMessageDto>> getRecentMessages(@PathVariable String roomId) {
        List<ChatMessageDto> list = new ArrayList<>();
        List<Chatting> chattingList= chatService.getRecentMessages(roomId);
        for (Chatting chat : chattingList) {
            ChatMessageDto dto = ChatMessageDto.builder()
                    .message(chat.getMessage())
                    .roodId(chat.getChatRoom().getRoomId())
                    .sender(chat.getSender().getNick())
                    .build();
            list.add(dto);
        }
        return ResponseEntity.ok(list);
    }
}