let usernameAlert;
let passwordAlert;
let repeatPasswordAlert;
let emailAlert;
let firstNameAlert;
let lastNameAlert;
let patronymicAlert;
let phoneNumberAlert;
let companyNameAlert;

let usernameInput;
let passwordInput;
let repeatPasswordInput;
let emailInput;
let firstNameInput;
let lastNameInput;
let patronymicInput;
let phoneNumberInput;
let companyNameInput;

document.addEventListener("DOMContentLoaded", (event) => {
    usernameAlert = document.getElementById("username-alert");
    passwordAlert = document.getElementById("password-alert");
    repeatPasswordAlert = document.getElementById("repeatPassword-alert");
    emailAlert = document.getElementById("email-alert");
    firstNameAlert = document.getElementById("firstName-alert");
    lastNameAlert = document.getElementById("lastName-alert");
    patronymicAlert = document.getElementById("patronymic-alert");
    phoneNumberAlert = document.getElementById("phoneNumber-alert");
    companyNameAlert = document.getElementById("companyName-alert");

    usernameInput = document.getElementById("username-input");
    passwordInput = document.getElementById("password-input");
    repeatPasswordInput = document.getElementById("repeatPassword-input");
    emailInput = document.getElementById("email-input");
    firstNameInput = document.getElementById("firstName-input");
    lastNameInput = document.getElementById("lastName-input");
    patronymicInput = document.getElementById("patronymic-input");
    phoneNumberInput = document.getElementById("phoneNumber-input");
    companyNameInput = document.getElementById("companyName-input");
});


function SignUp() {
    let valid = true;
    console.log(passwordInput);
    console.log(passwordInput.value);
    if (usernameInput) {
        if (isEmpty(usernameInput.value) || !validateUsername(usernameInput.value)) {
            usernameAlert.hidden = false;
            valid = false;
        } else
            usernameAlert.hidden = true;
    }
    if (companyNameInput) {
        if (isEmpty(companyNameInput.value)) {
            companyNameAlert.hidden = false;
            valid = false;
        } else companyNameAlert.hidden = true;
    }
    if (passwordInput) {
        if (isEmpty(passwordInput.value) || !validatePassword(passwordInput.value)) {
            passwordAlert.hidden = false;
            valid = false;
        } else
            passwordAlert.hidden = true;
    }
    if (repeatPasswordInput) {
        if (isEmpty(repeatPasswordInput.value) || !checkPasswordsEquality(passwordInput.value, repeatPasswordInput.value)) {
            console.log("not eq")
            repeatPasswordAlert.hidden = false;
            valid = false;
        } else
            repeatPasswordAlert.hidden = true;
    }
    if (emailInput) {
        if (isEmpty(emailInput.value) || !validateEmail(emailInput.value)) {
            emailAlert.hidden = false;
            valid = false;
        } else
            emailAlert.hidden = true;
    }
    if (firstNameInput) {
        if (isEmpty(firstNameInput.value) || !validateName(firstNameInput.value)) {
            firstNameAlert.hidden = false;
            valid = false;
        } else
            firstNameAlert.hidden = true;
    }
    if (lastNameInput) {
        if (!validateName(lastNameInput.value)) {
            lastNameAlert.hidden = false;
            valid = false;
        } else
            lastNameAlert.hidden = true;
    }
    if (patronymicInput) {
        if (!validatePatronymic(patronymicInput.value)) {
            patronymicAlert.hidden = false;
            valid = false;
        } else
            patronymicAlert.hidden = true;
    }
    if (phoneNumberInput) {
        if (!validatePhone(phoneNumberInput.value)) {
            phoneNumberAlert.hidden = false;
            valid = false;
        } else
            passwordAlert.hidden = true;
    }

    if (!valid) return;

    let attributes = new Map();
    if (patronymicInput) attributes.set('patronymic', new Array(patronymicInput.value));
    if (phoneNumberInput) attributes.set('phoneNumber', new Array(phoneNumberInput.value));
    if (companyNameInput) attributes.set('companyName', new Array(companyNameInput.value));

    let body = JSON.stringify({
        'username': usernameInput.value,
        'password': passwordInput.value,
        'email': emailInput.value,
        'firstName': firstNameInput.value,
        'lastName': lastNameInput.value,
        'attributes': Object.fromEntries(attributes)
    });

    console.log(body);
    fetch(signUpEndpoint, {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: body
    })
        .then((response) => {
            if (response.status <= 299)
                window.location = signInPage;
            if (response.status === 409)
                alert('пользователь уже существует');
        });
}