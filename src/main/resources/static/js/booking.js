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

    // Mock data for booked tables (you would get this from your backend in reality)
    const bookedTables = {
        // Format: 'YYYY-MM-DD': { time: [tableIds] }
        [today.toISOString().split('T')[0]]: {
            '18:00': [1, 3],
            '19:00': [2, 4]
        },
        // Tomorrow's bookings
        [new Date(today.setDate(today.getDate() + 1)).toISOString().split('T')[0]]: {
            '18:30': [5, 6],
            '20:00': [1, 2]
        }
    };

    // Table configuration
    const tables = [
        { id: 1, seats: 2, location: 'Fönster' },
        { id: 2, seats: 4, location: 'Fönster' },
        { id: 3, seats: 6, location: 'Mitten' },
        { id: 4, seats: 2, location: 'Bar' },
        { id: 5, seats: 4, location: 'Mitten' },
        { id: 6, seats: 6, location: 'Mitten' }
    ];

    function isTableBooked(tableId, date, time) {
        return bookedTables[date]?.hasOwnProperty(time) &&
            bookedTables[date][time].includes(tableId);
    }

    function updateTables() {
        const date = dateInput.value;
        const selectedPeople = parseInt(document.getElementById('antal').value);

        tableGrid.innerHTML = '';
        tables.forEach(table => {
            const tableBtn = document.createElement('button');
            tableBtn.className = 'table-btn';

            // Check if table is booked for selected time
            const isBooked = selectedTime && isTableBooked(table.id, date, selectedTime);
            // Check if table has enough seats
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
    }

    // Update your time display function
    function displayAvailableTimes(date) {
        const times = [
            "16:00", "16:30", "17:00", "17:30", "18:00", "18:30",
            "19:00", "19:30", "20:00", "20:30", "21:00", "21:30"
        ];

        timeGrid.innerHTML = '';
        times.forEach(time => {
            const timeBtn = document.createElement('button');
            timeBtn.className = 'time-btn';

            // Check if all tables are booked for this time
            const bookedTablesForTime = bookedTables[date]?.[time] || [];
            const isFullyBooked = bookedTablesForTime.length === tables.length;

            if (isFullyBooked) {
                timeBtn.classList.add('fully-booked');
                timeBtn.title = 'Fullbokat denna tid';
            }

            timeBtn.textContent = time;

            if (!isFullyBooked) {
                timeBtn.onclick = () => selectTime(time, timeBtn);
            }

            timeGrid.appendChild(timeBtn);
        });
    }

    function selectTime(time, btn) {
        document.querySelectorAll('.time-btn').forEach(b => b.classList.remove('selected'));
        btn.classList.add('selected');
        selectedTime = time;
        updateTables(); // Update tables when time is selected
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

    // Initialize with today's date
    dateInput.value = new Date().toISOString().split('T')[0];
    displayAvailableTimes(dateInput.value);
    updateTables();

    // Form submission handling remains the same...
    bookingForm.addEventListener('submit', async function(e) {
        // ... your existing form submission code
    });
});