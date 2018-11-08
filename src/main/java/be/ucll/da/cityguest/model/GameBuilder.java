package be.ucll.da.cityguest.model;

import java.util.List;

class GameBuilder {
    private String name;
    private String location;
    private String description;
    private Coordinates coordinates;
    private List<Question> questions;

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

    public GameBuilder setQuestions(List<Question> questions) {
        this.questions = questions;
        return this;
    }

    public Game build() {
        var game = new Game();
        game.setCoordinates(coordinates);
        game.setDescription(description);
        game.setLocation(location);
        game.setName(name);
        game.setQuestions(questions);
        return game;
    }
}
