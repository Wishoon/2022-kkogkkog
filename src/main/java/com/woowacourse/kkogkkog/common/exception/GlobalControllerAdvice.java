package com.woowacourse.kkogkkog.common.exception;

import static com.woowacourse.kkogkkog.common.exception.ErrorType.COMMON_UNHANDLED;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    /**
     * 사용자의 잘못된 요청을 처리
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(final BadRequestException e) {
        log.warn("Bad Request Exception", e);
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    /**
     * 승인되지 않은 요청을 처리
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> unauthorizedExceptionHandler(final UnauthorizedException e) {
        log.warn("Unauthorized Exception", e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    /**
     * 권한이 없는 요청을 처리
     */
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> forbiddenExceptionHandler(final ForbiddenException e) {
        log.warn("Forbidden Exception", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(e.getCode(), e.getMessage()));
    }

    /**
     * @Valid 요청을 처리
     * TODO: 추후 적용
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> springValidationExceptionHandler(final MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("MethodArgumentNotValid Exception : {}", message);
        return ResponseEntity.badRequest().body(new ErrorResponse(ErrorType.COMMON_INVALID_HTTP.getCode(), message));
    }

    /**
     * 예외로 걸러지지 않거나, 예상치 못한 예외들을 처리
     * TODO: 슬랙으로 알림 전송
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unHandledExceptionHandler(final Exception e) {
        log.error("Not Expected Exception is occurred", e);
        return ResponseEntity.internalServerError().body(
            new ErrorResponse(COMMON_UNHANDLED.getCode(), COMMON_UNHANDLED.getMessage()));
    }
}
