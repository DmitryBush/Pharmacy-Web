document.addEventListener('DOMContentLoaded', () => {

    document.getElementById('username').addEventListener('input',
            event => formatPhone(event.target));

    function formatPhone(input) {
        const start = input.selectionStart;
        const value = input.value;

        let digitCountBefore = 0;
        for (let i = 0; i < start; i++) {
            if (/\d/.test(value[i])) digitCountBefore++;
        }

        let cleaned = value.replace(/\D/g, '');
        console.log(cleaned);

        if (/^79/.test(cleaned)) {
            cleaned = cleaned.substring(2);
        } else if (/^7|8|9$/.test(cleaned)) {
            cleaned = cleaned.substring(1);
        }

        const digits = cleaned.substring(0, 9);
        console.log(digits + ' ' + digits.length);

        let formatted = '+7 (9';
        if (digits.length > 0) {
            formatted += `${digits.substring(0, 2)}`;
        }
        if (digits.length >= 2) {
            formatted += ')';
        }
        if (digits.length > 2) {
            formatted += ` ${digits.substring(2, 5)}`;
        }
        if (digits.length > 5) {
            formatted += `-${digits.substring(5, 7)}`;
        }
        if (digits.length > 7) {
            formatted += `-${digits.substring(7, 9)}`;
        }

        let newCursorPosition = formatted.length;
        let digitCount = 0;

        for (let i = 0; i < formatted.length; i++) {
            if (/\d/.test(formatted[i])) {
                digitCount++;
                if (digitCount === digitCountBefore) {
                    newCursorPosition = i + 1;
                    break;
                }
            }
        }

        input.value = formatted;
        input.setSelectionRange(newCursorPosition, newCursorPosition);
    }
})