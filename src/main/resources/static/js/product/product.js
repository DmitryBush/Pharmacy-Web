import RestClient from "../RestClient.js";
import {getCartProductsSet} from "../cart/cart-utils.js";
import {withAuth} from "../login/auth.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    let cartItemsSet = new Set();
    const buyButton = document.getElementById("buy-button");

    const id = parseInt(window.location.pathname.split('/').pop());
    fetch(`/api/v1/branches/products/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Произошла сетевая ошибка. Попробуйте перезагрузить страницу');
            return response.json();
        })
        .then(stores => renderStores(stores, id))
        .catch(error => showError(error));

    try {
        cartItemsSet = await getCartProductsSet();
        cartItemsSet.has(id)
            ? renderButtonAsInCart(buyButton)
            : renderButtonAsNotInCart(buyButton);
        buyButton.addEventListener('click', async () => markAsInCart());
    } catch (e) {
        console.error(e);
    }

    async function markAsInCart() {
        await restClient.fetchData(`/api/v1/carts/me/items`, 'PATCH',
            {'Content-Type': 'application/json'},
            JSON.stringify({
                item: {
                    productId: id,
                    quantity: 1
                }
            }));
        cartItemsSet.add(id);
        renderButtonAsInCart(buyButton);
    }

    function renderButtonAsInCart(buyButton) {
        buyButton.textContent = 'В корзине';
        buyButton.classList.add('in-cart');
        buyButton.addEventListener('click', withAuth(() => markAsNotInCart(buyButton)));
    }

    async function markAsNotInCart(buyButton) {
        await restClient.fetchData(`/api/v1/carts/me/items/${id}`, 'DELETE');
        cartItemsSet.delete(id);
        renderButtonAsNotInCart(buyButton);
    }

    function renderButtonAsNotInCart(buyButton) {
        buyButton.textContent = 'В корзину';
        buyButton.classList.remove('in-cart');
        buyButton.addEventListener('click', withAuth(() => markAsInCart(buyButton)));
    }

    const carousel = document.querySelector(".carousel-container");
    const images = document.querySelectorAll(".product-image");

    let imageIndex = 0;

    document.getElementById('prev-image-btn')
        .addEventListener('click', () => slideImage(imageIndex - 1));
    document.getElementById('next-image-btn')
        .addEventListener('click', () => slideImage(imageIndex + 1));

    function slideImage(index) {
        if (index >= images.length)
            index = 0;
        else if (index < 0)
            index = images.length - 1;
        carousel.style.transform = `translateX(-${index * 100}%)`;
        imageIndex = index;
    }

    function showError(error) {
        const container = document.getElementById('availability-stores-container');
        container.innerHTML = `<div class="error">Ошибка загрузки данных: ${error.message}</div>`;
    }

    async function renderStores(stores, id) {
        const container = document.getElementById('availability-stores-container');
        container.innerHTML = '';

        if (stores.length === 0) {
            const messageElement = document.createElement('div');
            messageElement.innerHTML = `<h4>Товара нет в наличии</h4>`;
            container.appendChild(messageElement);
        } else {
            for (const branch of stores) {
                const storeElement = document.createElement('li');
                storeElement.className = 'store-card';

                const response = await fetch(`/api/v1/warehouse/branches/${branch.id}/products/${id}/quantity`);
                const amount = await response.json();
                storeElement.innerHTML = `
            <h4>Аптека</h4>
            <span>${branch.address.settlement}, ${branch.address.street}, ${branch.address.house}</span>
            <span>Наличие: ${renderAvailability(amount)}</span>
            `;
                container.appendChild(storeElement);
            }
        }
    }

    function renderAvailability(amount) {
        if (amount > 100)
            return "Много";
        else if (amount > 30)
            return "Достаточно";
        else if (amount > 10)
            return "Мало";
        else if (amount <= 10)
            return `Осталось ${amount} штук`;
    }
});