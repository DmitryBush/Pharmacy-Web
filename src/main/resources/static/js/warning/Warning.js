import {LoginModal} from "../login/login-modal.js";
import {RegisterModal} from "../login/register-modal.js";

export function renderAuthWarning(authWarningContainer, warningDescription) {
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
    emptyCartDescription.textContent = warningDescription;
    authWarningContainer.appendChild(emptyCartDescription);

    const actionButtonContainer = document.createElement("div");
    actionButtonContainer.classList.add("cr-auth-actions");
    const loginButton = document.createElement("button");
    loginButton.classList.add("cr-empty-btn");
    loginButton.textContent = 'Войти';
    loginButton.addEventListener('click', () => {
        const loginModal = LoginModal.getInstance();
        loginModal.show();
    });
    const registerButton = document.createElement("button");
    registerButton.classList.add("cr-auth-btn");
    registerButton.classList.add("cr-auth-btn--secondary");
    registerButton.textContent = "Зарегистрироваться";
    registerButton.addEventListener('click', () => {
        const registerModal = RegisterModal.getInstance();
        registerModal.show();
    });
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

export function renderEmptyCart(warningContainer, warningDescription) {
    warningContainer.innerHTML = '';
    warningContainer.innerHTML = `
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
    warningContainer.appendChild(emptyCartTitle);

    const emptyCartDescription = document.createElement("p");
    emptyCartDescription.classList.add("cr-empty-desc");
    emptyCartDescription.textContent = warningDescription;
    warningContainer.appendChild(emptyCartDescription);

    const catalogLink = document.createElement("a");
    catalogLink.classList.add("cr-empty-btn");
    catalogLink.textContent = 'Перейти в каталог';
    catalogLink.href = '/catalog';
    warningContainer.appendChild(catalogLink);

    const mainPageLinkContainer = document.createElement("div");
    mainPageLinkContainer.classList.add("cr-back-link");
    const mainPageLink = document.createElement("a");
    mainPageLink.href = '/';
    mainPageLink.textContent = '← Вернуться на главную';
    mainPageLinkContainer.appendChild(mainPageLink);
    warningContainer.appendChild(mainPageLinkContainer);
}

export function renderSuccessfulWarning(warningContainer, warningTitle, warningDescription, actionHref, actionText) {
    warningContainer.innerHTML = '';
    warningContainer.innerHTML = `
        <svg class="cr-success-icon" viewBox="0 0 24 24" fill="none" stroke="#27ae60" 
        stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"></circle>
            <path d="M8 12l3 3 5-5"></path>
        </svg>
        `;

    const emptyCartTitle = document.createElement("h2");
    emptyCartTitle.classList.add("cr-empty-title");
    emptyCartTitle.textContent = warningTitle;
    warningContainer.appendChild(emptyCartTitle);

    const emptyCartDescription = document.createElement("p");
    emptyCartDescription.classList.add("cr-empty-desc");
    emptyCartDescription.textContent = warningDescription;
    warningContainer.appendChild(emptyCartDescription);

    const catalogLink = document.createElement("a");
    catalogLink.classList.add("cr-empty-btn");
    catalogLink.textContent = actionText;
    catalogLink.href = actionHref;
    warningContainer.appendChild(catalogLink);

    const mainPageLinkContainer = document.createElement("div");
    mainPageLinkContainer.classList.add("cr-back-link");
    const mainPageLink = document.createElement("a");
    mainPageLink.href = '/';
    mainPageLink.textContent = '← Вернуться на главную';
    mainPageLinkContainer.appendChild(mainPageLink);
    warningContainer.appendChild(mainPageLinkContainer);
}

export function renderErrorWarning(warningContainer, warningTitle, warningDescription) {
    warningContainer.innerHTML = '';
    warningContainer.innerHTML = `
        <svg class="cr-fail-icon" viewBox="0 0 24 24" fill="none" stroke="#e74c3c" stroke-width="2" 
            stroke-linecap="round" stroke-linejoin="round">
            <circle cx="12" cy="12" r="10"></circle>
            <line x1="15" y1="9" x2="9" y2="15"></line>
            <line x1="9" y1="9" x2="15" y2="15"></line>
        </svg>
        `;

    const emptyCartTitle = document.createElement("h2");
    emptyCartTitle.classList.add("cr-empty-title");
    emptyCartTitle.textContent = warningTitle;
    warningContainer.appendChild(emptyCartTitle);

    const emptyCartDescription = document.createElement("p");
    emptyCartDescription.classList.add("cr-empty-desc");
    emptyCartDescription.textContent = warningDescription;
    warningContainer.appendChild(emptyCartDescription);

    const catalogLink = document.createElement("a");
    catalogLink.classList.add("cr-empty-btn");
    catalogLink.textContent = 'Обновить страницу';
    catalogLink.href = window.location;
    warningContainer.appendChild(catalogLink);

    const mainPageLinkContainer = document.createElement("div");
    mainPageLinkContainer.classList.add("cr-back-link");
    const mainPageLink = document.createElement("a");
    mainPageLink.href = '/';
    mainPageLink.textContent = '← Вернуться на главную';
    mainPageLinkContainer.appendChild(mainPageLink);
    warningContainer.appendChild(mainPageLinkContainer);
}