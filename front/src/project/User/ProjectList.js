import Cookies from "js-cookie";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../../functions/HttpRequestOptions";
import React, {useEffect, useState} from "react";
import ProjectCard from "./ProjectCard";
import {Navigate, redirect} from "react-router-dom";
import {authorize} from "../../functions/RefreshFunction";

export default function ProjectList({selectedFolder, update}){

    const [projects, setProjects] = useState([]);
    const [openProjectId, setOpenProjectId] = useState(-1);
    const [forceUpdate, setForceUpdate] = useState(update);
    const [role, setRole] = useState(-1);

    useEffect(()=>{
        fetchProjects();
    }, [update])

    useEffect(()=>{
        fetchProjects();
    },[selectedFolder])

    useEffect(()=>{
        fetchProjects();
    },[]);

    const fetchProjects = () => {
        let token = Cookies.get("accessToken");

        authorize(setRole, ()=>{
            fetch("/projects/folder/" + selectedFolder, GetHTTPRequestOptions({accessToken: token}))
                .then(response => response.json())
                .then(response => {
                    setProjects(response);
                });
        });
    }

    function renderProjectCards() {
        let test = projects.map((item)=>
            <ProjectCard id={item.id} name={item.name} date={item.date}
                         description={item.shortDescription} imageurl={"images/"+item.imageFileName} onClickFunction={setOpenProjectId}/>)

        return test;
    }

    return (
        <>
            {openProjectId > 0 ? <Navigate to={"../projects/" +openProjectId} state={{id:openProjectId}}/> : ""}
            {renderProjectCards()}</>
    )
}