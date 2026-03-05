import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();

    const catalogHeader = document.getElementById("catalog-header");

    const productContainer = document.getElementById('product-grid');
    const filterForm = document.getElementById('filter-form');
    const sortSelector = document.getElementById('sort-selector');
    const submitButton = document.getElementById('submit-button');
    const resetFilterButton = document.getElementById('reset-filter-button');

    const mainList = document.getElementById('product-list-container');
    const productLoader = new Loader(mainList);
    const paginationManager = new PaginationManager(mainList, initialize);

    submitButton.addEventListener('click', async (e) => {
        e.preventDefault();
        await initialize();
    });
    resetFilterButton.addEventListener('click', async (e) => {
        e.preventDefault();
        resetFilters();
        await initialize();
    });
    sortSelector.addEventListener('change', async (e) => {
        e.preventDefault();
        await initialize();
    });

    try {
        await initialize();
    } catch (e) {
        console.error(e);
    }

    async function initialize() {
        lockFilterButtons();
        productLoader.showLoading();
        await loadProducts();
        unlockFilterButtons();
    }

    function loadFilters(filterAggregation) {
        createManufacturerFilters(filterAggregation);
        createCountryFilters(filterAggregation);
        createActiveIngredientFilters(filterAggregation);
    }

    function createManufacturerFilters(filterAggregation) {
        if (filterAggregation.manufacturers === undefined) {
            throw Error('Integrity of the filter aggregation of manufacturers structure has been compromised');
        }

        const manufacturerFilterContainer = document.getElementById('manufacturer-filters-container');
        manufacturerFilterContainer.innerHTML = '';
        const manufacturersMap = new Map(Object.entries(filterAggregation.manufacturers));
        manufacturersMap.forEach((value, key) => {
            const item = document.createElement("li");
            item.append(createLabelFilter('manufacturer', `${key} (${value})`, key));
            manufacturerFilterContainer.append(item);
        });
    }

    function createCountryFilters(filterAggregation) {
        if (filterAggregation.countries === undefined) {
            throw Error('Integrity of the filter aggregation of countries structure has been compromised');
        }

        const countryFilterContainer = document.getElementById('country-filter-container');
        countryFilterContainer.innerHTML = '';
        const countriesMap = new Map(Object.entries(filterAggregation.countries));
        countriesMap.forEach((value, key) => {
            const item = document.createElement("li");
            item.append(createLabelFilter('country', `${key} (${value})`, key));
            countryFilterContainer.append(item);
        });
    }

    function createActiveIngredientFilters(filterAggregation) {
        if (filterAggregation.activeIngredients === undefined) {
            throw Error('Integrity of the filter aggregation of active ingredients structure has been compromised');
        }

        const activeIngredientFilterContainer = document.getElementById('active-ingredient-filter-container');
        activeIngredientFilterContainer.innerHTML = '';
        const activeIngredientsMap = new Map(Object.entries(filterAggregation.activeIngredients));
        activeIngredientsMap.forEach((value, key) => {
            const item = document.createElement("li");
            item.append(createLabelFilter('ingredient', `${key} (${value})`, key));
            activeIngredientFilterContainer.append(item);
        });
    }

    function createLabelFilter(name, text, value) {
        const labelItem = document.createElement("label");
        labelItem.classList.add('price-filter-group');
        labelItem.appendChild(createCheckbox(name, value));
        labelItem.insertAdjacentHTML('beforeend', text);
        return labelItem;
    }

    function createCheckbox(name, value) {
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.name = name;
        checkbox.value = value;
        return checkbox;
    }

    function initializeFilterParams() {
        const urlParams = new URLSearchParams(window.location.search);

        urlParams.getAll("manufacturer").forEach(value => {
            const element = document.querySelector(`input[name="manufacturer"][value="${value}"]`);
            element.checked = true;
        });

        urlParams.getAll('country').forEach(value => {
            const element = document.querySelector(`input[name="country"][value="${value}"]`);
            element.checked = true;
        });

        urlParams.getAll('ingredient').forEach(value => {
            const element = document.querySelector(`input[name="ingredient"][value="${value}"]`);
            element.checked = true;
        });

        const recipeValue = urlParams.get("recipe");
        if (recipeValue) {
            document.querySelector(`input[name="recipe"][value="${recipeValue}"]`).checked = true;
        }

        document.getElementById("minPrice").value = urlParams.get("minPrice") || "";
        document.getElementById("maxPrice").value = urlParams.get("maxPrice") || "";
    }

    async function loadProducts() {
        productContainer.innerHTML = '';

        const productResponse = await (await restClient.fetchData(`/api/v1/search/products/filter?${applyFilters()}`,
            'GET', {})).json();
        const pageStatistic = productResponse.pageResponse.page;
        catalogHeader.textContent = `Лекарства ${pageStatistic.totalElements} товаров`;
        paginationManager.initializePagination(pageStatistic.number, pageStatistic.size, pageStatistic.totalElements);

        loadFilters(productResponse.filterAggregation);
        initializeFilterParams();

        productLoader.hideLoading();
        if (pageStatistic.totalElements === 0) {
            const newsMessage = document.createElement('h3');
            newsMessage.textContent = 'Нет доступных товаров';
            productContainer.appendChild(newsMessage);
            return;
        }
        const productList = productResponse.pageResponse._embedded.productPreviewDtoList;
        productList.forEach(product => createProductElement(product));
    }

    function getProductTypeFromPathname() {
        const pathname = window.location.pathname;
        return pathname.replace(/^\/catalog\/?/, '');
    }

    function applyFilters() {
        const formData = new FormData(filterForm);
        const params = new URLSearchParams(formData);
        params.set('sort', sortSelector.value);
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
        window.history.replaceState(null, null, `?${params.toString()}`);
        params.set('type', getProductTypeFromPathname());
        return params.toString();
    }

    function resetFilters() {
        filterForm.reset();
        const params = new URLSearchParams();
        params.set('sort', sortSelector.value);
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
        window.history.replaceState(null, null, `?${params.toString()}`);
    }

    function createProductElement(product) {
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');

        productCard.appendChild(createProductImage(product));
        productCard.appendChild(createProductNameLink(product));
        productCard.appendChild(createProductPriceContainer(product));
        productContainer.appendChild(productCard);
    }

    function createProductImage(product) {
        const imageLink = document.createElement('a');
        imageLink.href = `/catalog/${product.id}`;
        const productImage = document.createElement('img');
        if (product.imagePaths.length > 0) {
            productImage.src = `/api/v1/product-image/${product.imagePaths[0]}`;
            productImage.alt = product.name;
            productImage.setAttribute('width', '200px');
        } else {
            productImage.classList.add('.image-unavailable');
        }
        imageLink.appendChild(productImage);
        return imageLink;
    }

    function createProductNameLink(product) {
        const productNameContainer = document.createElement('div');
        const productNameLink = document.createElement('a');
        productNameLink.href = `/catalog/${product.id}`;
        productNameLink.textContent = product.name;
        productNameContainer.appendChild(productNameLink);
        return productNameContainer;
    }

    function createProductPriceContainer(product) {
        const productPriceContainer = document.createElement('div');
        const productPrice = document.createElement('p');
        productPrice.textContent = `${product.price} ₽`;
        const buyButton = document.createElement('button');
        buyButton.textContent = 'Купить'
        productPriceContainer.appendChild(productPrice);
        productPriceContainer.appendChild(buyButton);
        return productPriceContainer;
    }

    function lockFilterButtons() {
        sortSelector.disabled = true;
        submitButton.disabled = true;
        resetFilterButton.disabled = true;
    }

    function unlockFilterButtons() {
        sortSelector.disabled = false;
        submitButton.disabled = false;
        resetFilterButton.disabled = false;
    }
});