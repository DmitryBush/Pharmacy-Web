document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('save-btn')
        .addEventListener('click', this.handleUpdate.bind(this,
            document.getElementById('product-id').getAttribute('data-id')));
    document.getElementById('delete-btn')
        .addEventListener('click', this.handleDelete.bind(this,
            document.getElementById('product-id').getAttribute('data-id')));
});

async function handleUpdate(id) {
    const body = getFormData();
    console.log(body);
    await fetchData(`/api/admin/product/${id}`, 'PUT', body);
}

async function handleDelete(id) {
    await fetchData(`/api/admin/product/${id}`, 'DELETE');
}

function getFormData() {
    return {
        name: document.getElementById('name').value,
        type: document.getElementById('type').value,
        manufacturer: {
            id: document.getElementById('manufacturerPart').getAttribute('data-id'),
            name: document.getElementById('manufacturerName').value,
            country: {
                country: document.getElementById('country').value,
            }
        },
        supplier: {
            itn: document.getElementById('itn').value,
            name: document.getElementById('supplierName').value,
            address: {
                id: document.getElementById('addressPart').getAttribute('data-id'),
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
        activeIngredient: document.getElementById('active-ingredient').value,
        expirationDate: document.getElementById('expiration-date').value,
        composition: document.getElementById('composition').value.trim() || null,
        indication: document.getElementById('indications').value.trim() || null,
        contraindication: document.getElementById('contraindications').value.trim() || null,
        sideEffect: document.getElementById('side-effects').value.trim() || null,
        interaction: document.getElementById('interactions').value.trim() || null,
        admissionCourse: document.getElementById('admission-course').value.trim() || null,
        overdose: document.getElementById('overdose').value.trim() || null,
        specialInstruction: document.getElementById('special-instructions').value.trim() || null,
        storageCondition: document.getElementById('storage-conditions').value.trim() || null,
        releaseForm: document.getElementById('release-form').value
    };
}

async function fetchData(url, method, body = null) {
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
