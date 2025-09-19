import RestClient from "./RestClient.js";

document.addEventListener('DOMContentLoaded', () => {
    const restClient = new RestClient();

    const dailyProductsSection = document.getElementById('daily-product-section');

    initialize();

    async function initialize() {
        loadDailyProducts();
    }

    async function loadDailyProducts() {
        const dailyProducts = await (await restClient.fetchData('/api/v1/products/daily-products',
            'GET', {})).json();

        const dailyProductContainer = dailyProductsSection.querySelector('.product-container');
        dailyProducts.forEach((dailyProduct) => {
            const productCard = document.createElement('div');
            productCard.classList.add('product-card');

            const imageLink = document.createElement('a');
            imageLink.href = `/catalog/${dailyProduct.id}`;
            imageLink.innerHTML = `<img src="/api/v1/product-image/${dailyProduct.imagePaths[0].id}"
                                        width="200px"
                                        alt="${dailyProduct.name}">`;

            const nameLinkContainer = document.createElement('div');
            const nameLink = document.createElement('a');
            nameLink.href = `/catalog/${dailyProduct.id}`;
            nameLink.textContent = dailyProduct.name;

            nameLinkContainer.appendChild(nameLink);

            const actionContainer = document.createElement('div');

            const price = document.createElement('p');
            price.textContent = `${dailyProduct.price} ₽`;
            const buyButton = document.createElement('button');
            buyButton.textContent = 'Купить';

            actionContainer.appendChild(price);
            actionContainer.appendChild(buyButton);

            productCard.append(imageLink);
            productCard.append(nameLinkContainer);
            productCard.append(actionContainer);

            dailyProductContainer.append(productCard);
        });
    }
})