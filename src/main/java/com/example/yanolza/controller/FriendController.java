package com.example.yanolza.controller;
import com.example.yanolza.dto.FriendDto;
import com.example.yanolza.entity.Friend;
import com.example.yanolza.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {
    public final FriendService friendService;
    @PostMapping("/application")
    public ResponseEntity<Boolean> friendApplication(@RequestBody FriendDto friendDto) {
        return ResponseEntity.ok(friendService.friendApplication(friendDto));
    }
    @PostMapping("/friendlist")
    public ResponseEntity<List<FriendDto>> friendList(){
        return ResponseEntity.ok(friendService.selectAllFriends());
    }
}
