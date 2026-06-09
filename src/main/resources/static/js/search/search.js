import RestClient from "../RestClient.js";

export class Search {
    constructor(searchField, resultItemContainer) {
        this.searchDelay = 300;
        this.restClient = new RestClient();
        this.searchField = searchField;
        this.resultItemContainer = resultItemContainer;


        this._attachEventListeners();
    }

    static getInstance(searchField, resultItemContainer) {
        if (!this._instance) {
            this._instance = new Search(searchField, resultItemContainer);
        }
        return this._instance;
    }

    _attachEventListeners() {
        this.searchField.addEventListener('input', (e) => this._handleSearchInput(e));
    }

    _handleSearchInput(e) {
        clearTimeout(this.searchDelay);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 2) {
            this.resultItemContainer.innerHTML = '';
            return;
        }

        this.searchDelay = setTimeout(() => this._fetchResults(searchTerm), this.searchDelay);
    }

    async _fetchResults(searchTerm) {
        try {
            const data = await
            (await this.restClient.fetchData(
                `/api/v1/search/products/filter?${this._getSearchParams(searchTerm)}`,
                'GET')).json();
            this._displayResults(data);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    _getSearchParams(searchTerm) {
        const param = new URLSearchParams();
        param.set('size', '5');
        param.set('name', searchTerm);
        param.set('type', '');
        return param.toString();
    }

    _displayResults(items) {
        this.resultItemContainer.innerHTML = '';

        if (items.pageResponse._embedded === undefined) {
            this.resultItemContainer.classList.remove('active');
            return;
        }

        this.resultItemContainer.classList.add('active');
        items.pageResponse._embedded.productPreviewDtoList.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';

            if (result.imagePaths.length > 0) {
                const image = document.createElement('img');
                image.src = `/api/v1/product-image/${result.imagePaths[0].id}`;
                image.width = 50;
                image.height = 50;
                div.appendChild(image);
            }
            const name = document.createElement('span');
            name.textContent = result.name;
            div.appendChild(name);

            div.onclick = () => window.location.replace(`/product/${encodeURIComponent(result.id)}`);

            this.resultItemContainer.appendChild(div);
        });
    }
}