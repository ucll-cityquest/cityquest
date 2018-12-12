import AbstractCQElement from './cq-element.js';
import './cq-game-selector.js';
import './cq-game-editor.js';
import './cq-game-player.js?t=3';

class CityQuest extends AbstractCQElement {
    
    connectedCallback() {
        super.connectedCallback();
        this.router = new Navigo(null, true);
        this.router
            .on(() => this.show('cq-game-selector'))
            .on('games/new', (params) => this.show('cq-game-editor', params))
            .on('games/:id', (params) => this.show('cq-game-player', params))
            .on('games/:id/edit', (params) => this.show('cq-game-editor', params))
            .resolve();
    }

    get template() {
        return ``;
    }

    show(el, params, query) {
        let element = document.createElement(el);
        while(this.shadowRoot.childNodes.length > 0) {
            if(this.shadowRoot.childNodes[0].destroy) {
                this.shadowRoot.childNodes[0].destroy();
            }
            this.shadowRoot.removeChild(this.shadowRoot.childNodes[0]);
        }
        this.shadowRoot.appendChild(element);
        if(element.init) {
            element.init(params, query);
        }
    }
    
  }
  customElements.define('cq-app', CityQuest);