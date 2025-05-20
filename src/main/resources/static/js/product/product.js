document.addEventListener('DOMContentLoaded', () => {
    const id = window.location.pathname.split('/').pop();
    fetch(`/api/v1/branches/products/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Произошла сетевая ошибка. Попробуйте перезагрузить страницу');
            return response.json();
        })
        .then(stores => renderStores(stores, id))
        .catch(error => showError(error));
});

async function renderStores(stores, id) {
    const container = document.getElementById('availability-stores-container');
    container.innerHTML = '';
    console.log(stores);

    if (stores.length === 0) {
        const messageElement = document.createElement('div');
        messageElement.innerHTML = `<h4>Товара нет в наличии</h4>`;
        container.appendChild(messageElement);
    }
    else {
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

function showError(error) {
    console.log(error.message);
    const container = document.getElementById('availability-stores-container');
    container.innerHTML = `<div class="error">Ошибка загрузки данных: ${error.message}</div>`;
}

const carousel = document.querySelector(".carousel-container");
const images = document.querySelectorAll(".product-image");

let imageIndex = 0;

function slideImage(index) {
    if (index >= images.length)
        index = 0;
    else if (index < 0)
        index = images.length - 1;
    carousel.style.transform = `translateX(-${index * 100}%)`;
    imageIndex = index;
}

document.querySelector('.prev').addEventListener('click', () => slideImage(imageIndex - 1));
document.querySelector('.next').addEventListener('click', () => slideImage(imageIndex + 1));