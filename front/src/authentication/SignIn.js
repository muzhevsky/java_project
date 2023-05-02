import React, {useState} from "react";
import EmailInput from "../common/inputs/EmailInput";
import PasswordInput from "../common/inputs/PasswordInput";
import ForgotPasswordButton from "./ForgotPasswordButton";
import Error from "../error/Error"
import Cookies from 'js-cookie'
import {Navigate} from "react-router-dom";

export default function SignIn() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [signInError, setSignInError] = useState(false)
    const [serviceError, setServiceError] = useState(false)
    const [authorized, setAuthorized] = useState(false);

    const sendSignupData = (e) => {
        e.preventDefault();

        var body = JSON.stringify({
            email:email,
            password:password
        });

        var headers = new Headers();
        headers.append("Content-Type", "application/json");

        var requestOptions = {
            method: 'POST',
            headers: headers,
            redirect: 'follow',
            body: body
        };

        fetch("/signin", requestOptions)
            .then((response) => {
                setSignInError(false);
                setServiceError(false);
                if (response.status === 401){
                    setSignInError(true);
                }
                else if (response.status >= 500){
                    setServiceError(true);
                }
                else{
                    return response.json();
                }
            })
            .then(response => {
                Cookies.set("accessToken", response.accessToken);
                Cookies.set("refreshToken", response.refreshToken);
                setAuthorized(true);
            })
            .catch(error => console.log('error', error));
    }

    const handleErrors = () => {
        if (serviceError) return <Error message="service is unavailable now"></Error>
        else if (signInError) return <Error message="invalid email or password"></Error>
    }

    return (
        <div className="container col-md-4 col-sm-8">
            <EmailInput fieldName="email" className="textInput" onChangeFunction={ (e) => setEmail(e.target.value)}/>
            <PasswordInput fieldName="password" className="textInput" onChangeFunction={ (e) => setPassword(e.target.value)}/>
            <button onClick={sendSignupData}>submit</button>
            <ForgotPasswordButton/>
            {handleErrors()}
            {authorized ? <Navigate to="/home"/> : ""}
        </div>
    )
}