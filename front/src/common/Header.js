import HomeButton from "./HomeButton";
import {useEffect, useState} from "react";
import {authorize} from "../functions/RefreshFunction";
import UserHeader from "./headers/UserHeader";
import CompanyHeader from "./headers/CompanyHeader";
import GuestHeader from "./headers/GuestHeader";

export default function Header(){

    const [role, setRole] = useState(-1);

    useEffect(()=>{
        authorize(setRole);
    }, []);


    function render(){
        if (role === -1) return "";
        let inner;
        switch (role){
            case "user": inner = <UserHeader/>
            case "company": inner = <CompanyHeader/>
            case "default": inner = <GuestHeader/>
        }
        return (<div className="header">{inner}</div>)
    }

    return (
        render()
    )
}