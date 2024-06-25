package com.example.yanolza.config;

import com.example.yanolza.dto.ChatMessageDto;
import com.example.yanolza.service.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;
    private final Map<WebSocketSession, String> sessionRoomIdMap = new ConcurrentHashMap<>(); // 세션이 속한 채팅방(roomID) 보기.
    @Override // 클라이언트가 보낸 텍스트 메시지를 처리하는 메서드
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception { // 클라이언트가 보낸 텍스트 메시지를 처리하는 메서드
        String payload = message.getPayload(); // 클라이언트가 전송한 메시지
        log.warn("{}", payload);
        // JSON 문자열을 ChatMessageDto 객체로 변환
        ChatMessageDto chatMessage = objectMapper.readValue(payload, ChatMessageDto.class);
        //payload를 ChatMessageDto 객체로 변환합니다. objectMapper의 readValue 메서드를 사용하여 JSON 문자열을 지정된 클래스의 객체로 변환
        String roomId = chatMessage.getRoodId(); // 채팅방 ID
        // 세션과 채팅방 ID를 매핑
        sessionRoomIdMap.put(session, chatMessage.getRoodId()); // 세션과 채팅방 ID를 매핑
        if (chatMessage.getType() == ChatMessageDto.MessageType.ENTER) { // 메시지 타입이 ENTER이면
            chatService.addSessionAndHandleEnter(roomId, session, chatMessage); // 채팅방에 입장한 세션 추가
        } else if (chatMessage.getType() == ChatMessageDto.MessageType.CLOSE) {
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage); // 채팅방 퇴장
        } else { // ChatMessageDto.MessageType.TALK
            chatService.sendMessageToAll(roomId, chatMessage); // 내가 보낸 메세지가 모두에게 보이게 해, 원활한 채팅하기
        }

    }
    @Override // 웹소켓 연결이 닫힐 때 호출되는 메서드
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션과 매핑된 채팅방 ID 가져오기
        log.warn("afterConnectionClosed : {}", session); // 로그에 경고 수준으로 웹소켓 세션이 종료되었음을 기록합니다.
        String roomId = sessionRoomIdMap.remove(session); // sessionRoomIdMap은 Map<WebSocketSession, String> 타입
        // Map의 remove(key) 함수는 제거하는 value 값을 반환함.
        if (roomId != null) {
            ChatMessageDto chatMessage = new ChatMessageDto();
            chatMessage.setType(ChatMessageDto.MessageType.CLOSE);
            chatService.removeSessionAndHandleExit(roomId, session, chatMessage);
        }
    }
}