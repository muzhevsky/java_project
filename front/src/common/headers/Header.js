import HomeButton from "../HomeButton";
import {useEffect, useState} from "react";
import {authorize} from "../../functions/RefreshFunction";
import UserHeader from "./UserHeader";
import CompanyHeader from "./CompanyHeader";
import GuestHeader from "./GuestHeader";
import ProfileButton from "./ProfileButton";

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
        return (<div className="header" style={{display:"flex", justifyContent:"center"}}>
            <div className="col-8 container" style={{display:"flex"}}>
                <div className="col-8">{inner}</div>
                <div className="col-4 profile-button-container"><ProfileButton/></div>
            </div>
        </div>)
    }

    return (
        render()
    )
}