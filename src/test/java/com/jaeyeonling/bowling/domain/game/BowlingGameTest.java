package com.jaeyeonling.bowling.domain.game;

import com.jaeyeonling.bowling.domain.frame.FinishedBowlingGameException;
import com.jaeyeonling.bowling.domain.frame.KnockdownPins;
import com.jaeyeonling.bowling.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.jaeyeonling.bowling.domain.frame.KnockdownPins.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BowlingGameTest {

    private User user;
    private BowlingGame bowlingGame;

    @BeforeEach
    void setUp() {
        user = User.of("TST");
        bowlingGame = BowlingGame.with(user);
    }

    @DisplayName("전부 거터 시 검증한다.")
    @Test
    void gutter() {
        // given
        bowl(GUTTER, 20);

        // when
        final String expected = user.visualize() +
                "  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |  -|-  |";

        // then
        assertThat(bowlingGame.visualize()).isEqualTo(expected);
    }

    @DisplayName("전부 퍼펙트 시 검증한다.")
    @Test
    void perfect() {
        // given
        bowl(MAX, 12);

        // when
        final String expected = user.visualize() +
                "   X   |   X   |   X   |   X   |   X   |   X   |   X   |   X   |   X   | X|X|X |";

        // then
        assertThat(bowlingGame.visualize()).isEqualTo(expected);
    }

    @DisplayName("전부 스페어 시 검증한다.")
    @Test
    void spare() {
        // given
        bowl(valueOf(5), 20);
        bowl(MAX);

        // when
        final String expected = user.visualize() +
                "  5|/  |  5|/  |  5|/  |  5|/  |  5|/  |  5|/  |  5|/  |  5|/  |  5|/  | 5|/|X |";

        // then
        assertThat(bowlingGame.visualize()).isEqualTo(expected);
    }

    @DisplayName("복잡한 게임을 검증한다.")
    @Test
    void complex() {
        // given
        bowlingGame.bowl(GUTTER); bowlingGame.bowl(GUTTER);
        bowlingGame.bowl(valueOf(1)); bowlingGame.bowl(valueOf(1));
        bowlingGame.bowl(valueOf(5)); bowlingGame.bowl(GUTTER);
        bowlingGame.bowl(MAX);
        bowlingGame.bowl(valueOf(5)); bowlingGame.bowl(valueOf(5));
        bowlingGame.bowl(GUTTER); bowlingGame.bowl(MAX);
        bowlingGame.bowl(MAX);
        bowlingGame.bowl(MAX);
        bowlingGame.bowl(GUTTER); bowlingGame.bowl(GUTTER);
        bowlingGame.bowl(MAX); bowlingGame.bowl(MAX); bowlingGame.bowl(MAX);

        // when
        final String expected = user.visualize() +
                "  -|-  |  1|1  |  5|-  |   X   |  5|/  |  -|/  |   X   |   X   |  -|-  | X|X|X |";

        // then
        assertThat(bowlingGame.visualize()).isEqualTo(expected);
    }

    @DisplayName("완료된 게임을 시작하면 예외처리한다.")
    @Test
    void throwFinishedBowlingGameException() {
        // given
        bowl(MAX, 12);

        // when / then
        assertThatExceptionOfType(FinishedBowlingGameException.class)
                .isThrownBy(() -> bowlingGame.bowl(MAX));
    }

    private void bowl(final KnockdownPins knockdownPins,
                      final int count) {
        for (int i = 0; i < count; i++) {
            bowl(knockdownPins);
        }
    }

    private void bowl(final KnockdownPins knockdownPins) {
        bowlingGame.bowl(knockdownPins);
    }
}
