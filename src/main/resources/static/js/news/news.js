import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    const newsSlug = document.getElementById('main-content').dataset.slug;

    const newsContainer = document.getElementById('news-container');
    const loader = new Loader(newsContainer);

    try {
        loader.showLoading()
        await loadNews();
    } catch (err) {
        console.error(err);
    }

    async function loadNews() {
        const news = await (await restClient.fetchData(`/api/v1/news/${newsSlug}`, 'GET', {})).json();
        loader.hideLoading();
        createContent(news);
    }

    function createContent(news) {
        const newsHeader = createHeader(news);
        const newsBody = createBodyNews(news);

        newsContainer.appendChild(newsHeader);
        newsContainer.appendChild(newsBody);
    }

    function createPreTitle(news) {
        const newsPreTitle = document.createElement('div');
        newsPreTitle.classList.add('news-pre-title');
        const newsType = document.createElement('span');
        newsType.textContent = news.type.typeName;
        const newsDate = document.createElement('span');
        newsDate.classList.add('news-date');
        newsDate.textContent = formatDate(news.creationTime);

        newsPreTitle.appendChild(newsType);
        newsPreTitle.appendChild(newsDate);
        return newsPreTitle;
    }

    function createHeader(news) {
        const newsHeader = document.createElement('div');
        newsHeader.classList.add('news-header');
        const newsTitle = document.createElement('h1');
        newsTitle.classList.add('news-title');
        newsTitle.textContent = news.title;
        const newsImage = document.createElement('img');
        newsImage.src = `/api/v1/news-images/${news.imageDtoList[0].id}`;
        newsImage.alt = news.title;

        newsHeader.appendChild(createPreTitle(news));
        newsHeader.appendChild(newsTitle);
        newsHeader.appendChild(newsImage);
        return newsHeader;
    }

    function createBodyNews(news) {
        const newsBody = document.createElement('div');
        newsBody.classList.add('news-body');
        const newsText = document.createElement('p');
        newsText.textContent = news.body;

        newsBody.appendChild(newsText);
        return newsBody;
    }

    function formatDate(date) {
        const dateObject = new Date(date);
        return Intl.DateTimeFormat('ru-Ru', {
            day: 'numeric',
            month: 'long',
            year: 'numeric'
        }).format(dateObject);
    }
});