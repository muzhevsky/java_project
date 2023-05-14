import Cookies from "js-cookie";
import {Navigate} from "react-router-dom";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "./HttpRequestOptions";

export async function authorize(setRole, onRoleSetFunc) {
    fetch("/authorize", GetHTTPRequestOptions(
        {accessToken: Cookies.get("accessToken")}))
        .then(response => {
                if (response.status === 200) {
                    response.text().then((res) => {
                        setRole(res);
                        if (onRoleSetFunc !== undefined) onRoleSetFunc();
                    })
                }
                else if (response.status === 401){
                    refreshToken(()=>
                            {
                                fetch("/authorize", GetHTTPRequestOptions(
                                    {accessToken: Cookies.get("accessToken")}))
                                    .then(response => {
                                        if (response.status === 200) {
                                            response.text().then((res) => {
                                                setRole(res);
                                                if (onRoleSetFunc !== undefined) onRoleSetFunc();
                                            })
                                     } else setRole("guest");
                            })
                        }
                    )
                }
            }
        )
}

function refreshToken(continueFunc) {
    return fetch('/refresh', {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'plain/text',
        },
        body: Cookies.get("refreshToken")
    })
        .then((response)=>{
            if (response.status === 200) {
                response.json().then(
                    (response)=>{
                        if (response.accessToken !== undefined){
                            Cookies.set("accessToken", response.accessToken);
                            Cookies.set("refreshToken", response.refreshToken);
                            continueFunc();
                        }
                    }
                )

            }
        })
}