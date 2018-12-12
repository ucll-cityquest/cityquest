import AbstractCQElement from './cq-element.js';
import './form-fields/cq-input-coordinates.js';
import './form-fields/cq-input-text.js';

class GameQuestionEditor extends AbstractCQElement {

    init(question) {
        this.showQuestion(question);
        this.initEventListeners();
        $(this.byId('questionModal')).modal();
        $(this.byId('questionModal')).on('hidden.bs.modal', e => {
            this.byId('questionModal').remove();
            this.remove();
        });
    }

    showQuestion(question) {
        if(question) {
            this.byId('question').value = question.question;
            this.byId('coordinates').value = question.coordinates;
            this.byId('answers').innerHTML = '';
            question.answers.forEach(answer => this.createAnswerInput(answer));
            this.byId('correct-answer').value = question.correctAnswer;
        }
    }

    initEventListeners() {
        this.byId('add-answer').addEventListener('click', () => this.createAnswerInput());
        this.byId('save').addEventListener('click', () => this.validateAndSaveQuestion());
    }

    
    createAnswerInput(value) {
        let input = htmlToElement('<input type="text" class="form-control" placeholder="Possible answer">');
        if(value) {
            input.value = value;
        }
        this.byId('answers').appendChild(input);
        this.byId('correct-answer').appendChild(htmlToElement(`<option value="${this.byId('answers').childElementCount - 1}">${this.byId('answers').childElementCount}</option>`))
    }

    validateAndSaveQuestion() {
        if(!this.byId('answers').childNodes[this.byId('correct-answer').value] || 
                this.byId('answers').childNodes[this.byId('correct-answer').value].value.trim() == '') {
            this.byId('correct-answer').setCustomValidity('invalid');
            return;
        }
    }

    get template() {
        return `
            <style>
                div.cq-question-buttons {
                    margin-left: auto;
                }
            </style>

            <div id="questionModal" class="modal" tabindex="-1" role="dialog">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">New Question</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <cq-input-text id="question" label="Question" placeholder="The quiz question!"></cq-input-text>
                                <cq-input-coordinates id="coordinates" label="Question coordinates"></cq-input-coordinates>
                                <div id="answer-group" class="form-group">
                                    <div style="display: flex">
                                        <label for="answer-group">Possible answers</label>
                                        <button id="add-answer" type="button" class="btn btn-primary btn-sm" style="margin-left: auto">Add</button>
                                    </div>
                                    <div id="answers">
                                        <input type="text" class="form-control" placeholder="Possible answer 1">
                                        <input type="text" class="form-control" placeholder="Possible answer 2">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="correct-answer">Correct answer</label>
                                    <select class="form-control" id="correct-answer"></select>
                                    <div class="invalid-feedback">
                                        The selected correct answer does not exist!
                                    </div>
                                </div>
                                <cq-input-text id="extra-info" label="Extra info" placeholder="Some extra info or noteworthy info..."></cq-input-text>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button id="save" type="button" class="btn btn-primary">Save changes</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
            `;
    }
  }
  customElements.define('cq-game-editor-question-editor', GameQuestionEditor);