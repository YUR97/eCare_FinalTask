window.onload = function () {

    const email = $('#email');
    const isExist = (error === "emailExist");

    if (isExist) {
        email.addClass('errorInput');
        $('#signUpButton').prop('disabled', true);
        $('#errorText').text("Этот Email уже занят");
    }

}

function validation() {

    const patternEmail = /^[a-z0-9_-]+@[a-z0-9-]+\.[a-z]{2,6}$/;
    const patternPassword = /^[a-z0-9]{6,20}$/;
    const patternName = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternSurname = /^[А-Яа-я]{1}[а-я]{1,14}$/;

    const email = $('#email');
    const password = $('#password');
    const name = $('#name');
    const surname = $('#surname');

    if (email.val().search(patternEmail) !== 0) {
        email.addClass('errorInput');
    }

    if (password.val().search(patternPassword) !== 0) {
        password.addClass('errorInput');
    }

    if (name.val().search(patternName) !== 0) {
        name.addClass('errorInput');
    }

    if (surname.val().search(patternSurname) !== 0) {
        surname.addClass('errorInput');
    }

    const emailHasError = email.hasClass('errorInput');
    const passwordHasError = password.hasClass('errorInput');
    const nameHasError = name.hasClass('errorInput');
    const surnameHasError = surname.hasClass('errorInput');

    if (emailHasError || passwordHasError || nameHasError || surnameHasError) {
        $('#signUpButton').prop('disabled', true);
        $('#errorText').text("Неверные данные");
    } else {
        $('#signUpButton').prop('disabled', false);
    }

}

function checkInput() {

    const patternEmail = /^[a-z0-9_-]+@[a-z0-9-]+\.[a-z]{2,6}$/;
    const patternPassword = /^[a-z0-9]{6,20}$/;
    const patternName = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternSurname = /^[А-Яа-я]{1}[а-я]{1,14}$/;

    const email = $('#email');
    const password = $('#password');
    const name = $('#name');
    const surname = $('#surname');

    if (email.val().search(patternEmail) === 0) {
        email.removeClass('errorInput');
    }

    if (password.val().search(patternPassword) === 0) {
        password.removeClass('errorInput');
    }

    if (name.val().search(patternName) === 0) {
        name.removeClass('errorInput');
    }

    if (surname.val().search(patternSurname) === 0) {
        surname.removeClass('errorInput');
    }

    const emailHasError = email.hasClass('errorInput');
    const passwordHasError = password.hasClass('errorInput');
    const nameHasError = name.hasClass('errorInput');
    const surnameHasError = surname.hasClass('errorInput');

    if (emailHasError || passwordHasError || nameHasError || surnameHasError) {
        $('#signUpButton').prop('disabled', true);
        $('#errorText').text("Неверные данные");
    } else {
        $('#signUpButton').prop('disabled', false);
        $('#errorText').text("");
    }

}