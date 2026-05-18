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

// Отправка формы ответов через REST API с гарантированной защитой от мигания
document.getElementById('rsvpForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const submitButton = this.querySelector('button');
    submitButton.disabled = true;
    submitButton.innerText = 'Отправка...';

    const data = {
        token: document.getElementById('guestTokenHolder').getAttribute('data-token'),
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
        if (response.ok) {
            const formContainer = document.getElementById('formContainer');
            const successMessage = document.getElementById('successMessage');

            // 1. Плавно тушим форму
            formContainer.style.transition = 'opacity 0.4s ease';
            formContainer.style.opacity = '0';

            setTimeout(() => {
                // 2. Полностью убираем форму из документа
                formContainer.style.display = 'none';

                // 3. Подготавливаем плашку успеха (она скрыта через opacity)
                successMessage.style.display = 'block';
                successMessage.style.opacity = '0';
                successMessage.style.transition = 'opacity 0.5s ease';

                // 4. На следующем кадре плавно её проявляем
                setTimeout(() => {
                    successMessage.style.opacity = '1';
                }, 50);

            }, 400);
        } else {
            alert('Произошла ошибка при сохранении ответа. Попробуйте еще раз.');
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
document.addEventListener('DOMContentLoaded', () => {
  const blocks = document.querySelectorAll('.reveal-block');

  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('is-visible');
        observer.unobserve(entry.target); // Анимация срабатывает один раз
      }
    });
  }, {
    threshold: 0.15 // Блок начинает проявляться, когда виден на 15%
  });

  blocks.forEach(block => observer.observe(block));
});
