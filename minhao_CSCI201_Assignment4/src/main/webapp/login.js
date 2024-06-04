function validateSignUpForm() {
    var emailInput = document.getElementById('signup-email').value;
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    var usernameInput = document.getElementById('signup-username').value;
    var passwordInput = document.getElementById('signup-password').value;
    var confirmInput = document.getElementById('signup-confirm-password').value;
    var termsInput = document.getElementById('terms').value;


    if (emailInput.trim() === "") {
        alert("Email cannot be empty.");
        return false;
    }
    if (usernameInput.trim() === "") {
        alert("username cannot be empty.");
        return false;
    }

    if (!emailPattern.test(emailInput)) {
        alert("Please enter a valid email address.");
        return false;
    }

    if (termsInput === "") {
        alert("Please check the box");
        return false;
    }

    if (passwordInput.trim() === "") {
        alert("password cannot be empty.");
        return false;
    }
    if (confirmInput.trim() === "" || confirmInput != passwordInput) {
        alert("confirm password cannot be empty.");
        return false;
    }

    return true; 
}

function submitSignUpFormWithAjax(event) {

    if (!validateSignUpForm()) {
        return false;  
    }


    var url = "ServletSignUp";
    var params = new URLSearchParams(new FormData(document.getElementById('signup-form'))).toString();
    var fullUrl = url + "?" + params;
    console.log(fullUrl);
    // Use the Fetch API to make the AJAX call
    // fetch(fullUrl, {
		// method: 'POST'
	// })
	var formData = {
        email: document.getElementById('signup-email').value,
        username: document.getElementById('signup-username').value,
        password: document.getElementById('signup-password').value,
    };

    // “How to fetch?” prompt (2 lines). ChatGPT, 25 Nov. version, OpenAI, 25 Nov. 2023, chat.openai.com/chat
    fetch('/minhao_CSCI201_Assignment4/ServletSignUp', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' 
        },
        body: JSON.stringify(formData)  
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json(); 
        })
        .then(data => {
	        if (data.status === 'success') {
	            window.location.href = 'home.html';
	        } else {
	            alert(data.message);
	        }
        })
        .catch(error => {
            console.error('There has been a problem with your fetch operation:', error);
        });

    return false;
}

document.addEventListener('DOMContentLoaded', function() {
    const signupForm = document.getElementById('signup-form');
    if(signupForm) {
        signupForm.addEventListener('submit', function(event) {
			event.preventDefault();
            submitSignUpFormWithAjax(event); 
        });
    } else {
        console.error('Signup form not found');
    }
});

