package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Game;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
public class GameController {
    private final GameRepository gameRepository;

    public GameController(GameRepository gameRepository) {
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

    @PostMapping("/games")
    public void addGame(@Valid @RequestBody Game game) {
        gameRepository.save(game);
    }

    @GetMapping("/games/{id}")
    public Game retrieveGame(@PathVariable UUID id) {
        Optional<Game> game = gameRepository.findById(id);

        if (!game.isPresent())
            throw new EntityNotFoundException("Game with id: " + id);

        return game.get();
    }
}
