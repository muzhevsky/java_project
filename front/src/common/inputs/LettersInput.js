import TextInput from "./TextInput";

export default function LettersInput({className, fieldName, onChangeFunction}){
    return(
        <TextInput className={className} pattern="/^[A-Za-zА-Яа-я]+$/"
                   fieldName={fieldName} onChangeFunction={onChangeFunction}/>
    )
}