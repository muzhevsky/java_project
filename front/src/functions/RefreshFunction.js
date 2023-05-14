import Cookies from "js-cookie";
import {Navigate} from "react-router-dom";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "./HttpRequestOptions";

let funcList = [];
let setRoleList = [];
let flag = false;

export async function authorize(setRole, onRoleSetFunc) {
    setRoleList.push(setRole);
    funcList.push(onRoleSetFunc);
    if (flag) return;
    flag = true;
    fetch("/authorize", GetHTTPRequestOptions(
        {accessToken: Cookies.get("accessToken")}))
        .then(response => {
                if (response.status === 200) {
                    response.text().then((res) => {
                        console.log(funcList);
                        console.log(setRoleList);
                        funcList.forEach((func)=>{if (func !== undefined) func()});
                        setRoleList.forEach((setRoleFunc)=>{if (setRoleFunc !== undefined) setRoleFunc(res)});
                        funcList = [];
                        setRoleList = [];
                        flag = false;
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
                                                console.log(funcList.length);
                                                console.log(setRoleList.length);
                                                funcList.forEach((func)=>{if (func !== undefined) func()});
                                                setRoleList.forEach((setRoleFunc)=>{if (setRoleFunc !== undefined) setRoleFunc(res)});
                                                funcList = [];
                                                setRoleList = [];
                                                flag = false;
                                            })
                                     }
                                        else{
                                            setRoleList.forEach((setRoleFunc)=>{if (setRoleFunc !== undefined) setRoleFunc("guest")});
                                            setRoleList = [];
                                        }
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