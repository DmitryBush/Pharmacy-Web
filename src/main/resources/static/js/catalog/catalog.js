import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();

    const catalogHeader = document.getElementById("catalog-header");

    const productContainer = document.getElementById('product-grid');
    const filterForm = document.getElementById('filter-form');
    const sortSelector = document.getElementById('sort-selector');

    const mainList = document.getElementById('product-list-container');
    const productLoader = new Loader(mainList);
    const paginationManager = new PaginationManager(mainList, initialize);

    filterForm.addEventListener('change', event => {
        event.preventDefault();
        initialize();
    });
    document.getElementById('submit-button').addEventListener('click', (e) => {
        e.preventDefault();
        initialize();
    });
    document.getElementById('reset-filter-button').addEventListener('click', (e) => {
        e.preventDefault();
        resetFilters();
        initialize();
    });
    sortSelector.addEventListener('change', (e) => {
        e.preventDefault();
        initialize();
    });

    try {
        await initialize();
    } catch (e) {
        console.error(e);
    }

    async function initialize() {
        productLoader.showLoading();
        await loadFilters();
        initializeFilterParams();
        await loadProducts();
    }

    async function loadFilters() {
        await Promise.allSettled([
            createManufacturerFilters(),
            createCountryFilters(),
            createActiveIngredientFilters(),
        ]);
    }

    async function createManufacturerFilters(){
        const manufacturerFilters =
           await (await restClient.fetchData(`/api/v1/search/manufacturer/filter/count`, 'GET', {})).json();

        const manufacturerFilterContainer = document.getElementById('manufacturer-filter-container');
        manufacturerFilters.forEach(manufacturer => {
            const item = document.createElement("li");
            item.append(createLabelFilter('manufacturer', manufacturer));
            manufacturerFilterContainer.append(item);
        });
    }

    async function createCountryFilters(){
        const countryFilters =
            await (await restClient.fetchData(`/api/v1/search/country/filter/count`, 'GET', {})).json();

        const countryFilterContainer = document.getElementById('country-filter-container');
        countryFilters.forEach(country => {
            const item = document.createElement("li");
            item.append(createLabelFilter('country', country));
            countryFilterContainer.append(item);
        })
    }

    async function createActiveIngredientFilters(){
        const activeIngredientFilters =
            await (await restClient.fetchData(`/api/v1/search/active-ingredient/filter/count`, 'GET', {})).json();

        const activeIngredientFilterContainer = document.getElementById('active-ingredient-filter-container');
        activeIngredientFilters.forEach(ingredient => {
            const item = document.createElement("li");
            item.append(createLabelFilter('ingredient', ingredient));
            activeIngredientFilterContainer.append(item);
        })
    }

    function createLabelFilter(name, value){
        const labelItem = document.createElement("label");
        labelItem.classList.add('price-filter-group');
        labelItem.append(createCheckbox(name, value));
        labelItem.textContent = value;
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
            document.querySelector(`input[name="manufacturer"][value="${value}"]`).forEach(checkbox => {
                checkbox.checked = true;
            });
        });

        urlParams.getAll('country').forEach(value => {
            document.querySelector(`input[name="country"][value="${value}"]`).forEach(checkbox => {
                checkbox.checked = true;
            });
        });

        urlParams.getAll('ingredient').forEach(value => {
            document.querySelector(`input[name="ingredient"][value="${value}"]`).forEach(checkbox => {
                checkbox.checked = true;
            });
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
        const productResponse = await (await restClient.fetchData(`/api/v1/products?${applyFilters()}`,
            'GET', {})).json();
        const totalElements = productResponse.page.totalElements;
        catalogHeader.textContent = `Лекарства ${totalElements} товаров`;
        paginationManager.initializePagination(productResponse.page.number, productResponse.page.size, totalElements);

        productLoader.hideLoading();
        if (totalElements === 0) {
            const newsMessage = document.createElement('h3');
            newsMessage.textContent = 'Нет доступных товаров';
            productContainer.appendChild(newsMessage);
            return;
        }
        const productList = productResponse._embedded.newsReadDtoList;
        productList.forEach(product => createProductElement(product));
    }

    function applyFilters() {
        const formData = new FormData(filterForm);
        const params = new URLSearchParams(formData);
        params.set('sort', sortSelector.value);
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
        window.history.replaceState(null, null, `?${params.toString()}`);
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
            productImage.src = `/api/v1/product-image/${product.imagePaths[0].id}`;
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
        productNameContainer.appendChild(productNameLink);
        return productNameContainer;
    }

    function createProductPriceContainer(product) {
        const productPriceContainer = document.createElement('div');
        const productPrice = document.createElement('p');
        productPrice.textContent = `${product.price} ₽`;
        const buyButton = document.createElement('button');
        productPriceContainer.appendChild(productPrice);
        productPriceContainer.appendChild(buyButton);
        return productPriceContainer;
    }
});