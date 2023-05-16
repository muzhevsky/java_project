import UserProjectsSection from "../../common/folders/UserProjectsSection";
import {createContext, useEffect, useState} from "react";
import {authorize} from "../../functions/RefreshFunction";

export default function UserHome(){

    return (
        <div className="userHome container col-8">
            <h1>Мои проекты</h1>
            <UserProjectsSection />
        </div>
    )
}