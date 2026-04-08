import RestClient from "./RestClient.js";
import Loader from "./loader/loader.js";
import ProductRenderer from "./product/product-renderer.js";
import {getCartProductsSet} from "./cart/cart-utils.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    let cartItemsSet = new Set();

    const dailyProductsContainer = document.getElementById('product-container');
    const dailyProductsLoader = new Loader(dailyProductsContainer);

    const newsContainer = document.getElementById('news-container');
    const newsLoader = new Loader(newsContainer);

    try {
        await initialize();
    } catch (error) {
        console.log(error);
    }

    async function initialize() {
        showLoadingAnimation();
        cartItemsSet = getCartProductsSet();
        loadDailyProducts();
        loadNews();
    }

    function showLoadingAnimation() {
        newsLoader.showLoading();
        dailyProductsLoader.showLoading();
    }

    async function loadDailyProducts() {
        const dailyProducts = await (await restClient.fetchData('/api/v1/products/daily-products',
            'GET', {})).json();

        dailyProductsLoader.hideLoading();
        dailyProducts.forEach(dailyProduct => new ProductRenderer(dailyProduct, dailyProductsContainer, cartItemsSet));
    }

    async function loadNews() {
        const newsResponse = await (await restClient.fetchData('/api/v1/news?size=3', 'GET', {})).json();
        newsLoader.hideLoading();
        if (newsResponse._embedded === undefined) {
            const newsMessage = document.createElement('p');
            newsMessage.textContent = 'Нет доступных новостей';
            newsContainer.appendChild(newsMessage);
            return;
        }

        const newsArray = newsResponse._embedded.newsReadDtoList;
        newsArray.forEach((news) => {
            const newsLink = document.createElement('a');
            newsLink.href = `/news/${news.slug}`;

            const newsItem = document.createElement('div');
            newsItem.classList.add('news-item');
            newsLink.append(newsItem);

            const newsImage = document.createElement('div');
            newsImage.classList.add('item-image');

            const image = document.createElement('img');
            if (news.imageDtoList.length > 0) {
                image.src = `/api/v1/news-images/${news.imageDtoList[0].id}`;
                image.alt = news.title;
            } else {
                image.classList.add('.image-unavailable');
            }
            newsImage.append(image);
            newsItem.append(newsImage);

            const newsContent = document.createElement('div');
            newsContent.classList.add('item-content');
            const newsTitle = document.createElement('h4');
            newsTitle.classList.add('item-title');
            newsTitle.textContent = news.title;
            newsContent.append(newsTitle);

            const newsDetails = document.createElement('div');
            newsDetails.classList.add('item-details');
            const newsTypeContainer = document.createElement('div');
            newsTypeContainer.classList.add('item-types');
            const newsTypeName = document.createElement('span');
            newsTypeName.classList.add('item-type');
            newsTypeName.textContent = news.type.typeName;
            newsTypeContainer.appendChild(newsTypeName);
            newsDetails.append(newsTypeContainer);

            const newsDate = document.createElement('span');
            newsDate.classList.add('item-date');
            newsDate.textContent = formatDate(news.creationTime);
            newsDetails.append(newsDate);
            newsContent.append(newsDetails);
            newsItem.append(newsContent);

            const newsArrowContainer = document.createElement('div');
            newsArrowContainer.classList.add('item-arrow');
            newsItem.append(newsArrowContainer);

            newsContainer.append(newsLink);
        });
    }

    function formatDate(date) {
        const dateObject = new Date(date);
        return Intl.DateTimeFormat('ru-Ru', {
            day: 'numeric',
            month: 'long',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        }).format(dateObject);
    }
});