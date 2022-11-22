package com.woowacourse.kkogkkog.testClass;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @GetMapping("/api/test")
    public ResponseEntity<Long> createEntity() {
        Long saveId = testService.save();
        return ResponseEntity.ok(saveId);
    }
}
