import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";
import {formatPhone} from "../formatter/formatter.js"

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();
    const notification = new Notification();

    document.getElementById('cancel-btn').addEventListener('click',
        () => window.location.replace('/admin/branch'));

    document.getElementById('branch-phone').addEventListener('input',
        (e) => formatPhone(e.target));

    document.getElementById('save-btn').addEventListener('click', event => {
        event.preventDefault();
        createPharmacyBranch()
            .then(() => {
                notification.showNotification('Управление филиалами', 'Филиал успешно создан');
                setTimeout(() => window.location.replace('/admin/branch'), 1000);
            })
            .catch((err) => {
                console.error(err);
                notification.showNotification('Управление филиалами',
                    `Произошла ошибка при создании филиала. Код ${err}`);
            });
    });

    async function createPharmacyBranch(){
        await restClient.fetchData(`/api/v1/admin/branches`, 'POST',
            {'Content-Type': 'application/json'}, JSON.stringify(getFormData()));
    }

    function getFormData() {
        return {
            name: document.getElementById('branch-name').value,
            warehouseLimitations: document.getElementById('warehouse-limitations').value,
            contactPhone: document.getElementById('branch-phone').value,
            address: getAddress(),
            workingHoursDtoList: getWorkingHours()
        }
    }

    function getAddress() {
        return {
            subject: document.getElementById('subject').value,
            district: document.getElementById('district').value,
            settlement: document.getElementById('settlement').value,
            street: document.getElementById('street').value,
            house: document.getElementById('house').value,
            apartment: document.getElementById('apartment').value,
            postalCode: document.getElementById('postal-code').value,
        }
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