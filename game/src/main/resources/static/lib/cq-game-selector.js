import AbstractCQElement from './cq-element.js';
import './cq-game-selector-option.js';

class GameSelector extends AbstractCQElement {
        
    connectedCallback() {
        super.connectedCallback();
        this.showGames();
        this.initNewButton();
    }

    showGames() {
        let gamesDiv = this.byId('games');
        fetch(settings.backendUrl('games'))
            .then(resp => resp.json())
            .then(games => {
                games.forEach(game => {
                    let gameOption = document.createElement('cq-game-selector-option');
                    gamesDiv.appendChild(gameOption);
                    gameOption.setGame(game);
                });
            });
    }

    initNewButton() {
        this.byId('new-game').addEventListener('click', e => this.app.router.navigate('/games/new'));
    }

    get template() {
        return `
            <style>
                .cq-header {
                    display: flex;
                }
                .cq-header button {
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <div class="cq-header">
                    <h1>Welcome to City Quest</h1>
                    <button id="new-game" type="button" class="btn btn-primary">New</button>
                </div>
                <h4>These are the available games:</h4>
                <div id="games"></div>
            </div>
            `;
    }
  }
  customElements.define('cq-game-selector', GameSelector);