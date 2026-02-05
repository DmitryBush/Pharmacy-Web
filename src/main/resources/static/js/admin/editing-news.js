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
        getNewsTypes();
        if (newsId !== null && newsId !== undefined) {
            deleteBtn.classList.remove('disabled');
            fillFormData();
        }
    }

    async function getNewsTypes() {
        const typeList = await restClient.fetchData(`/api/v1/news/types`, 'GET', {});
        const data = await typeList.json();

        data.forEach(item => {
            const option = new Option(item, item);
            option.classList.add('pop-up-btn');
            newsTypeSelector.add(option);
        });
    }

    async function fillFormData(newsId) {
        const response = restClient.fetchData(`/api/v1/news/${newsId}`, 'GET', {});
        const dataResponse = await response.json();

        document.getElementById('title').value = dataResponse.title;
        document.getElementById('slug').value = dataResponse.slug;
        document.getElementById('body').value = dataResponse.body;
        fillImages(newsId, dataResponse.imageDtoList);
    }

    function fillImages(newsId, images) {
        images.forEach(image => {
            const imageItem = document.createElement('div');
            imageItem.classList.add('image-item');
            imageItem.innerHTML = `
                <img src='/api/v1/news-images/${newsId}/${image.id}'
                                 width="350px">
                <button class="delete-image-btn" data-id="${image.id}">×</button>
            `;
            newImagesContainer.appendChild(imageItem);
        })
    }

    async function saveNewsForm() {
        if (newsId === null || newsId === undefined) {
            restClient.fetchData('/api/v1/news', 'POST', {}, getFormData())
                .then(() => {
                    notification.showNotification('Управление новостями',
                        'Новость успешно создана');
                    setTimeout(() => window.location.replace('/admin/news'), 1000);
                })
                .catch(e => notification.showNotification('Управление новостями',
                    `Во время создании новости произошла ошибка. Ошибка: ${e.message}`));
        } else {
            restClient.fetchData('/api/v1/news', 'PUT', {}, getFormData())
                .then(() => {
                    notification.showNotification('Управление новостями',
                        'Новость успешно обновлена');
                    setTimeout(() => window.location.replace('/admin/news'), 1000);
                })
                .catch(e => notification.showNotification('Управление новостями',
                    `Во время обновлении новости произошла ошибка. Ошибка: ${e.message}`));
        }
    }

    function getFormData() {
        const formData = new FormData();

        formData.append('title', document.getElementById('title').value.trim());
        formData.append('slug', document.getElementById('slug').value.trim());
        formData.append('type', newsTypeSelector.value);
        formData.append('body', document.getElementById('body').value.trim());
        fillNewsImages(formData);

        return formData;
    }

    function fillNewsImages(formData) {
        if (newImageFiles.length > 0) {
            newImageFiles.forEach(file => formData.append('images', file));
        }
    }

    function deleteNews() {
        restClient.fetchData(`/api/v1/news/${newsId}`, 'DELETE', {}, getFormData())
            .then(() => {
                notification.showNotification('Управление новостями',
                    'Новость успешно удалена');
                setTimeout(() => window.location.replace('/admin/news'), 1000);
            })
            .catch((e) => notification.showNotification('Управление товарами',
                `Во время удаления новости произошла ошибка. Ошибка: ${e.message}`));
    }
});