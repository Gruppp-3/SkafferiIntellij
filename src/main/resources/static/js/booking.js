document.addEventListener('DOMContentLoaded', function() {
    console.log('Booking script loaded!');

    const dateInput = document.getElementById('datum');
    const timeGrid = document.getElementById('timeGrid');
    const bookingForm = document.getElementById('bookingForm');
    const statusDiv = document.getElementById('bookingStatus');

    let selectedTime = null;

    // Set minimum booking date to tomorrow
    const today = new Date();
    today.setDate(today.getDate() + 1);
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

    bookingForm.addEventListener('submit', async function (e) {
        e.preventDefault(); // Stop normal form submission

        if (!selectedTime) {
            statusDiv.textContent = 'Välj en tid innan du bokar.';
            statusDiv.className = 'error';
            return;
        }

        const bookingData = {
            date: dateInput.value,
            time: selectedTime,
            name: document.getElementById('namn').value.trim(),
            email: document.getElementById('email').value.trim(),
            phone: document.getElementById('telefon').value.trim(),
            peopleCount: parseInt(document.getElementById('antal').value)
        };

        console.log("Booking Data Sent:", bookingData); // Debug log

        try {
            const response = await fetch('/api/bookings', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(bookingData)
            });

            console.log("Response Status:", response.status);

            const result = await response.json();
            console.log("Response Data:", result);

            if (response.ok) {
                statusDiv.textContent = 'Bokning bekräftad!';
                statusDiv.className = 'success';
            } else {
                statusDiv.textContent = `Fel: ${result.message}`;
                statusDiv.className = 'error';
            }
        } catch (error) {
            console.error('Error:', error);
            statusDiv.textContent = 'Kunde inte genomföra bokningen.';
            statusDiv.className = 'error';
        }
    });

    dateInput.value = today.toISOString().split('T')[0];
    displayAvailableTimes(dateInput.value);
});
