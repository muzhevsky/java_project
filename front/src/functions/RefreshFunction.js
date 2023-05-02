import Cookies from "js-cookie";

export const refreshToken = () => {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "text/plain");

    var raw = Cookies.get("accessToken");

    console.log(raw);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    // todo подумать над рефрешем
    // fetch("http://localhost:8080/refresh", requestOptions)
    //     .then(response => response.json())
    //     .then(response => {
    //         if (response.status > 200) {
    //             console.log(response.status);
    //         }
    //         if (response.status === 401){
    //             refreshToken();
    //         }
    //
    //         setRole(response.role);
    //     })
    //     .catch(error => console.log('error', error))}, [])
}