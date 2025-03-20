document.addEventListener("DOMContentLoaded", function() {
        // Парсинг параметров из URL
        const urlParams = new URLSearchParams(window.location.search);

        // Установка значений из URL
        urlParams.getAll("type").forEach(value => {
            document.querySelectorAll(`input[name="type"][value="${value}"]`).forEach(checkbox => {
                checkbox.checked = true;
            });
        });

        urlParams.getAll("manufacturer").forEach(value => {
            document.querySelectorAll(`input[name="manufacturer"][value="${value}"]`).forEach(checkbox => {
                checkbox.checked = true;
            });
        });

        const recipeValue = urlParams.get("recipe");
        if (recipeValue) {
            document.querySelector(`input[name="recipe"][value="${recipeValue}"]`).checked = true;
        }

        document.getElementById("minPrice").value = urlParams.get("minPrice") || "";
        document.getElementById("maxPrice").value = urlParams.get("maxPrice") || "";
    });

document.querySelector("form").addEventListener("change", function() {
        const formData = new FormData(this);
        const params = new URLSearchParams(formData).toString();
        window.history.replaceState(null, null, "?" + params);
        window.location.search = params;
    });

    document.getElementById("pageSize").addEventListener("change", function() {
        const url = new URL(window.location.href);
        url.searchParams.set('size', this.value);

        url.searchParams.set('page', 0);
        window.location.href = url.toString();
    })


document.getElementById("resetFilters").addEventListener("click", function() {
    document.querySelector("form").reset();

    window.history.replaceState(null, null, window.location.pathname);

    window.location.search = "";
});