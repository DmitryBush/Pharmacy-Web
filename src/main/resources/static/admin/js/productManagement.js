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

async function deleteProduct(id) {
    try {
        const response = await fetch(`/api/admin/product/${id}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        });

        if (response.status === 403) {
            throw new Error('Доступ запрещен. Требуется авторизация');
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