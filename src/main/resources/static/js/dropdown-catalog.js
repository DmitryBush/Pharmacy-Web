(function() {
    const button = document.getElementById('dropdownButton');
    const menu = document.getElementById('dropdownMenu');
    const categoryItems = document.querySelectorAll('.category-item');
    const subcategoryBlocks = document.querySelectorAll('.subcategory-block');

    function setActiveCategory(categoryId) {
        categoryItems.forEach(item => item.classList.remove('active'));
        subcategoryBlocks.forEach(block => block.classList.remove('active'));

        const activeItem = document.querySelector(`.category-item[data-category="${categoryId}"]`);
        const activeBlock = document.querySelector(`.subcategory-block[data-category="${categoryId}"]`);

        if (activeItem && activeBlock) {
            activeItem.classList.add('active');
            activeBlock.classList.add('active');
        }
    }

    categoryItems.forEach(item => {
        item.addEventListener('mouseenter', function() {
            const catId = this.getAttribute('data-category');
            setActiveCategory(catId);
        });
    });

    if (categoryItems.length > 0) {
        const firstCatId = categoryItems[0].getAttribute('data-category');
        setActiveCategory(firstCatId);
    }

    button.addEventListener('click', function(event) {
        event.stopPropagation();
        menu.classList.toggle('show');
    });

    document.addEventListener('click', function(event) {
        if (!button.contains(event.target) && !menu.contains(event.target)) {
            menu.classList.remove('show');
        }
    });

    menu.addEventListener('click', function(event) {
        event.stopPropagation();
    });
})();