import HomeButton from "./HomeButton";
import {useEffect, useState} from "react";
import {authorize} from "../functions/RefreshFunction";
import UserHeader from "./headers/UserHeader";
import CompanyHeader from "./headers/CompanyHeader";
import GuestHeader from "./headers/GuestHeader";

export default function Header(){

    const [role, setRole] = useState(-1);

    useEffect(()=>{
        //getRoleFromToken(setRole);
    }, []);


    function render(){
        if (role === -1) return "";
        switch (role){
            case "user": return <UserHeader/>
            case "company": return <CompanyHeader/>
            case "default": return <GuestHeader/>
        }
    }

    return (
        render()
    )
}