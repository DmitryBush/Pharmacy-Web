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
    button.addEventListener('click',function() {
        const productId = this.getAttribute('data-id');
        try {
            updateProduct(productId);
        } catch (error) {
            alert('Ошибка при обновлении: ' + error.message);
        }
    });
});

document.getElementById('createBtn').addEventListener('click', createProduct);

async function sendProduct(url, method) {
    const product = await fillProduct();
    try {
        const response = await getResponse(url, method, product);

        if (response.status === 403) {
            throw new Error('Требуется авторизация')
        }
        else if (!response.ok) {
            throw new Error(response.statusText);
        }

        return response.status;
    } catch (error) {
        console.error('Create error:', error);
        alert('Ошибка при создании: ' + error.message);
    }
}

function createProduct() {
    showCreateMenu(false);
}

async function updateProduct(id) {
    try {
        const response = await getResponse(`/api/admin/product/${id}`, 'GET');

        if (!response.ok)
            throw new Error(response.statusText);

        const data = await response.json();

        primaryMedicine = data.id;

        tmpDataStore.set(primaryMedicine, data);
        for (let i = 1; i < 4; i++) {
            fillForm(primaryMedicine, i);
        }
        showCreateMenu(true);
    }
    catch (error) {
        console.log(error);
        alert("Ошибка при подготовке обновления элемента: " + error.message);
    }
}

async function fillProduct() {
    return {
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
                id: primaryAddress,
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

function showCreateMenu(updateMode) {
    const modal = document.getElementById('modal');
    modal.style.display = 'block';
    document.getElementById('modalBackdrop').style.display = 'block';
    initFormSteps(updateMode);

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

    currentStep = 1;
    primaryMedicine = null;
    primaryManufacturer = null;
    primaryAddress = null;
    tmpDataStore.clear();
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

function initFormSteps(updateMode) {
    currentStep = 1;
    showStep(currentStep);
    setupNavigation(updateMode);
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

function setupNavigation(updateMode) {
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
                if (updateMode) {
                    await sendProduct(`/api/admin/product/${primaryMedicine}`,'PUT');
                } else {
                    await sendProduct(`/api/admin/product`,'POST');
                }
                closeCreateMenu();
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

    if (searchTerm.length < 1) {
        resultsContainer.innerHTML = '';
        return;
    }

    debounceTimer = setTimeout(() => {
        fetchResults(searchTerm);
    }, DEBOUNCE_DELAY);
});

async function getResponse(url, method, body) {
    if (body !== null) {
        return await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        });
    } else {
        return await fetch(url, {
            method: method,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }
}

async function fetchResults(searchTerm) {
    try {
        if (currentStep === 1) {
            const response = await getResponse(`/api/search/supplier?name=${searchTerm}`, 'GET');
            const data = await response.json();
            displayResults(data);
        } else {
            const response = await getResponse(`/api/search/manufacturer?name=${searchTerm}`, 'GET');
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
        fillForm(result.itn, currentStep);
    } else if (currentStep === 2) {
        fillForm(result.id, currentStep);
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
            manufacturer: {
                id: result.id,
                name: result.name,
                country: result.country
            }
        };

        // Сохраняем данные через WeakMap
        tmpDataStore.set(result.id, dataToStore);

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

        // Сохраняем данные через WeakMap
        tmpDataStore.set(result.itn, dataToStore);

        // Дополнительно сохраняем в dataset для доступа из CSS/HTML
        parent.dataset.tmpData = JSON.stringify(dataToStore);
    }
}

function fillForm(id, step) {
    const data = tmpDataStore.get(id);
    const form = document.forms.productCreationForm;
    if (step === 3) {
        primaryMedicine = data.id;

        form.elements.name.value = data.name;
        form.elements.type.value = data.type;
        form.elements.price.value = data.price;
        form.elements.recipe.value = data.recipe;
    }if (step === 2) {
        primaryManufacturer = data.manufacturer.id;

        form.elements.manufacturerName.value = data.manufacturer.name;
        form.elements.country.value = data.manufacturer.country;
    } else {
        primaryAddress = data.supplier.itn;

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
}