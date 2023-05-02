export default function PasswordInput({className, fieldName, onChangeFunction}){
    return(
        <div className={className + " form-floating"}>
            <input type="password" className="form-control floatingPassword"
                   placeholder="Password" onChange={onChangeFunction}></input>
            <label htmlFor="floatingPassword">{fieldName}</label>
        </div>
    )
}