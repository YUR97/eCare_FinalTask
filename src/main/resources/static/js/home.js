function openPop() {

    const overlay = $('.overlay');
    const main = $('main');
    const header = $('header');

    main.fadeTo(1000, 0.4, function () {
    })

    header.fadeTo(1000, 0.4, function () {
    })

    overlay.fadeIn(1000, function () {
        overlay.css('visibility', 'visible');
    })

}

function closePop() {

    const overlay = $('.overlay');
    const main = $('main');
    const header = $('header');

    main.fadeTo(1000, 1, function () {
    })

    header.fadeTo(1000, 1, function () {
    })

    overlay.css('display', 'none')

}

function validationInfo() {

    const patternName = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternSurname = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternPassport = /^[0-9]{4}[-]{1}[0-9]{6}$/;
    const patternAddress = /^[А-Я]{1}[а-яА-Я-]{1,19}$/;

    const name = $('input[name="name"]');
    const surname = $('input[name="surname"]');
    const passport = $('input[name="passport"]');
    const address = $('input[name="address"]');
    const changeButtonInfo = $('#changeButtonInfo');

    if (name.val().search(patternName) !== 0) {
        name.addClass('errorInput');
    }

    if (surname.val().search(patternSurname) !== 0) {
        surname.addClass('errorInput');
    }

    if (passport.val().search(patternPassport) !== 0) {
        passport.addClass('errorInput');
    }

    if (address.val().search(patternAddress) !== 0) {
        address.addClass('errorInput');
    }

    const nameHasError = name.hasClass('errorInput');
    const surnameHasError = surname.hasClass('errorInput');
    const passportHasError = passport.hasClass('errorInput');
    const addressHasError = address.hasClass('errorInput');

    if (nameHasError || surnameHasError || passportHasError || addressHasError) {
        changeButtonInfo.prop('disabled', true);
        $('#errorPersonData').text("Неверные данные");
    } else {
        changeButtonInfo.prop('disabled', false);
    }

}

function checkInput() {

    const patternName = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternSurname = /^[А-Яа-я]{1}[а-я]{1,14}$/;
    const patternPassport = /^[0-9]{4}[-]{1}[0-9]{6}$/;
    const patternAddress = /^[А-Я]{1}[а-яА-Я-]{1,19}$/;

    const name = $('input[name="name"]');
    const surname = $('input[name="surname"]');
    const passport = $('input[name="passport"]');
    const address = $('input[name="address"]');
    const changeButtonInfo = $('#changeButtonInfo');

    if (name.val().search(patternName) === 0) {
        name.removeClass('errorInput');
    }

    if (surname.val().search(patternSurname) === 0) {
        surname.removeClass('errorInput');
    }

    if (passport.val().search(patternPassport) === 0) {
        passport.removeClass('errorInput');
    }

    if (address.val().search(patternAddress) === 0) {
        address.removeClass('errorInput');
    }

    const nameHasError = name.hasClass('errorInput');
    const surnameHasError = surname.hasClass('errorInput');
    const passportHasError = passport.hasClass('errorInput');
    const addressHasError = address.hasClass('errorInput');

    if (nameHasError || surnameHasError || passportHasError || addressHasError) {
        changeButtonInfo.prop('disabled', true);
    } else {
        changeButtonInfo.prop('disabled', false);
        $('#errorPersonData').text("");
    }

}

function validationPassword() {

    const patternPassword = /^[a-z0-9]{6,20}$/;

    const changeButtonPassword = $('#changeButtonPassword');
    const newPassword = $('input[name="newPassword"]');
    const password = $('input[name="password"]');

    if (newPassword.val() === password.val()) {
        if (password.val().search(patternPassword) !== 0) {
            password.addClass('errorInput');
            newPassword.addClass('errorInput');
            $('#errorPassword').text("Неверные данные");
        }
    } else {
        $('#errorPassword').text("Пароли не совпадают");
        password.addClass('errorInput');
        newPassword.addClass('errorInput');
    }

    const passwordHasError = password.hasClass('errorInput');

    if (passwordHasError) {
        changeButtonPassword.prop('disabled', true);
    } else {
        changeButtonPassword.prop('disabled', false);
        $('#errorPassword').text("");
    }

}

function checkInputPassword() {

    const patternPassword = /^[a-z0-9]{6,20}$/;

    const changeButtonPassword = $('#changeButtonPassword');
    const newPassword = $('input[name="newPassword"]');
    const password = $('input[name="password"]');

    if (newPassword.val().search(patternPassword) === 0) {
        newPassword.removeClass('errorInput');
    }

    if (password.val().search(patternPassword) === 0) {
        password.removeClass('errorInput');
    }

    if(newPassword.val() === password.val()) {
        $('#errorPassword').text("Неверные данные");
    }
    else {
        $('#errorPassword').text("Пароли не совпадают");
    }

    const passwordHasError = password.hasClass('errorInput');
    const newPasswordHasError = newPassword.hasClass('errorInput');

    if (passwordHasError || newPasswordHasError) {
        changeButtonPassword.prop('disabled', true);
    } else {
        changeButtonPassword.prop('disabled', false);
        $('#errorPassword').text("");
    }

}