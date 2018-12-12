import AbstractCQElement from './cq-element.js';

class GameSelectorOption extends AbstractCQElement {

    connectedCallback() {
        super.connectedCallback();
        this.initEventListeners();
    }

    setGame(game) {
        console.log(game);
        this.game = game;
        this.byId('name').innerHTML = game.name;
        this.byId('description').innerHTML = game.description;
        this.byId('location').innerHTML = game.location;
    }

    initEventListeners() {
        this.byId('game').addEventListener('click', () => this.app.router.navigate(`/games/${this.game.id}`));
    }

    get template() {
        return `
            <style>
                div.cq-game-info {
                    margin-left: auto;
                }
            </style>
            <a id="game" class="list-group-item">
                <button type="button" class="btn btn-primary bmd-btn-fab">
                    L
                </button>
                <div class="bmd-list-group-col">
                    <p id="name" class="list-group-item-heading">Game name</p>
                    <p id="description" class="list-group-item-text">Game description</p>
                </div>
                <div class="cq-game-info">
                <p id="location" class="list-group-item-text">Location</p>
                </div>
            </a>
            `;
    }
  }
  customElements.define('cq-game-selector-option', GameSelectorOption);