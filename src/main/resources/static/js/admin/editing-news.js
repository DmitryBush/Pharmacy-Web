import RestClient from "../RestClient.js";

document.addEventListener("DOMContentLoaded", () => {
    const restClient = new RestClient();

    const newsTypeSelector = document.getElementById("news-type");

    initialize();

    function initialize() {
        getNewsTypes();
    }

    async function getNewsTypes() {
        const typeList = await restClient.fetchData(`/api/v1/news/types`, 'GET', {});
        const data = await typeList.json();

        data.forEach(item => {
            const option = new Option(item, item);
            newsTypeSelector.add(option);
        });
    }
});