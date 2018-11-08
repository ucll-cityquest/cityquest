package be.ucll.da.cityguest.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    private String question;

    @OneToOne
    private Coordinates coordinates;

    @ElementCollection
    private List<String> answers = new ArrayList<>();

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

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
