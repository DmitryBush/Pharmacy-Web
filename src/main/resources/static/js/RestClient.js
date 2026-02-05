export default class RestClient {
    async fetchData(url, method, headers = {}, body = null) {
        try {
            const response = await fetch(url, {
                method: method,
                body: body,
                headers: /^[gG][eE][tT]$/.test(method) ? headers : {...headers,
                    ...(this.getCsrfToken())},
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

    getCsrfToken() {
        const token = document.querySelector('meta[name="_csrf"]').content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

        return { [csrfHeader]: token };
    }
}