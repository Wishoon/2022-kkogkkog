package com.woowacourse.kkogkkog.reservation.presentation;

import com.woowacourse.kkogkkog.auth.presentation.AuthMember;
import com.woowacourse.kkogkkog.reservation.application.ReservationQueryService;
import com.woowacourse.kkogkkog.reservation.application.ReservationService;
import com.woowacourse.kkogkkog.reservation.application.dto.request.ReservationCreateRequest;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationResponse;
import com.woowacourse.kkogkkog.reservation.application.dto.response.ReservationsResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reservations")
@RestController
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationQueryService reservationQueryService;

    @PostMapping
    public ResponseEntity<Void> create(@AuthMember Long memberId,
                                       @RequestBody ReservationCreateRequest request) {
        Long reservationId = reservationService.save(memberId, request);

        return ResponseEntity.created(URI.create("/api/reservations/" + reservationId)).build();
    }

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

    @GetMapping("/approved")
    public ResponseEntity<ReservationsResponse> selectAllByMemberAfterNowDateTime(@AuthMember Long memberId) {
        ReservationsResponse response = reservationQueryService.findAllByMemberAndAfterDateTime(memberId);

        return ResponseEntity.ok(response);
    }
}
