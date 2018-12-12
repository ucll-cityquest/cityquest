import AbstractCQElement from './cq-element.js';
import './form-fields/cq-input-coordinates.js';
import './form-fields/cq-input-text.js';
import './cq-game-editor-question.js';
import './cq-game-editor-question-editor.js';

class GameEditor extends AbstractCQElement {

    init(params) {
        if(params && params.id) {
            fetch(settings.backendUrl(`games/${params.id}`))
                .then(resp => resp.json())
                .then(game => this.showGame(game));
        }
        this.initEventListeners();
    }

    showGame(game) {
        this.game = game;
        this.byId('name').value = game.name;
        this.byId('location').value = game.location;
        this.byId('coordinates').value = game.coordinates;
        this.byId('teaser').value = game.description;
        this.byId('questions').innerHTML = '';
        game.questions.forEach((question, index) => this.showQuestion(question, index));
    }

    showQuestion(question, index) {
        let questionElement = document.createElement('cq-game-creator-question');
        this.byId('questions').appendChild(questionElement);
        questionElement.init(question, index);
    }

    initEventListeners() {
        this.byId('new-question-btn').addEventListener('click', e => {
            this.questionEditor = document.createElement('cq-game-editor-question-editor');
            this.shadowRoot.appendChild(this.questionEditor);
            this.questionEditor.init();
        });
        this.byId('save-btn').addEventListener('click', e => {
            let game = {};
            game.name = this.byId('name').value;
            game.location = this.byId('location').value;
            game.coordinates = this.byId('coordinates').value;
            console.log(game);
        });
    }

    get template() {
        return `
            <style>
                div.cq-question-buttons {
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <h1>Create a new City Quest</h1>
                <form>
                    <cq-input-text id="name" label="Game name" placeholder="Your city game name"></cq-input-text>
                    <cq-input-text id="location" label="Location" placeholder="Location where one can play this game"></cq-input-text>
                    <cq-input-coordinates id="coordinates" label="Location coordinates"></cq-input-coordinates>
                    <cq-input-text id="teaser" label="Game teaser" placeholder="Provide a small teaser about your game"></cq-input-text>
                    <div class="form-group">
                        <div class="form-row">
                            <label for="city-name" class="col-10">Game questions</label>
                            <button id="new-question-btn" type="button" class="col-2 btn btn-primary">New Question</button>
                        </div>
                        <div id="questions">
                            There are no questions created yet.<br />
                            You can create a question by clicking on the 'NEW QUESTION' button.
                        </div>
                    </div>
                    <button id="save-btn" type="button" class="btn btn-primary">Save</button>
                </form>
            </div>
            `;
    }
  }
  customElements.define('cq-game-editor', GameEditor);