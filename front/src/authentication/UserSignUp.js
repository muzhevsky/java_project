import EmailInput from "../common/inputs/EmailInput";
import PasswordInput from "../common/inputs/PasswordInput";
import React, {useEffect, useState} from "react";
import CheckboxInput from "../common/inputs/CheckboxInput";
import InputWarning from "../common/inputs/InputWarning";
import Cookies from 'js-cookie'
import {checkPasswordsEquality, validateEmail, validatePassword} from "../functions/Validation";
import {PostHttpRequestOptions} from "../functions/HttpRequestOptions";
import { Navigate } from "react-router-dom";


export default function UserSignUp() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [isSubscriber, setIsSubscriber] = useState('n');
    const [authorized, setAuthorized] = useState(false);

    const [emailIsValid, setEmailValid] = useState(false);
    const [passwordIsValid, setPasswordValid] = useState(false);
    const [passwordsAreEqual, setPasswordsEqual] = useState(false);


    const sendSignupData = (e) => {
        e.preventDefault();
        if (!emailIsValid || !passwordIsValid || !passwordsAreEqual) return;

        var body = JSON.stringify({
            accountCreationForm: {
                email:email,
                password:password,
                isSubscriber: isSubscriber
            }
        });

        fetch("/signup/user", PostHttpRequestOptions(body))
            .then(response => response.json())
            .then(response => {
                Cookies.set("accessToken", response.accessToken);
                Cookies.set("refreshToken", response.refreshToken);
                setAuthorized(true);
            })
            .catch(error => console.log('error', error));
    }

    useEffect(()=>{validateEmail(email, setEmailValid)}, [email]);
    useEffect(()=>{validatePassword(password, setPasswordValid)}, [password]);
    useEffect(()=>{checkPasswordsEquality(password, passwordRepeat, setPasswordsEqual)}, [password, passwordRepeat]);

    return (
        <form name="signup">
            {authorized ? <Navigate to="/home"/> : ""}
            <EmailInput fieldName="email" onChangeFunction={(e) => setEmail(e.target.value)} className="textInput"/>
            {emailIsValid ? "" : <InputWarning message={"Invalid email format"}/>}
            <PasswordInput fieldName="password" onChangeFunction={(e) => setPassword(e.target.value)} className="textInput"/>
            {passwordIsValid ? "" : <InputWarning message={"Invalid password format. Password should:"} itemsList={[
                "contain 8 or more chars",
                "include one or more digit",
                "inclue one or more lower case letters",
                "include one or more upper case letters"]}/>}
            <PasswordInput fieldName="repeatPassword" onChangeFunction={(e) => setPasswordRepeat(e.target.value)} className="textInput"/>
            {passwordsAreEqual ? "" : <InputWarning message = "Passwords don't match"/>}
            <CheckboxInput fieldName="isSubscriber" onChangeFunction={(e) => setIsSubscriber(e.target.checked ? 'y' : 'n')} className="checkboxInput"/>
            <button onClick={sendSignupData}>submit</button>
        </form>
    );
}




