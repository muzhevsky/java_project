import ProjectData from "../ProjectData";
import Button from "../../common/inputs/Button";
import {useEffect, useState} from "react";
import Cookies from "js-cookie";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../../functions/HttpRequestOptions";

export default function UserProject({projectData, setDeleteWindowOpen}) {
    const [isOwner, setOwner] = useState(false);

    useEffect(() => {
        let token = Cookies.get("accessToken");
        fetch("/projects/isowner/" + projectData.id, GetHTTPRequestOptions({accessToken: token}))
            .then((response) => response.text())
            .then((response) => {
                setOwner(response)
            });
    }, []);

    return (
        <>
            <ProjectData projectData={projectData}/>

            {isOwner && projectData.revoked !== 'y' ?
                <div className="container">
                        <div className="btn-group" role="group" aria-label="Basic example">
                            <a href={"/files/" + projectData.fileName} download>
                                <Button text="Скачать прикрепленные файлы"></Button>
                            </a>
                            <Button text="delete" onClickFunction={setDeleteWindowOpen}></Button>
                        </div>
                </div>
                : ""}
        </>
    )
}