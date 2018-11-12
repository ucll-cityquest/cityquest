package be.ucll.da.cityquest.model;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String question;

    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates;

    @ElementCollection
    private List<String> answers;

    private int correctAnswer;

    private String extraInfo;

    private Question() {
    }

    public Question(String question, Coordinates coordinates, List<String> answers, int correctAnswer, String extraInfo) {
        this.question = question;
        this.coordinates = coordinates;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.extraInfo = extraInfo;
    }

    public UUID getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getExtraInfo() {
        return extraInfo;
    }
}
