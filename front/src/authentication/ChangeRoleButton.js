export default function ChangeRoleButton({text, onClickFunction}){
    return (
        <button className="btn btn-primary" onClick={onClickFunction}>
            {text}
        </button>
    )
}