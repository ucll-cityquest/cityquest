import AbstractCQElement from '../cq-element.js';

class CoordinatesFormGroup extends AbstractCQElement {
        
    connectedCallback() {
        if(!this.getAttribute('label')) throw 'label is required';
        super.connectedCallback();
    }

    set value(val) {
        this.byId('lat').value = val.lat;
        this.byId('lng').value = val.lng;
    }

    get value() {
        return {"lat": this.byId('lat').value, "lng": this.byId('lng').value};
    }

    get template() {
        return `
            <div class="form-group">
                <div class="form-row">
                    <label for="city-name" class="col-12">${this.getAttribute('label')}</label>
                </div class="form-row">
                <div class="form-row">
                    <div class="col-6">
                        <input type="text" class="form-control" id="lat" aria-describedby="city-coordinates-help" placeholder="latitude e.g. 50.879166">
                    </div>
                    <div class="col-6">
                        <input type="text" class="form-control" id="lng" aria-describedby="city-coordinates-help" placeholder="longitude e.g. 4.704599">
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-12">
                        <small id="city-coordinates-help" class="form-text text-muted">Enter the coordinates in decimal degrees: 50.879166, 4.704599</small>
                    </div>
                </div>
            </div>
            `;
    }
  }
  customElements.define('cq-input-coordinates', CoordinatesFormGroup);