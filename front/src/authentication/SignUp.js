import UserSignUp from "./UserSignUp";
import Button from "../common/inputs/Button";
import CompanySignUp from "./CompanySignUp";
import React, {useLayoutEffect, useState} from "react";

export default function SignUp() {
    const [role, setRole] = useState(0);


    return (
        <div className="container col-md-4 col-sm-8">
            {
                role === 0 ?
                    <div><UserSignUp/>
                        <Button text={"Зарегистрироваться как исполнитель"}
                                onClickFunction={() => {setRole(1)}}/></div> :
                    <div><CompanySignUp/>>
                        <Button text={"Зарегистрироваться как заказчик"}
                                onClickFunction={() => setRole(0)}/></div>
            }
        </div>
    )
}