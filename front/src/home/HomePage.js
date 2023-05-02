import {useEffect, useState} from "react";
import Cookies from 'js-cookie'
import UnauthorizedHome from "./UnauthorizedHome";
import CompanyHome from "./CompanyHome";
import UserHome from "./UserHome";
import {refreshToken} from "../functions/RefreshFunction";

export default function HomePage(){
    const [role, setRole] = useState("");


    useEffect(() => {
        var myHeaders = new Headers();
        myHeaders.append("Content-Type", "text/plain");

        var raw = Cookies.get("accessToken");

        var requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("http://localhost:8080/home", requestOptions)
            .then(response => response.text())
            .then(response => {
                if (response.status > 200) {
                    console.log(response.status);
                }
                if (response.status === 401){
                    refreshToken();
                }

                setRole(response);
            })
            .catch(error => console.log('error', error))}, []);



    return (
        <div>
            {role == "user"? <UserHome/> : role == "company"? <CompanyHome/> : <UnauthorizedHome/>}
        </div>
    )
}