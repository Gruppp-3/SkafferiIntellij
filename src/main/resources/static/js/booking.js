document.addEventListener('DOMContentLoaded', function() {
    console.log('Booking script loaded!');

    const dateInput = document.getElementById('datum');
    const timeGrid = document.getElementById('timeGrid');
    const bookingForm = document.getElementById('bookingForm');
    const statusDiv = document.getElementById('bookingStatus');

    let selectedTime = null;

    // Sätt minimum datum till idag
    const today = new Date();
    dateInput.min = today.toISOString().split('T')[0];

    async function displayAvailableTimes(date) {
        try {
            const response = await fetch(`/api/bookings/availability?date=${date}`);
            if (!response.ok) throw new Error('Failed to fetch available times');
            const times = await response.json();

            timeGrid.innerHTML = '';
            times.forEach(time => {
                const timeBtn = document.createElement('button');
                timeBtn.className = 'time-btn';
                timeBtn.textContent = time;
                timeBtn.onclick = () => selectTime(time, timeBtn);
                timeGrid.appendChild(timeBtn);
            });
        } catch (error) {
            console.error('Error:', error);
            statusDiv.textContent = 'Kunde inte hämta tillgängliga tider';
            statusDiv.className = 'error';
        }
    }

    function selectTime(time, btn) {
        document.querySelectorAll('.time-btn').forEach(b => b.classList.remove('selected'));
        btn.classList.add('selected');
        selectedTime = time;
    }

    dateInput.addEventListener('change', function() {
        selectedTime = null;
        displayAvailableTimes(this.value);
    });

    bookingForm.addEventListener('submit', async function(e) {
        // Samma kod som ovan för att hantera bokning
    });

    dateInput.value = today.toISOString().split('T')[0];
    displayAvailableTimes(dateInput.value);
});
