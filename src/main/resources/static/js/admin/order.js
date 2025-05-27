document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.order-more-det').forEach(el => {
        el.addEventListener('click', (e) => {
            window.location.replace(`/admin/orders/${e.target.getAttribute('data-id')}`);
        })
    });
});