import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import {formatPhone} from "../formatter/formatter.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    const linkedUsersSet = new Set();
    const userTableBody = document.getElementById('users-table-body');

    let DEBOUNCE_DELAY = 300;

    const searchContainer = document.getElementById("search-container");
    const searchField = document.getElementById("search-field");
    const resultsContainer = document.getElementById("search-result-container");
    const searchBackdrop = document.getElementById('search-backdrop');

    let searchOutsideClick = null;

    document.getElementById("search-field").addEventListener('input', handleSearchInput);
    document.getElementById("add-user-btn")
        .addEventListener("click", async () => handleOpenSearch());

    try {
        await initializeTable();
    } catch (e) {
        console.error(e);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${e}`);
    }

    async function initializeTable() {
        const tableBodyContent = await (await restClient.fetchData(`/api/v1/admin/branches/1/users`, 'GET')).json();
        tableBodyContent.forEach(element => renderTableRow(element));
    }

    function renderTableRow(user) {
        const tableRow = document.createElement('tr');
        linkedUsersSet.add(user.mobilePhone);
        tableRow.appendChild(renderName(user));
        tableRow.appendChild(renderMobilePhone(user));
        tableRow.appendChild(renderRole(user));
        const actionBtn = renderActionBtn(user);
        tableRow.appendChild(actionBtn);

        actionBtn.addEventListener('click', async e => {
            e.preventDefault();
            try {
                await restClient.fetchData(``, 'DELETE');
                notification.showNotification('Управление филиалами',
                    `Сотрудник ${user.surname} ${user.name} успешно откреплен от филиала`);
                document.removeChild(tableRow);
                linkedUsersSet.delete(user.mobilePhone);
            } catch (e) {
                console.error(e);
                notification.showNotification('Управление филиалами',
                    `Произошла ошибка при откреплении сотрудника. Код: ${e}`);
            }
        });
        userTableBody.appendChild(tableRow);
    }

    function renderActionBtn() {
        const button = document.createElement("button");
        button.classList.value = 'action-btn agree-btn';
        button.textContent = 'Открепить';
        return button;
    }

    function renderRole(user) {
        return renderTableData(user.role);
    }

    function renderMobilePhone(user) {
        return renderTableData(user.mobilePhone);
    }

    function renderName(user) {
        return renderTableData(`${user.surname} ${user.name} ${user.lastName}`);
    }

    function renderTableData(content) {
        const tableData = document.createElement('td');
        tableData.textContent = content;
        return tableData;
    }

    function handleOpenSearch() {
        searchContainer.style.display = 'block';
        searchBackdrop.style.display = 'block';

        searchOutsideClick = (event) => {
            if (searchContainer && !searchContainer.contains(event.target) && !resultsContainer.contains(event.target)) {
                closeSearch();
                document.removeEventListener('click', searchOutsideClick);
                searchOutsideClick = null;
            }
        };
        setTimeout(() => document.addEventListener('click', searchOutsideClick), 10);
    }

    function handleSearchInput(e) {
        clearTimeout(DEBOUNCE_DELAY);
        formatPhone(e.target);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 2) {
            resultsContainer.innerHTML = '';
            return;
        }

        DEBOUNCE_DELAY = setTimeout(() => {
            fetchResults(searchTerm)
                .catch(error =>
                    notification.showNotification('Управление филиалами',
                        `Произошла ошибка при поиске сотрудника: ${error}`));
        }, DEBOUNCE_DELAY);
    }

    async function fetchResults(searchTerm) {
        try {
            const params = new URLSearchParams();
            params.set('mobilePhone', searchTerm);
            params.set('role', 'OPERATOR,ADMIN');
            const data = await
                (await restClient.fetchData(`/api/v1/search/employees/filter?${params.toString()}`,
                    'GET')).json();
            displayResults(data);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    function displayResults(results) {
        resultsContainer.innerHTML = '';
        if (results.length === 0) {
            return;
        }

        results.content.forEach(result => {
            if (!linkedUsersSet.has(result.mobilePhone)) {
                const div = document.createElement('div');
                div.className = 'result-item';
                div.textContent = `${result.surname} ${result.name} ${result.lastName} - ${result.mobilePhone}`;

                if (!/^(\+7|8)\s\(\d{3}\)\s\d{3}-\d{2}-\d{2}$/.test(result.mobilePhone)) {
                    throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                        'Обратитесь к вашему администратору');
                }
                div.onclick = () => linkUserToBranch(result.mobilePhone);

                resultsContainer.appendChild(div);
            }
        });
    }

    function linkUserToBranch(mobilePhone) {
        restClient.fetchData(`/api/v1/admin/branches/1/users`, 'PATCH',
            {'Content-Type': 'text/plain'}, mobilePhone)
            .then(() => {
                notification.showNotification('Управление филиалами',
                    'Сотрудник прикреплен к филиалу успешно');
                closeSearch();
            })
            .catch(error => {
                console.error(error);
                notification.showNotification('Управление филиалами',
                    'Произошла ошибка при прикреплении сотрудника к филиалу');
            });
    }

    function closeSearch() {
        searchContainer.style.display = 'none';
        searchBackdrop.style.display = 'none';

        searchField.value = '';
        resultsContainer.innerHTML = '';

        if (searchOutsideClick !== null) {
            document.removeEventListener('click', searchOutsideClick);
            searchOutsideClick = null;
        }
    }
});