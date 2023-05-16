import './App.css';
import Header from "./common/headers/Header";
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import SignUp from "./authentication/SignUp";
import SignIn from "./authentication/SignIn";
import HomePage from "./main/HomePage";
import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./style.css"
import ProjectPage from "./project/ProjectPage";
export default function App() {

    return (
        <div>
            <BrowserRouter>
                <Header/>
                <Routes>
                    <Route path="/" element={<Navigate to="/home" />}></Route>
                    <Route path="/home" element={<HomePage/>}></Route>
                    <Route path="/signup" element={<SignUp/>}> </Route>
                    <Route path="/signin" element={<SignIn/>}> </Route>
                    <Route path="/projects/:id" element={<ProjectPage/>}/>
                </Routes>
            </BrowserRouter>

        </div>
    );
}
