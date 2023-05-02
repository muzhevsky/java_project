import EmailInput from "../common/inputs/EmailInput";
import PasswordInput from "../common/inputs/PasswordInput";
import React, {useEffect, useState} from "react";
import CheckboxInput from "../common/inputs/CheckboxInput";
import LettersInput from "../common/inputs/LettersInput";
import InputWarning from "../common/inputs/InputWarning";
import {useCookies} from "react-cookie";
import FileInput from "../common/inputs/FileInput";
import {checkEmpty, checkPasswordsEquality, validateEmail, validateFile, validatePhone, validatePassword} from "../functions/Validation";


export default function CompanySignUp() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [companyName, setCompanyName] = useState("");
    const [ownerName, setOwnerName] = useState("");
    const [ownerSurname, setOwnerSurname] = useState("");
    const [ownerPatronymic, setOwnerPatronymic] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [innFile, setInnFile] = useState(null);
    const [isSubscriber, setIsSubscriber] = useState('n');

    const [emailIsValid, setEmailValid] = useState(false);
    const [passwordIsValid, setPasswordValid] = useState(false);
    const [passwordsAreEqual, setPasswordsEqual] = useState(false);
    const [phoneNumberIsValid, setPhoneNumberValid] = useState(false);
    const [companyNameIsValid, setCompanyNameValid] = useState(false);
    const [ownerNameIsValid, setOwnerNameValid] = useState(false);
    const [ownerSurnameIsValid, setOwnerSurnameValid] = useState(false);
    const [innFileIsValid, setInnFileValid] = useState(false);

    const [accessToken, setAccessToken] = useCookies(['myCookie']);
    const [refreshToken, setRefreshToken] = useCookies(['myCookie']);

    const sendSignupData = (e) => {
        e.preventDefault();

        let fileRepresentation = {
            name: innFile.name,
            type: innFile.type
        }

        var body = JSON.stringify({
            accountCreationForm: {
                email:email,
                password:password,
                isSubscriber: isSubscriber,
            },
            companyName:companyName,
            ownerName:ownerName,
            ownerSurname:ownerSurname,
            ownerPatronymic: ownerPatronymic,
            phoneNumber: phoneNumber,
            innFileJson: JSON
        });

        var headers = new Headers();
        headers.append("Content-Type", "application/json");

        var requestOptions = {
            method: 'POST',
            headers: headers,
            redirect: 'follow',
            body: body
        };

        fetch("/signup/company", requestOptions)
            .then(response => response.json())
            .then(response => {
                setAccessToken("accessToken", response.accessToken);
                setRefreshToken("refreshToken", response.refreshToken);
            })
            .catch(error => console.log('error', error));
    }

    useEffect(()=>{validateEmail(email, setEmailValid)}, [email]);
    useEffect(()=>{validatePassword(password, setPasswordValid)}, [password]);
    useEffect(()=>{checkPasswordsEquality(password, setPasswordRepeat)}, [password, passwordRepeat]);
    useEffect(()=>{validatePhone(phoneNumber, setPhoneNumberValid)}, [phoneNumber]);
    useEffect(()=>{checkEmpty(companyName, setCompanyNameValid)}, [companyName]);
    useEffect(()=>{checkEmpty(ownerName, setOwnerNameValid)}, [ownerName]);
    useEffect(()=>{checkEmpty(ownerSurname, setOwnerSurnameValid)}, [ownerSurname]);
    useEffect(()=>{validateFile(innFile, setInnFileValid)}, [innFile]);

    return (
        <form name="signup">
            <EmailInput fieldName="email" onChangeFunction={(e)=>setEmail(e.target.value)} className="textInput"/>
            {emailIsValid ? "" : <InputWarning message={"Invalid email format"}/>}
            <PasswordInput fieldName="password" onChangeFunction={(e)=>setPassword(e.target.value)} className="textInput"/>
            {passwordIsValid ? "" : <InputWarning message={"Invalid password format. Password should:"} itemsList={[
                "contain 8 or more chars",
                "include one or more digit",
                "inclue one or more lower case letters",
                "include one or more upper case letters"]}/>}
            <PasswordInput fieldName="repeatPassword" onChangeFunction={(e)=>setPasswordRepeat(e.target.value)} className="textInput"/>
            {passwordsAreEqual ? "" : <InputWarning message = "Passwords don't match"/>}
            <LettersInput fieldName="phoneNumber" onChangeFunction={(e) => setPhoneNumber(e.target.value)}/>
            {phoneNumberIsValid ? "" : <InputWarning message = "Invalid phone format"/>}
            <LettersInput fieldName="companyName" onChangeFunction={(e)=>setCompanyName(e.target.value)}/>
            {companyNameIsValid ? "" : <InputWarning message = "fill this field"/>}
            <LettersInput fieldName="surname" onChangeFunction={(e)=>setOwnerName(e.target.value)}/>
            {ownerSurnameIsValid ? "" : <InputWarning message = "fill this field"/>}
            <LettersInput fieldName="name" onChangeFunction={(e)=>setOwnerSurname(e.target.value)}/>
            {ownerNameIsValid ? "" : <InputWarning message = "fill this field"/>}
            <LettersInput fieldName="patronymic" onChangeFunction={(e)=>setOwnerPatronymic(e.target.value)}/>
            <FileInput fieldName="innFile" onChangeFunction={(e)=>setInnFile(e.target.files[0])}/>
            {innFileIsValid ? "" : <InputWarning message="pdf file should have .pdf extension"/> }
            <CheckboxInput fieldName="isSubscriber" onChangeFunction={(e) => setIsSubscriber(e.target.checked ? 'y' : 'n')} className="checkboxInput"/>

            <button onClick={sendSignupData}>submit</button>
        </form>
    );
}