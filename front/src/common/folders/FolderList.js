import FolderItem from "./FolderItem";
import React, {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../../functions/HttpRequestOptions";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import Button from "../inputs/Button";
import {authorize} from "../../functions/RefreshFunction";

export default function FolderList({activeFolder, setActiveFolder, ignoreSystem: ignoreSystemFolders, forceUpdate}) {

    const defaultFolders = [{
        id: -1,
        name: "Активные проекты",
        selected: true,
        deletable: false,
    },
    {
        id: -2,
        name: "Отозванные проекты",
        selected: false,
        deletable: false
    }]

    const [role, setRole] = useState(-1);
    const [activeFolderObject, setActiveObject] = useState(undefined);
    const [folderObjects, setFolderObjects] = useState(defaultFolders);
    const [update, setUpdate] = useState(forceUpdate);

    const fetchFolders = () => {
        authorize(setRole, () => {
            let token = Cookies.get("accessToken");
            fetch("/home/user/fetchfolders", GetHTTPRequestOptions({accessToken: token}))
                .then(response => response.json())
                .then(response => {
                    response.forEach((item) => {
                        item.selected = false
                    });
                    setFolderObjects(defaultFolders.concat(response));
                });
        });
    }

    useEffect(()=>{
        fetchFolders();
    }, [forceUpdate])

    useEffect(()=>{
        setActiveObject(folderObjects.filter((item)=>item.id === activeFolder)[0]);
    }, [activeFolderObject])

    function render() {
        if (activeFolderObject !== undefined && role !== -1){
            let folderHtmlObjects = [];
            for (let i = 0; i < folderObjects.length; i++) {
                folderHtmlObjects.push(folderObjects[i]);
            }
            folderHtmlObjects = folderHtmlObjects.filter(item => item !== undefined);
            for (let i = 0; i < folderHtmlObjects; i++) if (folderHtmlObjects[i].id >= 0) folderHtmlObjects[i].deletable = true;
            folderHtmlObjects = folderHtmlObjects.map((item) => <FolderItem name={item.name} id={item.id} key = {item.id}
                                                                            setActive={(item) =>{
                                                                                setActiveFolder(item);
                                                                                setActiveObject(undefined);
                                                                            }}></FolderItem>)

            if (ignoreSystemFolders) folderHtmlObjects = folderHtmlObjects.filter((item)=> item.props.id >= 0);

            return (
                <>
                    <button type="button" className="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false">
                        {activeFolderObject.name}
                    </button>
                    <ul className="dropdown-menu">
                        {folderHtmlObjects}
                    </ul>
                </>)
        }

        return "";
    }

    return render();
}