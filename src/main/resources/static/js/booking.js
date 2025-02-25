document.addEventListener('DOMContentLoaded', function() {
    console.log('Booking script loaded!');

    const dateInput = document.getElementById('datum');
    const timeGrid = document.getElementById('timeGrid');
    const tableGrid = document.getElementById('tableGrid');
    const bookingForm = document.getElementById('bookingForm');
    const statusDiv = document.getElementById('bookingStatus');

    let selectedTime = null;
    let selectedTable = null;

    // Set minimum date to today
    const today = new Date();
    dateInput.min = today.toISOString().split('T')[0];

    // Table configuration
    const tables = [
        { id: 1, seats: 2, location: 'Fönster' },
        { id: 2, seats: 4, location: 'Fönster' },
        { id: 3, seats: 6, location: 'Mitten' },
        { id: 4, seats: 2, location: 'Bar' },
        { id: 5, seats: 4, location: 'Mitten' },
        { id: 6, seats: 6, location: 'Mitten' }
    ];

    // Fetch booked tables from database
    async function getBookedTables(date) {
        try {
            const response = await fetch(`/api/bookings/booked-tables?date=${date}`);
            if (!response.ok) throw new Error('Failed to fetch bookings');
            return await response.json();
        } catch (error) {
            console.error('Error fetching booked tables:', error);
            return {};
        }
    }

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

    async function updateTables() {
        const date = dateInput.value;
        const selectedPeople = parseInt(document.getElementById('antal').value);

        try {
            const bookedTablesData = await getBookedTables(date);

            tableGrid.innerHTML = '';
            tables.forEach(table => {
                const tableBtn = document.createElement('button');
                tableBtn.className = 'table-btn';

                const isBooked = selectedTime &&
                    bookedTablesData[selectedTime]?.includes(table.id);
                const isTooSmall = table.seats < selectedPeople;

                if (isBooked) {
                    tableBtn.classList.add('booked');
                    tableBtn.title = 'Bordet är redan bokat';
                }
                if (isTooSmall) {
                    tableBtn.classList.add('too-small');
                    tableBtn.title = 'För litet bord för sällskapet';
                }

                tableBtn.innerHTML = `
                    <div>Bord ${table.id}</div>
                    <div>${table.seats} platser</div>
                    <div class="table-location">${table.location}</div>
                    ${isBooked ? '<div class="booked-label">Upptaget</div>' : ''}
                `;

                if (!isBooked && !isTooSmall) {
                    tableBtn.onclick = () => selectTable(table, tableBtn);
                }

                tableGrid.appendChild(tableBtn);
            });
        } catch (error) {
            console.error('Error:', error);
            statusDiv.textContent = 'Kunde inte uppdatera bordsstatus';
            statusDiv.className = 'error';
        }
    }

    function selectTime(time, btn) {
        document.querySelectorAll('.time-btn').forEach(b => b.classList.remove('selected'));
        btn.classList.add('selected');
        selectedTime = time;
        updateTables();
    }

    function selectTable(table, btn) {
        document.querySelectorAll('.table-btn').forEach(b => b.classList.remove('selected'));
        btn.classList.add('selected');
        selectedTable = table;
    }


    // Event listeners
    dateInput.addEventListener('change', function() {
        selectedTime = null;
        selectedTable = null;
        displayAvailableTimes(this.value);
        updateTables();
    });

    document.getElementById('antal').addEventListener('change', updateTables);

    // Booking Form Handler

    bookingForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        if (!selectedTime || !selectedTable) {
            document.getElementById('bookingStatus').textContent = 'Vänligen välj både tid och bord';
            return;
        }

        const formData = {
            name: document.getElementById('namn').value,
            email: document.getElementById('email').value,
            phone: document.getElementById('telefon').value,
            date: dateInput.value,
            time: selectedTime + ":00", // Add seconds to match TIME format
            peopleCount: parseInt(document.getElementById('antal').value),
            tableNumber: selectedTable.id
        };

        try {
            const response = await fetch('/api/bookings', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                document.getElementById('bookingStatus').textContent = 'Bokning bekräftad!';
                bookingForm.reset();

                // Clear the selection
                selectedTime = null;
                selectedTable = null;
                document.querySelectorAll('.selected').forEach(el =>
                    el.classList.remove('selected')
                );
            } else {
                const error = await response.json();
                document.getElementById('bookingStatus').textContent =
                    error.message || 'Bokning misslyckades';
            }
        } catch (error) {
            document.getElementById('bookingStatus').textContent =
                'Kunde inte genomföra bokningen';
        }
    });

    // Initialize with today's date
    dateInput.value = new Date().toISOString().split('T')[0];
    displayAvailableTimes(dateInput.value);
    updateTables();
});