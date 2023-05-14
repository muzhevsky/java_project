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

    const [nameIsValid, setNameValid] = useState(false);
    const [descriptionIsValid, setDescriptionIsValid] = useState(false);
    const [shortDescriptionIsValid, setShortDescriptionIsValid] = useState(false);

    useEffect(()=>{checkEmpty(name, setNameValid)}, [name]);
    useEffect(()=>{checkEmpty(description, setDescriptionIsValid)}, [description]);
    useEffect(()=>{checkEmpty(shortDescription, setShortDescriptionIsValid)}, [shortDescription]);

    const createProject = (e) => {
        e.preventDefault();
        console.log('try');
        if (!nameIsValid || !shortDescriptionIsValid || !descriptionIsValid) return;

        console.log('create');

        onAcceptButtonClick({
            name: name,
            shortDescription: shortDescription,
            description: description,
            files: files,
            image: images,
            folderId: activeFolder
        });
    }

    return (
        <div className="modal fade show" id="exampleModalCenter" tabIndex="-1" aria-labelledby="exampleModalCenterTitle"
             style={{display: "block"}} aria-modal="true" role="dialog">
            <div className="modal-dialog modal-xl modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalCenterTitle">Создание проекта</h1>
                    </div>
                    <form>
                    <TextInput fieldName="Название проекта" onChangeFunction={(e)=>{setName(e.target.value)}}></TextInput>
                    {nameIsValid ? "" : <span>Обязательно для заполнения</span>}
                    <TextInput fieldName="Краткое описание" onChangeFunction={(e)=>{setShortDescription(e.target.value)}}></TextInput>
                    {shortDescriptionIsValid ? "" : <span>Обязательно для заполнения</span>}
                    <TextareaInput fieldName="Полное описание" onChangeFunction={(e)=>{setDescription(e.target.value)}}></TextareaInput>
                    {descriptionIsValid ? "" : <span>Обязательно для заполнения</span>}
                    <FileInput fieldName="Файлы (.pdf, .dwg, .cdw)" constraints=".pdf,.dwg,.cdw"  onChangeFunction={(e)=>{setFiles(e.target.files)}} mult={true}></FileInput>
                    <FileInput fieldName="Картинка" constraints=".png,.jpg,.jfif,.svg" onChangeFunction={(e)=>{setImages(e.target.files[0]);}} mult={false}></FileInput>
                        <br></br>
                    <FolderList activeFolder={activeFolder} setActiveFolder={setActive} ignoreSystem={true}></FolderList>
                    <div className="modal-footer">
                        <Button text="Закрыть" onClickFunction={()=>{onRefuseButtonClick()}}/>
                        <Button text="Создать" onClickFunction={(e)=>{createProject(e);}}/>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    )
}