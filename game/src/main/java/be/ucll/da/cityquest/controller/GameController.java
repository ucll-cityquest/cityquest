package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Game;
import be.ucll.da.cityquest.model.GamePreferences;
import be.ucll.da.cityquest.service.RecommendationService;
import be.ucll.da.recommendation.model.RecommendedItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
public class GameController {
    private final GameRepository gameRepository;
    private final RecommendationService recommendationService;

    public GameController(GameRepository gameRepository, RecommendationService recommendationService) {
        this.gameRepository = gameRepository;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/games")
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
    public Game addGame(@Valid @RequestBody Game game) {
        return gameRepository.save(game);
    }

    @GetMapping("/games/{uuid}")
    public Game getGame(@PathVariable UUID uuid) {
        return gameRepository
                .findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("No game with id " + uuid));
    }

    @GetMapping("/games/recommended/{userId}")
    public GamePreferences getRecommended(@PathVariable UUID userId) {
        try {
            return recommendationService.getRecomendations(userId);
        } catch (ServiceUnavailableException e) {
            return new GamePreferences();
        }
    }

    @PostMapping("/games/{gameId}/rate")
    public ResponseEntity<RecommendedItem> rateGame(@PathVariable UUID gameId, @Valid @RequestBody RatingDto rating) {
        if (!gameId.equals(rating.getGameId())) {
            return ResponseEntity.unprocessableEntity().build();
        }
        try {
            return ResponseEntity
                    .ok(recommendationService.rateGame(rating.getUserId(), rating.getGameId(), rating.getRating()));
        } catch (ServiceUnavailableException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).build();
        }
    }
}
