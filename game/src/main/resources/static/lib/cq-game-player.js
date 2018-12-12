import AbstractCQElement from './cq-element.js';
import './cq-game-selector-option.js';

class GamePlayer extends AbstractCQElement {

    init(params) {
        this.initGame(params.id);
        this.initEventListeners();
    }

    initGame(gameId) {
        fetch(settings.backendUrl(`games/${gameId}`))
            .then(resp => resp.json())
            .then(game => this.showGame(game));
    }

    showGame(game) {
        this.game = game;
        this.byId('name').innerHTML = game.name;
        
        this.mapDiv.style.display = 'block';
        this.map = L.map(this.mapDiv, {center: [game.coordinates.lat, game.coordinates.lng], zoom: 14.4, zoomSnap: 0.1});
        L
            .tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'})
            .addTo(this.map);
        L
            .marker([50.879166, 4.704599])
            .addTo(this.map);
        
        
        this.geoLocationWatchID = navigator.geolocation.watchPosition(
            (position) => this.showOnMap(position.coords), 
            (error) => console.error(error),
            { enableHighAccuracy: true, timeout: 5000, maximumAge: 5000 });
    }

    showOnMap(coords) {
        if(this.currentLocation) {
            this.currentLocation.setLatLng([coords.latitude, coords.longitude]);
        } else {
            this.currentLocation = L
                .marker([coords.latitude, coords.longitude])
                .addTo(this.map);
        }
    }

    initEventListeners() {
        this.byId('show-game-selector').addEventListener('click', e => this.app.router.navigate(`/`));
        this.byId('edit-game').addEventListener('click', e => this.app.router.navigate(`/games/${this.game.id}/edit`));
    }

    destroy() {
        this.mapDiv.style.display = 'none';
        this.map.remove();
        navigator.geolocation.watchPosition
    }

    get mapDiv() {
        return this.byId('map');
    }

    get template() {
        return `
            <style>
                .cq-header {
                    display: flex;
                }
                .cq-header .button-container {
                    display: flex;
                    margin-left: auto;
                }
            </style>
            <div class="animate">
                <div class="cq-header">
                    <h1 id="name">Game name</h1>
                    <div class="button-container">
                        <button id="show-game-selector" type="button" class="btn">Back</button>
                        <button id="edit-game" type="button" class="btn btn-primary">Edit</button>
                    </div>
                </div>
                <div id="game">
                </div>
            </div>
            `;
    }
  }
  customElements.define('cq-game-player', GamePlayer);