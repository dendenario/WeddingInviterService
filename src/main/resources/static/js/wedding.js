// Логика таймера обратного отсчета
const rawDateStr = document.getElementById('weddingIsoTimestamp').getAttribute('data-iso') || "2026-09-25T16:00:00";
const targetDate = new Date(rawDateStr).getTime();

function updateTimer() {
    const now = new Date().getTime();
    const difference = targetDate - now;

    if (difference <= 0) {
        document.getElementById('countdown').innerHTML = "<p style='font-family:\"Playfair Display\", serif; font-size:1.2rem; width:100%; text-align:center;'>Этот счастливый день настал!</p>";
        clearInterval(timerInterval);
        return;
    }

    const days = Math.floor(difference / (1000 * 60 * 60 * 24));
    const hours = Math.floor((difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));

    document.getElementById('days').innerText = String(days).padStart(2, '0');
    document.getElementById('hours').innerText = String(hours).padStart(2, '0');
    document.getElementById('minutes').innerText = String(minutes).padStart(2, '0');
}

updateTimer();
const timerInterval = setInterval(updateTimer, 60000);

// Инициализация карты строго после окончания CSS-анимации всплытия карточки
document.addEventListener("DOMContentLoaded", function() {
    const mapFrame = document.getElementById('weddingMap');
    if (mapFrame) {
        const realMapUrl = mapFrame.getAttribute('data-src');
        setTimeout(() => {
            mapFrame.setAttribute('src', realMapUrl);
        }, 1300); // 1.3 секунды задержки спасают маркер Яндекса от зависания
    }
});

// Отправка новой формы ответов через REST API
document.getElementById('rsvpForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const submitButton = this.querySelector('button');
    submitButton.disabled = true;
    submitButton.innerText = 'Отправка...';

    const data = {
        name: document.getElementById('name').value,
        attending: document.getElementById('attending').checked,
        alcoholPreference: document.getElementById('alcohol').value,
        shuttleRequired: document.getElementById('shuttle').checked,
        commentary: document.getElementById('commentary').value
    };

    fetch('/api/rsvp', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    })
    .then(response => {
        if(response.ok) {
            const formContainer = document.getElementById('formContainer');
            formContainer.style.transition = 'opacity 0.5s ease';
            formContainer.style.opacity = '0';

            setTimeout(() => {
                formContainer.style.display = 'none';
                document.getElementById('successMessage').style.display = 'block';
            }, 500);
        } else {
            alert('Произошла ошибка при отправке. Попробуйте еще раз.');
            submitButton.disabled = false;
            submitButton.innerText = 'Отправить ответ';
        }
    })
    .catch(error => {
        console.error('Ошибка:', error);
        alert('Сбой соединения с сервером.');
        submitButton.disabled = false;
        submitButton.innerText = 'Отправить ответ';
    });
});
