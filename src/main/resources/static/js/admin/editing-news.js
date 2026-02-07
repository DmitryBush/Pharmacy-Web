import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", () => {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/pencil-fill.png');

    const newsId = document.getElementById('news-id').dataset.id;

    const newImagesContainer = document.querySelector('#imagePreview');
    let newImageFiles = [];

    const newsTypeSelector = document.getElementById("news-type");

    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const deleteBtn = document.getElementById('delete-btn');

    saveBtn.addEventListener('click', saveNewsForm)
    cancelBtn.addEventListener('click', () => window.location.replace('/admin/news'));
    deleteBtn.addEventListener('click', deleteNews);

    document.getElementById('imageInput').addEventListener('change', (e) => {
        const files = Array.from(e.target.files);
        newImageFiles = [...newImageFiles, ...files];
        files.forEach(file => {
            const reader = new FileReader();
            reader.onload = (event) => {
                const imgContainer = document.createElement('div');
                imgContainer.className = 'image-item new-image';
                imgContainer.innerHTML = `
                    <img src="${event.target.result}" width="350px">
                    <button class="delete-new-image-btn" onclick="removeNewImage(this)">×</button>
                `;
                newImagesContainer.appendChild(imgContainer);
            };
            reader.readAsDataURL(file);
        });
    });

    initialize();

    async function initialize() {
        await getNewsTypes();
        if (newsId !== null && newsId !== undefined) {
            deleteBtn.hidden = false;
            fillFormData();
        }
    }

    async function getNewsTypes() {
        const typeList = await restClient.fetchData(`/api/v1/news/types`, 'GET', {});
        const data = await typeList.json();

        data.forEach(item => {
            const option = new Option(item.typeName, item.id);
            option.classList.add('pop-up-btn');
            newsTypeSelector.add(option);
        });
    }

    async function fillFormData() {
        const response = await restClient.fetchData(`/api/v1/news/${newsId}`, 'GET', {});
        const dataResponse = await response.json();

        document.getElementById('title').value = dataResponse.title;
        document.getElementById('slug').value = dataResponse.slug;
        document.getElementById('body').value = dataResponse.body;
        newsTypeSelector.selectedIndex = dataResponse.type - 1;
        fillImages(newsId, dataResponse.imageDtoList);
    }

    function fillImages(newsId, images) {
        images.forEach(image => {
            const imageItem = document.createElement('div');
            imageItem.classList.add('image-item');
            imageItem.innerHTML = `
                <img src='/api/v1/news-images/${image.id}'
                                 width="350px">
                <button class="delete-image-btn" data-id="${image.id}">×</button>
            `;
            imageItem.querySelector('.delete-image-btn')
                .addEventListener('click', (e) => {
                    e.preventDefault();
                    restClient.fetchData(`/api/v1/news-images/${image.id}`, 'DELETE', {})
                        .then(() => {
                            notification.showNotification('Управление новостями',
                                'Изображение новости успешно удалено');
                            imageItem.remove();
                        })
                        .catch((e) => notification.showNotification('Управление товарами',
                            `Во время удаления изображения произошла ошибка. Ошибка: ${e.message}`));
                });
            newImagesContainer.appendChild(imageItem);
        })
    }

    async function saveNewsForm() {
        if (newsId === null || newsId === undefined) {
            try {
                await restClient.fetchData(`/api/v1/news`, 'POST',
                    {'Content-Type': 'application/json'}, getJsonFormData());
                if (newImageFiles.length > 0) {
                    await restClient.fetchData(
                        `/api/v1/news-images/upload/${document.getElementById('slug').value.trim()}`,
                        'POST', {}, fillNewsImages());
                }
                notification.showNotification('Управление новостями', 'Новость успешно создана');
                setTimeout(() => window.location.replace('/admin/news'), 1000);
            } catch (e) {
                console.error(e);
                notification.showNotification('Управление новостями',
                    `Во время создании новости произошла ошибка. Ошибка: ${e.message}`)
            }
        } else {
            try {
                if (newImageFiles.length > 0) {
                    await Promise.all([
                        restClient.fetchData(`/api/v1/news/${newsId}`, 'PATCH',
                            {'Content-Type': 'application/json'}, getJsonFormData()),
                        restClient.fetchData(`/api/v1/news-images/upload/${newsId}`, 'POST', {},
                            fillNewsImages())
                    ]);
                } else {
                    await restClient.fetchData(`/api/v1/news/${newsId}`, 'PATCH',
                        {'Content-Type': 'application/json'}, getJsonFormData());
                }
                notification.showNotification('Управление новостями', 'Новость успешно обновлена');
                setTimeout(() => window.location.replace('/admin/news'), 1000);
            } catch (e) {
                console.error(e);
                notification.showNotification('Управление новостями',
                    `Во время обновлении новости произошла ошибка. Ошибка: ${e.message}`);
            }
        }
    }

    function getJsonFormData() {
        const form = {
            title: document.getElementById('title').value.trim(),
            slug: document.getElementById('slug').value.trim(),
            type: newsTypeSelector.value,
            body: document.getElementById('body').value.trim()
        };
        return JSON.stringify(form);
    }

    function fillNewsImages() {
        const formData = new FormData();
        newImageFiles.forEach(file => formData.append('images', file));
        return formData
    }

    function deleteNews() {
        restClient.fetchData(`/api/v1/news/${newsId}`, 'DELETE', {}, getJsonFormData())
            .then(() => {
                notification.showNotification('Управление новостями',
                    'Новость успешно удалена');
                setTimeout(() => window.location.replace('/admin/news'), 1000);
            })
            .catch((e) => notification.showNotification('Управление товарами',
                `Во время удаления новости произошла ошибка. Ошибка: ${e.message}`));
    }
});