document.addEventListener('DOMContentLoaded', () => {
    const productId = document.getElementById('product-id').dataset.id;
    const saveBtn = document.getElementById('save-btn');
    const imageInput = document.getElementById('imageInput');
    const newImagesContainer = document.querySelector('#imagePreview');
    let newImagesFiles = [];

    const editBtn = document.getElementById('editBtn');
    const previewBtn = document.getElementById('previewBtn');
    const editor = document.getElementById('product-id');
    const preview = document.getElementById('product-preview');

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

    imageInput.addEventListener('change', (e) => {
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

    async function deleteImage(button) {
        const imageId = button.getAttribute('data-id');

        if (confirm('Удалить изображение?')) {
            try {
                const response = await fetch(`/api/product-image/${imageId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });

                if (response.ok) {
                    button.closest('.image-item').remove();
                } else {
                    const error = await response.text();
                    alert(`Ошибка: ${error}`);
                }
            } catch (error) {
                console.error('Ошибка:', error);
                alert('Не удалось удалить изображение');
            }
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


    const updateProduct = async () => {
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

        try {
            const response = await fetch(`/api/admin/product/${productId}`, {
                method: 'PUT',
                body: formData
            });

            if (response.ok) {
                window.location.replace('/admin/product');
            } else {
                const error = await response.text();
                alert(`Ошибка: ${error}`);
            }
        } catch (error) {
            console.error('Ошибка:', error);
            alert('Сетевая ошибка. Проверьте подключение.');
        }
    };

    const deleteProduct = async () => {
        if (!confirm('Удалить продукт?')) return;

        try {
            const response = await fetch(`/api/admin/product/${productId}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                window.location.replace('/admin/product');
            } else {
                alert('Ошибка удаления');
            }
        } catch (error) {
            console.error('Ошибка:', error);
        }
    };

    saveBtn.addEventListener('click', updateProduct);
    document.getElementById('delete-btn').addEventListener('click', deleteProduct);
});