export const validateEmail = (email, setEmailValid) => {
    setEmailValid(false);
    if (email && email.match("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) setEmailValid(true);
}
export const checkPasswordsEquality = (password, passwordRepeat, setPasswordsEqual) =>{
    setPasswordsEqual(false)
    if (password === passwordRepeat) {setPasswordsEqual(true)}
}
export const validatePhone = (phoneNumber, setPhoneNumberValid) => {
    setPhoneNumberValid(false)
    if (phoneNumber.match("^[+][0-9]{11,15}$")) setPhoneNumberValid(true);
}
export const validateFile = (innFile, setInnFileValid, type) => {
    setInnFileValid(false)
    if (!innFile || innFile.type==type) setInnFileValid(true);
}
export const checkEmpty = (state, setFunction) => {
    setFunction(false);
    if (state) setFunction(true);
}

export const validateUsername = (userName, setUsernameValid) =>{
    setUsernameValid(false);
    console.log(userName.match("[A-Za-zА-Яа-я]{8,50}"));
    if (userName.match("[A-Za-zА-Яа-я]{8,50}")) setUsernameValid(true);
}

export const validatePassword = (password, setPasswordValid) => {
    let upperCase = password.match("[A-Z]");
    let lowerCase = password.match("[a-z]");
    let digits = password.match("[0-9]");

    setPasswordValid(false)

    if  (password.length >= 8 &&
        upperCase && upperCase.length > 0 &&
        lowerCase && lowerCase.length > 0 &&
        digits && digits.length > 0)  {
        setPasswordValid(true)
    }
}

