package game.yachu.domain;

import game.yachu.exception.DiceValueOutOfRangeException;
import lombok.Getter;

@Getter
public class Dice {
    private int value;

    public Dice(int value) {
        if (isOutOfRange(value)) {
            throw new DiceValueOutOfRangeException();
        }
        this.value = value;
    }

    public void roll() {
        this.value = RandomUtil.randomBetweenOneAndSix();
    }

    public void resetValue() {
        value = 0;
    }

    private boolean isOutOfRange(int value) {
        return value < 0 || value > 6;
    }
}
