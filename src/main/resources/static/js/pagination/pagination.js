export default class PaginationManager {
    constructor(container, restartAction) {
        this.totalPages = 0;
        this.pageSize = 15;
        this.currentPage = 0;

        this.paginationWrapper = document.createElement("div");
        this.paginationWrapper.classList.add('pagination-wrapper');
        container.appendChild(this.paginationWrapper);

        this.restartAction = restartAction;
    }

    createBackButtonElement() {
        const button = document.createElement('button');
        button.disabled = this.currentPage === 0;
        button.classList.add('pagination-btn');
        button.classList.add('prev-btn');
        button.innerHTML = `
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path d="M10 12L6 8L10 4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
        `;
        button.addEventListener('click', (event) => {
            event.preventDefault();
            this.currentPage -= 1;
            this.restartAction();
        });
        return button;
    }

    createPaginationNumbersElement() {
        const paginationNumbersContainer = document.createElement('div');
        paginationNumbersContainer.classList.add('page-numbers');
        if (this.totalPages <= 3) {
            for (let i = 0; i < this.totalPages; i++) {
                this.createPageNumberButton(i, paginationNumbersContainer);
            }
            return paginationNumbersContainer;
        } else {
            for (let i = 0; i < 3; i++) {
                this.createPageNumberButton(i, paginationNumbersContainer);
            }
        }
        const spanDots = document.createElement('span');
        spanDots.classList.add('page-dots');
        spanDots.textContent = '...';
        paginationNumbersContainer.appendChild(spanDots);
        for (let i = this.totalPages; i < this.totalPages - 3; i--) {
            this.createPageNumberButton(i, paginationNumbersContainer);
        }
        return paginationNumbersContainer;
    }

    createPageNumberButton(iteration, pageNumberContainer) {
        const pageNumberButton = document.createElement('button');
        pageNumberButton.classList.add('page-number');
        if (this.currentPage === iteration) {
            pageNumberButton.classList.add('active');
        }
        pageNumberButton.textContent = String(iteration + 1);
        pageNumberContainer.appendChild(pageNumberButton);
        pageNumberButton.addEventListener('click', (event) => {
            event.preventDefault();
            this.currentPage = iteration;
            this.restartAction();
        });
    }

    createNextButtonElement() {
        const button = document.createElement('button');
        button.disabled = (this.currentPage + 1 === this.totalPages);
        button.classList.add('pagination-btn');
        button.classList.add('next-btn');
        button.innerHTML = `
            <svg width="16" height="16" viewBox="0 0 16 16" fill="none">
                <path d="M6 12L10 8L6 4" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
        `;
        button.addEventListener('click', (event) => {
            event.preventDefault();
            this.currentPage += 1;
            this.restartAction();
        });
        return button;
    }

    createPageSizeSelectorElement() {
        const pageSizeSelectorDiv = document.createElement('div');
        pageSizeSelectorDiv.classList.add('page-size-selector');

        const selectorLabel = document.createElement('span');
        selectorLabel.classList.add('selector-label');
        selectorLabel.textContent = 'Показывать на странице:';
        pageSizeSelectorDiv.appendChild(selectorLabel);

        const selectWrapper = document.createElement('div');
        selectWrapper.classList.add('select-wrapper');

        const selector = document.createElement('select');
        selector.classList.add('page-size-select');
        selector.innerHTML = `
            <option value="15" ${this.pageSize === 15 ? 'selected' : ''}>15</option>
            <option value="30" ${this.pageSize === 30 ? 'selected' : ''}>30</option>
            <option value="60" ${this.pageSize === 60 ? 'selected' : ''}>60</option>
        `;
        selectWrapper.appendChild(selector);

        pageSizeSelectorDiv.appendChild(selectorLabel);
        pageSizeSelectorDiv.appendChild(selectWrapper);
        selector.addEventListener('change', (event) => {
            event.preventDefault();
            this.pageSize = selector.value;
            this.restartAction();
        });
        return pageSizeSelectorDiv;
    }

    render() {
        this.paginationWrapper.innerHTML = '';
        this.paginationWrapper.appendChild(this.createBackButtonElement());
        this.paginationWrapper.appendChild(this.createPaginationNumbersElement());
        this.paginationWrapper.appendChild(this.createNextButtonElement());
        this.paginationWrapper.appendChild(this.createPageSizeSelectorElement());
    }

    initializePagination(currentPage, pageSize, totalPages) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.render();
    }
}