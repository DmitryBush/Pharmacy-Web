export function formatPhone(input) {
    const start = input.selectionStart;
    const value = input.value;

    let digitCountBefore = 0;
    for (let i = 0; i < start; i++) {
        if (/\d/.test(value[i])) digitCountBefore++;
    }

    let cleaned = value.replace(/\D/g, '');

    if (/^79/.test(cleaned)) {
        cleaned = cleaned.substring(2);
    } else if (/^7|8|9$/.test(cleaned)) {
        cleaned = cleaned.substring(1);
    }

    const digits = cleaned.substring(0, 9);

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

export function getDayText(day) {
    if (day === 'MONDAY') {
        return 'Понедельник';
    } else if (day === 'TUESDAY') {
        return 'Вторник';
    } else  if (day === 'WEDNESDAY') {
        return 'Среда';
    } else  if (day === 'THURSDAY') {
        return 'Четверг';
    } else  if (day === 'FRIDAY') {
        return 'Пятница';
    } else  if (day === 'SATURDAY') {
        return 'Суббота';
    } else  if (day === 'SUNDAY') {
        return 'Воскресенье';
    }
    throw new Error(`Unknown day: ${day}`);
}

export function getShortDayText(day) {
    if (day === 'MONDAY') {
        return 'Пн';
    } else if (day === 'TUESDAY') {
        return 'Вт';
    } else  if (day === 'WEDNESDAY') {
        return 'Ср';
    } else  if (day === 'THURSDAY') {
        return 'Чт';
    } else  if (day === 'FRIDAY') {
        return 'Пт';
    } else  if (day === 'SATURDAY') {
        return 'Сб';
    } else  if (day === 'SUNDAY') {
        return 'Вс';
    }
    throw new Error(`Unknown day: ${day}`);
}

export function getTimeText(workingHour) {
    if (workingHour.dayOff) {
        return 'Выходной';
    } else if (workingHour.aroundClock) {
        return 'Круглосуточно';
    } else {
        const formatter = new Intl.DateTimeFormat([], {
            hour: '2-digit',
            minute: '2-digit'
        });
        const openTime = new Date(`1970-01-01T${workingHour.openTime}`);
        const closeTime = new Date(`1970-01-01T${workingHour.closeTime}`);
        return `${formatter.format(openTime)} - ${formatter.format(closeTime)}`;
    }
}

export function getRoleText(role) {
    if (role === 'ADMIN') {
        return 'Администратор';
    } else if (role === 'OPERATOR') {
        return 'Оператор';
    } else if (role === 'CUSTOMER') {
        return 'Покупатель';
    } else {
        return 'Покупатель';
    }
}

export function getStatusText(statusName) {
    switch (statusName) {
        case 'PAYMENT_AWAIT': return 'Ожидает оплаты';
        case 'CANCELLED': return 'Отменен';
        case 'DEFERRED': return 'Отложен';
        case 'DECOR': return 'Оформляется';
        case 'ASSEMBLY': return 'Собирается';
        case 'TRANSIT': return 'В пути';
        case 'DELIVERED': return 'Доставлен';
        case 'COMPLETED': return 'Завершен';
        case 'NOT_DEMAND': return 'Не востребован';
        case 'RETURN_REQUESTED': return 'Возврат запрошен';
        case 'RETURN_REJECTED': return 'Возврат отклонен';
        case 'AWAITING_CUSTOMER_SHIPMENT': return 'Ожидание возврата товара от пользователя';
        case 'RETURN_SHIPPED_BY_CUSTOMER': return 'Покупатель вернул товары';
        case 'RETURN_TRANSIT': return 'В пути к продавцу';
        case 'RETURN_CLOSED': return 'Возврат завершен';
        case 'RETURN_DEFERRED': return 'Возврат отложен';
        default: return 'Неизвестен';
    }
}