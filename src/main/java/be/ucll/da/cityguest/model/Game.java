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

    private Game() {
    }

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
