export const requestToResetPassword = (userCredentialObj) => {
    let promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userCredentialObj)
        };
        fetch("/api/request-reset-password", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((responseObj) => {
                if (responseObj.successful === true && responseObj.exception === false) {
                    resolve(responseObj.successful);
                }
                else if (responseObj.successful === false && responseObj.exception === true) {
                    reject(responseObj.result);
                }
            })
            .catch(error => {
                console.error("Error while fetching countries: " + error);
            })
    });
    return promise;
}

export const resetPassword = (token,password) => {
  //  alert(typeof token);
    const formData = new FormData();
    formData.append('token',token);
    formData.append('password',password);
    let promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            body: formData
        };
        fetch("/api/reset-password", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((responseObj) => {
                if (responseObj.successful === true && responseObj.exception === false) {
                    resolve(responseObj.successful);
                }
                else if (responseObj.successful === false && responseObj.exception === true) {
                    reject(responseObj.result);
                }
            })
            .catch(error => {
                console.error("Error while fetching countries: " + error);
            })
    });
    return promise;
}