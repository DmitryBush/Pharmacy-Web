import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener('DOMContentLoaded', () => {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/file-earmark-medical-fill.png');

    let DEBOUNCE_DELAY = 300;
    let typeCounter = 1;

    const newImagesContainer = document.querySelector('#imagePreview');
    let newImagesFiles = [];

    const editBtn = document.getElementById('editBtn');
    const previewBtn = document.getElementById('previewBtn');

    const editor = document.getElementById('product-id');
    const preview = document.getElementById('product-preview');

    const typeContainer = document.getElementById('type-container');

    const searchContainer = document.getElementById("search-container");
    const searchField = document.getElementById("search-field");
    const resultsContainer = document.getElementById("search-result-container");
    const searchBackdrop = document.getElementById('search-backdrop');

    const idMap = new Map();

    let searchOutsideClick = null;
    let searchEndpoint = null;
    let searchElement= null;

    document.getElementById("search-field").addEventListener('input', handleSearchInput);

    document.querySelectorAll(".search-btn").forEach(btn =>
        btn.addEventListener('click', e => {
            e.preventDefault();
            if (btn.closest('span').classList.contains('type')) {
                searchEndpoint = 'type';
            } else if (btn.closest('span').classList.contains('parent-type')) {
                searchEndpoint = 'type/parent';
            } else if (btn.closest('div').classList.contains('manufacturerPart')) {
                searchEndpoint = 'manufacturer';
            } else if (btn.closest('div').classList.contains('supplierPart')) {
                searchEndpoint = 'supplier';
            } else if (btn.closest('div').classList.contains('countryPart')) {
                searchEndpoint = 'country';
            }

            searchElement = btn.closest('div');
            handleOpenSearch();
        }));

    document.getElementById('add-type-btn').addEventListener('click', (e) => {
        const newType = document.createElement('div');
        newType.className = 'type';
        newType.innerHTML = `<label class="input-group" for="type">
                                Тип товара ${++typeCounter}
                                <span class="input-with-button type">
                                    <input class="input-group type" type="text" id="type">
                                    <button class="search-btn search-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                                        </svg>
                                    </button>
                                </span>
                            </label>
                            <label class="input-group" for="parent-type">
                                Родительский тип
                                <span class="input-with-button parent-type">
                                    <input class="input-group parent-type" type="text" id="parent-type">
                                    <button class="search-btn search-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                                        </svg>
                                    </button>
                                </span>
                            </label>`;

        typeContainer.insertBefore(newType,e.target);
    });

    document.getElementById('save-btn').addEventListener('click',
        () => createProduct()
            .then(() => {
                notification.showNotification('Управление товарами',
                    'Создание товара завершено успешно');
                setTimeout(() => window.location.replace('/admin/product'), 1000);
            })
            .catch((e) => notification.showNotification('Управление товарами',
                `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`)));

    document.getElementById('cancel-btn').addEventListener('click', () => {
        window.location.replace('/admin/product');
    });

    editBtn.addEventListener('click', () => {
        editBtn.classList.add('active');
        previewBtn.classList.remove('active');
        editor.style.display = 'block';
        preview.style.display = 'none';
    });

    previewBtn.addEventListener('click', () => {
        previewBtn.classList.add('active');
        editBtn.classList.remove('active');
        editor.style.display = 'none';
        preview.style.display = 'block';
        fillPreview(getFormData());
    });

    document.getElementById('imageInput').addEventListener('change', (e) => {
        const files = Array.from(e.target.files);
        newImagesFiles = [...newImagesFiles, ...files];
        files.forEach(file => {
            const reader = new FileReader();
            reader.onload = (event) => {
                const imgContainer = document.createElement('div');
                imgContainer.className = 'image-item new-image';
                imgContainer.innerHTML = `
                    <img src="${event.target.result}" width="350px">
                    <button class="delete-new-image-btn" onclick="removeNewImage(this)">×</button>
                `;
                newImagesContainer.appendChild(imgContainer);
            };
            reader.readAsDataURL(file);
        });
    });

    window.removeNewImage = (button) => {
        const imgContainer = button.closest('.image-item');
        const index = Array.from(newImagesContainer.children).indexOf(imgContainer) -
            parseInt(document.getElementById('product-id').dataset.imageCount);
        newImagesFiles.splice(index, 1);
        imgContainer.remove();
    };

    function fillPreview(data) {
        const infoSpans = document.querySelectorAll('.product-info span:last-child');
        infoSpans[0].textContent = `${data.manufacturer.name}, ${data.manufacturer.country.country}`;
        infoSpans[1].textContent = data.type;
        infoSpans[2].textContent = data.activeIngredient;
        infoSpans[3].textContent = data.expirationDate;

        document.querySelector('.price-value').textContent = `${data.price} ₽`;

        const details = document.querySelectorAll('.details-section div p');

        const mapping = {
            0: `${data.manufacturer.name}, ${data.manufacturer.country.country}`,
            1: data.composition,
            2: data.indication,
            3: data.contraindication,
            4: data.sideEffect,
            5: data.interaction,
            6: data.admissionCourse,
            7: data.overdose,
            8: data.specialInstruction,
            9: data.storageCondition,
            10: data.releaseForm
        };

        Object.entries(mapping).forEach(([index, value]) => {
            if (details[index]) {
                details[index].textContent = value;

                if (details[index].style.whiteSpace === 'pre-wrap') {
                    details[index].innerHTML = value.replace(/\n/g, '<br>');
                }
            }
        });
    }

    async function createProduct() {
        const formData = new FormData();
        const productBlob = new Blob(
            [JSON.stringify(getFormData())],
            { type: 'application/json' }
        );
        formData.append('product', productBlob);

        if (newImagesFiles.length > 0) {
            newImagesFiles.forEach(file => formData.append('images', file));
        } else {
            formData.append('images', new File([], 'empty'));
        }

        await restClient.fetchData(`/api/v1/admin/products`, 'POST', {}, formData);
    }

    function getFormData()  {
        return {
            name: document.getElementById('name').value,
            type: getTypeData(),
            price: parseFloat(document.getElementById('price').value),
            recipe: document.querySelector('input[name="recipe"]:checked').value === '1',
            activeIngredient: document.getElementById('active-ingredient').value,
            expirationDate: document.getElementById('expiration-date').value,
            composition: document.getElementById('composition').value,
            indication: document.getElementById('indications').value,
            contraindication: document.getElementById('contraindications').value,
            sideEffect: document.getElementById('side-effects').value,
            interaction: document.getElementById('interactions').value,
            admissionCourse: document.getElementById('admission-course').value,
            overdose: document.getElementById('overdose').value,
            releaseForm: document.getElementById('release-form').value,
            specialInstruction: document.getElementById('special-instructions').value,
            storageCondition: document.getElementById('storage-conditions').value,
            supplier: {
                itn: document.getElementById('itn').value,
                name: document.getElementById('supplierName').value,
                address: {
                    id: idMap.get('address') ?? null,
                    subject: document.getElementById('subject').value,
                    district: document.getElementById('district').value,
                    settlement: document.getElementById('settlement').value,
                    street: document.getElementById('street').value,
                    house: document.getElementById('house').value,
                    apartment: document.getElementById('apartment').value,
                    postalCode: document.getElementById('postalCode').value
                }
            },
            manufacturer: {
                id: idMap.get('manufacturer') ?? null,
                name: document.getElementById('manufacturerName').value,
                country: {
                    country: document.getElementById('country').value
                }
            }
        }
    }

    function getTypeData() {
        let types = [];
        typeContainer.querySelectorAll('.type').forEach((item) => {
            if (item.querySelector('#type').classList.contains('type')) {
                types.push({
                    type: {
                        name: item.querySelector('#type').value,
                        parent: item.querySelector('#parent-type').value
                    },
                    isMain : false
                });
            } else if (item.querySelector('#type').classList.contains('main-type')) {
                types.push({
                    type: {
                        name: item.querySelector('#type').value,
                        parent: item.querySelector('#parent-type').value
                    },
                    isMain : true
                });
            }
        });
        return types;
    }

    function handleOpenSearch() {
        searchContainer.style.display = 'block';
        searchBackdrop.style.display = 'block';

        searchOutsideClick = (event) => {
            if (searchContainer && !searchContainer.contains(event.target) && !resultsContainer.contains(event.target)) {
                closeSearch();
                document.removeEventListener('click', searchOutsideClick);
                searchOutsideClick = null;
            }
        };
        setTimeout(() => document.addEventListener('click', searchOutsideClick), 10);
    }

    function handleSearchInput(e) {
        clearTimeout(DEBOUNCE_DELAY);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 2) {
            resultsContainer.innerHTML = '';
            return;
        }

        DEBOUNCE_DELAY = setTimeout(() => {
            fetchResults(searchTerm)
                .catch(error =>
                    notification.showNotification('Управление продуктами',
                        `Произошла ошибка при поиске продукта: ${error}`));
        }, DEBOUNCE_DELAY);
    }

    async function fetchResults(searchTerm) {
        try {
            const data = await
                (await restClient.fetchData(`/api/v1/search/${searchEndpoint}?searchTerm=${searchTerm}`,
                    'GET')).json();
            displayResults(data);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }

    function displayResults(results) {
        resultsContainer.innerHTML = '';
        if (results.length === 0) {
            return;
        }

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = searchEndpoint === 'country' ? result.country : result.name;

            div.onclick = () => handleSearchClick(result);

            resultsContainer.appendChild(div);
        });
    }

    function handleSearchClick(result) {
        try {
            if (searchEndpoint === 'type') {
                searchElement.querySelector('#type').value = result.name;
                searchElement.querySelector('#parent-type').value = result.parent;
            } else if (searchEndpoint === 'type/parent') {
                searchElement.querySelector('#parent-type').value = result.name;
            } else if (searchEndpoint === 'manufacturer') {
                idMap.set('manufacturer', result.id);

                searchElement.querySelector('#manufacturerName').value = result.name;
                searchElement.querySelector('#country').value = result.country;
            } else if (searchEndpoint === 'supplier') {
                idMap.set('address', result.id);

                searchElement.querySelector('#itn').value = result.itn;
                searchElement.querySelector('#supplierName').value = result.name;

                searchElement.querySelector('#subject').value = result.address.subject;

                if (result.district !== null)
                    searchElement.querySelector('#district').value = result.address.district;

                searchElement.querySelector('#settlement').value = result.address.settlement;
                searchElement.querySelector('#street').value = result.address.street;
                searchElement.querySelector('#house').value = result.address.house;

                if (result.apartment !== null)
                    searchElement.querySelector('#apartment').value = result.address.apartment;

                searchElement.querySelector('#postalCode').value = result.address.postalCode;
            } else if (searchEndpoint === 'country') {
                searchElement.querySelector('#county').value = result.country;
            }
            blockInput(searchElement);
        } catch (error) {
            console.error(error);
            notification.showNotification('Управление продуктами',
                `Произошла ошибка в модуле поиска: ${error.message}`);
        } finally {
            closeSearch();
        }
    }

    function blockInput(element) {
        let blockedInputs = [];
        let inputContainer = null;

        if (searchEndpoint === 'manufacturer') {
            inputContainer = element.querySelector('.countryPart');
            idMap.delete('manufacturer');
        } else if (searchEndpoint === 'supplier') {
            inputContainer = element.querySelector('.addressPart');
            idMap.delete('address');
        } else if (searchEndpoint === 'type') {
            inputContainer = element.querySelector('.parent-type');
        }

        if (inputContainer !== null)
            inputContainer.querySelectorAll('input')
                .forEach((input) => {
                    input.readOnly = true;
                    input.classList.add('locked-input');
                    blockedInputs.push(input);
                });

        element.querySelectorAll('input').forEach((input) => {
            input.addEventListener('input', () => {
                blockedInputs.forEach(blockedInput => unblockInput(blockedInput));
            });
        });
    }

    function unblockInput(inputElement) {
        inputElement.readOnly = false;
        inputElement.classList.remove('locked-input');
    }

    function closeSearch() {
        searchContainer.style.display = 'none';
        searchBackdrop.style.display = 'none';

        searchField.value = '';
        resultsContainer.innerHTML = '';

        searchElement = null;
        searchEndpoint = null;

        if (searchOutsideClick !== null) {
            document.removeEventListener('click', searchOutsideClick);
            searchOutsideClick = null;
        }
    }
});