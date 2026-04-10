import RestClient from "../RestClient.js";

document.addEventListener("DOMContentLoaded", async () => {
    const restClient = new RestClient();

    let totalCartItemsCounter = 0;

    const authWarningContainer = document.getElementById("empty-cart-container");
    const gridContainer = document.getElementById("cart-grid-container");

    try {
        await initialize();
    } catch (error) {
        console.error(error);
    }

    async function initialize() {
        await fetchCartItems();
    }

    async function fetchCartItems() {
        try {
            const items = await (await restClient.fetchData(`/api/v1/carts/me`, 'GET')).json();
            if (items.cartItems.length > 0) {
                createCartItems(items.cartItems);
                renderSummaryLayout();
            } else {
                renderEmptyCart();
            }
        } catch (error) {
            renderAuthWarning();
        }
    }

    function renderSummaryLayout() {
        const aside = document.createElement("aside");
        aside.classList.add("cr-summary");

        const title = document.createElement('h2');
        title.classList.add('cr-summary-title');
        title.textContent = 'Итого';
        aside.appendChild(title);

        aside.appendChild(createResultGroup());
        aside.appendChild(createDeliveryGroup());
        aside.appendChild(createDiscountGroup());
        aside.appendChild(createTotalGroup());

        const placeOrderButton = document.createElement("button");
        placeOrderButton.type = "button";
        placeOrderButton.classList.add("cr-checkout");
        placeOrderButton.textContent = 'Оформить заказ';
        aside.appendChild(placeOrderButton);
        gridContainer.appendChild(aside);

        updateSummaryValues();
    }

    function updateSummaryValues() {
        if (totalCartItemsCounter > 0) {
            let totalPrice = 0;
            let itemCount = 0;
            gridContainer.querySelectorAll('.cr-item').forEach(item => {
                const price = parseInt(item.querySelector('.cr-item-price').textContent);
                const quantity = parseInt(item.querySelector('.cr-quantity-field').textContent);
                totalPrice += price * quantity;
                itemCount += quantity;
            });
            document.querySelector('#result-label').textContent = `Итог (${itemCount} товар)`;
            document.querySelector('#result-value').textContent = `${totalPrice} ₽`;

            document.querySelector('#discount-value').textContent = `- 0 ₽`;

            document.querySelector('#total-value').textContent = `${totalPrice} ₽`;
        } else {
            gridContainer.innerHTML = '';
            renderEmptyCart();
        }
    }

    function renderEmptyCart() {
        authWarningContainer.innerHTML = '';
        authWarningContainer.innerHTML = `
        <svg class="cr-empty-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" 
                stroke-linecap="round" stroke-linejoin="round">
            <path d="M6 2L3 6v14a2 2 0 002 2h14a2 2 0 002-2V6l-3-4z"/>
            <line x1="3" y1="6" x2="21" y2="6"/>
            <path d="M16 10a4 4 0 01-8 0"/>
        </svg>
        `;

        const emptyCartTitle = document.createElement("h2");
        emptyCartTitle.classList.add("cr-empty-title");
        emptyCartTitle.textContent = 'Ваша корзина пока пуста';
        authWarningContainer.appendChild(emptyCartTitle);

        const emptyCartDescription = document.createElement("p");
        emptyCartDescription.classList.add("cr-empty-desc");
        emptyCartDescription.textContent = 'Добавьте товары из каталога, чтобы оформить заказ';
        authWarningContainer.appendChild(emptyCartDescription);

        const catalogLink = document.createElement("a");
        catalogLink.classList.add("cr-empty-btn");
        catalogLink.textContent = 'Перейти в каталог';
        catalogLink.href = '/catalog';
        authWarningContainer.appendChild(catalogLink);

        const mainPageLinkContainer = document.createElement("div");
        mainPageLinkContainer.classList.add("cr-back-link");
        const mainPageLink = document.createElement("a");
        mainPageLink.href = '/';
        mainPageLink.textContent = '← Вернуться на главную';
        mainPageLinkContainer.appendChild(mainPageLink);
        authWarningContainer.appendChild(mainPageLinkContainer);
    }

    function renderAuthWarning() {
        authWarningContainer.innerHTML = `
        <svg class="cr-auth-icon" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round">
            <rect x="3" y="11" width="18" height="11" rx="2" ry="2"></rect>
            <path d="M7 11V7a5 5 0 0110 0v4"></path>
        </svg>
        `;

        const authWarningTitle = document.createElement("h2");
        authWarningTitle.classList.add("cr-empty-title");
        authWarningTitle.textContent = 'Требуется вход в аккаунт';
        authWarningContainer.appendChild(authWarningTitle);

        const emptyCartDescription = document.createElement("p");
        emptyCartDescription.classList.add("cr-empty-desc");
        emptyCartDescription.textContent = 'Для управления корзиной и оформления заказа ' +
            'необходимо войти в аккаунт или создать новый';
        authWarningContainer.appendChild(emptyCartDescription);

        const actionButtonContainer = document.createElement("div");
        actionButtonContainer.classList.add("cr-auth-actions");
        const loginButton = document.createElement("a");
        loginButton.href = '/login';
        loginButton.classList.add("cr-empty-btn");
        loginButton.textContent = 'Войти';
        const registerButton = document.createElement("a");
        registerButton.href = '/register';
        registerButton.classList.add("cr-auth-btn");
        registerButton.classList.add("cr-auth-btn--secondary");
        registerButton.textContent = "Зарегистрироваться";
        actionButtonContainer.appendChild(loginButton);
        actionButtonContainer.appendChild(registerButton);
        authWarningContainer.appendChild(actionButtonContainer);

        const mainPageLinkContainer = document.createElement("div");
        mainPageLinkContainer.classList.add("cr-back-link");
        const mainPageLink = document.createElement("a");
        mainPageLink.href = '/';
        mainPageLink.textContent = '← Вернуться на главную';
        mainPageLinkContainer.appendChild(mainPageLink);
        authWarningContainer.appendChild(mainPageLinkContainer);
    }

    function createResultGroup() {
        const resultGroup = document.createElement("div");
        resultGroup.classList.add('cr-row');

        const resultLabel = document.createElement('span');
        resultLabel.classList.add('cr-row-label');
        resultLabel.id = 'result-label';
        resultGroup.appendChild(resultLabel);
        const resultSummary = document.createElement("span");
        resultSummary.classList.add('cr-row-value');
        resultSummary.id = 'result-value';
        resultGroup.appendChild(resultSummary);
        return resultGroup;
    }

    function createDeliveryGroup() {
        const deliveryGroup = document.createElement("div");
        deliveryGroup.classList.add('cr-row');

        const deliveryLabel = document.createElement('span');
        deliveryLabel.textContent = `Доставка`;
        deliveryLabel.classList.add('cr-row-label');
        deliveryLabel.id = 'delivery-label';
        deliveryGroup.appendChild(deliveryLabel);
        const resultSummary = document.createElement("span");
        resultSummary.textContent = `Бесплатно`;
        resultSummary.classList.add('cr-row-value');
        resultSummary.id = 'result-value';
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
        discountSummary.id = 'discount-value';
        discountSummary.classList.add('cr-row-value');
        discountGroup.appendChild(discountSummary);
        return discountGroup;
    }

    function createTotalGroup() {
        const totalGroup = document.createElement("div");
        totalGroup.classList.add('cr-row');

        const totalLabel = document.createElement('span');
        totalLabel.textContent = `К оплате`;
        totalLabel.classList.add('cr-row-label');
        totalGroup.appendChild(totalLabel);
        const totalSummary = document.createElement("span");
        totalSummary.classList.add('cr-row-value');
        totalSummary.id = 'total-value';
        totalGroup.appendChild(totalSummary);
        return totalGroup;
    }

    function createCartItems(cartItems) {
        const itemsContainer = document.createElement("div");
        itemsContainer.classList.add("cr-items");
        cartItems.forEach((item) => itemsContainer.appendChild(createCartItem(item)));
        gridContainer.appendChild(itemsContainer);
    }

    function createCartItem(item) {
        const cartItem = document.createElement("div");
        cartItem.classList.add("cr-item");

        cartItem.appendChild(createCartItemImage(item.medicine.imagePaths));
        cartItem.appendChild(createCartItemInfo(item));
        cartItem.appendChild(createCartItemAction(item.medicine));

        cartItem.querySelector('button[class=cr-remove]').addEventListener('click', (e) => {
            e.preventDefault();
            cartItem.remove();
            totalCartItemsCounter--;
            updateSummaryValues();
        });
        totalCartItemsCounter++;
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
            image.src = `/api/v1/product-image/${imagePaths[0].id}`;
            image.width = 100;
            image.height = 100;
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

        cartItemInfo.appendChild(createCartItemQuantity(info));
        return cartItemInfo;
    }

    function createCartItemQuantity(info) {
        const quantityCounter = document.createElement("div");
        quantityCounter.classList.add('cr-qty');

        const decrementButton = document.createElement("button");
        decrementButton.type = "button";
        decrementButton.textContent = '-';
        quantityCounter.appendChild(decrementButton);

        const quantityField = document.createElement("span");
        quantityField.classList.add('cr-quantity-field');
        quantityField.textContent = info.amount;
        quantityCounter.appendChild(quantityField);

        const incrementButton = document.createElement("button");
        incrementButton.type = "button";
        incrementButton.textContent = '+';
        quantityCounter.appendChild(incrementButton);

        decrementButton.addEventListener('click', async () => {
            let quantity = parseInt(quantityField.textContent);
            if (quantity > 2) {
                quantity--;
            } else if (quantity === 2) {
                quantity--;
                decrementButton.disabled = true;
            }
            await updateCartItem(quantity, info.medicine.id);
            quantityField.textContent = String(quantity);
            updateSummaryValues();
        });
        incrementButton.addEventListener('click', async () => {
            let quantity = parseInt(quantityField.textContent);
            if (quantity <= 1) {
                quantity++;
                decrementButton.disabled = false;
            } else if (quantity > 1) {
                quantity++;
            }
            await updateCartItem(quantity, info.medicine.id);
            quantityField.textContent = String(quantity);
            updateSummaryValues();
        });
        return quantityCounter;
    }

    async function updateCartItem(quantity, productId) {
        await restClient.fetchData(`/api/v1/carts/me/items`, 'PATCH',
            {'Content-type': 'application/json'},
            JSON.stringify({
                item: {
                    quantity: quantity,
                    productId: productId
                }
            }));
    }

    function createCartItemAction(product) {
        const cartItemActions = document.createElement("div");
        cartItemActions.classList.add("cr-item-actions");

        const itemPrice = document.createElement("span");
        itemPrice.classList.add("cr-item-price");
        itemPrice.textContent = `${product.price} ₽`;
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
        deleteButton.addEventListener('click', async (e) => {
            e.preventDefault();
            await restClient.fetchData(`/api/v1/carts/me/items/${product.id}`, 'DELETE');
            updateSummaryValues();
        })
        cartItemActions.appendChild(deleteButton);
        return cartItemActions;
    }
});