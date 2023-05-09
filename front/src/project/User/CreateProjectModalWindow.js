import TextInput from "../../common/inputs/TextInput";
import Button from "../../common/inputs/Button";
import {useEffect, useState} from "react";
import FileInput from "../../common/inputs/FileInput";
import FolderList from "../../common/folders/FolderList";
import {checkEmpty, checkPasswordsEquality, validateEmail, validatePassword} from "../../functions/Validation";
import TextareaInput from "../../common/inputs/TextareaInput";

export default function CreateProjectModalWindow({onAcceptButtonClick, onRefuseButtonClick}){

    const [name, setName] = useState("");
    const [shortDescription, setShortDescription] = useState("");
    const [description, setDescription] = useState("");
    const [files, setFiles] = useState("");
    const [images, setImages] = useState("");
    const [activeFolder, setActive] = useState(-1);
    const [update, setUpdate] = useState(0);

    const [nameIsValid, setNameValid] = useState(false);
    const [descriptionIsValid, setDescriptionIsValid] = useState(false);
    const [shortDescriptionIsValid, setShortDescriptionIsValid] = useState(false);

    useEffect(()=>{checkEmpty(name, setNameValid)}, [name]);
    useEffect(()=>{checkEmpty(description, setDescriptionIsValid)}, [description]);
    useEffect(()=>{checkEmpty(shortDescription, setShortDescriptionIsValid)}, [shortDescription]);

    return (
        <div className="modal fade show" id="exampleModalCenter" tabIndex="-1" aria-labelledby="exampleModalCenterTitle"
             style={{display: "block"}} aria-modal="true" role="dialog">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalCenterTitle">Modal title</h1>
                    </div>
                    <TextInput fieldName="name" onChangeFunction={(e)=>{setName(e.target.value)}}></TextInput>
                    {nameIsValid ? "" : <span>required field</span>}
                    <TextInput fieldName="shortDescription" onChangeFunction={(e)=>{setShortDescription(e.target.value)}}></TextInput>
                    {shortDescriptionIsValid ? "" : <span>required field</span>}
                    <TextareaInput fieldName="description" onChangeFunction={(e)=>{setDescription(e.target.value)}}></TextareaInput>
                    {descriptionIsValid ? "" : <span>required field</span>}
                    <FileInput fieldName="files" onChangeFunction={(e)=>{setFiles(e.target.files)}} mult={true}></FileInput>
                    <FileInput fieldName="images" onChangeFunction={(e)=>{setImages(e.target.files[0]);}} mult={false}></FileInput>
                    {descriptionIsValid ? "" : <span>required field</span>}
                    <FolderList activeFolder={activeFolder} setActiveFolder={setActive} update={setUpdate} ignoreSystem={true}></FolderList>
                    <div className="modal-footer">
                        <Button text="close" onClickFunction={()=>{onRefuseButtonClick()}}/>
                        <Button text="create" onClickFunction={()=>{onAcceptButtonClick({
                            name: name,
                            shortDescription: shortDescription,
                            description: description,
                            files: files,
                            image: images,
                            folderId: activeFolder
                        });}
                            }/>
                    </div>
                </div>
            </div>
        </div>
    )
}