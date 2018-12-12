package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Game;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @GetMapping("/api/games")
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

    @PostMapping("/api/games")
    public Game addGame(@Valid @RequestBody Game game) {
        return gameRepository.save(game);
    }

    @GetMapping("/api/games/{uuid}")
    public Game getGame(@PathVariable UUID uuid) {
        return gameRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("No game with id " + uuid));
    }
}
