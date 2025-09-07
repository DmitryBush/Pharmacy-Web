import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener('DOMContentLoaded', () => {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/file-earmark-medical-fill.png');

    let DEBOUNCE_DELAY = 300;

    const searchContainer = document.getElementById("search-container");
    const searchField = document.getElementById("search-field");
    const resultsContainer = document.getElementById("search-result-container");
    const searchBackdrop = document.getElementById('search-backdrop');

    let searchOutsideClick = null;

    document.getElementById('createBtn').addEventListener('click',
        () => window.location.replace('/admin/product/creation'));

    document.querySelectorAll('.btn-update').forEach((item) => {
        item.addEventListener('click', e => {
                try {
                    const productId = e.currentTarget.dataset.id;
                    testProductId(productId);

                    window.location.replace(`/admin/product/${encodeURIComponent(productId)}`);
                } catch (e) {
                    console.error(e);
                    notification.showNotification('Управление заказами', e.message);
                }
            }
        );
    });

    document.querySelectorAll('.btn-delete').forEach(button => {
        button.addEventListener('click', e => {
            e.preventDefault();
            const productId = e.currentTarget.dataset.id;
            testProductId(productId);

            deleteProduct(productId);
        });
    });

    function testProductId(productId) {
        if (!(/^\d+$/.test(productId)))
            throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                'Обратитесь к вашему администратору');
    }

    function deleteProduct(productId) {
        if (!confirm('Удалить продукт?')) return;

        try {
            if (!/^\d+$/.test(productId))
                throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                    'Обратитесь к вашему администратору');
        } catch (e) {
            console.error(e);
            notification.showNotification('Управление заказами', e.message);
        }

        restClient.fetchData(`/api/v1/admin/products/${encodeURIComponent(productId)}`, 'DELETE')
            .then(() => {
                notification.showNotification('Управление товарами',
                    'Удаление товара завершено успешно');
                setTimeout(() => window.location.replace('/admin/product'), 1000);
            })
            .catch(e => notification.showNotification('Управление товарами',
                `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`));
    }

    document.querySelectorAll('.search-btn,  .search-modal-btn').forEach(button => {
        button.addEventListener('click', () => handleOpenSearch());
    });

    document.getElementById('search-field').addEventListener('input', handleSearchInput);

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
            return;
        }

        DEBOUNCE_DELAY = setTimeout(() => {
            fetchResults(searchTerm)
                .catch(error =>
                    notification.showNotification('Управление продуктами',
                        `Произошла ошибка при поиске продукта: ${error}`));
        }, DEBOUNCE_DELAY);
    }

    async function fetchResults(searchTerm) {
        try {
            const data = await
                (await restClient.fetchData(`/api/v1/search/medicine?searchTerm=${encodeURIComponent(searchTerm)}`,
                    'GET')).json();
            displayResults(data);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    function displayResults(results) {
        resultsContainer.innerHTML = '';
        if (results.length === 0) {
            return;
        }

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = result.name;

            if (!/^\d+$/.test(result.id))
                throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                    'Обратитесь к вашему администратору');
            div.onclick = () => window.location.replace(`/admin/product/${encodeURIComponent(result.id)}`);

            resultsContainer.appendChild(div);
        });
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
});
