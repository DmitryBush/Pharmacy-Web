import RestClient from "../RestClient.js";

document.addEventListener("DOMContentLoaded", function() {
    let DEBOUNCE_DELAY = 300;
    const map = new Map();
    const restClient = new RestClient();

    const productList = document.getElementById("product-list");

    const searchContainer = document.getElementById("search-container");
    const searchField = document.getElementById("search-field");
    const resultsContainer = document.getElementById("search-result-container");
    const searchBackdrop = document.getElementById('search-backdrop');
    let searchOutsideClick = null;

    document.getElementById("search-field").addEventListener('input', handleSearchInput);
    document.getElementById("search-btn").addEventListener('click', handleOpenSearch);

    function handleOpenSearch() {
        searchContainer.style.display = 'block';
        searchBackdrop.style.display = 'block';

        searchOutsideClick = (event) => {
            if (searchContainer && !searchContainer.contains(event.target) && !resultsContainer.contains(event.target)) {
                closeSearch();
                document.removeEventListener('click', searchOutsideClick);
                searchOutsideClick = null;
            }
        };
        setTimeout(() => document.addEventListener('click', searchOutsideClick), 10);
    }

    function handleSearchInput(e) {
        clearTimeout(DEBOUNCE_DELAY);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 2) {
            resultsContainer.innerHTML = '';
            map.clear();
            return;
        }

        DEBOUNCE_DELAY = setTimeout(() => {
            fetchResults(searchTerm);
        }, DEBOUNCE_DELAY);
    }

    async function fetchResults(searchTerm) {
        try {
            const data = await
                (await restClient.fetchData(`/api/search/medicine?name=${searchTerm}`, 'GET')).json();
            displayResults(data);
        } catch (error) {
            console.error(error);
        }
    }

    function displayResults(results) {
        resultsContainer.innerHTML = '';
        map.clear();
        if (results.length === 0) {
            return;
        }

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = result.name;
            div.dataset.id = result.id;
            map.set(result.id, result);

            div.onclick = () => handleSearchClick(result);

            resultsContainer.appendChild(div);
        });
    }

    function handleSearchClick(e) {
        const productItem = document.createElement("div");
        productItem.className = "product-item";
        productItem.innerHTML = `
            <a href="/admin/product/${e.id}">
                <img src="/api/product-image/${e.imagePaths[0].id}"
                     width="50px"
                     height="50px"
                     alt="${e.name}">
            </a>
            <div>
                <a href="/admin/product/${e.id}">${e.name}</a>
            </div>
            <div>
                <p>1 шт.</p>
            </div>
            `;
        productList.append(productItem);
        setTimeout(() => closeSearch(), 10);
    }

    function closeSearch() {
        searchContainer.style.display = 'none';
        searchBackdrop.style.display = 'none';

        searchField.value = '';
        resultsContainer.innerHTML = '';

        if (searchOutsideClick !== null) {
            document.removeEventListener('click', searchOutsideClick);
            searchOutsideClick = null;
        }
    }
})

