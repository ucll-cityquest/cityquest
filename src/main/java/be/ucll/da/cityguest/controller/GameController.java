package be.ucll.da.cityguest.controller;

import be.ucll.da.cityguest.database.GameRepository;
import be.ucll.da.cityguest.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping("/games")
    public List<Game> game() {
        return gameRepository.findAll();
    }
}
