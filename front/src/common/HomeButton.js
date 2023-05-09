import {Link, Navigate} from "react-router-dom";
import React from "react";

export default function HomeButton(){
    return(
        <Link to="/home"> <a className="homeButton " href="https://localhost:3000/home">HOME</a></Link>
    )
}