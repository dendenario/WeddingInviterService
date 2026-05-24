/**
 * FINE DESIGN SYSTEM — DENIS & MARIA WEDDING
 * CORE LOGIC FILE (TIMER, RSVP FETCH, ANIMATIONS)
 */

document.addEventListener('DOMContentLoaded', () => {
    // ==========================================================================
    // 1. ТАЙМЕР ОБРАТНОГО ОТСЧЕТА
    // ==========================================================================
    const timerElement = document.getElementById('weddingIsoTimestamp');

    if (timerElement) {
        const rawDateStr = timerElement.getAttribute('data-iso') || "2026-08-29T15:00:00";
        const targetDate = new Date(rawDateStr).getTime();

        const countdownContainer = document.getElementById('countdown');
        const daysSpan = document.getElementById('days');
        const hoursSpan = document.getElementById('hours');
        const minutesSpan = document.getElementById('minutes');

        function updateTimer() {
            const now = new Date().getTime();
            const difference = targetDate - now;

            // Если дата свадьбы наступила или прошла
            if (difference <= 0) {
                if (countdownContainer) {
                    countdownContainer.innerHTML = "<p style='font-family: \"Montserrat\", -apple-system, sans-serif; font-size: 1.2rem; font-weight: 500; width: 100%; text-align: center;'>Этот счастливый день настал!</p>";
                }
                clearInterval(timerInterval);
                return;
            }

            // Расчет единиц времени
            const days = Math.floor(difference / (1000 * 60 * 60 * 24));
            const hours = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
            const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));

            // Безопасное обновление DOM (если элементы существуют на странице)
            if (daysSpan) daysSpan.innerText = String(days).padStart(2, '0');
            if (hoursSpan) hoursSpan.innerText = String(hours).padStart(2, '0');
            if (minutesSpan) minutesSpan.innerText = String(minutes).padStart(2, '0');
        }

        updateTimer();
        const timerInterval = setInterval(updateTimer, 60000); // Обновление раз в минуту
    }

    // ==========================================================================
    // 2. ОТПРАВКА ФОРМЫ ОТВЕТОВ RSVP (REST API)
    // ==========================================================================
    const rsvpForm = document.getElementById('rsvpForm');

    if (rsvpForm) {
        rsvpForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const submitButton = this.querySelector('button');
            if (!submitButton) return;

            // Блокировка кнопки во избежание дублирующих запросов
            submitButton.disabled = true;
            submitButton.innerText = 'Отправка...';

            // Сбор данных формы
            const data = {
                token: rsvpForm.getAttribute('data-token'),
                attending: document.getElementById('attending') ? document.getElementById('attending').checked : false,
                alcoholPreference: document.getElementById('alcohol') ? document.getElementById('alcohol').value : "Не определено",
                shuttleRequired: document.getElementById('shuttle') ? document.getElementById('shuttle').checked : false,
                commentary: document.getElementById('commentary') ? document.getElementById('commentary').value : ""
            };

            fetch('/api/rsvp', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(data)
            })
            .then(response => {
                if (response.ok) {
                    const formContainer = document.getElementById('formContainer');
                    const successMessage = document.getElementById('successMessage');

                    if (formContainer && successMessage) {
                        // 1. Плавно тушим контейнер формы
                        formContainer.style.transition = 'opacity 0.4s ease';
                        formContainer.style.opacity = '0';

                        setTimeout(() => {
                            // 2. Полностью убираем форму из разметки
                            formContainer.style.display = 'none';

                            // 3. Подготавливаем плашку успеха
                            successMessage.style.display = 'block';
                            successMessage.style.opacity = '0';
                            successMessage.style.transition = 'opacity 0.5s ease';

                            // 4. На следующем кадре плавно её проявляем
                            setTimeout(() => {
                                successMessage.style.opacity = '1';
                            }, 50);

                        }, 400);
                    }
                } else {
                    alert('Произошла ошибка при сохранении ответа. Попробуйте еще раз.');
                    submitButton.disabled = false;
                    submitButton.innerText = 'Отправить ответ';
                }
            })
            .catch(error => {
                console.error('Ошибка RSVP:', error);
                alert('Сбой соединения с сервером.');
                submitButton.disabled = false;
                submitButton.innerText = 'Отправить ответ';
            });
        });
    }

    // ==========================================================================
    // 3. ПЛАВНОЕ ПОЯВЛЕНИЕ ЭЛЕМЕНТОВ (INTERSECTION OBSERVER)
    // ==========================================================================
    const revealBlocks = document.querySelectorAll('.reveal-block');

    if (revealBlocks.length > 0) {
        // Проверка: если экран мобильный (меньше 480px), пропускаем инициализацию observer,
        // так как CSS принудительно отобразит блоки мгновенно ради производительности.
        if (window.innerWidth > 480) {
            const observerOptions = {
                threshold: 0.15 // Элемент активируется, когда виден на 15%
            };

            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('is-visible');
                        observer.unobserve(entry.target); // Анимация срабатывает только один раз
                    }
                });
            }, observerOptions);

            revealBlocks.forEach(block => observer.observe(block));
        } else {
            // Для мобильных устройств сразу добавляем класс видимости
            revealBlocks.forEach(block => block.classList.add('is-visible'));
        }
    }
});
