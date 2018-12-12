import AbstractCQElement from './cq-element.js';
import './cq-game-editor-question-editor.js';

class GameQuestion extends AbstractCQElement {
        
    init(question, index) {
        this.showQuestion(question, index);
        this.initEventListeners();
    }

    showQuestion(question, index) {
        this.question = question;
        this.byId('counter').innerHTML = `Q${index + 1}`;
        this.byId('question').innerHTML = question.question;
        this.byId('correct-answer').innerHTML = question.answers[question.correctAnswer];
    }

    initEventListeners() {
        this.byId('edit-btn').addEventListener('click', () => {
            this.questionEditor = document.createElement('cq-game-editor-question-editor');
            this.shadowRoot.appendChild(this.questionEditor);
            this.questionEditor.init(this.question);
        });
    }

    get template() {
        return `
            <style>
                div.cq-question-buttons {
                    margin-left: auto;
                }
            </style>

            <div class="list-group-item">
                <span id="counter" class="label label-default label-pill">Q1</span>
                <div class="bmd-list-group-col">
                    <p id="question" class="list-group-item-heading">This is the question</p>
                    <p id="correct-answer" class="list-group-item-text">Answer</p>
                </div>
                <div class="cq-question-buttons">
                    <button id="edit-btn" type="button" class="btn btn-primary bmd-btn-icon">
                        <i class="material-icons">edit</i>
                    </button>
                    <button id="delete-btn" type="button" class="btn btn-primary bmd-btn-icon">
                        <i class="material-icons">delete</i>
                    </button>
                </div>
            </div>
            `;
    }
  }
  customElements.define('cq-game-creator-question', GameQuestion);