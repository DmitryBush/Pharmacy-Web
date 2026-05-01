import RestClient from "../RestClient.js";
import Loader from "../loader/loader.js";
import PaginationManager from "../pagination/pagination.js";
import {getStatusText} from "../formatter/formatter.js";
import {renderAuthWarning} from "../warning/Warning.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    const orderList = document.getElementById('order-list');
    const emptyOrderContainer = document.getElementById('empty-order-container');

    const loader = new Loader(emptyOrderContainer);
    const paginationManager = new PaginationManager(emptyOrderContainer, initializeOrders);

    try {
        loader.showLoading();
        await initializeOrders();
    } catch (e) {
        console.error(e);
    }

    async function initializeOrders() {
        try {
            const orders = await (await fetchOrders()).json();
            renderOrderCards(orders);
        } catch (e) {
            if (e.message !== '401') {
                throw e;
            }
            loader.hideLoading();
            renderAuthWarning(emptyOrderContainer,
                'Для просмотра ваших заказов необходимо войти в аккаунт');
        }
    }

    async function fetchOrders() {
        const fetchUrl = `/api/v1/orders/me?size=${paginationManager.pageSize}&page=${paginationManager.currentPage}`;
        return await restClient.fetchData(fetchUrl, 'GET');
    }

    function renderOrderCards(orders) {
        loader.hideLoading();
        orders._embedded.orderReadDtoList.forEach(order => {
            const orderCard = document.createElement('div');
            orderCard.classList.add('cr-order-card');
            orderCard.appendChild(renderOrderHeader(order));
            orderCard.appendChild(renderOrderBody(order));
            orderCard.appendChild(renderOrderFooter(order));

            orderList.appendChild(orderCard);
        });
    }

    function renderOrderHeader(order) {
        const orderHeader = document.createElement('div');
        orderHeader.classList.add('cr-order-header');

        const orderId = document.createElement('span');
        orderHeader.classList.add('cr-order-number');
        orderId.textContent = `Заказ - ${order.id}`;
        orderHeader.append(orderId);

        const orderDate = document.createElement('span');
        orderDate.classList.add('cr-order-date');
        const date = new Date(order.date);
        orderDate.textContent = String(date.toLocaleDateString('ru-RU'));
        orderHeader.append(orderDate);

        const orderStatus = document.createElement('span');
        orderStatus.classList.add('cr-order-status');
        const orderCancelledStatuses = ['CANCELLED', 'NOT_DEMAND', 'RETURN_REJECTED'];
        const orderProcessingStatuses = ['DECOR','DEFERRED','ASSEMBLY','TRANSIT',
            'RETURN_REQUESTED','AWAITING_CUSTOMER_SHIPMENT'];
        if (orderCancelledStatuses.includes(order.statusOrder.name)) {
            orderStatus.classList.add('cr-order-status--cancelled');
        } else if (orderProcessingStatuses.includes(order.statusOrder.name)) {
            orderStatus.classList.add('cr-order-status--processing');
        } else {
            orderStatus.classList.add('cr-order-status--delivered');
        }
        orderStatus.textContent = getStatusText(order.statusOrder.name);
        orderHeader.append(orderStatus);
        return orderHeader;
    }

    function renderOrderBody(order) {
        const orderBody = document.createElement('div');
        orderBody.classList.add('cr-order-body');

        const orderItems = document.createElement('div');
        orderItems.classList.add('cr-order-items');
        order.cartItems.forEach(orderItem => {
            const item = document.createElement('div');
            item.classList.add('cr-order-item-name');
            item.textContent = orderItem.medicine.name;
            orderItems.appendChild(item);
        });
        orderBody.append(orderItems);

        const orderTotalPrice = document.createElement('div');
        orderTotalPrice.classList.add('cr-order-total');
        orderTotalPrice.textContent = `${order.result} ₽`;
        orderBody.append(orderTotalPrice);
        return orderBody;
    }

    function renderOrderFooter(order) {
        const cancelOrderState = ['DECOR', 'DEFERRED', 'ASSEMBLY', 'TRANSIT', 'DELIVERED'];
        const rejectOrderState = ['COMPLETED'];

        const orderFooter = document.createElement('div');
        orderFooter.classList.add('cr-order-footer');

        if (cancelOrderState.includes(order.statusOrder.name)) {
            const cancelButton = document.createElement('button');
            cancelButton.classList.add('cr-btn', 'cr-order-status--cancelled');
            cancelButton.textContent = 'Отменить';
            orderFooter.append(cancelButton);
        } else if (rejectOrderState.includes(order.statusOrder.name)) {
            const rejectButton = document.createElement('button');
            rejectButton.classList.add('cr-btn', 'cr-order-status--processing');
            rejectButton.textContent = 'Оформить возврат';
            orderFooter.append(rejectButton);
        }
        return orderFooter;
    }
});