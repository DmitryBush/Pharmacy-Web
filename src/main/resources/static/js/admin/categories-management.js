import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import PopupManager from "../popup/popup.js";

document.addEventListener("DOMContentLoaded", () => {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/folder-fill.png');

    const CHARACTER_REM_SIZE = 2.5;

    const categoriesList = document.getElementById('categories-list');
    const loader = document.querySelector('.loader');
    const backButton = document.getElementById('back-btn');
    let backButtonPressed = false;

    const addButton = document.getElementById('category-add-btn');

    const changeParentButton = document.getElementById('change-parent-btn');
    const moveCategoryList = [];

    const titleContainer = document.getElementById('title-container');
    const titleStack = ['Управление категориями'];

    backButton.addEventListener('click', (e) => {
        e.preventDefault();

        backButtonPressed = true;
        if (titleStack.length === 2) {
            titleStack.pop();
            changeCategory(null)
                .catch((e) => {
                    console.error(e);
                    notification.showNotification('Управление категориями',
                        'При переходе на предыдущую страницу, возникла ошибка');
                });
            backButton.querySelector('svg').classList.remove('active-btn');
        } else if (titleStack.length > 2) {
            changeCategory(titleStack.pop())
                .catch((e) => {
                    console.error(e);
                    notification.showNotification('Управление категориями',
                        'При переходе на предыдущую страницу, возникла ошибка');
                });
        }
        backButtonPressed = false;
    });

    addButton.addEventListener('click', (e) => {
        e.preventDefault();
        const popupContent = `<label for="categoryInput">
                                        Название категории
                                        <input type="text" id="categoryInput" placeholder="Введите название">
                                    </label>`;
        new PopupManager()
            .setTarget(e.target)
            .setPopupContent(popupContent)
            .setSubmitAction(() => {
                const categoryName = document.querySelector('#categoryInput').value;
                const parentCategory = titleStack.length > 1
                    ? titleStack[titleStack.length - 1]
                    : null;

                restClient.fetchData(`/api/v1/categories`, 'POST',
                    {'Content-Type': 'application/json'}, JSON.stringify({
                        name: categoryName,
                        parent: parentCategory
                    })).then(() => {
                        notification.showNotification('Управление категориями',
                            'Создание категории успешно завершено');
                        updateList();
                    }).catch((err) => {
                        console.error(err);
                        notification.showNotification('Управление категориями',
                            'Во время создания категории произошла ошибка');
                    });
            })
            .createPopup();
    });

    function updateList() {
        titleStack.length > 1
            ? createListCategories(titleStack[titleStack.length - 1])
            : createListCategories(null);
    }

    changeParentButton.addEventListener('click', (e) => {
        e.preventDefault();

        if (titleStack.length > 1) {
            restClient.fetchData('', 'PATCH', {}, {
                typeNames: moveCategoryList,
                parent: titleStack[titleStack.length - 1]
            })
                .then(() => {
                    changeParentButton.style.display = 'none';
                    moveCategoryList.splice(0, moveCategoryList.length);
                })
                .catch((err) => {
                    console.error(err);
                    notification.showNotification('Управление категориями',
                        'При перемещении категории возникла ошибка');
                });
        } else {
            restClient.fetchData('', 'PATCH', {}, {
                typeNames: moveCategoryList,
                parent: null
            })
                .then(() => {
                    changeParentButton.style.display = 'none';
                    moveCategoryList.splice(0, moveCategoryList.length);
                })
                .catch((err) => {
                    console.error(err);
                    notification.showNotification('Управление категориями',
                        'При перемещении категории возникла ошибка');
                });
        }
    });

    initialize()
        .catch(e => {
            console.error(e);
            notification.showNotification('Управление категориями',
                'Во время загрузки данных произошла ошибка');
        });

    async function initialize() {
        await changeCategory(null);
    }

    async function createListCategories(parent) {
        categoriesList.innerHTML = '';
        const data = await (await restClient.fetchData(resolveUrl(parent), 'GET'))
            .json();
        loader.style.display = 'none';

        if (data.length > 0) {
            data.forEach((category) => createCategoryElement(category));
        } else {
            categoriesList.innerHTML = `
                <h2 class="missing-types">В данном подтипе отсутствуют типы товаров</h2>
            `;
        }
    }

    function resolveUrl(parent) {
        if (parent)
            return `/api/v1/categories?parent=${parent}`;
        return `/api/v1/categories`;
    }

    function createCategoryElement(category) {
        const categoryElement = document.createElement('div');
        categoryElement.classList.add('category-item');
        categoryElement.innerHTML = `
                <div>
                    <span>${category.name}</span>
                </div>
                <div class="item-actions">
                    <button class="edit-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
                            <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"/>
                        </svg>
                        Редактировать
                    </button>
                    <button class="move-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-folder-fill" viewBox="0 0 16 16">
                            <path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a2 2 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3m-8.322.12q.322-.119.684-.12h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981z"/>
                        </svg>
                        Переместить
                    </button>
                    <button class="delete-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash3" viewBox="0 0 16 16">
                          <path d="M6.5 1h3a.5.5 0 0 1 .5.5v1H6v-1a.5.5 0 0 1 .5-.5M11 2.5v-1A1.5 1.5 0 0 0 9.5 0h-3A1.5 1.5 0 0 0 5 1.5v1H1.5a.5.5 0 0 0 0 1h.538l.853 10.66A2 2 0 0 0 4.885 16h6.23a2 2 0 0 0 1.994-1.84l.853-10.66h.538a.5.5 0 0 0 0-1zm1.958 1-.846 10.58a1 1 0 0 1-.997.92h-6.23a1 1 0 0 1-.997-.92L3.042 3.5zm-7.487 1a.5.5 0 0 1 .528.47l.5 8.5a.5.5 0 0 1-.998.06L5 5.03a.5.5 0 0 1 .47-.53Zm5.058 0a.5.5 0 0 1 .47.53l-.5 8.5a.5.5 0 1 1-.998-.06l.5-8.5a.5.5 0 0 1 .528-.47M8 4.5a.5.5 0 0 1 .5.5v8.5a.5.5 0 0 1-1 0V5a.5.5 0 0 1 .5-.5"/>
                        </svg>
                        Удалить
                    </button>
                </div>
                <div class="fake-btn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" fill="#3498db" class="bi bi-caret-right" viewBox="0 0 16 16">
                      <path d="M6 12.796V3.204L11.481 8zm.659.753 5.48-4.796a1 1 0 0 0 0-1.506L6.66 2.451C6.011 1.885 5 2.345 5 3.204v9.592a1 1 0 0 0 1.659.753"/>
                    </svg>
                </div>
        `;
        categoryElement.addEventListener('click', () => changeCategory(category.name));

        categoryElement.querySelector('.edit-btn').addEventListener('click', (e) => {
            e.stopPropagation();
            const popupContent = `<div class="popup-title"><h4>Редактировать категорию</h4></div>
                                        <label for="categoryInput">
                                            Название категории
                                            <input type="text" id="categoryInput" placeholder="Введите название">
                                        </label>`;

            new PopupManager()
                .setTarget(e.target)
                .setPopupContent(popupContent)
                .setSubmitAction(() => {
                    const newCategoryName = categoryElement.querySelector('#categoryInput').value;

                    restClient.fetchData('', 'PATCH', {}, JSON.stringify({
                        oldCategoryName: category.name,
                        newCategoryName: newCategoryName
                    }))
                        .then(() => {
                            notification.showNotification('Управление категориями',
                                'Изменение названия категории успешно завершено');
                            updateList();
                        })
                        .catch((error) => {
                            console.error(error);
                            notification.showNotification('Управление категориями',
                                'Во время изменения категории произошла ошибка');
                        });
                })
                .createPopup();
        });
        categoryElement.querySelector('.move-btn').addEventListener('click', (e) => {
            e.stopPropagation();

            if (e.target.classList.contains('disabled-btn')) {
                moveCategoryList.splice(moveCategoryList.findIndex(type => type === category.name), 1);
                e.target.classList.remove('disabled-btn');
                notification.showNotification('Управление категориями',
                    `"${category.name}" удален из списка для перемещения`);
            } else {
                moveCategoryList.push(category.name);
                e.target.classList.add('disabled-btn');
                notification.showNotification('Управление категориями',
                    `"${category.name}" добавлен в список для перемещения`);
            }
        });
        categoryElement.querySelector('.delete-btn').addEventListener('click', (e) => {
            e.stopPropagation();
            new PopupManager()
                .setTarget(e.target)
                .setPopupContent('<span>Вы уверены, что хотите удалить категорию. Изменение необратимо</span>')
                .setSubmitAction(() => {
                    restClient.fetchData('', 'DELETE',
                        {}, JSON.stringify({typeName: category.name}))
                        .then(() => {
                            notification.showNotification('Управление категориями',
                                'Категории успешно удалена');
                            updateList();
                        })
                        .catch((error) => {
                            console.error(error);
                            notification.showNotification('Управление категориями',
                                'Во время удаления категории произошла ошибка');
                        });
                })
                .createPopup();
        });

        categoriesList.appendChild(categoryElement);
    }

    async function changeCategory(category) {
        loader.style.display = 'block';
        if (category !== null && !backButton.querySelector('svg').classList.contains('active-btn')) {
            backButton.querySelector('svg').classList.add('active-btn');
        }
        changeTitle(category);

        await createListCategories(category);
    }

    function changeTitle(category) {
        const titleElement = titleContainer.querySelector('.page-title');
        titleElement.textContent = '';
        if (category !== null && !backButtonPressed)
            titleStack.push(category);

        let tmpTitle = [];
        for (let i = titleStack.length - 1; i >= 0; i--) {
            let currentLength = 0;
            tmpTitle.forEach((type) => currentLength += type.length);

            if (titleStack[i].length * parseToPx(CHARACTER_REM_SIZE)
                + currentLength * parseToPx(CHARACTER_REM_SIZE) > getAvailableTitleWidth() && tmpTitle.length > 0)
                break;
            tmpTitle.push(titleStack[i]);
        }

        tmpTitle = tmpTitle.reverse();
        if (tmpTitle.length < titleStack.length) {
            titleElement.textContent = '... ';
            tmpTitle.forEach((type) =>
                titleElement.textContent = `${titleElement.textContent} > ${type}`);
        } else {
            for (let i = 0; i < tmpTitle.length; i++) {
                if (i === 0)
                    titleElement.textContent = `${tmpTitle[i]}`;
                else
                    titleElement.textContent = `${titleElement.textContent} > ${tmpTitle[i]}`;
            }
        }
    }

    function parseToPx(rem) {
        return 16.0 * parseFloat(rem);
    }

    function getAvailableTitleWidth() {
        const containerWidth = titleContainer.offsetWidth;
        const buttonWidth = backButton.offsetWidth;
        const gap = parseFloat(getComputedStyle(titleContainer).gap);

        return containerWidth - buttonWidth - gap;
    }
});