package com.example.yanolza.controller;

import com.example.yanolza.dto.ChatMessageDto;
import com.example.yanolza.dto.ChatRoomReqDto;
import com.example.yanolza.dto.ChatRoomResDto;
import com.example.yanolza.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin( origins = "http://localhost:3000")
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    @PostMapping("/new")
    public ResponseEntity<String> createRoom(@RequestBody ChatRoomReqDto chatRoomReqDTO) {
        log.warn("chatRoomDto : {}", chatRoomReqDTO);
        ChatRoomResDto room = chatService.createRoom(chatRoomReqDTO.getName());
        return new ResponseEntity<>(room.getRoomId(), HttpStatus.OK);
    }
    @GetMapping("/room")
    public List<ChatRoomResDto> getRooms() {
        return chatService.findAllRoom();
    }
    // 방 정보 가져오기
    @GetMapping("/room/{roomId}")
    public ChatRoomResDto findRoomById(@PathVariable String roomId) {
        return chatService.findRoomById(roomId);
    }
    // 메세지 저장하기
    @PostMapping("/message/{name}")
    public ResponseEntity<Void> saveMessage(@PathVariable String  name) {
        chatService.createRoom(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // 세션 수 가져오기
//    @GetMapping("/room/{roomId}/sessioncount")
//    public int getSessionCount(@PathVariable String roomId) {
//        return chatService.getSessionCount(roomId);
//    }
    // 이전 메세지 가져오기
//    @GetMapping("/message/{roomId}")
//    public List<ChatMessageDto> getRecentMessages(@PathVariable String roomId) {
//        return chatService.getRecentMessages(roomId);
//    }
}