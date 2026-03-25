import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import {getDayText, getRoleText, getTimeText} from "../formatter/formatter.js";
import Loader from "../loader/loader.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    const pageTitle = document.getElementById('page-title');
    const baseInfoContainer = document.getElementById("base-info-container");
    const addressContainer = document.getElementById("address-container");
    const workingHoursContainer = document.getElementById("working-hours-container");
    const employerTableBody = document.getElementById("users-table-body");

    const baseInfoLoader = new Loader(baseInfoContainer);
    const addressLoader = new Loader(addressContainer);
    const workingHoursLoader = new Loader(workingHoursContainer);

    try {
        await initializePage();
    } catch (e) {
        console.error(e);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${e}`);
    }

    async function initializePage() {
        baseInfoLoader.showLoading();
        addressLoader.showLoading();
        workingHoursLoader.showLoading();
        await Promise.all([getBranchInfo(), getLinkedUsers()]);
    }

    async function getBranchInfo() {
        const branchInfoResponse = await restClient.fetchData(`/api/v1/admin/branches/1`, 'GET');
        const branchInfoData = await branchInfoResponse.json();
        initializeBaseInfo(branchInfoData);
        initializeAddress(branchInfoData.address);
        initializeWorkingHours(branchInfoData.workingHoursList);
    }

    async function getLinkedUsers() {
        const linkedUsersResponse = await restClient.fetchData(`/api/v1/admin/branches/1/users`, 'GET');
        const linkedUsers = await linkedUsersResponse.json();
        linkedUsers.forEach((user) => renderLinkedUserRows(user));
    }

    function renderLinkedUserRows(user) {
        const tr = document.createElement("tr");
        tr.appendChild(renderUserName(user));
        const role = renderRole(user);
        tr.appendChild(role);
        employerTableBody.appendChild(tr);
    }

    function renderUserName(user) {
        return renderTableData(`${user.surname} ${user.name} ${user.lastName}`);
    }

    function renderRole(user) {
        return renderTableData(`${getRoleText(user.role)}`);
    }

    function renderTableData(data) {
        const td = document.createElement("td");
        td.textContent = data;
        return td;
    }

    function initializeBaseInfo(branchInfo) {
        baseInfoLoader.hideLoading();
        const baseInfoHeader = createFrameHeader('Основная информация',
            '/admin/branch/1/general');

        const infoGridContainer = document.createElement("div");
        infoGridContainer.classList.add('info-grid');
        pageTitle.textContent = branchInfo.name;
        infoGridContainer.appendChild(createInfoItem({label: 'Название филиала', value: branchInfo.name}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Состояние филиала', value: branchInfo.isActive}, true));
        infoGridContainer.appendChild(createInfoItem({
                label: 'Ограничение хранения склада (м^3)',
                value: branchInfo.warehouseLimitations
            },
            false));
        infoGridContainer.appendChild(createInfoItem({
            label: 'Контактный телефон',
            value: branchInfo.branchPhone
        }, false));
        infoGridContainer.appendChild(createInfoItem({
            label: 'Директор филиала',
            value: branchInfo.supervisor === null
                ? 'Не закреплен'
                : `${branchInfo.supervisor.surname} ${branchInfo.supervisor.name} ${branchInfo.supervisor.lastName}`
        }, false));
        baseInfoContainer.appendChild(baseInfoHeader);
        baseInfoContainer.appendChild(infoGridContainer);
    }

    function createFrameHeader(headerInfo, detailsLink) {
        const headerContainer = document.createElement("div");
        headerContainer.classList.add('frame-header');

        const header = document.createElement('h2');
        header.textContent = headerInfo;
        headerContainer.appendChild(header);

        const headerDetailsLink = document.createElement("a");
        headerDetailsLink.classList.add('branch-info-details-btn');
        headerDetailsLink.href = detailsLink;
        headerDetailsLink.textContent = 'Изменить';
        headerContainer.appendChild(headerDetailsLink);
        return headerContainer;
    }

    function createInfoItem(infoItem, branchStatus = false) {
        const infoItemContainer = document.createElement("div");
        infoItemContainer.classList.add('info-item');

        infoItemContainer.appendChild(createInfoLabel(infoItem.label));
        infoItemContainer.appendChild(createInfoValue(infoItem.value, branchStatus));
        return infoItemContainer;
    }

    function createInfoLabel(labelContent) {
        const infoLabel = document.createElement('div');
        infoLabel.classList.add('info-label');
        infoLabel.textContent = labelContent;
        return infoLabel;
    }

    function createInfoValue(valueContent, branchStatus = false) {
        const infoValue = document.createElement('div');
        infoValue.classList.add('info-value');
        if (branchStatus) {
            const status = document.createElement('span');
            status.classList.add(valueContent ? 'branch-status-active' : 'branch-status-deactivated');
            status.textContent = valueContent ? 'Активен' : 'Не активен';
            infoValue.appendChild(status);
        } else {
            infoValue.textContent = valueContent;
        }
        return infoValue;
    }

    function createInfoRow(infoItem) {
        const infoItemContainer = document.createElement("div");
        infoItemContainer.classList.add('info-row');

        infoItemContainer.appendChild(createInfoLabel(infoItem.label));
        infoItemContainer.appendChild(createInfoValue(infoItem.value, false));
        return infoItemContainer;
    }

    function initializeAddress(branchAddress) {
        addressLoader.hideLoading();
        const baseInfoHeader = createFrameHeader('Адрес',
            '/admin/branch/1/address');

        const infoGridContainer = document.createElement("div");
        infoGridContainer.classList.add('info-grid');
        infoGridContainer.appendChild(createInfoItem({label: 'Область', value: branchAddress.subject}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Район', value: branchAddress.district}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Город', value: branchAddress.settlement}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Улица', value: branchAddress.street}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Дом', value: branchAddress.house}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Квартира', value: branchAddress.apartment}, false));
        infoGridContainer.appendChild(createInfoItem({label: 'Почтовый код', value: branchAddress.postalCode}, false));
        addressContainer.appendChild(baseInfoHeader);
        addressContainer.appendChild(infoGridContainer);
    }

    function initializeWorkingHours(branchWorkingHours) {
        workingHoursLoader.hideLoading();
        const baseInfoHeader = createFrameHeader('Рабочие часы',
            '/admin/branch/1/working-hours');

        const infoGridContainer = document.createElement("div");
        branchWorkingHours.forEach((workingHour) => {
            infoGridContainer.appendChild(createInfoRow({
                label: getDayText(workingHour.dayOfWeek),
                value: getTimeText(workingHour)
            }));
        });
        workingHoursContainer.appendChild(baseInfoHeader);
        workingHoursContainer.appendChild(infoGridContainer);
    }
});