package com.woowacourse.kkogkkog.testClass;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public ResponseEntity<Long> test() {
        return ResponseEntity.ok(1L);
    }
}
