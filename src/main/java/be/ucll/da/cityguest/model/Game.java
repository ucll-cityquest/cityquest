package be.ucll.da.cityguest.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String location;

    private String description;

    @OneToOne
    private Coordinates coordinates;

    @OneToMany
    private List<Question> questions;

    Game() {
    }

    public Game(String name, String location, Coordinates coordinates, String description, List<Question> questions) {
        this.name = name;
        this.location = location;
        this.coordinates = coordinates;
        this.description = description;
        this.questions = questions;
    }

    void setName(String name) {
        this.name = name;
    }

    void setLocation(String location) {
        this.location = location;
    }

    void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    void setDescription(String description) {
        this.description = description;
    }

    void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}
