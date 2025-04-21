class ProductManagement {
    constructor() {
        this.tmpDataStore = new Map();

        this.currentStep = 1;
        this.primaryAddress = null;
        this.primaryManufacturer = null;
        this.primaryMedicine = null;
        this.modalOutsideClickListener = null;

        this.resultsContainer = document.getElementById('search-result-container');
        this.searchOutsideClick = null;

        this.DEBOUNCE_DELAY = 300;
        this.initEventListeners();
    }

    initEventListeners() {
        document.querySelectorAll('.btn-delete').forEach(button => {
            button.addEventListener('click', this.handleDelete.bind(this));
        });

        document.querySelectorAll('.btn-update').forEach(button => {
            button.addEventListener('click',
                this.handleUpdate.bind(this, button.getAttribute('data-id')));
        });

        document.getElementById('createBtn').addEventListener('click', this.handleCreate.bind(this));
        document.getElementById('close-modal').addEventListener('click', this.closeCreateMenu.bind(this));
        document.getElementById('search-btn').addEventListener('click', this.handleOpenSearch.bind(this));
        document.getElementById('search-field').addEventListener('input', this.handleSearchInput.bind(this));
    }

    handleOpenSearch() {
        const searchContainer = document.getElementById('search-container');
        const searchResults = document.getElementById('search-result-container');
        searchContainer.style.display = 'block';
        document.getElementById('search-backdrop').style.display = 'block';

        this.searchOutsideClick = (event) => {
            if (searchContainer && !searchContainer.contains(event.target) && !searchResults.contains(event.target)) {
                this.closeSearch();
                document.removeEventListener('click', this.searchOutsideClick);
                this.searchOutsideClick = null;
            }
        };
        setTimeout(() => document.addEventListener('click', this.searchOutsideClick), 10);
    }

    handleSearchInput(e) {
        clearTimeout(this.DEBOUNCE_DELAY);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 1) {
            this.resultsContainer.innerHTML = '';
            return;
        }

        this.DEBOUNCE_DELAY = setTimeout(() => {
            this.fetchResults(searchTerm);
        }, this.DEBOUNCE_DELAY);
    }

    async handleDelete(event) {
        const productId = event.currentTarget.getAttribute('data-id');
        if (confirm('Удалить товар?')) {
            try {
                await this.fetchData(`/api/admin/product/${productId}`, `DELETE`);
                event.currentTarget.closest('.product-item').remove();
            } catch (error) {
                alert('Ошибка при удалении: ' + error.message);
            }
        }
    }

    async fetchData(url, method, body = null) {
        const headers = {
            'Content-Type': 'application/json'
        };

        try {
            const response = await fetch(url, {
                method,
                headers,
                body: body ? JSON.stringify(body) : null
            });

            if (response.status === 403)
                throw new Error('Требуется авторизация');
            if (!response.ok) {
                const errorText = await response.text();
                try {
                    const errorJson = JSON.parse(errorText);
                    throw new Error(errorJson.message || errorText);
                } catch {
                    throw new Error(errorText);
                }
            }

            return response;
        } catch (error) {
            console.error(`${method} error:`, error);
            alert(`${method} ошибка:` + error);
            throw error;
        }
    }

    get stepConfig() {
        return {
            1: {
                fields: ['itn', 'supplierName', 'subject', 'settlement', 'street', 'house', 'postalCode'],
                validate: (formData) => {
                    if (!/^\d{10}$/.test(formData.itn)) throw new Error('Неверный ИНН');
                    return true;
                }
            },
            2: {
                fields: ['manufacturerName', 'country'],
                validate: (formData) => {
                    if (!/^[A-Za-zА-Яа-я\s-]+$/.test(formData.country)) throw new Error('Некорректная страна');
                    return true;
                }
            },
            3: {
                fields: ['name', 'type', 'price', 'recipe'],
                validate: (formData) => {
                    if (isNaN(formData.price) || formData.price <= 0) throw new Error('Некорректная цена');
                    return true;
                }
            }
        };
    }

    // Обновленная валидация с проверкой типов
    validateStep(step) {
        const config = this.stepConfig[step];
        const formData = this.getFormData(config.fields);

        try {
            return config.validate(formData);
        } catch (error) {
            return false;
        }
    }

    // Получение данных формы
    getFormData(fields) {
        return fields.reduce((data, field) => {
            if (field === "recipe") {
                data[field] = document.querySelector('input[name="recipe"]:checked').value === "1";
                return data;
            } else {
                const element = document.getElementById(field);
                if (!element) throw new Error(`Поле ${field} не найдено`);
                data[field] = element.value.trim();
                return data;
            }
        }, {});
    }

    handleCreate() {
        this.showCreateMenu(false);
    }

    async handleUpdate(id) {
        try {
            const response = await this.fetchData(`/api/admin/product/${id}`, 'GET');

            if (!response.ok)
                throw new Error(response.statusText);

            const data = await response.json();

            this.primaryMedicine = data.id;

            this.tmpDataStore.set(this.primaryMedicine, data);
            for (let i = 1; i < 4; i++) {
                this.fillForm(this.primaryMedicine, i);
            }
            this.showCreateMenu(true);
        }
        catch (error) {
            console.log(error);
            alert("Ошибка при подготовке обновления элемента: " + error.message);
        }
    }

    async fillProduct() {
        return {
            name: document.getElementById('name').value,
            type: document.getElementById('type').value,
            manufacturer: {
                id: this.primaryManufacturer,
                name: document.getElementById('manufacturerName').value,
                country: {
                    country: document.getElementById('country').value,
                }
            },
            supplier: {
                itn: document.getElementById('itn').value,
                name: document.getElementById('supplierName').value,
                address: {
                    id: this.primaryAddress,
                    subject: document.getElementById('subject').value,
                    district: document.getElementById('district').value.trim() || null,
                    settlement: document.getElementById('settlement').value,
                    street: document.getElementById('street').value,
                    house: document.getElementById('house').value,
                    apartment: document.getElementById('apartment').value.trim() || null,
                    postalCode: document.getElementById('postalCode').value
                }
            },
            price: document.getElementById('price').value,
            recipe: document.querySelector('input[name="recipe"]:checked').value === "1"
        };
    }

    showCreateMenu(updateMode) {
        const modal = document.getElementById('modal');
        modal.style.display = 'block';
        document.getElementById('modalBackdrop').style.display = 'block';
        this.initFormSteps(updateMode);

        this.modalOutsideClickListener = (event) => {
            const isSearchActive = document.getElementById('search-container').style.display === 'block';
            if (modal && !modal.contains(event.target) && !isSearchActive) {
                this.closeCreateMenu();
                document.removeEventListener('click', this.modalOutsideClickListener);
                this.modalOutsideClickListener = null;
            }
        };
        setTimeout(() => document.addEventListener('click', this.modalOutsideClickListener), 10);
    }

    closeCreateMenu() {
        document.getElementById('modal').style.display = 'none';
        document.getElementById('modalBackdrop').style.display = 'none';

        this.resetForm();
        if (this.modalOutsideClickListener !== null) {
            document.removeEventListener('click', this.modalOutsideClickListener);
            this.modalOutsideClickListener = null;
        }
    }

    resetForm() {
        this.currentStep = 1;
        this.primaryMedicine = null;
        this.primaryManufacturer = null;
        this.primaryAddress = null;
        this.tmpDataStore.clear();

        for (let i = 1; i < 4; i++) {
            const config = this.stepConfig[i];
            config.fields.forEach((field) => {
                const element = document.getElementById(field);
                if (element) {
                    element.value = '';
                } else if (field === 'recipe') {
                    document.querySelector('input[name="recipe"][value="0"]').checked = true;
                }
                else {
                    console.warn(`Элемент с ID "${field}" не найден`);
                }
            }, {});
        }
    }

    initFormSteps(updateMode) {
        this.currentStep = 1;
        this.showStep(this.currentStep);
        this.setupNavigation(updateMode);
    }

    async fetchResults(searchTerm) {
        try {
            if (this.currentStep === 1) {
                const data =
                    await (await this.fetchData(`/api/search/supplier?name=${searchTerm}`, 'GET')).json();
                this.displayResults(data);
            } else {
                const data =
                    await (await this.fetchData(`/api/search/manufacturer?name=${searchTerm}`, 'GET')).json();
                this.displayResults(data);
            }
        } catch (error) {
            console.error('Ошибка:', error);
        }
    }

    // Отображение результатов
    displayResults(results) {
        this.resultsContainer.innerHTML = '';
        if (results.length === 0) {
            this.primaryManufacturer = null;
            this.primaryAddress = null;
            return;
        }

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = result.name;
            div.onclick = () => this.handleSearchClick(result);
            this.saveTMPData(result, div);

            this.resultsContainer.appendChild(div);
        });
    }

    handleSearchClick(result) {
        if (this.currentStep === 1) {
            this.fillForm(result.itn, this.currentStep);
        } else if (this.currentStep === 2) {
            this.fillForm(result.id, this.currentStep);
        }
        setTimeout(() => this.closeSearch(), 10);
    }

    closeSearch() {
        document.getElementById('search-container').style.display = 'none';
        document.getElementById('search-backdrop').style.display = 'none';

        document.getElementById('search-field').value = '';
        this.resultsContainer.innerHTML = '';

        if (this.searchOutsideClick !== null) {
            document.removeEventListener('click', this.searchOutsideClick);
            this.searchOutsideClick = null;
        }
    }

    saveTMPData(result, parent) {
        if (this.currentStep === 2) {
            const dataToStore = {
                manufacturer: {
                    id: result.id,
                    name: result.name,
                    country: result.country
                }
            };
            this.primaryManufacturer = result.id;

            // Сохраняем данные через WeakMap
            this.tmpDataStore.set(this.primaryManufacturer, dataToStore);

            // Дополнительно сохраняем в dataset для доступа из CSS/HTML
            parent.dataset.tmpData = JSON.stringify(dataToStore);
        } else {
            const dataToStore = {
                supplier: {
                    itn: result.itn,
                    name: result.name,
                    address: result.address
                }
            };
            this.primaryAddress = result.itn;

            // Сохраняем данные через WeakMap
            this.tmpDataStore.set(this.primaryAddress, dataToStore);

            // Дополнительно сохраняем в dataset для доступа из CSS/HTML
            parent.dataset.tmpData = JSON.stringify(dataToStore);
        }
    }

    fillForm(id, step) {
        try {
            const data = this.tmpDataStore.get(id);
            const form = document.forms.productCreationForm;

            if (!data)
                throw new Error("Продукт не найден");
            if (step === 3) {
                this.primaryMedicine = data.id;

                form.elements.name.value = data.name;
                form.elements.type.value = data.type;
                form.elements.price.value = data.price;
                form.elements.recipe.value = data.recipe;
            }
            if (step === 2) {
                form.elements.manufacturerName.value = data.manufacturer.name;
                form.elements.country.value = data.manufacturer.country;
            } else if (step === 1) {
                form.elements.itn.value = data.supplier.itn;
                form.elements.supplierName.value = data.supplier.name;

                form.elements.subject.value = data.supplier.address.subject;
                form.elements.district.value = data.supplier.address.district || '';
                form.elements.settlement.value = data.supplier.address.settlement;
                form.elements.street.value = data.supplier.address.street;
                form.elements.house.value = data.supplier.address.house;
                form.elements.apartment.value = data.supplier.address.apartment || '';
                form.elements.postalCode.value = data.supplier.address.postalCode;
            }
        } catch (error) {
            console.error(error);
            alert("Произошла ошибка при заполнении: " + error);
        }
    }

    showStep(stepNumber) {
        const steps = ['supplierPart', 'manufacturerPart', 'productPart'];

        steps.forEach(step => {
            document.getElementById(step).style.display = 'none';
        });

        document.getElementById(steps[stepNumber - 1]).style.display = 'block';

        this.updateButtons(stepNumber);
    }

    updateButtons(step) {
        const prevBtn = document.getElementById('cancel');
        const nextBtn = document.getElementById('accept');

        if (step === 3) {
            prevBtn.textContent = 'Назад';
            nextBtn.textContent = 'Создать';
        } else if (step === 2) {
            prevBtn.textContent = 'Назад';
            nextBtn.textContent = 'Далее';
        } else {
            prevBtn.textContent = 'Отмена';
            nextBtn.textContent = 'Далее';
        }
    }

    setupNavigation(updateMode) {
        document.getElementById('cancel').addEventListener('click', () => {
            if (this.currentStep > 1) {
                this.currentStep--;
                this.showStep(this.currentStep);
            } else {
                this.closeCreateMenu();
            }
        });

        document.getElementById('accept').addEventListener('click', async () => {
            if (this.currentStep < 3) {
                if (this.validateStep(this.currentStep)) {
                    this.currentStep++;
                    this.showStep(this.currentStep);
                } else {
                    alert("Ошибка валидации");
                }
            } else {
                if (this.validateStep(this.currentStep)) {
                    if (updateMode) {
                        await this.fetchData(`/api/admin/product/${this.primaryMedicine}`,
                            'PUT', await this.fillProduct());
                    } else {
                        await this.fetchData(`/api/admin/product`,
                            'POST', await this.fillProduct());
                    }
                    this.closeCreateMenu();
                } else {
                    alert("Ошибка валидации");
                }
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const productManager = new ProductManagement();
});
