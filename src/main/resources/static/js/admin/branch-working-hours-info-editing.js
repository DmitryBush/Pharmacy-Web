import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    const workingHoursForm = document.getElementById('branch-working-hours-info-editing-form');
    const saveBtn = document.getElementById('save-btn');
    const cancelBtn = document.getElementById('cancel-btn');

    saveBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        try {
            await saveForm();
            notification.showNotification('Управление филиалами',
                'Обновление рабочих часов филиала произошло успешно');
            setTimeout(() => window.location.replace('/admin/branch/1'), 1500);
        } catch (e) {
            console.error(e);
            notification.showNotification('Управление филиалами',
                `Произошла ошибка при обновлении основной информации филиала. Код ${e}`);
        }
    });
    cancelBtn.addEventListener('click', () => window.location.replace('/admin/branch/1'));

    try {
        await initializePage();
    } catch (error) {
        console.error(error);
        notification.showNotification('Управление филиалами',
            `Произошла ошибка при инициализации страницы. Код ${error}`);
    }

    async function initializePage() {
        const workingHoursList = await (await restClient.fetchData(`/api/v1/admin/branches/1/working-hours`, 'GET')).json();
        workingHoursList.forEach(workingHour => fillFormElement(workingHour));
    }

    function fillFormElement(workingHour) {
        const dayRow = workingHoursForm.querySelector(`div[data-day=${workingHour.dayOfWeek}]`);
        if (workingHour.dayOff) {
            dayRow.querySelector('input[name=day-off]').checked = true;
        } else if (workingHour.aroundClock) {
            dayRow.querySelector('input[name=around-clock]').checked = true;
        } else {
            console.log(workingHour.openTime);
            dayRow.querySelector('input[name=open-time]').value = workingHour.openTime;
            dayRow.querySelector('input[name=close-time]').value = workingHour.closeTime;
        }
    }

    function saveForm() {
        restClient.fetchData(`/api/v1/admin/branches/1/working-hours`, 'PATCH',
            {'Content-Type': 'application/json'}, JSON.stringify(getWorkingHours()));
    }

    function getWorkingHours() {
        let workingHours = [];
        document.querySelectorAll('.day-row').forEach((row) => {
            if (row.querySelector('input[name="day-off"]').checked) {
                workingHours.push({
                    dayOfWeek: row.dataset.day,
                    openTime: `00:00`,
                    closeTime: `00:00`,
                    dayOff: true,
                    aroundClock: false
                });
            } else if (row.querySelector('input[name="around-clock"]').checked) {
                workingHours.push({
                    dayOfWeek: row.dataset.day,
                    openTime: `00:00`,
                    closeTime: `00:00`,
                    dayOff: false,
                    aroundClock: true
                });
            } else {
                const openTimeElement = row.querySelector('input[name="open-time"]');
                const closeTimeElement = row.querySelector('input[name="close-time"]');
                workingHours.push({
                    dayOfWeek: row.dataset.day,
                    openTime: `${openTimeElement.value}`,
                    closeTime: `${closeTimeElement.value}`,
                    dayOff: false,
                    aroundClock: false
                });
            }
        });
        return workingHours;
    }
});