import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";
import {renderProductElement} from "../product/product-renderer.js";

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

    function loadFilters(activeFilters, fullFilters) {
        createManufacturerFilters(activeFilters, fullFilters);
        createCountryFilters(activeFilters, fullFilters);
        createActiveIngredientFilters(activeFilters, fullFilters);
    }

    function createManufacturerFilters(activeFilters, fullFilters) {
        if (activeFilters.manufacturers === undefined || fullFilters.manufacturers === undefined) {
            throw Error('Integrity of the filter aggregation of manufacturers structure has been compromised');
        }

        const manufacturerFilterContainer = document.getElementById('manufacturer-filters-container');
        manufacturerFilterContainer.innerHTML = '';
        const activeFiltersCountMap = new Map(Object.entries(activeFilters.manufacturers));
        const fullFiltersCountMap = new Map(Object.entries(fullFilters.manufacturers));
        fullFiltersCountMap.forEach((value, key) => {
            const item = document.createElement("li");
            if (activeFiltersCountMap.has(key)) {
                item.append(createLabelFilter('manufacturer',
                    `${key} (${activeFiltersCountMap.get(key)})`, key));
            } else {
                item.append(createLabelFilter('manufacturer', `${key} (${value})`, key));
            }
            manufacturerFilterContainer.append(item);
        });
    }

    function createCountryFilters(activeFilters, fullFilters) {
        if (activeFilters.countries === undefined || fullFilters.countries === undefined) {
            throw Error('Integrity of the filter aggregation of countries structure has been compromised');
        }

        const countryFilterContainer = document.getElementById('country-filter-container');
        countryFilterContainer.innerHTML = '';
        const activeFiltersCountMap = new Map(Object.entries(activeFilters.countries));
        const fullFiltersCountMap = new Map(Object.entries(fullFilters.countries));
        fullFiltersCountMap.forEach((value, key) => {
            const item = document.createElement("li");
            if (activeFiltersCountMap.has(key)) {
                item.append(createLabelFilter('manufacturer',
                    `${key} (${activeFiltersCountMap.get(key)})`, key));
            } else {
                item.append(createLabelFilter('manufacturer', `${key} (${value})`, key));
            }
            countryFilterContainer.append(item);
        });
    }

    function createActiveIngredientFilters(activeFilters, fullFilters) {
        if (activeFilters.activeIngredients === undefined || fullFilters.activeIngredients === undefined) {
            throw Error('Integrity of the filter aggregation of active ingredients structure has been compromised');
        }

        const activeIngredientFilterContainer = document.getElementById('active-ingredient-filter-container');
        activeIngredientFilterContainer.innerHTML = '';
        const activeFiltersCountMap = new Map(Object.entries(activeFilters.activeIngredients));
        const fullFiltersCountMap = new Map(Object.entries(activeFilters.activeIngredients));
        fullFiltersCountMap.forEach((value, key) => {
            const item = document.createElement("li");
            if (activeFiltersCountMap.has(key)) {
                item.append(createLabelFilter('manufacturer',
                    `${key} (${activeFiltersCountMap.get(key)})`, key));
            } else {
                item.append(createLabelFilter('manufacturer', `${key} (${value})`, key));
            }
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

        const [aggregationResponse, productResponse] = await Promise.all(
            [fetchAllProducts(), fetchActiveFilterProducts()]);
        const pageStatistic = productResponse.pageResponse.page;
        catalogHeader.textContent = `Лекарства ${pageStatistic.totalElements} товаров`;
        paginationManager.initializePagination(pageStatistic.number, pageStatistic.size, pageStatistic.totalElements);

        loadFilters(productResponse.filterAggregation, aggregationResponse.filterAggregation);
        initializeFilterParams();

        productLoader.hideLoading();
        if (pageStatistic.totalElements === 0) {
            const newsMessage = document.createElement('h3');
            newsMessage.textContent = 'Нет доступных товаров';
            productContainer.appendChild(newsMessage);
            return;
        }
        const productList = productResponse.pageResponse._embedded.productPreviewDtoList;
        productList.forEach(product => renderProductElement(product, productContainer));
    }

    async function fetchAllProducts() {
        const response = await restClient.fetchData(
            `/api/v1/search/products/filter?${applyFiltersForAggregation()}`, 'GET', {});
        return response.json();
    }

    async function fetchActiveFilterProducts() {
        const response = await restClient.fetchData(`/api/v1/search/products/filter?${applyFilters()}`,
            'GET', {});
        return response.json();
    }

    function getProductTypeFromPathname() {
        const pathname = window.location.pathname;
        return pathname.replace(/^\/catalog\/?/, '');
    }

    function applyFilters() {
        const formData = new FormData(filterForm);
        const params = new URLSearchParams(formData);
        applyPagination(params);
        window.history.replaceState(null, null, `?${params.toString()}`);
        applyTypeFilter(params);
        return params.toString();
    }

    function applyFiltersForAggregation() {
        const params = new URLSearchParams();
        applyTypeFilter(params);
        return params.toString();
    }

    function applyPagination(params) {
        params.set('sort', sortSelector.value);
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
    }

    function applyTypeFilter(params) {
        params.set('type', getProductTypeFromPathname());
    }

    function resetFilters() {
        filterForm.reset();
        const params = new URLSearchParams();
        params.set('sort', sortSelector.value);
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
        window.history.replaceState(null, null, `?${params.toString()}`);
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