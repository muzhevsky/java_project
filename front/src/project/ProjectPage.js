import {useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {authorize} from "../functions/RefreshFunction";
import GuestHome from "../main/GuestHome";
import UserProject from "./User/UserProject";
import CompanyProject from "./CompanyProject";
import ModalWithInput from "../common/modals/ModalWithInput";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../functions/HttpRequestOptions";
import Cookies from "js-cookie";

export default function ProjectPage(){
    const location = useLocation();
    const [role, setRole] = useState(-1);
    const [deleteWindowIsOpen, setDeleteWindowOpen] = useState(false);
    const [projectData, setProjectData] = useState(-1);
    const navigate = useNavigate();


    const fetchProjectData = () => {
        authorize(setRole, ()=>{fetch("/projects/" + location.state.id,
            GetHTTPRequestOptions({accessToken: Cookies.get("accessToken")}))
            .then(response => response.json())
            .then(response => {
                setProjectData(response);
            });
        });
    }

    useEffect(()=>{
        fetchProjectData();
    },[])


    const deleteProject = () => {
        console.log ("here");
        authorize(setRole, ()=> {
            fetch("/projects/delete", PostHttpRequestOptions(JSON.stringify({
                accessToken: Cookies.get("accessToken"),
                projectId: projectData.id
            })))
                .then(()=>{navigate("/");});
        });
    }

    const render = () => {
        if (role === -1 || projectData === -1) return null;
        console.log(projectData);
        switch(role){
            case "user": return (
                <>
                    {deleteWindowIsOpen ? <ModalWithInput text="type project name if u're sure"
                                                          onRefuseButtonClick={()=>{setDeleteWindowOpen(false)}}
                                                          onAcceptButtonClick={(input)=>{if (input === projectData.name) deleteProject()}} inputClassName="project name"/> : ""}
                    {projectData === -1 ? "" : <UserProject projectData={projectData} setDeleteWindowOpen={setDeleteWindowOpen}/>}
                </>
            )
            case "company": return <CompanyProject/>
            default: return <GuestHome/>
        }
    }

    return render();
}