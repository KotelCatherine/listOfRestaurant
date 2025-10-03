// JavaScript для веб-интерфейса ресторанов

document.addEventListener('DOMContentLoaded', function () {
    // Инициализация tooltips Bootstrap
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Анимация загрузки карточек
    const cards = document.querySelectorAll('.card');
    cards.forEach((card, index) => {
        card.style.animationDelay = `${index * 0.1}s`;
    });

    // Обработка формы поиска
    const searchForm = document.querySelector('form[action*="/search"]');
    if (searchForm) {
        searchForm.addEventListener('submit', function (e) {
            const searchInput = this.querySelector('input[name="nameQuery"]');
            if (searchInput.value.trim() === '') {
                e.preventDefault();
                searchInput.focus();
                showAlert('Введите поисковый запрос', 'warning');
            }
        });
    }

    // Обработка кликов по карточкам ресторанов
    const restaurantCards = document.querySelectorAll('.card');
    restaurantCards.forEach(card => {
        const link = card.querySelector('a[href*="/restaurants/"]');
        if (link) {
            card.style.cursor = 'pointer';
            card.addEventListener('click', function (e) {
                if (e.target.tagName !== 'A' && e.target.tagName !== 'BUTTON') {
                    link.click();
                }
            });
        }
    });

    // Копирование ID ресторана в буфер обмена
    const copyButtons = document.querySelectorAll('[data-copy]');
    copyButtons.forEach(button => {
        button.addEventListener('click', function () {
            const text = this.getAttribute('data-copy');
            navigator.clipboard.writeText(text).then(() => {
                showAlert('ID скопирован в буфер обмена', 'success');
            });
        });
    });

    // Автоматическое скрытие алертов
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(alert => {
        if (alert.classList.contains('alert-success') || alert.classList.contains('alert-info')) {
            setTimeout(() => {
                alert.style.transition = 'opacity 0.5s ease-out';
                alert.style.opacity = '0';
                setTimeout(() => alert.remove(), 500);
            }, 3000);
        }
    });

    // Обработка пагинации
    const pagination = document.querySelector('.pagination');
    if (pagination) {
        pagination.addEventListener('click', function (e) {
            if (e.target.classList.contains('page-link')) {
                // Добавляем индикатор загрузки
                showLoadingIndicator();
            }
        });
    }

    // Обработка ошибок AJAX (если будут добавлены AJAX запросы)
    window.addEventListener('unhandledrejection', function (event) {
        console.error('Unhandled promise rejection:', event.reason);
        showAlert('Произошла ошибка при загрузке данных', 'danger');
    });
});

// Функция для показа уведомлений
function showAlert(message, type = 'info') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    `;

    const container = document.querySelector('main .container');
    container.insertBefore(alertDiv, container.firstChild);

    // Автоматически скрываем через 5 секунд
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, 5000);
}

// Функция для показа индикатора загрузки
function showLoadingIndicator() {
    const existingLoader = document.querySelector('.loading-indicator');
    if (existingLoader) {
        existingLoader.remove();
    }

    const loader = document.createElement('div');
    loader.className = 'loading-indicator text-center py-4';
    loader.innerHTML = `
        <div class="spinner-border text-primary" role="status">
            <span class="visually-hidden">Загрузка...</span>
        </div>
        <p class="mt-2 text-muted">Загрузка данных...</p>
    `;

    const container = document.querySelector('main .container');
    container.appendChild(loader);
}

// Функция для скрытия индикатора загрузки
function hideLoadingIndicator() {
    const loader = document.querySelector('.loading-indicator');
    if (loader) {
        loader.remove();
    }
}

// Функция для обновления статистики
function updateStats() {
    const cards = document.querySelectorAll('.card');
    const statsElement = document.querySelector('.stats-text');
    if (statsElement) {
        statsElement.textContent = `Показано ${cards.length} ресторанов`;
    }
}

// Функция для валидации форм
function validateForm(form) {
    const inputs = form.querySelectorAll('input[required]');
    let isValid = true;

    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('is-invalid');
            isValid = false;
        } else {
            input.classList.remove('is-invalid');
        }
    });

    return isValid;
}

document.addEventListener('DOMContentLoaded', function () {
    // Инициализируем все тултипы на странице
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl, {
            delay: {show: 300, hide: 100}, // Задержка для плавности
            trigger: 'hover' // Активация только при наведении
        });
    });

    // Добавляем анимацию при наведении на бейджи
    const cuisineBadges = document.querySelectorAll('.cuisine-badge');
    cuisineBadges.forEach(badge => {
        badge.addEventListener('mouseenter', function () {
            this.style.transform = 'scale(1.05)';
        });

        badge.addEventListener('mouseleave', function () {
            this.style.transform = '';
        });
    });
});


//new

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('createRestaurantForm');

    // Инициализация tooltips Bootstrap
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    const tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Валидация формы
    form.addEventListener('submit', function (e) {
        e.preventDefault();

        if (validateForm()) {
            // Показываем индикатор загрузки
            showLoadingIndicator();

            // Собираем данные формы
            const formData = collectFormData();

            // Отправляем данные на сервер
            createRestaurant(formData)
                .then(restaurantId => {
                    hideLoadingIndicator();
                    showAlert('Ресторан успешно создан!', 'success');

                    // Очистка формы после успешного создания
                    form.reset();

                    // Перенаправление на страницу со списком ресторанов
                    setTimeout(() => {
                        window.location.href = '/restaurants';
                    }, 2000);
                })
                .catch(error => {
                    hideLoadingIndicator();
                    showAlert('Ошибка при создании ресторана: ' + error.message, 'danger');
                });
        }
    });

    // Валидация в реальном времени
    const requiredFields = form.querySelectorAll('[required]');
    requiredFields.forEach(field => {
        field.addEventListener('blur', function() {
            validateField(this);
        });
    });

    // Валидация email
    const emailField = document.getElementById('email');
    if (emailField) {
        emailField.addEventListener('blur', function() {
            if (this.value && !isValidEmail(this.value)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }

    // Валидация URL
    const urlFields = document.querySelectorAll('input[type="url"]');
    urlFields.forEach(field => {
        field.addEventListener('blur', function() {
            if (this.value && !isValidUrl(this.value)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    });

    // Валидация телефона
    const phoneField = document.getElementById('phone');
    if (phoneField) {
        phoneField.addEventListener('blur', function() {
            if (this.value && !isValidPhone(this.value)) {
                this.classList.add('is-invalid');
            } else {
                this.classList.remove('is-invalid');
            }
        });
    }
});

// Функция для сбора данных формы
function collectFormData() {
    const form = document.getElementById('createRestaurantForm');
    const formData = new FormData(form);

    // Собираем данные в объект
    const data = {
        name: formData.get('name'),
        description: formData.get('description'),
        phone: formData.get('phone'),
        email: formData.get('email'),
        website: formData.get('website')
    };

    // Собираем данные адреса
    const addressData = {
        street: formData.get('street'),
        city: formData.get('city'),
        building: formData.get('building'),
        apartment: formData.get('apartment'),
        postalCode: formData.get('postalCode')
    };

    // Собираем удобства
    const amenities = [];
    const amenityCheckboxes = document.querySelectorAll('input[name="amenities"]:checked');
    amenityCheckboxes.forEach(checkbox => {
        amenities.push(checkbox.value);
    });

    return {
        restaurant: data,
        address: addressData,
        cuisineType: document.getElementById('cuisineType').value,
        priceRange: document.getElementById('priceRange').value,
        amenities: amenities
    };
}

// Функция для отправки данных на сервер
async function createRestaurant(data) {
    try {
        // Сначала создаем ресторан
        const restaurantResponse = await fetch('/api/restaurants', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data.restaurant)
        });

        if (!restaurantResponse.ok) {
            throw new Error('Ошибка при создании ресторана');
        }

        const restaurant = await restaurantResponse.json();

        // Затем создаем адрес для ресторана
        if (data.address.street || data.address.city) {
            const addressResponse = await fetch(`/restaurant/${restaurant.id}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data.address)
            });

            if (!addressResponse.ok) {
                console.warn('Адрес не был создан');
            }
        }

        return restaurant.id;
    } catch (error) {
        console.error('Ошибка:', error);
        throw error;
    }
}

