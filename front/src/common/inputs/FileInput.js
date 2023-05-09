export default function FileInput({className, fieldName, onChangeFunction, mult}){
    return(
        <div className={className + " mb-3"}>
            <label htmlFor="formFile" className="form-label">{fieldName}</label>
            <input className="form-control" type="file" name="formFile" onChange={onChangeFunction} multiple={mult}></input>
        </div>
    )
}