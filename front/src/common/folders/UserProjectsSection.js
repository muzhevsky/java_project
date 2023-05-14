import React, {useEffect, useState} from "react";
import Cookies from 'js-cookie'
import {PostHttpRequestOptions} from "../../functions/HttpRequestOptions";
import ModalWithInput from "../modals/ModalWithInput";
import {authorize} from "../../functions/RefreshFunction";
import FolderList from "./FolderList";
import Button from "../inputs/Button";
import ProjectList from "../../project/User/ProjectList";
import CreateProjectModalWindow from "../../project/User/CreateProjectModalWindow";

export default function UserProjectsSection() {
    const [role, setRole] = useState("user");
    const [newFolderWindowActive, setNewFolderWindowActive] = useState(false);
    const [newProjectWindowActive, setNewProjectWindowActive] = useState(false);
    const [activeFolder, setActiveFolder] = useState(-1);
    const [forceUpdate, setForceUpdate] = useState(0);

    const createNewFolder = (name) => {
        let token = Cookies.get("accessToken");

        let body = {
            accessToken: token,
            name: name
        }
        
        fetch("/home/user/createfolder", PostHttpRequestOptions(JSON.stringify(body)))
            .then(response => response.json())
            .then((response)=>{setActiveFolder(response.filter((item)=> item.name===body.name)[0].id)});
    }

    function readFileInputs(project, resolve){
        if (project.image === undefined || project.image.name === undefined){
            project.image = {
                name: "default.png",
                content: [-1]
            };
            readFiles(project, resolve);
            return;
        }

        let name = project.image.name;

        return new Promise(()=>{
        const reader = new FileReader();
        reader.onload = (event)=>{
            project.image = {
                name: name,
                content: Array.from(new Uint8Array(event.target.result))
            }
            readFiles(project, resolve);
        }
        reader.readAsArrayBuffer(project.image);
        });
    }

    function readFiles(project, resolve){
        let files = [];

        readFile(0, files);

        function readFile(index, output){
            if (index >= project.files.length){
                project.files = files;
                resolve(project);
                return;
            }

            let name = project.files[index].name;

            const reader = new FileReader();
            reader.onload = (event) => {
                output[index] = {
                    name: name,
                    content: Array.from(new Uint8Array(event.target.result))
                }
                readFile(index+1, output);
            }
            reader.readAsArrayBuffer(project.files[index]);
        }
    }

    const sendNewProject = (project) => {
        authorize(setRole, ()=>{
            let body = JSON.stringify({
                accessToken: Cookies.get("accessToken"),
                name: project.name,
                description: project.description,
                shortDescription: project.shortDescription,
                image: project.image,
                files: project.files,
                folderId: project.folderId
            });


            fetch("/projects/create", PostHttpRequestOptions(body))
                .then(response => response.json())
                .then(() => {
                   setNewProjectWindowActive(false);
                   setActiveFolder(project.folderId);
                   setForceUpdate(forceUpdate+1);
                });
        });
    }

    const createNewProject = (project) => {
        readFileInputs(project, sendNewProject);
    }

    function render(){
        return (
            <>
            {role==="user" ? <>
                <FolderList forceUpdate={forceUpdate} activeFolder={activeFolder} setActiveFolder={setActiveFolder}
                            setNewFolderWindowActive={setNewFolderWindowActive} ignoreSystem={false}/>
                <Button text="Создать папку" onClickFunction={() =>{setNewFolderWindowActive(true)}}>
                    create folder
                </Button>
                <Button text="Создать проект" onClickFunction={() =>{setNewProjectWindowActive(true)}}>
                    create project
                </Button>
                <ProjectList selectedFolder={activeFolder}/>
            </> : ""}
            {newFolderWindowActive ? <ModalWithInput name="Создание папки" inputName="Название папки" inputClassName="folder-name-input"
                                                     onAcceptButtonClick={(name) => {createNewFolder(name); setNewFolderWindowActive(false);}}
                                                     onRefuseButtonClick={() => {setNewFolderWindowActive(false)}}/> : ""}
            {newProjectWindowActive ? <CreateProjectModalWindow name="Создание проекта" onAcceptButtonClick={(project) => createNewProject(project)}
                                                                onRefuseButtonClick={()=> setNewProjectWindowActive(false)}/> : ""}
            </>);
    }

    return render();
}