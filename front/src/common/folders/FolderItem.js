import {useEffect, useState} from "react";

export default function FolderItem({id, name, setActive}){
    return (
        <>
            <li key={name}><a href="#" id={name} className={"dropdown-item"} type="radio" name="vbtn-radio"
                   onClick={()=>{setActive(id)}}>{name}</a></li>
        </>
    )
}