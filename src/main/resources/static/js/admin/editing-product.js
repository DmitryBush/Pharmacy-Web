import RestClient from "../RestClient.js";
import Notification from "../notification/notification.js";

document.addEventListener('DOMContentLoaded', () => {
    const restClient = new RestClient();
    const notification = new Notification('/api/v1/icons/admin/file-earmark-medical-fill.png');

    const productId = document.getElementById('product-id').dataset.id;
    const mapId = new Map();
    const newImagesContainer = document.querySelector('#imagePreview');
    let newImagesFiles = [];

    const typeContainer = document.getElementById('type-container');
    let typeCounter = 0;

    const editBtn = document.getElementById('editBtn');
    const previewBtn = document.getElementById('previewBtn');
    const editor = document.getElementById('product-id');
    const preview = document.getElementById('product-preview');

    init().catch(() => notification.showNotification('Управление товарами',
        `Во время загрузки данных произошла ошибка`));

    async function init() {
        const response = await
            (await restClient.fetchData(`/api/v1/admin/products/${productId}`, 'GET')).json();

        fillForm(response);
    }

    function fillForm(data) {
        fillTypes(data.types);
        fillImages(data.imagePaths);

        document.getElementById('name').value = data.name;
        document.getElementById('price').value = parseFloat(data.price);

        if (data.recipe)
            document.querySelector('input[name="recipe"][value="1"]').checked = true;
        else
            document.querySelector('input[name="recipe"][value="0"]').checked = true;

        document.getElementById('active-ingredient').value = data.activeIngredient;
        document.getElementById('expiration-date').value = data.expirationDate;
        document.getElementById('composition').value = data.composition;
        document.getElementById('indications').value = data.indication;
        document.getElementById('contraindications').value = data.contraindication;
        document.getElementById('side-effects').value = data.sideEffect;
        document.getElementById('interactions').value = data.interaction;
        document.getElementById('admission-course').value = data.admissionCourse;
        document.getElementById('overdose').value = data.overdose;
        document.getElementById('release-form').value = data.releaseForm;
        document.getElementById('special-instructions').value = data.specialInstruction;
        document.getElementById('storage-conditions').value = data.storageCondition;

        document.getElementById('itn').value = data.supplier.itn;
        document.getElementById('supplierName').value = data.supplier.name;

        mapId.set('address', data.supplier.address.id);
        document.getElementById('subject').value = data.supplier.address.subject;
        document.getElementById('district').value = data.supplier.address.district;
        document.getElementById('settlement').value = data.supplier.address.settlement;
        document.getElementById('street').value = data.supplier.address.street;
        document.getElementById('house').value = data.supplier.address.house;
        document.getElementById('apartment').value = data.supplier.address.apartment;
        document.getElementById('postalCode').value = data.supplier.address.postalCode;

        mapId.set('manufacturer', data.manufacturer.id);
        document.getElementById('manufacturerName').value = data.manufacturer.name;

        document.getElementById('country').value = data.manufacturer.country;
    }

    function fillTypes(types) {
        const addTypeButton = document.getElementById('add-type-btn');
        types.forEach(type => {
            let className;
            let typeDescription;
            if (type.isMain) {
                className = 'main-type';
                typeDescription = 'Основной тип товара';
            } else {
                className = 'type';
                typeDescription = 'Тип товара';
            }
            const typeElement = document.createElement('div');
            typeElement.className = 'type';
            typeElement.innerHTML = `
                            <label class="input-group" for="type">
                                ${typeDescription} ${++typeCounter}
                                <span class="input-with-button type">
                                    <input class="input-group ${className}" type="text" id="type" 
                                        value="${type.type.name}">
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
                                    <input class="input-group parent-type locked-input" type="text" id="parent-type" 
                                        value="${type.type.parent}" readonly>
                                    <button class="search-btn search-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                                        </svg>
                                    </button>
                                </span>
                            </label>`;

            typeContainer.insertBefore(typeElement, addTypeButton);
        })
    }

    function fillImages(images) {
        images.forEach(image => {
            const imageItem = document.createElement('div');
            imageItem.classList.add('image-item');
            imageItem.innerHTML = `
                <img src='/api/v1/product-image/${productId}/${image.path}'
                                 width="350px">
                <button class="delete-image-btn" data-id="${image.id}">×</button>
            `;
            newImagesContainer.appendChild(imageItem);
        })
    }

    document.getElementById('save-btn').addEventListener('click', () => updateProduct);
    document.getElementById('delete-btn').addEventListener('click', deleteProduct);

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

    document.addEventListener('click', (e) => {
        if (e.target.classList.contains('delete-image-btn')) {
            e.preventDefault(); // Предотвращаем действие по умолчанию
            deleteImage(e.target);
        }
    });

    document.getElementById('cancel-btn').addEventListener('click', () => {
        window.location.replace('/admin/product');
    })

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

     function deleteImage(button) {
        const imageId = button.getAttribute('data-id');

        if (confirm('Удалить изображение?')) {
            restClient.fetchData(`/api/product-image/${imageId}`, 'DELETE')
                .then(response => {
                    if (response.ok) {
                        notification.showNotification('Управление товарами',
                            'Товар успешно обновлен');
                        setTimeout(() => window.location.replace('/admin/product'), 1000);
                    }
                })
                .catch(e => notification.showNotification('Управление товарами',
                    `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`));
        }
    }


    window.removeNewImage = (button) => {
        const imgContainer = button.closest('.image-item');
        const index = Array.from(newImagesContainer.children).indexOf(imgContainer) -
            parseInt(document.getElementById('product-id').dataset.imageCount);
        newImagesFiles.splice(index, 1);
        imgContainer.remove();
    };

    const getFormData = () => ({
        name: document.getElementById('name').value,
        type: document.getElementById('type').value,
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
                id: document.getElementById('addressPart').getAttribute('data-id'),
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
            id: document.getElementById('manufacturerPart').getAttribute('data-id'),
            name: document.getElementById('manufacturerName').value,
            country: {
                country: document.getElementById('country').value
            }
        }
    });


    function updateProduct() {
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

        restClient.fetchData(`/api/v1/admin/products/${productId}`, 'PUT', {}, formData)
            .then(response => {
                if (response.ok) {
                    notification.showNotification('Управление товарами',
                        'Товар успешно обновлен');
                    setTimeout(() => window.location.replace('/admin/product'), 1000);
                }
            })
            .catch(e => notification.showNotification('Управление товарами',
                `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`));
    }

    function deleteProduct() {
        if (!confirm('Удалить продукт?')) return;

        restClient.fetchData(`/api/v1/admin/products/${productId}`, 'DELETE')
            .then(() => {
                notification.showNotification('Управление товарами',
                    'Удаление товара завершено успешно');
                setTimeout(() => window.location.replace('/admin/product'), 1000);
            })
            .catch(e => notification.showNotification('Управление товарами',
                `Во время завершения заказа произошла ошибка. Ошибка: ${e.message}`));
    }
});