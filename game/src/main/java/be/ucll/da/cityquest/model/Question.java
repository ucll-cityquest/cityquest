package be.ucll.da.cityquest.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @NotEmpty
    private String question;

    @NotNull
    @Lob
    private String extraInformation;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Coordinates coordinates;

    @ElementCollection
    @NotNull
    @NotEmpty
    private List<String> answers;

    @Min(0)
    private int correctAnswer;

    private Question() {
    }

    public Question(String question, Coordinates coordinates, List<String> answers, int correctAnswer, String extraInformation) {
        this.question = question;
        this.coordinates = coordinates;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.extraInformation = extraInformation;
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

    public String getExtraInformation() {
        return extraInformation;
    }
}
