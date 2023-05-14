import EmailInput from "../common/inputs/EmailInput";
import PasswordInput from "../common/inputs/PasswordInput";
import React, {useEffect, useState} from "react";
import CheckboxInput from "../common/inputs/CheckboxInput";
import InputWarning from "../common/inputs/InputWarning";
import Cookies from 'js-cookie'
import {checkPasswordsEquality, validateEmail, validatePassword, validateUsername} from "../functions/Validation";
import {PostHttpRequestOptions} from "../functions/HttpRequestOptions";
import { Navigate } from "react-router-dom";
import TextInput from "../common/inputs/TextInput";


export default function UserSignUp() {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [passwordRepeat, setPasswordRepeat] = useState("");
    const [isSubscriber, setIsSubscriber] = useState('n');
    const [authorized, setAuthorized] = useState(false);

    const [emailIsValid, setEmailValid] = useState(false);
    const [usernameIsValid, setUsernameValid] = useState(false);
    const [passwordIsValid, setPasswordValid] = useState(false);
    const [passwordsAreEqual, setPasswordsEqual] = useState(false);


    const sendSignupData = (e) => {
        e.preventDefault();
        if (!emailIsValid || !passwordIsValid || !passwordsAreEqual || !usernameIsValid) return;

        var body = JSON.stringify({
            username: username,
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
    useEffect(()=>{validateUsername(username, setUsernameValid)}, [username]);
    useEffect(()=>{validatePassword(password, setPasswordValid)}, [password]);
    useEffect(()=>{checkPasswordsEquality(password, passwordRepeat, setPasswordsEqual)}, [password, passwordRepeat]);

    return (
        <form name="signup">
            {authorized ? <Navigate to="/home"/> : ""}
            <TextInput fieldName="Никнейм" onChangeFunction={(e)=>setUsername(e.target.value)}></TextInput>
            {usernameIsValid ? "" : <InputWarning message={"Никнейм не соответствует формату"}/>}
            <EmailInput fieldName="e-mail" onChangeFunction={(e) => setEmail(e.target.value)} className="textInput"/>
            {emailIsValid ? "" : <InputWarning message={"Invalid email format"}/>}
            <PasswordInput fieldName="пароль" onChangeFunction={(e) => setPassword(e.target.value)} className="textInput"/>
            {passwordIsValid ? "" : <InputWarning message={"Некорректный формат пароля. Он должен включать:"} itemsList={[
                "не менее 8 символов",
                "не менее одной цифры",
                "не менее одной заглавной буквы",
                "не менее одной строчной буквы"]}/>}
            <PasswordInput fieldName="повторите пароль" onChangeFunction={(e) => setPasswordRepeat(e.target.value)} className="textInput"/>
            {passwordsAreEqual ? "" : <InputWarning message = "Пароли не совпадают"/>}
            <CheckboxInput fieldName="подписаться на рассылку" onChangeFunction={(e) => setIsSubscriber(e.target.checked ? 'y' : 'n')} className="checkboxInput"/>
            <button onClick={sendSignupData}>submit</button>
        </form>
    );
}




