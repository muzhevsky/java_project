export default function Button({text, onClickFunction}){
    return (
        <button className="btn btn-primary" onClick={onClickFunction}>
            {text}
        </button>
    )
}