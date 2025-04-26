document.addEventListener('DOMContentLoaded', () => {
    const productId = document.getElementById('product-id').dataset.id;
    const saveBtn = document.getElementById('save-btn');
    const imageInput = document.getElementById('imageInput');
    const newImagesContainer = document.querySelector('#imagePreview');
    let newImagesFiles = [];


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
                    <button class="delete-image-btn" onclick="removeNewImage(this)">×</button>
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
            // Добавляем пустой файл, если изображений нет
            formData.append('images', new File([], 'empty'));
        }

        try {
            const response = await fetch(`/api/admin/product/${productId}`, {
                method: 'PUT',
                body: formData
            });

            if (response.ok) {
                window.location.href = '/admin/product';
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
                window.location.href = '/admin/product';
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