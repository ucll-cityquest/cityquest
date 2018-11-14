package be.ucll.da.cityquest.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String location;

    @NotNull
    @NotEmpty
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Coordinates coordinates;

    @OneToMany(cascade = CascadeType.ALL)
    @NotEmpty
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
