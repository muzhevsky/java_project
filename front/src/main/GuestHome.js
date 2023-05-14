import {useEffect, useState} from "react";
import SignIn from "../authentication/SignIn";
import {Navigate} from "react-router-dom";

export default function GuestHome(){
    return(
        <Navigate to="/signin"></Navigate>
    )
}