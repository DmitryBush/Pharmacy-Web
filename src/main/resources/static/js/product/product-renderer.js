export function renderProductElement(product, productContainer) {
    const productCard = document.createElement('div');
    productCard.classList.add('product-card');

    productCard.appendChild(createProductImage(product));
    productCard.appendChild(createProductNameLink(product));
    productCard.appendChild(createProductPriceContainer(product));
    productContainer.appendChild(productCard);
}

function createProductImage(product) {
    const imageLink = document.createElement('a');
    imageLink.href = `/product/${product.id}`;
    const productImage = document.createElement('img');
    if (product.imagePaths.length > 0) {
        productImage.src = `/api/v1/product-image/${product.imagePaths[0]}`;
        productImage.alt = product.name;
        productImage.setAttribute('width', '200px');
    } else {
        productImage.classList.add('.image-unavailable');
    }
    imageLink.appendChild(productImage);
    return imageLink;
}

function createProductNameLink(product) {
    const productNameContainer = document.createElement('div');
    const productNameLink = document.createElement('a');
    productNameLink.href = `/product/${product.id}`;
    productNameLink.textContent = product.name;
    productNameContainer.appendChild(productNameLink);
    return productNameContainer;
}

function createProductPriceContainer(product) {
    const productPriceContainer = document.createElement('div');
    const productPrice = document.createElement('p');
    productPrice.textContent = `${product.price} ₽`;
    const buyButton = document.createElement('button');
    buyButton.textContent = 'Купить'
    productPriceContainer.appendChild(productPrice);
    productPriceContainer.appendChild(buyButton);
    return productPriceContainer;
}