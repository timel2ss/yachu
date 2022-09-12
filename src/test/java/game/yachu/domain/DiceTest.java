package game.yachu.domain;

import game.yachu.exception.DiceValueOutOfRangeException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiceTest {

    @Test
    void 주사위는_1부터_6까지의_값만_가진다() {
        assertThatThrownBy(() -> new Dice(7))
                .isInstanceOf(DiceValueOutOfRangeException.class);

        assertThatThrownBy(() -> new Dice(-1))
                .isInstanceOf(DiceValueOutOfRangeException.class);
    }

    @Test
    void 주사위를_굴린다() {
        Dice dice = new Dice(1);
        for (int i = 0; i < 10; i++) {
            dice.roll();
            System.out.println(dice.getValue());
        }
    }
}