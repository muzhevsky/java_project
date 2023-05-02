export default function TextInput({className, fieldName, pattern, onChangeFunction}){
    return(
        <div className={className + " form-floating"}>
            <input type="text" pattern={pattern} className="form-control floatingPassword"
               placeholder="Password" onChange={onChangeFunction}></input>
            <label htmlFor="floatingPassword">{fieldName}</label>
        </div>
    )
}