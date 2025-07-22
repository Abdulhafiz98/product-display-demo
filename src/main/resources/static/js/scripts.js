// scripts.js
function showModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.add('show');
    }
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    if (modal) {
        modal.classList.remove('show');
    }
}

document.body.addEventListener('htmx:afterSwap', function (e) {
    const targetId = e.detail.target.id;
    if(targetId === 'product-modal-container') {
        showModal('productModal');
    } else if (targetId === 'variant-modal-container') {
        showModal('variantModal');
    } else if (targetId === 'delete-modal-container') {
        showModal('deleteModal');
    }
});
