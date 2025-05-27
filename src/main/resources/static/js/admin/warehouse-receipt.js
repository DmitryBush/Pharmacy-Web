document.addEventListener("DOMContentLoaded", function() {
    let DEBOUNCE_DELAY = 300;
    const map = new Map();
    const name = document.getElementById("name");
    const quantity = document.getElementById("quantity");
    const productList = document.getElementById("productList");
    const resultsContainer = document.getElementById("search-result-container");

    document.getElementById("add-btn").addEventListener("click", function() {
        addRecord();
    })

    document.getElementById("name").addEventListener('input', handleSearchInput.bind(this))

    async function fetchData(url, body = null) {
        try {
            const response = await fetch(url, {
                method: "GET",
                body: body
            });

            if (!response.ok)
                throw new Error("Произошла ошибка при загрузке данных с сервера: Код " + response.status);

            return response;
        } catch (error) {
            alert(error);
        }
    }

    async function addRecord() {
        try {
            if (name.value.trim() === "" || quantity.value.trim() === "")
                throw new Error("Не введено значений в поля");

            const id = name.getAttribute("data-id");

            if (id === null)
                throw new Error("Произошла системная ошибка при получении идентификатора")

            const data = await (await fetchData(`/api/admin/product/${id}`, null)).json();

            map.set(id, data);

            const productItem = document.createElement("div");
            productItem.className = "product-item";
            productItem.innerHTML = `
            <a href="@{/admin/product/{id}(id=${data.id})}">
                <img src="/api/product-image/${data.imagePaths.get(0).id}"
                     width="50px"
                     height="50px"
                     alt="${data.name}">
            </a>
            <div>
                <a href="@{/admin/product/{id}(id=${data.id})}">${data.name}</a>
            </div>
            <div>
                <p>${quantity.value.trim()} шт.</p>
            </div>
            `;
            productList.append(productItem);
        } catch (e) {
            alert(e);
        }
    }

    function handleSearchInput(e) {
        clearTimeout(DEBOUNCE_DELAY);
        const searchTerm = e.target.value.trim();

        if (searchTerm.length < 2) {
            resultsContainer.innerHTML = '';
            return;
        }

        DEBOUNCE_DELAY = setTimeout(() => {
            fetchSearchResults(searchTerm);
        }, DEBOUNCE_DELAY);
    }

    async function fetchSearchResults(searchTerm) {
        try {
            const data = await (await this.fetchData(`/api/search/medicine?name=${searchTerm}`, 'GET')).json();
            showResults(data);
        } catch (e) {

        }
    }

    function showResults(data) {
        resultsContainer.innerHTML = '';
        if (data.length === 0)
            return null;

        results.forEach(result => {
            const div = document.createElement('div');
            div.className = 'result-item';
            div.textContent = result.name;
            div.onclick = () => this.handleSearchClick(result);
            this.saveTMPData(result, div);

            this.resultsContainer.appendChild(div);
        });
    }
})

