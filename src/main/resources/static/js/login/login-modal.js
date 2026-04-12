import RestClient from "../RestClient.js";

export class LoginModal {
    constructor() {
        this.modal = null;
        this.restClient = new RestClient();
        this._init();
    }

    static getInstance() {
        if (!this._instance) {
            this._instance = new LoginModal();
        }
        return this._instance;
    }

    _init() {
        if (document.getElementById('login-modal')) return;

        const modalHTML = `
            <div class="overlay" id="login-modal-overlay">
            <div class="modal" role="dialog" aria-modal="true">
                <button class="close-modal-login-btn" id="close-modal-login-btn" aria-label="Закрыть">&times;</button>
                
                <h2>Вход</h2>
                <p class="subtitle">Для продолжения необходимо авторизоваться</p>
                
                <form class="login-form" id="login-form">
                    <div class="form-group">
                        <label class="form-label" for="username">Мобильный телефон</label>
                        <input type="tel" 
                            id="username" 
                            name="username"
                            pattern="^(\\+7|8)\\s\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$" 
                            class="form-input" 
                            placeholder="+7 (___) ___-__-__" required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="password">Пароль</label>
                        <input type="password" 
                            name="password" 
                            id="password" 
                            class="form-input" 
                            placeholder="Введите пароль" required>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn-primary">Войти</button>
                    </div>
                </form>
                
                <div class="form-footer">
                    <p>Нет аккаунта? <a href="/register">Зарегистрироваться</a></p>
                </div>
            </div>
        </div>
        `;
        document.body.insertAdjacentHTML('beforeend', modalHTML);
        this._attachEventListeners();
    }

    _attachEventListeners() {
        this.modal = document.getElementById('login-modal-overlay');
        const closeBtn = this.modal.querySelector('.close-modal-login-btn');
        this.form = document.getElementById('login-form');
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
            const originalText = btn.textContent;

            btn.textContent = 'Вход...';
            btn.style.opacity = '0.8';
            const data = new FormData(e.target);
            try {
                await this.restClient.fetchData(`/login`, 'POST',
                    {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-Requested-With': 'XMLHttpRequest'
                    },
                    new URLSearchParams({
                        username: data.get('username'),
                        password: data.get('password')
                    }));
                setTimeout(() => {
                    btn.textContent = 'Успешно!';
                    btn.style.background = '#55a630';
                    btn.style.opacity = '1';
                    setTimeout(() => {
                        this.hide();
                        btn.textContent = originalText;
                        btn.style.background = '';
                        window.location.reload();
                    }, 1000);
                }, 1200);
            } catch (err) {
                setTimeout(() => {
                    btn.textContent = 'Неверный номер телефона или пароль';
                    btn.style.background = '#db222a';
                    btn.style.opacity = '1';
                    setTimeout(() => {
                        btn.textContent = originalText;
                        btn.style.background = '';
                    }, 1000);
                }, 1200);
            }
        });
    }

    show() {
        this.overlay.classList.add('active');
        setTimeout(() => document.getElementById('email').focus(), 300);
    }

    hide() {
        this.overlay.classList.remove('active');
        this.form.reset();
    }

    isVisible() {
        return !this.modal?.hidden;
    }
}