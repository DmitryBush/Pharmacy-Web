import RestClient from "../RestClient.js";
import {withAuth} from "../login/auth.js";

export default class ProductRenderer {
    constructor(product, productContainer, cartItemsArray) {
        this.restClient = new RestClient();
        this.cartItemsList = cartItemsArray;
        this.product = product;
        const productCard = document.createElement('div');
        productCard.classList.add('product-card');

        productCard.appendChild(this.createProductImage(product));
        productCard.appendChild(this.createProductNameLink(product));
        productCard.appendChild(this.createProductPriceContainer(product));
        productContainer.appendChild(productCard);
    }

    createProductImage(product) {
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

    createProductNameLink(product) {
        const productNameContainer = document.createElement('div');
        const productNameLink = document.createElement('a');
        productNameLink.href = `/product/${product.id}`;
        productNameLink.textContent = product.name;
        productNameContainer.appendChild(productNameLink);
        return productNameContainer;
    }

    createProductPriceContainer(product) {
        const productPriceContainer = document.createElement('div');
        const productPrice = document.createElement('p');
        productPrice.textContent = `${product.price} ₽`;
        const buyButton = document.createElement('button');
        this.cartItemsList.has(product.id)
            ? this.renderButtonAsInCart(buyButton)
            : this.renderButtonAsNotInCart(buyButton);

        productPriceContainer.appendChild(productPrice);
        productPriceContainer.appendChild(buyButton);
        return productPriceContainer;
    }

    async markAsInCart(buyButton) {
        await this.restClient.fetchData(`/api/v1/carts/me/items`, 'PATCH',
            {'Content-Type': 'application/json'},
            JSON.stringify({
                item: {
                    productId: this.product.id,
                    quantity: 1
                }
            }));
        this.cartItemsList.add(this.product.id);
        this.renderButtonAsInCart(buyButton);
    }

    renderButtonAsInCart(buyButton) {
        buyButton.textContent = 'В корзине';
        buyButton.classList.add('in-cart');
        buyButton.addEventListener('click', withAuth(() => this.markAsNotInCart(buyButton)));
    }

    async markAsNotInCart(buyButton) {
        await this.restClient.fetchData(`/api/v1/carts/me/${this.product.id}`, 'DELETE');
        this.cartItemsList.delete(this.product.id);
        this.renderButtonAsNotInCart(buyButton);
    }

    renderButtonAsNotInCart(buyButton) {
        buyButton.textContent = 'Купить';
        buyButton.classList.remove('in-cart');
        buyButton.addEventListener('click', withAuth(() => this.markAsInCart(buyButton)));
    }
}