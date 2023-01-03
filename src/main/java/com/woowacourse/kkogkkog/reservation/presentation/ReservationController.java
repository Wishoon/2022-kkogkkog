package com.woowacourse.kkogkkog.reservation.presentation;

import com.woowacourse.kkogkkog.auth.presentation.AuthMember;
import com.woowacourse.kkogkkog.reservation.application.ReservationQueryService;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final ReservationQueryService reservationQueryService;

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationResponse> select(@PathVariable Long reservationId) {
        ReservationResponse response = reservationQueryService.find(reservationId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ReservationsResponse> selectAllByMember(@AuthMember Long memberId) {
        ReservationsResponse response = reservationQueryService.findAllByMember(memberId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/approve")
    public ResponseEntity<ReservationsResponse> selectAllByMemberAfterNowDateTime(@AuthMember Long memberId) {
        ReservationsResponse response = reservationQueryService.findAllByMemberAndAfterDateTime(memberId);

        return ResponseEntity.ok(response);
    }
}
