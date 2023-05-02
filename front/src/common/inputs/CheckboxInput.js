export default function CheckboxInput({className, fieldName, onChangeFunction}){
    return(
        <div className={className}>
            <label htmlFor={fieldName}>{fieldName}</label>
            <input type="checkbox" name={fieldName} required onChange={onChangeFunction}></input>
        </div>
    )
}