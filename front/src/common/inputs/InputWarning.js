import React from "react";

export default function InputWarning({message, itemsList}){

    const getWarning = () => {
        if (!itemsList) return ""
        itemsList = itemsList.map((item, index)=><li key={"key"+index}>{item}</li>);
        return <ul>{itemsList}</ul>;
    }

    return(
        <>{message}{getWarning()}</>
    )
}