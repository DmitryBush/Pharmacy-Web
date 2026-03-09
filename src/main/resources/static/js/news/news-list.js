import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();

    const newsContainer = document.getElementById("news-container");
    const loader = new Loader(document.getElementById('news-section'));
    const paginationManager = new PaginationManager(document.getElementById('news-section'), initialize);

    try {
        await initialize();
    } catch (error) {
        console.error(error);
    }

    async function initialize(){
        loader.showLoading();
        await loadNewsList();
    }

    async function loadNewsList() {
        newsContainer.innerHTML = "";
        const newsObject = await (
            await restClient.fetchData(`/api/v1/news?size=${paginationManager.pageSize}&page=${paginationManager.currentPage}`,
                'GET', {})).json();
        paginationManager.initializePagination(newsObject.page.number, newsObject.page.size, newsObject.page.totalPages);
        loader.hideLoading();
        if (newsObject._embedded === undefined) {
            const newsMessage = document.createElement('p');
            newsMessage.textContent = 'Нет доступных новостей';
            newsContainer.appendChild(newsMessage);
            return;
        }

        const newsList = newsObject._embedded.newsReadDtoList;
        if (newsList.length === 0) {
            const newsMessage = document.createElement('p');
            newsMessage.textContent = 'Нет доступных новостей';
            return;
        }
        newsList.forEach((news) => {
            createNewsElement(news);
        });
    }

    function createNewsElement(news) {
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