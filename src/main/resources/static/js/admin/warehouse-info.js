document.addEventListener('DOMContentLoaded', () => {
    const branchId = document.getElementById("branch-id").dataset.id;

    document.getElementById('product-receipt-btn').addEventListener('click', (e) => {
        window.location.replace(`/admin/warehouse/${branchId}/receiving`);
    });

    document.getElementById('product-sale-btn').addEventListener('click', (e) => {
        window.location.replace(`/admin/warehouse/${branchId}/sale`);
    });

    document.getElementById('branch-transaction-history').addEventListener('click', (e) => {
        window.location.replace(`/admin/warehouse/${branchId}/history`);
    })
})