function validateForm() {
    const form = document.getElementById('createRestaurantForm');
    const requiredFields = form.querySelectorAll('[required]');
    let isValid = true;

    requiredFields.forEach(field => {
        if (!validateField(field)) {
            isValid = false;
        }
    });

    const emailField = document.getElementById('email');
    if (emailField && emailField.value && !isValidEmail(emailField.value)) {
        emailField.classList.add('is-invalid');
        isValid = false;
    }

    const urlFields = document.querySelectorAll('input[type="url"]');
    urlFields.forEach(field => {
        if (field.value && !isValidUrl(field.value)) {
            field.classList.add('is-invalid');
            isValid = false;
        }
    });

    const phoneField = document.getElementById('phone');
    if (phoneField && phoneField.value && !isValidPhone(phoneField.value)) {
        phoneField.classList.add('is-invalid');
        isValid = false;
    }

    return isValid;
}

function validateField(field) {
    if (!field.value.trim()) {
        field.classList.add('is-invalid');
        return false;
    } else {
        field.classList.remove('is-invalid');
        return true;
    }
}

function isValidEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function isValidUrl(url) {
    try {
        new URL(url);
        return true;
    } catch (_) {
        return false;
    }
}

function isValidPhone(phone) {
    // Простая валидация телефона (можно улучшить)
    const phoneRegex = /^[\+]?[0-9\s\-\(\)]{10,}$/;
    return phoneRegex.test(phone);
}

function showAlert(message, type = 'info') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show mt-3`;
    alertDiv.innerHTML = `
                ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            `;

    const container = document.querySelector('main .container');
    container.insertBefore(alertDiv, container.firstChild);

    // Автоматически скрываем через 5 секунд
    setTimeout(() => {
        if (alertDiv.parentNode) {
            alertDiv.remove();
        }
    }, 5000);
}

function showLoadingIndicator() {
    const existingLoader = document.querySelector('.loading-indicator');
    if (existingLoader) {
        existingLoader.remove();
    }

    const loader = document.createElement('div');
    loader.className = 'loading-indicator';
    loader.innerHTML = `
                <div class="text-center">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Загрузка...</span>
                    </div>
                    <p class="mt-2 text-muted">Создание ресторана...</p>
                </div>
            `;

    document.body.appendChild(loader);
}

function hideLoadingIndicator() {
    const loader = document.querySelector('.loading-indicator');
    if (loader) {
        loader.remove();
    }
}

// Экспорт функций для использования в других скриптах
window.RestaurantApp = {
    showAlert,
    showLoadingIndicator,
    hideLoadingIndicator,
    updateStats,
    validateForm
};
