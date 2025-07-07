import Notification from '../notification/notification.js'

document.addEventListener('DOMContentLoaded', () => {
    const notification = new Notification('/api/icons/admin/orders.png');
    document.getElementById('complete-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;

        fetchData(id, 'POST', { event: 'OPERATOR_COMPLETES_ORDER'})
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

        fetchData(id, 'POST', { event: "OPERATOR_CANCELS_ORDER" });
    });

    document.getElementById('refund-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Начата процедура возврата');

        fetchData(id, 'POST', { event: "OPERATOR_REFUNDS_ORDER" });
    });
});

async function fetchData(id, method, body = null) {
    try {
        const response = await fetch(`/api/v1/order/${id}/state`, {
            method,
            body: JSON.stringify(body),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            const errorText = await response.json();
            throw new Error(errorText.error);
        }

        return response;
    } catch (error) {
        console.error(`${method} error:`, error);
        throw error;
    }
}