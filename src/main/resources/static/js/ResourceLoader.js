
export function loadCSS(url) {
    return new Promise((resolve, reject) => {
        if (!isCSSLoaded(url)) {
            const link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = url;
            link.onload = () => resolve(link);
            link.onerror = () => reject(new Error(`Не удалось загрузить CSS: ${url}`));
            document.head.appendChild(link);
        }
    });
}

function isCSSLoaded(url) {
    return Array.from(document.styleSheets).some(sheet => {
        return sheet.href && sheet.href.includes(url);
    });
}