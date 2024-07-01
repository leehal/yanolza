package com.example.yanolza.controller;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin( origins = "http://localhost:3000")
@RequestMapping("/travel")
public class TravelController {
    public final TravelService travelService;

    @GetMapping("/travellist")
    public ResponseEntity<List<TravelDto>> travelList (){
        List<TravelDto> list = travelService.selectAllTravels();
        return ResponseEntity.ok(list);
    }
}
