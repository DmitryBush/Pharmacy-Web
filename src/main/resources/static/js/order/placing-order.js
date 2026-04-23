import RestClient from "../RestClient.js";
import {renderAuthWarning, renderEmptyCart} from "../warning/Warning.js";
import {getShortDayText, getTimeText} from "../formatter/formatter.js";

document.addEventListener('DOMContentLoaded', async () => {
    const restClient = new RestClient();

    let resultPrice = 0;

    const orderLayout = document.getElementById('order-layout');
    const emptyOrderContainer = document.getElementById("empty-order-container");

    try {
        await initialize();
    } catch (e) {
        console.error(e);
    }

    async function initialize(){
        try {
            const cartDto = await fetchCartItems();
            if (cartDto.cartItems.length > 0) {
                const branches = await fetchBranches();
                renderOrderPlacePage(cartDto.cartItems, branches);
            } else {
                renderEmptyCart(emptyOrderContainer, 'Добавьте товары из каталога, чтобы оформить заказ');
            }
        } catch (error) {
            console.error(error);
            renderAuthWarning(emptyOrderContainer, 'Для оформления заказа необходимо войти в аккаунт или создать новый');
        }
    }

    async function fetchCartItems(){
        return await (await restClient.fetchData(`/api/v1/carts/me`, 'GET')).json();
    }

    async function fetchBranches(){
        return await (await restClient.fetchData(`/api/v1/branches`, 'GET')).json();
    }

    function renderOrderPlacePage(cartItems, branches) {
        const mainContainer = document.createElement("div");
        mainContainer.classList.add("cr-checkout-main");
        mainContainer.append(renderBranchSection(branches));
        mainContainer.append(renderItemSection(cartItems));
        orderLayout.append(mainContainer);
        orderLayout.append(renderSummarySection());
    }

    function renderBranchSection(branches) {
        const branchSection = document.createElement('div');
        branchSection.classList.add('cr-section');
        branchSection.id = 'branch';

        const branchSectionTitle = document.createElement('h2');
        branchSectionTitle.textContent = 'Выберите филиал';
        branchSectionTitle.classList.add('cr-section-title');
        branchSection.append(branchSectionTitle);

        const branchesContainer = document.createElement('div');
        branchesContainer.classList.add('cr-branch-list');
        branches.forEach((branch, index) => {
            const branchCard = document.createElement('label');
            branchCard.classList.add('cr-branch-card');
            const input = document.createElement('input');
            input.dataset.id = branch.id;
            input.type = 'radio';
            input.name = 'cr-branch';
            if (index === 0) {
                input.checked = true;
            }
            input.classList.add('cr-branch-radio');
            branchCard.append(input);

            const branchInfoContainer = document.createElement('div');
            branchInfoContainer.classList.add('cr-branch-info');
            const branchName = document.createElement('span');
            branchName.classList.add('cr-branch-name');
            branchName.textContent = branch.name;
            branchInfoContainer.append(branchName);
            const branchAddress = document.createElement('span');
            branchAddress.classList.add('cr-branch-address');
            branchAddress.textContent = `${branch.address.subject}, ${branch.address.settlement}, 
            ${branch.address.street}, ${branch.address.house}`;
            branchInfoContainer.append(branchAddress);
            branch.workingHoursList.forEach(workingHour => {
                const branchHours = document.createElement('span');
                branchHours.classList.add('cr-branch-hours');
                branchHours.textContent = `${getShortDayText(workingHour.dayOfWeek)}: ${getTimeText(workingHour)}`;
                branchInfoContainer.append(branchHours);
            });
            branchCard.append(branchInfoContainer);

            branchesContainer.appendChild(branchCard);
        });
        branchSection.append(branchesContainer);
        return branchSection;
    }

    function renderItemSection(cartItems) {
        const orderSection = document.createElement('div');
        orderSection.classList.add('cr-section');
        orderSection.id = 'order';

        const orderSectionTitle = document.createElement('h2');
        orderSectionTitle.textContent = 'Ваш заказ';
        orderSectionTitle.classList.add('cr-section-title');
        orderSection.append(orderSectionTitle);

        cartItems.forEach(cartItem => {
            const item = document.createElement('div');
            item.dataset.id = cartItem.medicine.id;
            item.dataset.price = cartItem.medicine.price;
            item.dataset.quantity = cartItem.amount;
            item.classList.add("cr-item");
            item.append(renderItemImage(cartItem));
            item.append(renderItemInfo(cartItem));
            item.append(renderItemPrice(cartItem));
            orderSection.append(item);

            resultPrice += cartItem.amount * cartItem.medicine.price;
        });
        return orderSection;
    }

    function renderItemImage(item) {
        const imageContainer = document.createElement('div');
        imageContainer.classList.add('cr-item-image');
        if (item.medicine.imagePaths.length > 0) {
            const image = document.createElement('img');
            image.src = `/api/v1/product-image/${item.medicine.imagePaths[0].id}`;
            image.alt = item.medicine.name;
            image.width = 100;
            image.height = 100;
            imageContainer.appendChild(image);
        } else {
            imageContainer.innerHTML = `
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="3" y="3" width="18" height="18" rx="2"/>
                <circle cx="8.5" cy="8.5" r="1.5"/>
                <path d="M21 15l-5-5L5 21"/>
            </svg>
            `;
        }
        return imageContainer;
    }

    function renderItemInfo(item) {
        const itemInfoContainer = document.createElement('div');
        itemInfoContainer.classList.add('cr-item-info');

        const itemName = document.createElement('span');
        itemName.classList.add('cr-item-name');
        itemName.textContent = item.medicine.name;
        itemInfoContainer.append(itemName);

        const itemType = document.createElement('span');
        itemType.classList.add('cr-item-type');
        itemType.textContent = item.medicine.type;
        itemInfoContainer.append(itemType);
        return itemInfoContainer;
    }

    function renderItemPrice(item) {
        const priceContainer = document.createElement('div');
        priceContainer.classList.add('cr-item-price');

        const itemPrice = document.createElement('span');
        itemPrice.classList.add('cr-item-price');
        itemPrice.textContent = `${item.amount} x ${item.medicine.price} ₽`;
        priceContainer.append(itemPrice);
        return priceContainer;
    }

    function renderSummarySection() {
        const summaryContainer = document.createElement('aside');
        summaryContainer.classList.add('cr-summary');

        const summaryContainerTitle = document.createElement('h2');
        summaryContainerTitle.classList.add('cr-summary-title');
        summaryContainerTitle.textContent = 'Итого';
        summaryContainer.append(summaryContainerTitle);

        summaryContainer.append(renderResultSummary());
        summaryContainer.append(renderDeliverySummary());
        summaryContainer.append(renderDiscountSummary());
        summaryContainer.append(renderTotalSummary());

        const createOrderButton = document.createElement('button');
        createOrderButton.classList.add('cr-checkout');
        createOrderButton.type = 'button';
        createOrderButton.textContent = 'Оформить заказ';
        createOrderButton.addEventListener('click', () => placeOrder()
                .then(async () => {
                    await deleteOrderItemsFromCart();
                    window.location.replace('/');
                })
                .catch((err) => console.log(err)));
        summaryContainer.append(createOrderButton);
        return summaryContainer;
    }

    function renderResultSummary(cartItems) {
        const summaryContainer = document.createElement('div');
        summaryContainer.classList.add('cr-row');

        const summaryResultLabel = document.createElement('span');
        summaryResultLabel.classList.add('cr-row-label');
        summaryResultLabel.textContent = 'Итого';
        summaryContainer.append(summaryResultLabel);

        const summaryResultValue = document.createElement('span');
        summaryResultValue.classList.add('cr-row-value');
        summaryResultValue.textContent = `${resultPrice} ₽`;
        summaryContainer.append(summaryResultValue);
        return summaryContainer;
    }

    function renderDeliverySummary() {
        const deliverySummaryContainer = document.createElement('div');
        deliverySummaryContainer.classList.add('cr-row');

        const summaryDeliveryLabel = document.createElement('span');
        summaryDeliveryLabel.classList.add('cr-row-label');
        summaryDeliveryLabel.textContent = 'Доставка';
        deliverySummaryContainer.append(summaryDeliveryLabel);

        const summaryDeliveryValue = document.createElement('span');
        summaryDeliveryValue.classList.add('cr-row-value');
        summaryDeliveryValue.textContent = 'Бесплатно';
        deliverySummaryContainer.append(summaryDeliveryValue);
        return deliverySummaryContainer;
    }

    function renderDiscountSummary() {
        const discountSummaryContainer = document.createElement('div');
        discountSummaryContainer.classList.add('cr-row');

        const summaryDiscountLabel = document.createElement('span');
        summaryDiscountLabel.classList.add('cr-row-label');
        summaryDiscountLabel.textContent = 'Скидка';
        discountSummaryContainer.append(summaryDiscountLabel);

        const summaryDiscountValue = document.createElement('span');
        summaryDiscountValue.classList.add('cr-row-value');
        summaryDiscountValue.textContent = '- 0 ₽';
        discountSummaryContainer.append(summaryDiscountValue);
        return discountSummaryContainer;
    }

    function renderTotalSummary() {
        const totalSummaryContainer = document.createElement('div');
        totalSummaryContainer.classList.add('cr-row', 'cr-row-total');

        const totalSummaryLabel = document.createElement('span');
        totalSummaryLabel.classList.add('cr-row-label');
        totalSummaryLabel.textContent = 'К оплате';

        const totalSummaryValue = document.createElement('span');
        totalSummaryValue.classList.add('cr-row-value');
        totalSummaryValue.textContent = resultPrice;
        return totalSummaryContainer;
    }

    async function placeOrder() {
        const branchId = parseInt(document.querySelector('.cr-branch-radio:checked').dataset.id);
        let orderItems = [];
        document.querySelectorAll('.cr-item').forEach(item => {
            orderItems.push({
                productId: parseInt(item.dataset.id),
                quantity: parseInt(item.dataset.quantity),
                price: item.dataset.price
            });
        });
        await restClient.fetchData(`/api/v1/orders/me`, 'POST', {'Content-Type': 'application/json'},
            JSON.stringify({
                branchId: branchId,
                orderItems: orderItems
            }));
    }

    async function deleteOrderItemsFromCart(){
        let orderItems = [];
        document.querySelectorAll('.cr-item').forEach(item => {
            orderItems.push(parseInt(item.dataset.id));
        });
        await restClient.fetchData(`/api/v1/carts/me/items/batch-delete`, 'POST',
            {'Content-Type': 'application/json'},
            JSON.stringify({
                productIdList: orderItems
            }));
    }
});