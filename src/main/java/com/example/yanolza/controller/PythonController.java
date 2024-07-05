package com.example.yanolza.controller;

import com.example.yanolza.dto.TravelDto;
import com.example.yanolza.service.TravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin( origins = "http://localhost:5000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PythonController {
    private final TravelService travelService;

    @PostMapping("/travel")
    public ResponseEntity<Boolean> travelAoi(@RequestBody List<TravelDto> data) {
        boolean isTrue = false;
        for (TravelDto datum : data)
            isTrue = travelService.travelInsert(datum);
        return ResponseEntity.ok(isTrue);
    }
}
