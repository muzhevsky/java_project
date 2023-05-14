
export function PostHttpRequestOptions(body){
    let headers = new Headers();
    headers.append("Content-Type", "application/json")

    return({
        method: "POST",
        headers: headers,
        redirect: 'follow',
        body: body
    })
}

export function GetHTTPRequestOptions(headers){
    let formattedHeaders = new Headers();

    Object.entries(headers).forEach((entry)=>{
        formattedHeaders.append(entry[0], entry[1]);
    });


    return({
        method: "GET",
        headers: formattedHeaders,
        redirect: 'follow',
    })
}
