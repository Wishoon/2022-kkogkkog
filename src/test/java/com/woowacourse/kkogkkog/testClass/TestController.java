package com.woowacourse.kkogkkog.testClass;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RequiredArgsConstructor
@RestController
public class TestController {

    private final TestService testService;

    @GetMapping("/api/test")
    public ResponseEntity<Long> createEntity() {
        Long saveId = testService.save();
        return ResponseEntity.ok(saveId);
    }

    @GetMapping("/api/test1")
    public ResponseEntity<Long> createEntity1() throws Exception {
        throw new NullPointerException("예외 발생");
    }
}
