import RestClient from "../RestClient.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();

    const mainContainer = document.getElementById("main-container");

    try {
        await initialize();
    } catch (error) {
        console.error(error);
    }

    async function initialize(){
        const items = [
            {
                amount: 2,
                medicine: {
                    id: 1,
                    name: 'СУПРАСТИН табл. 25 мг №20',
                    type: 'Противоаллергические',
                    price: 4990,
                    imagePaths: []
                }
            }
        ];
        createCartItems();
        createSummaryLayout(items);
    }

    function createSummaryLayout() {
        const aside = document.createElement("aside");
        aside.classList.add("cr-summary");

        const title = document.createElement('h2');
        title.classList.add('cr-summary-title');
        title.textContent = 'Итого';
        aside.appendChild(title);

        let totalPrice = 0;
        let itemCount = 0;
        mainContainer.querySelectorAll('.cr-items').forEach(item => {
            const price = parseInt(item.querySelector('.cr-item-price').textContent);
            const quantity = parseInt(item.querySelector('.cr-quantity-field').textContent);
            totalPrice += price * quantity;
            itemCount += quantity;
        });

        aside.appendChild(createResultGroup(itemCount, totalPrice));
        aside.appendChild(createDeliveryGroup());
        aside.appendChild(createDiscountGroup());
        aside.appendChild(createTotalGroup(totalPrice));

        const placeOrderButton = document.createElement("button");
        placeOrderButton.type = "button";
        placeOrderButton.classList.add("cr-checkout");
        placeOrderButton.textContent = 'Оформить заказ';
        aside.appendChild(placeOrderButton);
        mainContainer.appendChild(aside);
    }

    function createResultGroup(itemCount, totalPrice) {
        const resultGroup = document.createElement("div");
        resultGroup.classList.add('cr-row');

        const resultLabel = document.createElement('span');
        resultLabel.classList.add('cr-row-label');
        resultLabel.textContent = `Итог (${itemCount} товар)`;
        resultGroup.appendChild(resultLabel);
        const resultSummary = document.createElement("span");
        resultSummary.textContent = `${totalPrice} ₽`;
        resultSummary.classList.add('cr-row-value');
        resultGroup.appendChild(resultSummary);
        return resultGroup;
    }

    function createDeliveryGroup() {
        const deliveryGroup = document.createElement("div");
        deliveryGroup.classList.add('cr-row');

        const deliveryLabel = document.createElement('span');
        deliveryLabel.textContent = `Доставка`;
        deliveryLabel.classList.add('cr-row-label');
        deliveryGroup.appendChild(deliveryLabel);
        const resultSummary = document.createElement("span");
        resultSummary.textContent = `Бесплатно`;
        resultSummary.classList.add('cr-row-value');
        deliveryGroup.appendChild(resultSummary);
        return deliveryGroup;
    }

    function createDiscountGroup() {
        const discountGroup = document.createElement("div");
        discountGroup.classList.add('cr-row');

        const discountLabel = document.createElement('span');
        discountLabel.textContent = `Скидка`;
        discountLabel.classList.add('cr-row-label');
        discountGroup.appendChild(discountLabel);
        const discountSummary = document.createElement("span");
        discountSummary.textContent = `- 0 ₽`;
        discountSummary.classList.add('cr-row-value');
        discountGroup.appendChild(discountSummary);
        return discountGroup;
    }

    function createTotalGroup(totalPrice) {
        const totalGroup = document.createElement("div");
        totalGroup.classList.add('cr-row');

        const totalLabel = document.createElement('span');
        totalLabel.textContent = `К оплате`;
        totalLabel.classList.add('cr-row-label');
        totalGroup.appendChild(totalLabel);
        const totalSummary = document.createElement("span");
        totalSummary.textContent = `${totalPrice} ₽`;
        totalSummary.classList.add('cr-row-value');
        totalGroup.appendChild(totalSummary);
        return totalGroup;
    }

    function createCartItems() {
        const itemsContainer = document.createElement("div");
        itemsContainer.classList.add("cr-items");
        const response = {
            amount: 2,
            medicine: {
                id: 1,
                name: 'СУПРАСТИН табл. 25 мг №20',
                type: 'Противоаллергические',
                price: 4990,
                imagePaths: []
            }
        }
        itemsContainer.appendChild(createCartItem(response));
        mainContainer.appendChild(itemsContainer);
    }

    function createCartItem(item) {
        const cartItem = document.createElement("div");
        cartItem.classList.add("cr-item");

        cartItem.appendChild(createCartItemImage(item.medicine.imagePaths));
        cartItem.appendChild(createCartItemInfo(item));
        cartItem.appendChild(createCartItemAction(item.medicine.price));
        return cartItem;
    }

    function createCartItemImage(imagePaths) {
        const itemImageContainer = document.createElement("div");
        itemImageContainer.classList.add("cr-item-img");
        if (imagePaths.length < 1) {
            itemImageContainer.innerHTML = `
            <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5">
                <rect x="3" y="3" width="18" height="18" rx="2"/>
                <circle cx="8.5" cy="8.5" r="1.5"/>
                <path d="M21 15l-5-5L5 21"/>
            </svg>`;
        } else {
            const image = document.createElement("img");
            image.src = `/api/v1/product-image/${imagePaths[0]}`;
            itemImageContainer.appendChild(image);
        }
        return itemImageContainer;
    }

    function createCartItemInfo(info) {
        const cartItemInfo = document.createElement("div");
        cartItemInfo.classList.add('cr-item-info');

        const itemName = document.createElement('span');
        itemName.classList.add('cr-item-name');
        itemName.textContent = info.medicine.name;
        cartItemInfo.appendChild(itemName);

        const itemType = document.createElement('span');
        itemType.classList.add('cr-item-type');
        itemType.textContent = info.medicine.type;
        cartItemInfo.appendChild(itemType);

        cartItemInfo.appendChild(createCartItemQuantity(info.amount));
        return cartItemInfo;
    }

    function createCartItemQuantity(quantity) {
        const quantityCounter = document.createElement("div");
        quantityCounter.classList.add('cr-qty');

        const decrementButton = document.createElement("button");
        decrementButton.type = "button";
        decrementButton.textContent = '-';
        quantityCounter.appendChild(decrementButton);

        const quantityField = document.createElement("span");
        quantityField.classList.add('cr-quantity-field');
        quantityField.textContent = quantity;
        quantityCounter.appendChild(quantityField);

        const incrementButton = document.createElement("button");
        incrementButton.type = "button";
        incrementButton.textContent = '+';
        quantityCounter.appendChild(incrementButton);

        decrementButton.addEventListener('click', () => {
            let quantity = parseInt(quantityField.textContent);
            if (quantity > 2) {
                quantity--;
            } else if (quantity === 2) {
                quantity--;
                decrementButton.disabled = true;
            }
            quantityField.textContent = String(quantity);
        });
        incrementButton.addEventListener('click', () => {
            let quantity = parseInt(quantityField.textContent);
            if (quantity <= 1) {
                quantity++;
                decrementButton.disabled = false;
            } else if (quantity > 1) {
                quantity++;
            }
            quantityField.textContent = String(quantity);
        });
        return quantityCounter;
    }

    function createCartItemAction(price) {
        const cartItemActions = document.createElement("div");
        cartItemActions.classList.add("cr-item-actions");

        const itemPrice = document.createElement("span");
        itemPrice.classList.add("cr-item-price");
        itemPrice.textContent = price;
        cartItemActions.appendChild(itemPrice);

        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.classList.add("cr-remove");
        deleteButton.innerHTML = `
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round">
            <line x1="18" y1="6" x2="6" y2="18"/>
            <line x1="6" y1="6" x2="18" y2="18"/>
        </svg>
        Удалить
        `;
        cartItemActions.appendChild(deleteButton);
        return cartItemActions;
    }
});