import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", async e => {
    const restClient = new RestClient();
    const notification = new Notification();

    const branchId = document.querySelector('meta[name=branchId]').content;

    const generalInfoForm = document.getElementById('branch-general-info-editing-form');

    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    saveBtn.addEventListener('click', async e => {
        e.preventDefault();
        try {
            await saveForm();
            notification.showNotification('Управление филиалами',
                'Обновление основной информации филиала произошло успешно');
            setTimeout(() => window.location.replace(`/admin/branch/${branchId}`), 1500);
        } catch (e) {
            console.error(e);
            notification.showNotification('Управление филиалами',
                `Произошла ошибка при обновлении основной информации филиала. Код ${e}`);
        }
    });
    cancelBtn.addEventListener('click', ()=> window.location.replace(`/admin/branch/${branchId}`));

    try {
        await initializePage();
    } catch (e) {
        console.error(e);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${e}`);
    }

    async function initializePage() {
        const branchInfo = await (await restClient.fetchData(`/api/v1/admin/branches/${branchId}`, 'GET')).json();
        fillForm(branchInfo);
    }

    function fillForm(branchInfo) {
        generalInfoForm.querySelector('input[id=name]').value = branchInfo.name;
        generalInfoForm.querySelector('input[id=warehouseLimitations]').value = branchInfo.warehouseLimitations;
        generalInfoForm.querySelector('input[id=contactPhone]').value = branchInfo.branchPhone;
        generalInfoForm.querySelector('input[type=checkbox][name=isActive]').checked = branchInfo.isActive;
    }

    async function saveForm() {
        generalInfoForm.querySelectorAll('input[type=text]').forEach(e => e.value = e.value.trim());
        const formData = new FormData(generalInfoForm);
        const data = Object.fromEntries(formData);
        data.isActive = formData.get('isActive') === 'on';
        await restClient.fetchData(`/api/v1/admin/branches/${branchId}`, 'PATCH',
            {'Content-Type': 'application/json'}, JSON.stringify(data));
    }
});