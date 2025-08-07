document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll('.branch-more-det').forEach(el => {
        el.addEventListener('click', (e) => {
            window.location.replace(`/admin/orders/branch/${e.target.getAttribute('data-id')}`);
        });
    });
});