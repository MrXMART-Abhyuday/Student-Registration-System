/**
 * Student Registration System & College Database Management
 * Client-side Controller & UI Interactions
 */

document.addEventListener('DOMContentLoaded', () => {
    // 1. Live Table Search (Filter without reload if on student list)
    const liveSearchInput = document.getElementById('liveStudentSearch');
    if (liveSearchInput) {
        liveSearchInput.addEventListener('keyup', function() {
            const term = this.value.toLowerCase().trim();
            const tableRows = document.querySelectorAll('#studentsTableBody tr');
            tableRows.forEach(row => {
                const text = row.innerText.toLowerCase();
                row.style.display = text.includes(term) ? '' : 'none';
            });
        });
    }

    // 2. Real-time Form Validation for Registration
    const regForm = document.getElementById('studentRegistrationForm');
    if (regForm) {
        regForm.addEventListener('submit', function(e) {
            const firstName = document.getElementById('firstName').value.trim();
            const lastName = document.getElementById('lastName').value.trim();
            const dob = document.getElementById('dateOfBirth').value.trim();
            const phone = document.getElementById('phoneNumber').value.trim();
            const email = document.getElementById('email').value.trim();

            if (!firstName || !lastName || !dob) {
                e.preventDefault();
                alert('Please fill in all mandatory fields marked with * (First Name, Last Name, Date of Birth).');
                return;
            }

            if (phone && !/^[0-9+\-\s()]{7,15}$/.test(phone)) {
                e.preventDefault();
                alert('Please enter a valid phone number (e.g. +91 9811002201).');
                return;
            }

            if (email && !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
                e.preventDefault();
                alert('Please enter a valid email address.');
                return;
            }
        });
    }
});

// Confirmation Modal / Prompt for Deletion
function confirmDelete(studentId, studentName) {
    if (confirm(`Are you sure you want to de-register and remove student "${studentName}" (ID: ${studentId})?\n\nThis action cannot be undone.`)) {
        window.location.href = `${window.location.origin}/student/delete?id=${studentId}`;
    }
}

// Quick Print Trigger
function printDocument() {
    window.print();
}

