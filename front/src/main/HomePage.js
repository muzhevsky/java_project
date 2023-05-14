import {useEffect, useState} from "react";
import Cookies from 'js-cookie'
import GuestHome from "./GuestHome";
import CompanyHome from "./CompanyHome";
import UserHome from "../project/User/UserHome";
import {authorize} from "../functions/RefreshFunction";

export default function HomePage(){
    const [role, setRole] = useState(-1);

    useEffect( () => {
        authorize(setRole);
    }, []);

    const render = () => {
        if (role === -1) return null;
        switch(role){
            case "user": return <UserHome/>
            case "company": return <CompanyHome/>
            default: return <GuestHome/>
        }
    }

    return render();
}