package com.woowacourse.kkogkkog.reservation.exeception;

import com.woowacourse.kkogkkog.common.exception.BadRequestException;
import com.woowacourse.kkogkkog.common.exception.ErrorType;

public class ReservationNotFoundException extends BadRequestException {

    public ReservationNotFoundException(final ErrorType errorType) {
        super(errorType);
    }
}
