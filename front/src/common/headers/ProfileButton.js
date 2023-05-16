import {authorize} from "../../functions/RefreshFunction";
import {useState} from "react";

export default function ProfileButton({imageUrl}){
    const [role, setRole] = useState("guest");


    function render(){
        return(
        <>
            <img className="avatar-image" src={imageUrl ? imageUrl : 'images/default.jpg'}></img>
            <span>ProfileName</span>
        </>
        )
    }

    return render();
}