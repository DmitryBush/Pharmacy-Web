import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener("DOMContentLoaded", async function () {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/box-fill.png');

    const objectContainer = document.getElementById("object-container");

    try {
        await initialize();
    } catch (error) {
        console.log(error);
        notification.showNotification('Управление складом',
            'Произошла неизвестная ошибка при инициализации истории транзакций');
    }

    async function initialize() {
        await getTransactions();
    }

    async function getTransactions() {
        const data = await (await restClient.fetchData(`/api/v1/warehouse/branches/1/transactions`, 'GET',
            {})).json();

        data.forEach(transaction => {
            createTransactionItem(transaction);
        });
    }

    function createTransactionItem(transaction) {
        const transactionItem = document.createElement("div");
        transactionItem.classList.add("object");
        transactionItem.innerHTML = `
                    <div class="transaction-header">
                        <div class="transaction-info">
                            <div>
                                <h3>Транзакция ${transaction.transactionId}</h3>
                                <p class="transaction-date">${formatDate(transaction.completedAt)}</p>
                            </div>
                            <p class="transaction-status">${formatTransactionType(transaction.type)}</p>
                        </div>
                        <div class="transaction-object-end">
                            <div class="transaction-price">
                                ${getResultTransactionPrice(transaction.transactionItems)} ₽
                            </div>
                            <div class="arrow">▼</div>
                        </div>
                    </div>
        `;
        transactionItem.appendChild(getProductItemsList(transaction.transactionItems));

        objectContainer.appendChild(transactionItem);

        transactionItem.addEventListener("click", (e) => toggleDetails(transactionItem));
    }

    function formatDate(date) {
        const dateObject = new Date(date);
        return Intl.DateTimeFormat('ru-Ru', {
            day: 'numeric',
            month: 'long',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        }).format(dateObject);
    }

    function formatTransactionType(type) {
        if (type === "RECEIVING") {
            return "Поступление"
        } else if (type === "SALE") {
            return "Продажа";
        }
        return "Неизвестный тип"
    }

    function getResultTransactionPrice(items) {
        let result = 0;
        items.forEach(item => {
            result += item.price * item.amount;
        });
        return result;
    }

    function getProductItemsList(items) {
        const productItemsList = document.createElement('div');
        productItemsList.classList.add('transaction-details');
        const detailsContent = document.createElement("div");
        detailsContent.classList.add('details-content');
        productItemsList.appendChild(detailsContent);

        items.forEach(item => {
            const product = document.createElement("div");
            product.classList.add('product-item');
            product.innerHTML = `
                                <a href="/admin/product/${encodeURIComponent(item.medicine.id)}}">
                                    <img src="/api/v1/product-image/${encodeURIComponent(item.medicine.imagePaths[0].id)}" 
                                         width="100px"
                                         height="100px"
                                         alt="${item.medicine.name}">
                                </a>
                                <div>
                                    <a href="/admin/product/${encodeURIComponent(item.medicine.id)}}}">
                                        ${item.medicine.name}
                                    </a>
                                </div>
                                <div>
                                    <p>${item.amount} шт.</p>
                                </div>
                                <div>
                                    <p>${item.price} ₽</p>
                                </div>
            `;
            detailsContent.appendChild(product);
        });
        return productItemsList;
    }

    function toggleDetails(element) {
        let detailsContent = element.querySelector('.transaction-details');
        let arrow = element.querySelector('.arrow');
        document.querySelectorAll('.transaction-details.active').forEach(item => {
            if (item !== detailsContent) {
                item.classList.remove('active');
                item.previousElementSibling.querySelector('.arrow').classList.remove('rotated');
            }
        });

        if (detailsContent.classList.contains('active')) {
            detailsContent.classList.remove('active');
            arrow.classList.remove('rotated');
        } else {
            detailsContent.classList.add('active');
            arrow.classList.add('rotated');
        }
    }
});