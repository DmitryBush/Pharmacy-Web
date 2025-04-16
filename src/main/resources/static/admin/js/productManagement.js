const tmpDataStore = new Map();
let modalOutsideClickListener = null;
let searchOutsideClick = null;

document.querySelectorAll('.btn-delete').forEach(button => {
    button.addEventListener('click', async function() {
        const productId = this.getAttribute('data-id');
        if (confirm('Удалить товар?')) {
            try {
                await deleteProduct(productId);
                // Удаляем элемент из DOM после успешного удаления
                this.closest('.product-item').remove();
            } catch (error) {
                alert('Ошибка при удалении: ' + error.message);
            }
        }
    });
});

document.querySelectorAll('.btn-update').forEach(button => {
    button.addEventListener('click', async function() {
        const productId = this.getAttribute('data-id');
        try {

        } catch (error) {
            alert('Ошибка при обновлении: ' + error.message);
        }
    });
});

document.getElementById('createBtn').addEventListener('click', showCreateMenu);

async function createProduct() {
    const form = document.getElementById('productCreationForm');
    const product = fillProduct();

    try {
        const response = await fetch('/api/admin/product', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });

        if (response.status === 403) {
            throw new Error('Требуется авторизация')
        }
        else if (!response.ok) {
            throw new Error(response.statusText);
        }

        closeCreateMenu();

        form.reset();
        return response.status;
    } catch (error) {
        console.error('Create error:', error);
        alert('Ошибка при создании: ' + error.message);
    }
}

