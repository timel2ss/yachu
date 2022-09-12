package game.yachu.controller;

import game.yachu.controller.request.GainRequest;
import game.yachu.controller.request.RollRequest;
import game.yachu.controller.response.GainResponse;
import game.yachu.controller.response.LoadResponse;
import game.yachu.controller.response.RollResponse;
import game.yachu.domain.Dice;
import game.yachu.domain.Player;
import game.yachu.domain.Rank;
import game.yachu.domain.Score;
import game.yachu.repository.GameStateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class GameController {

    private final GameStateRepository gameStateRepository;

    @Autowired
    public GameController(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @GetMapping("/game/{id}")
    public String game(@PathVariable Long id) {
        System.out.println("id: " + id);
        return "gamePage";
    }

    @ResponseBody
    @PostMapping("/api/new")
    public Long newGame(HttpSession session) {
        Long id = gameStateRepository.newGame();
        session.setAttribute("id", id);
        log.info("session created id = {}, maxInactiveInterval = {}", id, session.getMaxInactiveInterval());
        return id;
    }

    @ResponseBody
    @PostMapping("/api/{id}/load")
    public LoadResponse load(@PathVariable Long id) {
        Player player = gameStateRepository.get(id);
        List<Integer> diceValues = player.getDices().stream()
                .map(Dice::getValue)
                .collect(Collectors.toList());
        Score diceScore = getDiceScore(player, player.getDices());
        return new LoadResponse(player.getChance(), diceValues, player.getScore(), diceScore);
    }

    @ResponseBody
    @PostMapping("/api/{id}/roll")
    public RollResponse roll(@PathVariable Long id, @Valid @RequestBody RollRequest rollRequest) {
        Player player = gameStateRepository.get(id);
        List<Dice> dices = player.rollDices(rollRequest.getFixStates());
        Score calculated = getDiceScore(player, dices);
        return new RollResponse(dices, calculated, player.getChance());
    }

    private Score getDiceScore(Player player, List<Dice> dices) {
        Rank rank = new Rank(dices);
        Score calculated = rank.calculate();
        calculated.hasGained(player.getScore());
        return calculated;
    }

    @ResponseBody
    @PostMapping("/api/{id}/gain")
    public GainResponse gain(@PathVariable("id") Long id, @RequestBody GainRequest request) {
        Player player = gameStateRepository.get(id);
        player.setScore(request.getCategory(), request.getGained());
        if (player.isOver()) {
            log.info("Game is Over");
            gameStateRepository.deleteGame(id);
            return new GainResponse(player.getScore(), true);
        }
        player.resetState();
        return new GainResponse(player.getScore(), false);
    }
}
