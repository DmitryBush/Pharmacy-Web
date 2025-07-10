import RestClient from "../RestClient.js";

document.addEventListener("DOMContentLoaded", function() {
    let DEBOUNCE_DELAY = 300;
    const receiptMap = new Map();
    const restClient = new RestClient();

    const productList = document.getElementById("product-list");
    const itemsCounter = document.getElementById("items-counter");

    const actionFooter = document.getElementById("action-footer");

    const searchMap = new Map();
    const searchContainer = document.getElementById("search-container");
    const searchField = document.getElementById("search-field");
    const resultsContainer = document.getElementById("search-result-container");
    const searchBackdrop = document.getElementById('search-backdrop');
    let searchOutsideClick = null;

    document.getElementById("search-field").addEventListener('input', handleSearchInput);
    document.getElementById("search-btn").addEventListener('click', handleOpenSearch);

    document.getElementById('save-btn').addEventListener('click', saveReceipt);
    document.getElementById('cancel-btn').addEventListener('click',
        () => window.location.href = '/admin/warehouse');

    function saveReceipt() {
        const body = Array.from(document.querySelectorAll('.product-item'))
            .map(item => getReceiptData(item));

        restClient.fetchData(`/api/v1/warehouse/branches/1/receipts`, 'POST',
            {'Content-Type': 'application/json'}, JSON.stringify({productList: body}))
            .then(r => {

            });
    }

    function getReceiptData(item) {
        return {
            id: item.dataset.id,
            quantity: item.querySelector('.quantity-input').value
        };
    }

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
            searchMap.clear();
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
        searchMap.clear();
        if (results.length === 0) {
            return;
        }

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = result.name;
            div.dataset.id = result.id;
            searchMap.set(result.id, result);

            div.onclick = () => handleSearchClick(result);

            resultsContainer.appendChild(div);
        });
    }

    function handleSearchClick(e) {
        try {
            if (receiptMap.has(e.id)) {
                const productItem = document.getElementById(e.id);
                if (productItem)
                    productItem.querySelector('.quantity-input').value =
                        parseInt(productItem.querySelector('.quantity-input').value) + 1;
                else
                    throw new Error("Произошла критическая ошибка при обновлении контента формы");
            } else {
                receiptMap.set(e.id, 1);
                const productItem = document.createElement("div");
                productItem.id = e.id;
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
            <div class="quantity-control">
                <input type="number" min="1" value="1" class="quantity-input">
            </div>
            `;
                productList.append(productItem);
                updateItemsCounter();
            }
            setTimeout(() => closeSearch(), 10);
        } catch (e) {
            console.error(e);
        }
    }

    function updateItemsCounter() {
        const itemsCount = document.querySelectorAll('.product-item').length;
        itemsCounter.textContent = String(itemsCount);

        if (actionFooter.style.display === 'none' && itemsCount > 0)
            showFooter();
        else if (actionFooter.style.display === 'flex' && itemsCount === 0)
            closeFooter();
    }

    function showFooter() {
        actionFooter.style.display = 'flex';
    }

    function closeFooter() {
        actionFooter.style.display = 'none';
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