async function fillProduct() {
    return {
        id: primaryMedicine,
        name: document.getElementById('name').value,
        type: document.getElementById('type').value,
        manufacturer: {
            id: primaryManufacturer,
            name: document.getElementById('manufacturerName').value,
            country: {
                country: document.getElementById('country').value,
            }
        },
        supplier: {
            itn: document.getElementById('itn').value,
            name: document.getElementById('supplierName').value,
            address: {
                id: tmpDataStore.get(primaryAddress).address.id,
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
        recipe: document.querySelector('input[name="recipe"]:checked').value === "1",
    };
}

function showCreateMenu() {
    const modal = document.getElementById('modal');
    modal.style.display = 'block';
    document.getElementById('modalBackdrop').style.display = 'block';
    initFormSteps();

    modalOutsideClickListener = (event) => {
        const isSearchActive = document.getElementById('search-container').style.display === 'block';
        if (modal && !modal.contains(event.target) && !isSearchActive) {
            closeCreateMenu();
            document.removeEventListener('click', modalOutsideClickListener);
            modalOutsideClickListener = null;
        }
    };
    setTimeout(() => document.addEventListener('click', modalOutsideClickListener), 10);
}

function closeCreateMenu() {
    document.getElementById('modal').style.display = 'none';
    document.getElementById('modalBackdrop').style.display = 'none';

    primaryMedicine = null;
    primaryManufacturer = null;
    primaryAddress = null;
    if (modalOutsideClickListener !== null) {
        document.removeEventListener('click', modalOutsideClickListener);
        modalOutsideClickListener = null;
    }
}

// function handleOutsideClick(element, callback) {
//     setTimeout(() => {
//         const listener = (event) => {
//             if (element && !element.contains(event.target)) {
//                 callback();
//                 document.removeEventListener('click', listener);
//             }
//         };
//         document.addEventListener('click', listener);
//     }, 10);
// }

async function deleteProduct(id) {
    try {
        const response = await fetch(`/api/admin/product/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.status === 403) {
            throw new Error('Требуется авторизация');
        }

        if (!response.ok) {
            throw new Error(response.statusText);
        }
        return response.status;
    } catch (error) {
        console.error('Delete error:', error);
        throw error;
    }
}

let currentStep = 1;
let primaryAddress = null;
let primaryManufacturer = null;
let primaryMedicine = null;

function initFormSteps() {
    currentStep = 1;
    showStep(currentStep);
    setupNavigation();
}

function showStep(stepNumber) {
    const steps = ['supplierPart', 'manufacturerPart', 'productPart'];

    steps.forEach(step => {
        document.getElementById(step).style.display = 'none';
    });

    document.getElementById(steps[stepNumber - 1]).style.display = 'block';

    updateButtons(stepNumber);
}

function updateButtons(step) {
    const prevBtn = document.getElementById('cancel');
    const nextBtn = document.getElementById('accept');

    if (step === 3) {
        prevBtn.style.display = 'Отмена';
        nextBtn.textContent = 'Создать';
    } else {
        prevBtn.style.display = 'Отмена';
        nextBtn.textContent = 'Далее';
    }
}

function setupNavigation() {
    document.getElementById('cancel').addEventListener('click', () => {
        if (currentStep > 1) {
            currentStep--;
            showStep(currentStep);
        } else {
            closeCreateMenu();
        }
    });

    document.getElementById('accept').addEventListener('click', async () => {
        if (currentStep < 3) {
            if (validateStep(currentStep)) {
                currentStep++;
                showStep(currentStep);
            } else {
                alert("Ошибка валидации");
            }
        } else {
            if (validateStep(currentStep)) {
                await createProduct();
            } else {
                alert("Ошибка валидации");
            }
        }
    });
}

function validateStep(step) {
    const currentStepElement = document.getElementById(
        ['supplierPart', 'manufacturerPart', 'productPart'][step - 1]
    );

    const inputs = currentStepElement.querySelectorAll('input[required]');
    let isValid = true;

    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('invalid');
            isValid = false;
        } else {
            input.classList.remove('invalid');
        }
    });

    return isValid;
}

const resultsContainer = document.getElementById('search-result-container');

// Задержка перед отправкой запроса (в миллисекундах)
let debounceTimer;
const DEBOUNCE_DELAY = 300;

// Обработчик ввода
document.getElementById('search-field').addEventListener('input', function(e) {
    clearTimeout(debounceTimer);
    const searchTerm = e.target.value.trim();

    if (searchTerm.length < 3) {
        resultsContainer.innerHTML = '';
        return;
    }

    debounceTimer = setTimeout(() => {
        fetchResults(searchTerm);
    }, DEBOUNCE_DELAY);
});

// Запрос к бэкенду
async function fetchResults(searchTerm) {
    try {
        if (currentStep === 1) {
            const response = await fetch(`/api/search/supplier?name=${searchTerm}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            displayResults(data);
        } else {
            const response = await fetch(`/api/search/manufacturer?name=${searchTerm}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.json();
            displayResults(data);
        }
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

// Отображение результатов
function displayResults(results) {
    resultsContainer.innerHTML = '';
    if (results.length === 0) {
        primaryManufacturer = null;
        primaryAddress = null;
        // resultsContainer.innerHTML = '<div class="result-item">Нет результатов</div>';
        return;
    }

    results.forEach(result => {
        const div = document.createElement('div');
        div.className = 'result-item';
        div.textContent = result.name;
        div.onclick = () => handleSearchClick(result);
        saveTMPData(result, div);

        resultsContainer.appendChild(div);
    });
}

function handleSearchClick(result) {
    if (currentStep === 1) {
        fillForm(result.itn, document.forms.productCreationForm, currentStep);
    } else if (currentStep === 2) {
        fillForm(result.id, document.forms.productCreationForm, currentStep);
    }
    setTimeout(() => closeSearch(), 10);
}

document.getElementById('search-btn').addEventListener('click', () => {
    const searchContainer = document.getElementById('search-container');
    const searchResults = document.getElementById('search-result-container');
    searchContainer.style.display = 'block';
    document.getElementById('search-backdrop').style.display = 'block';

    searchOutsideClick = (event) => {
        if (searchContainer && !searchContainer.contains(event.target) && !searchResults.contains(event.target)) {
            closeSearch();
            document.removeEventListener('click', searchOutsideClick);
            searchOutsideClick = null;
        }
    };
    setTimeout(() => document.addEventListener('click', searchOutsideClick), 10);
});

function closeSearch() {
    document.getElementById('search-container').style.display = 'none';
    document.getElementById('search-backdrop').style.display = 'none';

    if (searchOutsideClick !== null) {
        document.removeEventListener('click', searchOutsideClick);
        searchOutsideClick = null;
    }
}

function saveTMPData(result, parent) {
    if (currentStep === 2) {
        const dataToStore = {
            id: result.id,
            name: result.name,
            country: result.country
        };

        // Сохраняем данные через WeakMap
        tmpDataStore.set(result.id, dataToStore);

        // Дополнительно сохраняем в dataset для доступа из CSS/HTML
        parent.dataset.tmpData = JSON.stringify(dataToStore);
    } else {
        const dataToStore = {
            itn: result.itn,
            name: result.name,
            address: result.address
        };

        // Сохраняем данные через WeakMap
        tmpDataStore.set(result.itn, dataToStore);

        // Дополнительно сохраняем в dataset для доступа из CSS/HTML
        parent.dataset.tmpData = JSON.stringify(dataToStore);
    }
}

function fillForm(id, form, step) {
    const data = tmpDataStore.get(id);
    if (step === 3) {
        primaryMedicine = data.id;

        form.elements.name = data.name;
        form.elements.type = data.type;
        form.elements.price = data.price;
        form.elements.recipe = data.recipe;
    }if (step === 2) {
        primaryManufacturer = data.id;

        form.elements.manufacturerName.value = data.name;
        form.elements.country.value = data.country;
    } else {
        primaryAddress = data.itn;

        form.elements.itn.value = data.itn;
        form.elements.supplierName.value = data.name;

        form.elements.subject.value = data.address.subject;
        form.elements.district.value = data.address.district || '';
        form.elements.settlement.value = data.address.settlement;
        form.elements.street.value = data.address.street;
        form.elements.house.value = data.address.house;
        form.elements.apartment.value = data.address.apartment || '';
        form.elements.postalCode.value = data.address.postalCode;
    }
}