import RestClient from "../RestClient.js";

export async function getCartProductsSet() {
    const restClient = new RestClient();
    const set = new Set();
    try {
        const cart = await (await restClient.fetchData(`/api/v1/carts/me`, 'GET')).json();
        cart.cartItems.forEach(item => set.add(item.medicine.id));
    } catch (error) {
        if (error.message !== '401') {
            throw error;
        }
    }
    return set;
}