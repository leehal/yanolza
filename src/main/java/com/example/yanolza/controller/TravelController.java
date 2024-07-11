package com.example.yanolza.controller;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin( origins = "http://localhost:3000")
@RequestMapping("/travel")
public class TravelController {
    public final TravelService travelService;

    @PostMapping("/travellist")
    public ResponseEntity<Map<String, Object>> travelList (@RequestBody Map<String, String> data){
        int page = Integer.parseInt(data.get("page"));
        String category = data.get("category");
        String city = data.get("city");
        String name = data.get("name");
        Map<String, Object> result = travelService.selectAllTravels(page,category,city,name);
        return ResponseEntity.ok(result);
    }
}
