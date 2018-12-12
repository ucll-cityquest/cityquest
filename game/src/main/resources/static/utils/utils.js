'use strict';

const get = (o, path) => path.split('.').reduce((o = {}, key) => o[key], o);
const set = (o, path, value) => {
    var i;
    path = path.split('.');
    for (i = 0; i < path.length - 1; i++)
        o = o[path[i]];

    o[path[i]] = value;
};

const htmlToElement = (html) => {
    var template = document.createElement('template');
    html = html.trim();
    template.innerHTML = html;
    return template.content.firstChild;
}