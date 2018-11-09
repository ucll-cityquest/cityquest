package be.ucll.da.cityguest.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestionBuilder {
    private UUID id;
    private String question;
    private Coordinates coordinates;
    private List<String> answers = new ArrayList<>();
    private int correctAnswer;
    private String extraInfo;

    private QuestionBuilder() {
    }

    public static QuestionBuilder aQuestion() {
        return new QuestionBuilder();
    }

    public QuestionBuilder setId(UUID id) {
        this.id = id;
        return this;
    }

    public QuestionBuilder setQuestion(String question) {
        this.question = question;
        return this;
    }

    public QuestionBuilder setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public QuestionBuilder addAnswer(String answer) {
        this.answers.add(answer);
        return this;
    }

    public QuestionBuilder setAnswers(List<String> answers) {
        this.answers = answers;
        return this;
    }

    public QuestionBuilder setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public QuestionBuilder setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public Question build() {
        return new Question(question, coordinates, answers, correctAnswer, extraInfo);
    }
}
