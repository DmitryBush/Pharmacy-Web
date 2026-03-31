import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import Loader from "../loader/loader.js";
import {formatPhone} from "../formatter/formatter.js";
import PaginationManager from "../pagination/pagination.js";

document.addEventListener("DOMContentLoaded", async function () {
    const restClient = new RestClient();
    const notification = new Notification();

    const userPhone = document.getElementById('user-phone');
    const roleSelect = document.getElementById('role-filter');

    const tableBody = document.getElementById('users-table-body');
    const loader = new Loader(tableBody);
    const paginationManager = new PaginationManager(tableBody, initialize);

    userPhone.addEventListener('input', event => {
        formatPhone(event.target);
        initialize();
    });

    roleSelect.addEventListener('change', event => {
        initialize();
    });

    try {
        await initialize();
    } catch (error) {
        console.error(error);
        notification.showNotification('Управление ролями',
            `При инициализации списка пользователей возникла ошибка. Код: ${error}`);
    }

    async function initialize() {
        loader.showLoading();
        await fillTableBody();
    }

    async function fillTableBody() {
        tableBody.innerHTML = '';

        const userResponse = await getUserResponse();
        const pageStatistic = userResponse.page;
        paginationManager.initializePagination(pageStatistic.number, pageStatistic.size, pageStatistic.totalPages);

        loader.hideLoading();
        if (pageStatistic.totalPages <= 0) {
            return;
        }
        userResponse._embedded.adminUserReadDtoList.forEach((user) => renderRow(user));
    }

    async function getUserResponse() {
        const response = await restClient.fetchData(`/api/v1/admin/users?${getParams()}`,
            'GET', {});
        return await response.json();
    }

    function getParams() {
        const params = new URLSearchParams();
        params.set('mobilePhone', userPhone.value);
        if (roleSelect.value.length > 0) {
            params.set('role', roleSelect.value);
        } else {
            params.set('role', 'ADMIN,OPERATOR,CUSTOMER');
        }
        params.set('page', paginationManager.currentPage);
        params.set('size', paginationManager.pageSize);
        return params.toString();
    }

    function renderRow(userData) {
        const tr = document.createElement("tr");
        tr.appendChild(renderUserName(userData));
        const role = renderRole(userData);
        tr.appendChild(role);

        const tdActionButton = document.createElement("td");
        const saveButton = renderSaveButton();
        tdActionButton.appendChild(saveButton);
        tr.appendChild(tdActionButton);

        saveButton.addEventListener("click", async function () {
            const selectedRole = role.querySelector("select").value;
            restClient.fetchData(`/api/v1/admin/users/${encodeURIComponent(userData.mobilePhone)}/role`,
                'PATCH', {'Content-Type': 'text/plain'}, selectedRole)
                .then(() => notification.showNotification('Управление ролями',
                    'Обновление роли пользователя проведено успешно'))
                .catch((error) => notification.showNotification('Управление ролями',
                    `Во время обновления роли произошла ошибка. Код: ${error}`));
        });

        tableBody.appendChild(tr);
    }

    function renderUserName(userData) {
        const tdName = document.createElement("td");
        tdName.textContent = `${userData.surname} ${userData.name} ${userData.lastName}`;
        return tdName;
    }

    function renderRole(userData) {
        const tdRole = document.createElement("td");
        const rowRoleSelector = document.createElement('select');
        rowRoleSelector.classList.add('input-group');

        const adminOption = document.createElement("option");
        adminOption.textContent = 'Администратор';
        adminOption.value = 'ADMIN';
        rowRoleSelector.appendChild(adminOption);

        const operatorOption = document.createElement("option");
        operatorOption.textContent = 'Оператор';
        operatorOption.value = 'OPERATOR';
        rowRoleSelector.appendChild(operatorOption);

        const customerOption = document.createElement("option");
        customerOption.textContent = 'Покупатель';
        customerOption.value = 'CUSTOMER';
        rowRoleSelector.appendChild(customerOption);

        rowRoleSelector.value = userData.role;
        tdRole.appendChild(rowRoleSelector);
        return tdRole;
    }

    function renderSaveButton() {
        const button = document.createElement("button");
        button.classList.value = 'action-btn agree-btn';
        button.textContent = 'Сохранить';
        return button;
    }
});