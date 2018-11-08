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

    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Question> questions;

    private Game() {
    }

    public Game(String name, String location, Coordinates coordinates, String description, List<Question> questions) {
        this.name = name;
        this.location = location;
        this.coordinates = coordinates;
        this.description = description;
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
