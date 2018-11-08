package be.ucll.da.cityguest.model;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private String question;
    private Coordinates coordinates;
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
