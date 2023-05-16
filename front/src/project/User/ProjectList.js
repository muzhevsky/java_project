import Cookies from "js-cookie";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../../functions/HttpRequestOptions";
import React, {useEffect, useState} from "react";
import ProjectCard from "./ProjectCard";
import {Navigate, redirect} from "react-router-dom";
import {authorize} from "../../functions/RefreshFunction";

export default function ProjectList({selectedFolder, forceUpdate}){

    const [projects, setProjects] = useState([]);
    const [openProjectId, setOpenProjectId] = useState(-1);
    const [role, setRole] = useState(-1);

    useEffect(()=>{
        fetchProjects();
    },[selectedFolder])
    useEffect(()=>{
        fetchProjects();
    },[forceUpdate])

    const fetchProjects = () => {
        authorize(setRole, ()=>{
            fetch("/projects/folder/" + selectedFolder, GetHTTPRequestOptions({accessToken: Cookies.get("accessToken")}))
                .then(response => response.json())
                .then(response => {
                    setProjects(response);
                });
        });
    }

    function renderProjectCards() {

        let test = projects.map((item)=>
        {
            let creationDate = new Date(Date.parse(item.creationDate));
            let options = { year: 'numeric', month: 'long', day: 'numeric' };

            creationDate = creationDate.toLocaleDateString('ru-RU', options);

            return (<ProjectCard id={item.id} name={item.name}
                         description={item.shortDescription} imageurl={"images/"+item.imageFileName}
                         onClickFunction={setOpenProjectId} creationDate={creationDate}/>
            )
        });
        return test;
    }

    return (
        <>
            {openProjectId > 0 ? <Navigate to={"../projects/" +openProjectId} state={{id:openProjectId}}/> : ""}
            {renderProjectCards()}</>
    )
}