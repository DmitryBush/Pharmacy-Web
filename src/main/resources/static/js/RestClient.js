export default class RestClient {
    async fetchData(url, method, headers = {}, body = null) {
        try {
            const response = await fetch(url, {
                method: method,
                body: body,
                headers: /^[gG][eE][tT]$/.test(method) ? headers : {...headers,
                    ...(await this.getCsrfToken())},
            });

            if (!response.ok) {
                const errorText = await response.json();
                throw new Error(errorText.status);
            }

            return response;
        } catch (error) {
            console.error(`${method} error:`, error);
            throw error;
        }
    }

    async getCsrfToken() {
        const response = await this.fetchData('/api/v1/csrf', 'GET');
        const csrf = await response.json();

        return { 'X-CSRF-TOKEN': csrf.token };
    }
}