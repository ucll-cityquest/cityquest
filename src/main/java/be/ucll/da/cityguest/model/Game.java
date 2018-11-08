package be.ucll.da.cityguest.model;

import java.util.List;

public class Game {
    private String name;
    private String location;
    private Coordinates coordinates;
    private String description;
    private List<Question> questions;

    private Game() {}

    public Game(String name, String location, Coordinates coordinates, String description, List<Question> questions) {
        this.name = name;
        this.location = location;
        this.coordinates = coordinates;
        this.description = description;
        this.questions = questions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
