export default class RestClient {
    async fetchData(url, method, headers = {}, body = null) {
        try {
            const response = await fetch(url, {
                method: method,
                body: body,
                headers: headers,
            });

            if (!response.ok) {
                const errorText = await response.json();
                throw new Error(errorText.error);
            }

            return response;
        } catch (error) {
            console.error(`${method} error:`, error);
            throw error;
        }
    }
}