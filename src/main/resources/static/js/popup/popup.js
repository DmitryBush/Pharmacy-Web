import {loadCSS} from "../ResourceLoader.js";

export default class PopupManager {
    constructor() {
        this.popup = null;
        this.popupOutsideClick = null;

        this._target = null;

        this._popupContent = null;

        this._submitAction = null;
        this._cancelAction = null;
    }

    async createPopup() {
        this.validatePopup();

        this.popup = document.createElement('div');
        this.popup.className = 'popup';
        this.popup.style.position = 'absolute';

        const rect = this._target.getBoundingClientRect();

        this.popup.innerHTML = '<div class="loader-container"><div class="loader"></div></div>';
        document.body.appendChild(this.popup);

        this.popup.style.width = `${rect.width}px`;
        this.popup.style.height = `${rect.height}px`;
        this.popup.style.opacity = '0';

        setTimeout(() => {
            this.popup.style.transition = 'all 0.4s cubic-bezier(0.18, 0.89, 0.32, 1.28)';

            this.popup.style.top = `${rect.bottom - (rect.bottom - rect.top)}px`;
            this.popup.style.left = `${rect.left + window.scrollX}px`;

            this.popup.style.width = '300px';
            this.popup.style.height = 'auto';
            this.popup.style.opacity = '1';

            this.popupOutsideClick = (event) => {
                if (!this.popup.contains(event.target) && event.target !== this.target)
                    this.closePopup(rect);
            }
            document.addEventListener('click', this.popupOutsideClick);
        }, 10);

        this.popup.innerHTML = `
                ${await this._popupContent}
                ${this._submitAction ? '<button id="submitButton">Применить</button>' : ''}
                <button id="cancelButton">Отменить</button>
        `;

        if (this._submitAction) {
            this.popup.querySelector('#submitButton').addEventListener('click', () => {
                this._submitAction();
                this.closePopup(rect);
            });
        }
        this.popup.querySelector('#cancelButton')
            .addEventListener('click', () => {
                if (this._cancelAction)
                    this._cancelAction();
                this.closePopup(rect);
            });
    }

    validatePopup() {
        if (this.target === null || this.target === undefined) {
            throw new Error('target must be defined');
        } else if (this.popupContent === null || this.popupContent === undefined) {
            throw new Error('popupContent must be defined');
        } else if (this.submitAction === null || this.submitAction === undefined) {
            console.warn('popupContent isn`t defined');
        }
    }

    closePopup(rect) {
        this.popup.innerHTML = '';
        this.popup.style.transition = 'all 0.4s ease';
        this.popup.style.width = `${rect.width}px`;
        this.popup.style.height = `${rect.height}px`;
        this.popup.style.opacity = '0';

        document.removeEventListener('click', this.popupOutsideClick);
        this.popupOutsideClick = null;

        setTimeout(() => {
            document.body.removeChild(this.popup);
            this.popup = null;
        }, 300);
    }

    set target(value) {
        this._target = value;
    }

    set popupContent(value) {
        this._popupContent = value;
    }

    set submitAction(value) {
        this._submitAction = value;
    }

    set cancelAction(value) {
        this._cancelAction = value;
    }

    get target() {
        return this._target;
    }

    setTarget(value) {
        this._target = value;
        return this;
    }

    get popupContent() {
        return this._popupContent;
    }

    setPopupContent(value) {
        this._popupContent = value;
        return this;
    }

    get submitAction() {
        return this._submitAction;
    }

    setSubmitAction(value) {
        this._submitAction = value;
        return this;
    }

    get cancelAction() {
        return this._cancelAction;
    }

    setCancelAction(value) {
        this._cancelAction = value;
        return this;
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadCSS('/css/popup/popup.css');
})