package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(@Autowired GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping("/games")
    public List<Game> game() {
        return gameRepository
                .findAll()
                .stream()
                .peek(game ->
                        // We want no questions in the json response
                        game.getQuestions().clear()
                )
                .collect(toList());
    }
}
