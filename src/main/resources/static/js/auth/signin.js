let username;
let password;

function SignIn() {
    let body = JSON.stringify({
        username: username,
        password: password
    });
    try{
        fetch(signInEndpoint, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json'
            },
            body: body
        })
            .then((response)=>{
                if (response.status === 200)
                    window.location = myProjectsPage;
            })
    }
    catch(error){
        console.log(error);
    }
}