const validateEmail = (email) => {
    if (email && email.match("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) return true;
    return false;
}
const validatePassword = (password) => {
    let upperCase = password.match("[A-Z]");
    let lowerCase = password.match("[a-z]");
    let digits = password.match("[0-9]");

    if  (password.length >= 8 &&
        upperCase && upperCase.length > 0 &&
        lowerCase && lowerCase.length > 0 &&
        digits && digits.length > 0)  {
        return true;
    }

    return false;
}
const checkPasswordsEquality = (password, passwordRepeat) =>{
    if (isEmpty(password) || isEmpty(passwordRepeat)) return false
    if (password === passwordRepeat) return true;
    return false;
}
const validatePhone = (phoneNumber) => {
    if (phoneNumber.match("[+][0-9]{10,15}")) return true;
    return false;
}
const isEmpty = (state) => {
    if (!state) return true;
    if (state.length === 0) return true;
    return false;
}
const validateUsername = (userName) =>{
    if (userName.match("[A-Za-z0-9]{8,50}")) return true;
    return false;
}

const containsOnlyLetters = (string) => {
    if (string.match("[A-Za-zА-Яа-я]")) return true;
    return false;
}

const validateName = (name) =>{
    if (isEmpty(name)) return false;
    if (!containsOnlyLetters(name)) return false;
    return true;
}

const validatePatronymic = (patronymic) => {
    if(isEmpty(patronymic)) return true;
    if (!containsOnlyLetters(patronymic)) return false;
    return true;
}