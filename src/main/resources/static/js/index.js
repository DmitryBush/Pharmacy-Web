import RestClient from "./RestClient.js";

document.addEventListener('DOMContentLoaded', () => {
    const restClient = new RestClient();

    const dailyProductsSection = document.getElementById('daily-product-section');
    const bestSellerSection = document.getElementById('bestseller-section');

    initialize();

    async function initialize() {
        loadDailyProducts();
        loadBestSellerProducts();
    }

    async function loadDailyProducts() {
        const dailyProducts = await (await restClient.fetchData('/api/v1/products/best-sellers',
            'GET', {})).json();

        const dailyProductContainer = dailyProductsSection.querySelector('.product-container');
        dailyProducts.forEach((dailyProduct) => {
            const productCard = createProductCard(dailyProduct);
            dailyProductContainer.append(productCard);
        });
    }

    async function loadBestSellerProducts() {
        const bestSellerProducts = await (await restClient.fetchData('/api/v1/products/daily-products',
            'GET', {})).json();

        const bestSellerProductContainer = bestSellerSection.querySelector('.product-container');
        bestSellerProducts.forEach((bestSeller) => {
            const productCard = createProductCard(bestSeller);
            bestSellerProductContainer.append(productCard);
        });
    }

    function createProductCard(product) {
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');

        const imageLink = document.createElement('a');
        imageLink.href = `/catalog/${product.id}`;
        imageLink.innerHTML = `<img src="/api/v1/product-image/${product.imagePaths[0].id}"
                                        width="200px"
                                        alt="${product.name}">`;

        const nameLinkContainer = document.createElement('div');
        const nameLink = document.createElement('a');
        nameLink.href = `/catalog/${product.id}`;
        nameLink.textContent = product.name;

        nameLinkContainer.appendChild(nameLink);

        const actionContainer = document.createElement('div');

        const price = document.createElement('p');
        price.textContent = `${product.price} ₽`;
        const buyButton = document.createElement('button');
        buyButton.textContent = 'Купить';

        actionContainer.appendChild(price);
        actionContainer.appendChild(buyButton);

        productCard.append(imageLink);
        productCard.append(nameLinkContainer);
        productCard.append(actionContainer);

        return productCard;
    }
})