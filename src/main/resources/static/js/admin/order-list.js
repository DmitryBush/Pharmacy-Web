document.addEventListener('DOMContentLoaded', () => {
    const branchId = document.getElementById('branch-id').dataset.id;
    document.querySelectorAll('.order-more-det').forEach(el => {
        el.addEventListener('click', (e) => {
            window.location.replace(`/admin/orders/branch/${branchId}/order/${e.target.getAttribute('data-id')}`);
        });
    });
});