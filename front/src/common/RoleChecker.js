import {Navigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {authorize} from "../functions/RefreshFunction";

export async function RoleChecker({roleToCompare}){
    const [role, setRole] = useState(-1);
    authorize(setRole);


    if (role !== roleToCompare) return false;
    return true;
}