import Notification from '../notification/notification.js'

document.addEventListener('DOMContentLoaded', () => {
    const notification = new Notification('/api/icons/admin/escape.png');
    document.getElementById('complete-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Управление заказами', 'Заказ успешно завершен');
        // fetchData(id, 'POST', 6)
        //     .then(r => {
        //         notification.showNotification('Заказ успешно завершен');
        //     });
    });

    document.getElementById('cancel-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Заказ отменен');

        fetchData(id, 'POST', 7);
    });

    document.getElementById('refund-order-btn').addEventListener('click',
        (e) => {
        const id = e.target.closest('div[data-id]').dataset.id;
        notification.showNotification('Начата процедура возврата');

        fetchData(id, 'POST', 8);
    });
});

async function fetchData(id, method, body = null) {
    try {
        const response = await fetch(`/api/v1/order/${id}/state`, {
            method,
            body: body
        });

        if (response.status === 403)
            throw new Error('Требуется авторизация');
        if (!response.ok) {
            const errorText = await response.text();
            try {
                const errorJson = JSON.parse(errorText);
                throw new Error(errorJson.message || errorText);
            } catch {
                throw new Error(errorText);
            }
        }

        return response;
    } catch (error) {
        console.error(`${method} error:`, error);
        alert(`${method} ошибка:` + error);
        throw error;
    }
}