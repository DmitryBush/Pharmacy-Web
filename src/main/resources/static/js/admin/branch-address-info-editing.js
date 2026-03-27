import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    const addressForm = document.getElementById('branch-address-info-editing-form');
    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    saveBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        try {
            await saveForm();
            notification.showNotification('Управление филиалами', 'Обновление адреса произошло успешно');
            setTimeout(() => window.location.replace('/admin/branch/1'), 1500);
        } catch (e) {
            console.error(e);
            notification.showNotification('Управление филиалами',
                `Произошла ошибка при обновлении основной информации филиала. Код ${e}`);
        }
    });

    cancelBtn.addEventListener('click',()=> window.location.replace('/admin/branch/1'));

    try {
        await initializePage();
    } catch (error) {
        console.error(error);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${error}`);
    }

    async function initializePage() {
        const branchData = await (await restClient.fetchData(`/api/v1/branches/1`, 'GET')).json();
        fillForm(branchData);
    }

    function fillForm(branchData) {
        addressForm.querySelector('input[id=subject]').value = branchData.address.subject;
        addressForm.querySelector('input[id=district]').value = branchData.address.district === null
            ? '' : branchData.address.district;
        addressForm.querySelector('input[id=settlement]').value = branchData.address.settlement;
        addressForm.querySelector('input[id=street]').value = branchData.address.street;
        addressForm.querySelector('input[id=house]').value = branchData.address.house;
        addressForm.querySelector('input[id=apartment]').value = branchData.address.apartment === null
            ? '' : branchData.address.apartment;
        addressForm.querySelector('input[id=postal-code]').value = branchData.address.postalCode;
    }

    function saveForm() {
        addressForm.querySelectorAll('input').forEach(e=> e.value = e.value.trim());
        const formData = new FormData(addressForm);
        restClient.fetchData(`/api/v1/admin/branches/1`, 'PATCH',
            {'Content-Type': 'application/json'}, JSON.stringify({address: Object.fromEntries(formData)}));
    }
});