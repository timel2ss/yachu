package game.yachu.repository;

import game.yachu.exception.PlayerNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GameStateRepositoryTest {

    GameStateRepository repository = new GameStateRepository();

    @Test
    void 플레이어를_찾을_수_없으면_예외를_발생시킨다() {
        Long id = repository.newGame();

        assertThatThrownBy(() -> repository.get(id + 1))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage(PlayerNotFoundException.message);
    }

}