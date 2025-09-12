// JavaScript для веб-интерфейса ресторанов

document.addEventListener('DOMContentLoaded', function() {
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
        searchForm.addEventListener('submit', function(e) {
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
            card.addEventListener('click', function(e) {
                if (e.target.tagName !== 'A' && e.target.tagName !== 'BUTTON') {
                    link.click();
                }
            });
        }
    });

    // Копирование ID ресторана в буфер обмена
    const copyButtons = document.querySelectorAll('[data-copy]');
    copyButtons.forEach(button => {
        button.addEventListener('click', function() {
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
        pagination.addEventListener('click', function(e) {
            if (e.target.classList.contains('page-link')) {
                // Добавляем индикатор загрузки
                showLoadingIndicator();
            }
        });
    }

    // Обработка ошибок AJAX (если будут добавлены AJAX запросы)
    window.addEventListener('unhandledrejection', function(event) {
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

// Экспорт функций для использования в других скриптах
window.RestaurantApp = {
    showAlert,
    showLoadingIndicator,
    hideLoadingIndicator,
    updateStats,
    validateForm
};
