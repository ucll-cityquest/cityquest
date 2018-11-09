package be.ucll.da.cityguest;
import be.ucll.da.cityguest.database.GameRepository;
import be.ucll.da.cityguest.model.Coordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static be.ucll.da.cityguest.model.GameBuilder.aGame;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(GameRepository repository) {
        return (args) -> {
           var game = aGame()
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
           repository.saveAndFlush(game);
        };
    }

}