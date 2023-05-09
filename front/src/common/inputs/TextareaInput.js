export default function TextareaInput({className, fieldName, pattern, onChangeFunction}){
    return (
        <div className="form-floating">
            <textarea className="form-control" placeholder="Leave a comment here" id="floatingTextarea2"
                      style={{height: "300px"}} onChange={onChangeFunction}></textarea>
            <label htmlFor="floatingTextarea2">{fieldName}</label>
        </div>
    )
}