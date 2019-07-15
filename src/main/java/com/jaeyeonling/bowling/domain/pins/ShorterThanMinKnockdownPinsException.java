package com.jaeyeonling.bowling.domain.pins;

public class ShorterThanMinKnockdownPinsException extends IllegalArgumentException {

    private static final String ERROR_MESSAGE = "쓰러트린 핀의 갯수는 %d 보다 작을 수 없습니다. (입력 값: %d)";

    ShorterThanMinKnockdownPinsException(final int input) {
        super(String.format(ERROR_MESSAGE, KnockdownPins.MIN_VALUE, input));
    }
}
