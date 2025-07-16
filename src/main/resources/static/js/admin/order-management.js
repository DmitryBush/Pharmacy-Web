import Notification from '../notification/notification.js'
import RestClient from "../RestClient.js";

document.addEventListener('DOMContentLoaded', () => {
    const notification = new Notification('/api/v1/icons/admin/orders.png');
    const restClient = new RestClient();

    document.getElementById('complete-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;

        restClient.fetchData(`/api/v1/management/orders/${id}/state`, 'POST',
            { 'Content-Type': 'application/json' }, JSON.stringify({ event: 'OPERATOR_COMPLETES_ORDER' }))
            .then(r => {
                notification.showNotification('Управление заказами', 'Заказ успешно завершен');
                e.target.parentElement.remove();
            })
            .catch(error => {
                notification.showNotification('Управление заказами',
                    `Во время завершения заказа произошла ошибка. Ошибка: ${error.message}`);
            });
    });

    document.getElementById('cancel-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Заказ отменен');

        restClient.fetchData(`/api/v1/management/orders/${id}/state`, 'POST',
            { 'Content-Type': 'application/json' },JSON.stringify({ event: "OPERATOR_CANCELS_ORDER" }));
    });

    document.getElementById('refund-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Начата процедура возврата');

        restClient.fetchData(`/api/v1/management/orders/${id}/state`, 'POST',
            { 'Content-Type': 'application/json' }, JSON.stringify( {event: "OPERATOR_REFUNDS_ORDER" }));
    });
});