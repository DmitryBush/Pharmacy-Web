import {loadCSS} from "../ResourceLoader.js";

export default class Loader {
    constructor(container) {
        const loaderContainer = document.createElement('div');
        loaderContainer.classList.add('loader-container');
        this.loader = document.createElement('div');
        this.loader.classList.add('loader');
        loaderContainer.append(this.loader);

        if (!container.contains(loaderContainer)) {
            container.appendChild(loaderContainer);
        }
    }

    showLoading() {
        this.loader.style.display = 'block';
    }

    hideLoading() {
        this.loader.style.display = 'none';
    }
}

document.addEventListener("DOMContentLoaded", async () => {
    await loadCSS('/css/base.css');
})