import RestClient from "../RestClient.js";

export class RegisterModal {
    constructor() {
        this.modal = null;
        this.restClient = new RestClient();
        this._init();
    }

    static getInstance() {
        if (!this._instance) {
            this._instance = new RegisterModal();
        }
        return this._instance;
    }

    _init() {
        if (document.getElementById('register-modal')) return;

        const modalHTML = `
            <div class="overlay" id="register-modal-overlay">
            <div class="modal" role="dialog" aria-modal="true">
                <button class="close-modal-login-btn" id="close-modal-register-btn" aria-label="Закрыть">&times;</button>
                
                <h2>Регистрация</h2>
                <p class="subtitle">Для регистрации заполните следующие поля</p>
                <form class="login-form" id="register-form">
                    <div class="form-group">
                        <label class="form-label" for="name">
                            Введите имя
                        </label>
                        <input id="name" name="name" type="text"
                               minlength="2"
                               maxlength="25"
                               placeholder="Имя"
                               class="form-input"
                               required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="surname">
                            Введите фамилию
                        </label>
                        <input id="surname" name="surname" type="text"
                               minlength="2"
                               maxlength="25"
                               placeholder="Фамилия"
                               class="form-input"
                               required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="last_Name">
                            Введите отчество
                        </label>
                        <input id="last_Name" name="lastName" type="text"
                               maxlength="25"
                               placeholder="Отчество"
                               class="form-input"
                               required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="username">
                            Введите номер телефона
                        </label>
                        <input id="username" name="mobilePhone" type="tel"
                               placeholder="+7 (___) ___-__-__"
                               pattern="^(\\+7|8)\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$"
                               class="form-input"
                               required>
                    </div>
                    <div class="form-group">
                        <label class="form-label" for="password">
                            Введите пароль
                        </label>
                        <input id="password" name="password" type="password"
                               minlength="4"
                               maxlength="256"
                               placeholder="Введите пароль"
                               class="form-input"
                               required>
                    </div>
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Зарегистрироваться</button>
                    </div>
                </form>
            </div>
        </div>
        `;
        document.body.insertAdjacentHTML('beforeend', modalHTML);
        this._attachEventListeners();
    }

    _attachEventListeners() {
        this.modal = document.getElementById('register-modal-overlay');
        const closeBtn = document.getElementById('close-modal-register-btn');
        this.form = document.getElementById('register-form');
        this.overlay = this.modal;

        closeBtn.addEventListener('click', () => this.hide());

        this.overlay.addEventListener('click', (e) => {
            if (e.target === this.overlay) {
                this.hide();
            }
        });

        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && this.overlay.classList.contains('active')) {
                this.hide();
            }
        });

        this.form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const btn = this.form.querySelector('.btn-primary');
            const btnOriginalText = btn.textContent;

            btn.textContent = 'Регистрация...';
            btn.style.opacity = '0.8';
            const data = new FormData(e.target);
            try {
                await this.restClient.fetchData('/register', 'POST',
                    {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    new URLSearchParams({
                        mobilePhone: data.get('username'),
                        password: data.get('password'),
                        name: data.get('name'),
                        surname: data.get('surname'),
                        lastName: data.get('lastName'),
                    }));
                setTimeout(() => {
                    btn.textContent = 'Успешно!';
                    btn.style.background = '#55a630';
                    btn.style.opacity = '1';
                    setTimeout(() => {
                        this.hide();
                        btn.textContent = btnOriginalText;
                        btn.style.background = '';
                        window.location.reload();
                    }, 1000);
                }, 1200);
            } catch (error) {
                setTimeout(() => {
                    btn.textContent = 'Произошла ошибка. Пожалуйста, повторите позже';
                    btn.style.background = '#db222a';
                    btn.style.opacity = '1';
                    setTimeout(() => {
                        btn.textContent = btnOriginalText;
                        btn.style.background = '';
                    }, 1000);
                }, 1200);
            }
        })
    }

    show() {
        this.overlay.classList.add('active');
    }

    hide() {
        this.overlay.classList.remove('active');
        this.form.reset();
    }

    isVisible() {
        return !this.modal?.hidden;
    }
}