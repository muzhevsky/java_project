export default function EmailInput({className, fieldName, onChangeFunction}){
    return(
        <div className={className + " form-floating mb-3"}>
            <input type="email" className="form-control floatingInput" placeholder="name@example.com" onChange={onChangeFunction}></input>
            <label htmlFor="floatingInput">{fieldName}</label>
        </div>
    )
}






