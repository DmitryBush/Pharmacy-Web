import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", function() {
    let DEBOUNCE_DELAY = 300;
    const receiptMap = new Map();
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/box-fill.png');

    const productList = document.getElementById("product-list");
    const branchId = document.getElementById("branch-id").dataset.branchid;
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

        restClient.fetchData(`/api/v1/warehouse/inventory-receipts`, 'POST',
            {'Content-Type': 'application/json'}, JSON.stringify({ productList: body, branchId: branchId }))
            .then(r => {
                notification.showNotification('Управление складом',
                    `Оформление товара успешно завершено`);
            })
            .catch(e => notification.showNotification('Управление складом',
                `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`));
    }

    function getReceiptData(item) {
        return {
            medicineId: item.dataset.id,
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
            fetchResults(searchTerm)
                .catch(error =>
                    notification.showNotification('Управление складом',
                        `Произошла ошибка при поиске продукта: ${error}`));
        }, DEBOUNCE_DELAY);
    }

    async function fetchResults(searchTerm) {
        try {
            const data = await
                (await restClient.fetchData(`/api/search/medicine?name=${searchTerm}`, 'GET')).json();
            displayResults(data);
        } catch (error) {
            console.error(error);
            throw error;
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
                    <div class="quantity-container">
                        <button class="quantity-btn decrement">-</button>
                        <input type="number" min="1" value="1" class="quantity-input">
                        <button class="quantity-btn increment">+</button>
                    </div>
                </div>
                <div>
                    <button class="delete-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                          <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
                        </svg>
                    </button>
                </div>
                `;
                productItem.querySelector('.increment').addEventListener('click',
                    (e) => updateQuantityCount(e));
                productItem.querySelector('.decrement').addEventListener('click',
                    (e) => updateQuantityCount(e));

                productItem.querySelector('.delete-btn').addEventListener('click',
                    () => {
                        productItem.remove();
                        receiptMap.delete(e.id);
                        updateItemsCounter();
                    });

                productList.append(productItem);
                updateItemsCounter();
            }
            setTimeout(() => closeSearch(), 10);
        } catch (e) {
            console.error(e);
            closeSearch();
            notification.showNotification('Управление складом', e);
        }
    }

    function updateQuantityCount(e) {
        const quantityInput = e.target.parentElement.querySelector('.quantity-input');
        if (e.target.className.includes('increment')) {
            quantityInput.value = parseInt(quantityInput.value) + 1;
        } else if (parseInt(quantityInput.value) > 1) {
            quantityInput.value = parseInt(quantityInput.value) - 1;
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

