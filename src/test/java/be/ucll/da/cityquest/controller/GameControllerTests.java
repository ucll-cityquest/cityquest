package be.ucll.da.cityquest.controller;

import be.ucll.da.cityquest.database.GameRepository;
import be.ucll.da.cityquest.model.Coordinates;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static be.ucll.da.cityquest.model.GameBuilder.aGame;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTests {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private GameRepository gameRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        final var game =  aGame()
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

        when(gameRepository.findAll())
            .thenReturn(List.of(game));
    }

    @Test
    public void gamesShouldReturnListWithAllGames() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void inTheGamesResponseQuestionsShouldBeEmpty() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/games"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].questions").isEmpty());
    }
}
