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

document.body.addEventListener('htmx:afterSwap', function(e) {
    const targetId = e.detail.target.id;
    const modalIds = {
        'product-modal-container': 'productModal',
        'variant-modal-container': 'variantModal',
        'delete-modal-container': 'deleteModal'
    };

    const modalId = modalIds[targetId];
    if (modalId) {
        setTimeout(() => {
            const modalElement = document.getElementById(modalId);
            if (!modalElement) return;

            showModal(modalId);

            const form = modalElement.querySelector("form");
            if (form) {
                form.onsubmit = function(event) {
                    event.preventDefault();
                    closeModal(modalId);
                    event.target.submit();
                };
            }
        }, 50);
    }
});