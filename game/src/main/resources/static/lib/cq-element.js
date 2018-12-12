export default class AbstractCQElement extends HTMLElement {
        
    connectedCallback() {
        this.initShadowDom();
    }

    initShadowDom() {
        if(this.shadowRoot) return;
        let shadowRoot = this.attachShadow({mode: 'open'});
        shadowRoot.innerHTML = this.template;
        shadowRoot.appendChild(document.getElementById('styletemplate').content.cloneNode(true));
        $(shadowRoot).bootstrapMaterialDesign();
    }

    get template() {
        throw "Template is not defined!"
    }

    byId(id) {
        return this.shadowRoot.getElementById(id) || document.getElementById(id); // why document? For modal
    }

    byCss(css) {
        return this.shadowRoot.querySelector(css) || document.querySelector(css); // why document? For modal
    }

    get app() {
        return document.querySelector('cq-app');
    }
  }