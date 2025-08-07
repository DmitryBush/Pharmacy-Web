import {loadCSS} from "../ResourceLoader.js";

export default class Notification {
    constructor(imageUrl = null) {
        this.container = document.getElementById('notification-container');
        this.imageUrl = imageUrl;
    }

    showNotification(title, description) {
        const notification = document.createElement('div');
        notification.className = `notification`;

        const notificationContent = document.createElement('div');
        notificationContent.className = 'notification-content';
        const titleElement = document.createElement('span');
        titleElement.textContent = title;
        titleElement.className = 'notification-title';
        notificationContent.append(titleElement);

        const descriptionElement = document.createElement('span');
        descriptionElement.textContent = description;
        descriptionElement.className = 'notification-description';
        notificationContent.append(descriptionElement);

        notification.append(notificationContent);

        if (this.imageUrl) {
            const iconElement = document.createElement('img');
            iconElement.src = this.imageUrl;
            iconElement.className = 'notification-icon';
            notification.append(iconElement);
        }

        this.container.append(notification);

        setTimeout(() => {
            notification.style.animation = 'fadeOut 0.5s forwards';

            notification.addEventListener('animationend', () => {
                notification.remove();
            }, { once: true });
        }, 5000);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    loadCSS('/css/notification/notification.css');

    const notificationElement = document.createElement('div');
    notificationElement.id = 'notification-container';

    if (!document.contains(notificationElement)) {
        document.body.appendChild(notificationElement);
    }
})