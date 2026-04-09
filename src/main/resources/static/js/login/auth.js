import {LoginModal} from "./login-modal.js";

export async function isAuthenticated() {
    try {
        const response = await fetch('/api/v1/auth/me/status', {method: 'GET'});
        return response.status === 200;
    } catch (error) {
        throw error;
    }
}

export async function requireAuth(onSuccess){
    const auth = await isAuthenticated();
    if (!auth) {
        const loginModal = LoginModal.getInstance();
        loginModal.show();
    }

    if (typeof onSuccess === 'function') {
        onSuccess();
    }
    return true;
}

export function withAuth(action) {
    return async (...args) => {
        await requireAuth(() => action(...args));
    }
}