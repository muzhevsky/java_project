import {useEffect, useState} from "react";
import {useLocation} from "react-router-dom";
import {GetHTTPRequestOptions, PostHttpRequestOptions} from "../functions/HttpRequestOptions";
import Cookies from "js-cookie";

export default function ProjectData({projectData}){

    return (
        <div className="container">
            <div className="row my-5">

                <div className="col-md-6">
                    <img src={"/images/"+projectData.imageFileName} alt="Project Image" className="project-image img-fluid"></img>
                </div>

                <div className="col-md-6">
                    <h2>{projectData.name}</h2>
                    <h3>short description</h3>
                    <p>{projectData.shortDescription}</p>
                </div>

                <div className="col-md-12 my-5">
                    <h3>full description</h3>
                    <p>{projectData.description}</p>
                </div>

                <div className="col-md-12 my-5">
                    <h3>full description</h3>
                    <p>{projectData.description}</p>
                </div>
            </div>
        </div>
    )
}