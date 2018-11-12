package be.ucll.da.cityquest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GameBuilder {
    private String name;
    private String location;
    private String description;
    private Coordinates coordinates;
    private List<Question> questions = new ArrayList<>();

    private GameBuilder() {

    }

    public static GameBuilder aGame() {
        return new GameBuilder();
    }

    public GameBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public GameBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public GameBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public GameBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public GameBuilder addQuestion(Function<QuestionBuilder, QuestionBuilder> questionBuilderFunction) {
        var question = questionBuilderFunction.apply(QuestionBuilder.aQuestion())
                .build();
        this.questions.add(question);
        return this;
    }

    public GameBuilder setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Game build() {
        return new Game(name, location, coordinates, description, questions);
    }
}
