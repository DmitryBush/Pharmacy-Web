import Notification from "../notification/notification.js";
document.addEventListener('DOMContentLoaded', () => {
    const notification = new Notification('/api/v1/icons/admin/orders.png');
    const branchId = document.getElementById('branch-id').dataset.id;
    document.querySelectorAll('.order-more-det').forEach(el => {
        el.addEventListener('click', (e) => {
            try {
                const orderId = e.target.dataset.id;
                if (!/^\d+$/.test(orderId))
                    throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                        'Обратитесь к вашему администратору');
                if (!/^\d+$/.test(branchId))
                    throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                        'Обратитесь к вашему администратору');
                window.location.replace(`/admin/orders/branch/${encodeURIComponent(branchId)}` +
                    `/order/${encodeURIComponent(orderId)}`);
            } catch (e) {
                console.error(e);
                notification.showNotification('Управление заказами', e.message);
            }
        });
    });
});