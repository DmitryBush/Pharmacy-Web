document.addEventListener('DOMContentLoaded', () => {
    const id = window.location.pathname.split('/').pop();
    fetch(`/api/v1/catalog/${id}`)
        .then(response => {
            if (!response.ok) throw new Error('Произошла сетевая ошибка. Попробуйте перезагрузить страницу');
            return response.json();
        })
        .then(stores => renderStores(stores))
        .catch(error => showError(error));
});

function renderStores(stores) {
    const container = document.getElementById('availability-stores-container');
    container.innerHTML = '';

    if (stores.length === 0) {
        const messageElement = document.createElement('div');
        messageElement.innerHTML = `<h4>Товара нет в наличии</h4>`;
        container.appendChild(messageElement);
        return;
    }
    else {
    stores.forEach(store => {
        const storeElement = document.createElement('li');
        storeElement.className = 'store-card';
        const id = window.location.pathname.split('/').pop();

        store.items.forEach(item => {
            if (item.medicine.id == id) {
                storeElement.innerHTML = `
                <h4>Аптека</h4>
                <p>Адрес: ${store.address.subject}, ${store.address.settlement}, ${store.address.street}, ${store.address.house}</p>
                <span>Наличие: ${renderAvailability(item.amount)}</span>
                `;
            }
        })
        
        container.appendChild(storeElement);
    })};
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