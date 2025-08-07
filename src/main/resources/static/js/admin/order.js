import Notification from "../notification/notification.js";
document.addEventListener("DOMContentLoaded", () => {
    const notification = new Notification('/api/v1/icons/admin/orders.png');
    document.querySelectorAll('.branch-more-det').forEach(el => {
        el.addEventListener('click', (e) => {
            try {
                const branchId = e.target.dataset.id;
                if (!/^\d+$/.test(branchId))
                    throw new Error('Произошла критическая ошибка при подготовке ресурсов. ' +
                        'Обратитесь к вашему администратору');
                window.location.replace(`/admin/orders/branch/${encodeURIComponent(branchId)}`);
            } catch (e) {
                console.error(e);
                notification.showNotification('Управление заказами', e.message);
            }
        });
    });
});