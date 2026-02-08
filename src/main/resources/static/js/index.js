import RestClient from "./RestClient.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    const dailyProductsSection = document.getElementById('daily-product-section');

    try {
        await initialize();
    } catch (error) {
        console.log(error);
    }

    async function initialize() {
        loadDailyProducts();
        loadNews();
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

    async function loadNews() {
        const newsResponse = await (await restClient.fetchData('/api/v1/news', 'GET', {})).json();

        const newsArray = newsResponse._embedded.newsReadDtoList;
        const newsContainer = document.getElementById('news-container');
        if (newsArray.length === 0) {
            const newsMessage = document.createElement('p');
            newsMessage.textContent = 'Нет доступных новостей';
            return;
        }
        newsArray.forEach((news) => {
            const newsLink = document.createElement('a');
            newsLink.href = `/news/${news.slug}`;

            const newsItem = document.createElement('div');
            newsItem.classList.add('news-item');
            newsLink.append(newsItem);

            // const newsLink = document.createElement('a');
            // newsLink.href = `/news/${news.slug}`;
            // newsItem.append(newsLink);

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
            newsTypeName.textContent = news.type;
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
})