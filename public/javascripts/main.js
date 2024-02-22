function methodForTime() {
    for (let hour = 0; hour < 24; hour++) {
        for (let minute = 0; minute < 60; minute += 15) {
            const timeString = `${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}`;
            document.write(`<option value="${timeString}">${timeString}</option>`);
        }
    }
}
const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show');
            entry.target.classList.remove('hidden');
        } else {
            entry.target.classList.remove('show');
            entry.target.classList.add('hidden');
        }
    });
}, {
    root: null,
    rootMargin: '0px',
    threshold: 0.5,
});


const hiddenElements = document.querySelectorAll('.hidden');


hiddenElements.forEach((el) => observer.observe(el));

document.addEventListener("DOMContentLoaded", function(event) {
    submitForm();


});

function submitForm() {
    a = document.getElementById("showReservation").innerHTML;
    if (a === "true") {
        document.getElementById("myForm").style.display = "block"
    }
}

function openForm() {
    document.getElementById("myForm").style.display = "block";
    setTimeout(function () {
        document.querySelector('.form-popup').style.transform = 'translate(-50%, -50%) scale(1)';
    }, 10);
    document.getElementById("notification1").style.display = "none";
}

function closeForm() {
    document.querySelector('.form-popup').style.transform = 'translate(-50%, -50%) scale(0)';
    setTimeout(function () {
        document.getElementById("myForm").style.display = "none";
    }, 500);
}
document.addEventListener('DOMContentLoaded', function() {
    window.addEventListener('scroll', function() {
        var elements = document.querySelectorAll('.animatedElement');

        elements.forEach(function(element) {
            var position = element.getBoundingClientRect();

            // Adjust the threshold as needed
            if (position.top < window.innerHeight / 2) {
                element.style.opacity = 1;
                element.style.transform = 'translateY(0)';
            } else {
                element.style.opacity = 0;
                element.style.transform = 'translateY(50%)';
            }
        });
    });
});

