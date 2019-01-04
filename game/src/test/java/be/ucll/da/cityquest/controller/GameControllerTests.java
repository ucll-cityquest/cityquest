package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.Application;
import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Coordinates;
import be.ucll.da.cityquest.model.Game;
import be.ucll.da.cityquest.model.GamePreferences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static be.ucll.da.cityquest.model.GameBuilder.aGame;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTests {
    @LocalServerPort
    private int port;

    @Autowired
    private GameRepository gameRepository;

    private TestRestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void clearDatabase() {
        gameRepository.deleteAll();
    }

    @Test
    public void whenThereAreGamesAndYouAskForAllGamesYouGetAListOfAllGames() {
        createGameAndPostIt();
        final var games = getAllGamesFromBackend();

        assertThat(games)
                .isNotEmpty();
    }

    @Test
    public void whenGettingAListOfAllGamesTheQuestionsShouldBeEmpty() {
        createGameAndPostIt();
        final var games = getAllGamesFromBackend();
        final var game = games.get(0);

        assertThat(game.getQuestions())
                .isEmpty();
    }

    @Test
    public void whenGettingASpecificGameReturnA404WhenTheIdDoesNotExists() {
        final var entity = new HttpEntity<>(null, new HttpHeaders());
        final var response = restTemplate.exchange(
                createURLWithPort("/games/" + UUID.randomUUID().toString()),
                HttpMethod.GET,
                entity,
                Game.class
        );
        final var statusCode = response.getStatusCode();

        assertThat(statusCode)
                .isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void whenAskingForRecommendationAndThereIsNoneReturnEmptyJsonObject() {
        var randomUserId = UUID.randomUUID().toString();

        final var response = restTemplate.getForEntity(
                createURLWithPort("/games/recommended/" + randomUserId),
                String.class
        );

        final var responseBody = response.getBody();
        assertThat(responseBody).isEqualTo("{}");
    }

    @Test
    public void whenYouPostAGameYouShouldBeAbleToGetIt() {
        final var game = getGameFromBackend(createGameAndPostIt().getId());
        assertThat(game).isNotNull();
    }

    private Game createGame() {
        return aGame()
                .setName("Mechelse stadquiz")
                .setDescription("Het coole mechelse stadquiz")
                .setLocation("Mechelen")
                .setCoordinates(new Coordinates(51.02574, 4.47762))
                .addQuestion(questionBuilder ->
                        questionBuilder
                                .setQuestion("Wanneer is het gemeentehuis gebouwd")
                                .setCoordinates(new Coordinates(51.028056, 4.480833))
                                .addAnswer("1526")
                                .addAnswer("1538")
                                .addAnswer("1914")
                                .setCorrectAnswer(1)
                                .setExtraInfo("https://nl.wikipedia.org/wiki/Stadhuis_van_Mechelen")
                )
                .build();
    }

    private Game createGameAndPostIt() {
        final var game = createGame();
        final var entity = new HttpEntity<>(game, new HttpHeaders());
        final var response = restTemplate.exchange(
                createURLWithPort("/games"),
                HttpMethod.POST,
                entity,
                Game.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return response.getBody();
    }

    private List<Game> getAllGamesFromBackend() {
        final var entity = new HttpEntity<>(null, new HttpHeaders());
        final var response = restTemplate.exchange(
                createURLWithPort("/games"),
                HttpMethod.GET,
                entity,
                Game[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        return Arrays.asList(response.getBody());
    }

    private Game getGameFromBackend(UUID id) {
        final var entity = new HttpEntity<>(null, new HttpHeaders());
        final var game = restTemplate.exchange(
                createURLWithPort("/games/" + id.toString()),
                HttpMethod.GET,
                entity,
                Game.class
        );

        assertThat(game.getStatusCode()).isEqualTo(HttpStatus.OK);

        return game.getBody();
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
