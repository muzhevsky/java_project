import Button from "../inputs/Button";
import TextInput from "../inputs/TextInput";
import {useState} from "react";

export default function ModalWithInput({onAcceptButtonClick, onRefuseButtonClick, inputClassName, text}) {

    const [input, setInput] = useState("");

    function render(){
        return (<div className="modal fade show" id="exampleModalCenter" tabIndex="-1"
                    aria-labelledby="exampleModalCenterTitle"
                    style={{display: "block"}} aria-modal="true" role="dialog">
            <div className="modal-dialog modal-dialog-centered">
                <div className="modal-content">
                    <div className="modal-header">
                        <h1 className="modal-title fs-5" id="exampleModalCenterTitle">Modal title</h1>
                    </div>
                    <TextInput className={inputClassName} fieldName={text}
                               onChangeFunction={(e) => {
                                   setInput(e.target.value)
                               }}></TextInput>
                    <div className="modal-footer">
                        <Button text="refuse" onClickFunction={() => {
                            onRefuseButtonClick()
                        }}/>
                        <Button text="accept" onClickFunction={() => {
                            onAcceptButtonClick(input)
                        }}/>
                    </div>
                </div>
            </div>
        </div>)
    }

    return render()
};