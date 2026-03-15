import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    const branchContainer = document.getElementById("branches-container");
    const loader = new Loader(document.getElementById('page-container'));
    const paginationManager = new PaginationManager(document.getElementById('page-container'),
        initialize);

    document.getElementById('create-btn').addEventListener('click',
        (e) => window.location.replace('/admin/branch/create'));

    try {
        await initialize();
    } catch (error) {
        console.log(error);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${error}`)
    }

    async function initialize() {
        loader.showLoading();
        await loadBranches();
    }

    async function loadBranches() {
        branchContainer.innerHTML = "";

        const branchResponse = await fetchBranches();
        const pageStatistic = branchResponse.page;
        paginationManager.initializePagination(pageStatistic.number, pageStatistic.size, pageStatistic.totalPages);

        loader.hideLoading();
        if (pageStatistic.totalPages <= 0) {
            const newsMessage = document.createElement('h3');
            newsMessage.textContent = 'Нет доступных филиалов';
            branchContainer.appendChild(newsMessage);
            return;
        }
        console.log(branchResponse);
        const branchList = branchResponse._embedded.pharmacyBranchReadDtoList;
        branchList.forEach(branch => renderBranchCard(branch));
    }

    async function fetchBranches() {
        const url = `/api/v1/admin/branches?size=${paginationManager.pageSize}&page=${paginationManager.currentPage}`;
        const response = await restClient.fetchData(url, 'GET');
        return await response.json();
    }

    function renderBranchCard(branch) {
        const branchCard = document.createElement('div');
        branchCard.classList.add('branch-card');
        branchCard.appendChild(renderBranchHeader(branch));
        branchCard.appendChild(renderBranchBody(branch));

        branchContainer.appendChild(branchCard);
    }

    function renderBranchHeader(branch) {
        const branchHeader = document.createElement('div');
        branchHeader.classList.add('branch-header');

        const branchLink = document.createElement('a');
        branchLink.classList.add('branch-link');
        branchLink.href = `/admin/branch/${branch.id}`;
        const branchTitle = document.createElement('h4');
        branchTitle.classList.add('branch-title');
        branchTitle.textContent = branch.name;
        branchLink.appendChild(branchTitle);
        branchHeader.appendChild(branchLink);

        const branchStatus = document.createElement('p');
        if (branch.isActive) {
            branchStatus.textContent = 'Активен';
            branchStatus.classList.add('branch-status-active');
        } else {
            branchStatus.textContent = 'Не активен';
            branchStatus.classList.add('branch-status-deactivated');
        }
        branchHeader.appendChild(branchStatus);

        const branchSettlement = document.createElement('p');
        branchSettlement.classList.add('branch-city');
        branchSettlement.textContent = branch.address.settlement;
        branchHeader.appendChild(branchSettlement);
        return branchHeader;
    }

    function renderBranchBody(branch) {
        const branchBody = document.createElement('div');
        branchBody.classList.add('branch-body');

        const address = document.createElement('p');
        address.innerHTML = `<strong>Адрес</strong>: ${branch.address.subject}, ${branch.address.settlement}, ${branch.address.street},
            ${branch.address.house}`;
        branchBody.appendChild(address);

        branchBody.appendChild(renderHoursList(branch));

        const contactInfo = document.createElement('p');
        contactInfo.innerHTML = `<strong>Контактный номер:</strong> ${branch.branchPhone}`;
        branchBody.appendChild(contactInfo);

        const branchDirector = document.createElement('p');
        if (branch.supervisor !== undefined && branch.supervisor !== null) {
            branchDirector.innerHTML = `<strong>Директор филиала:</strong> ${branch.supervisor.surname} 
                ${branch.supervisor.name} ${branch.supervisor.lastName}`;
        } else {
            branchDirector.innerHTML = `<strong>Директор филиала:</strong> не закреплен`;
        }
        branchBody.appendChild(branchDirector);

        const choiceButton = document.createElement('button');
        choiceButton.classList.add('branch-more-det');
        choiceButton.textContent = 'Изменить';
        choiceButton.addEventListener('click', (e) => {
            e.preventDefault();
            window.location.replace(`/admin/branch/${branch.id}`);
        });
        branchBody.appendChild(choiceButton);

        return branchBody;
    }

    function renderHoursList(branch) {
        const workingHours = document.createElement('div');
        workingHours.classList.add('working-hours');
        const workingHoursText = document.createElement('strong');
        workingHoursText.textContent = 'Часы работы:';

        const workingHoursList = document.createElement('ul');
        workingHoursList.classList.add('hours-list');
        branch.openingHours.forEach((workingHour) => {
            const hoursLi = document.createElement('li');
            hoursLi.textContent = workingHour;
            workingHoursList.appendChild(hoursLi);
        });
        return workingHours;
    }
});