export const blacklistStudent = (studentObj) => {
    let promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(studentObj)
        };
        fetch("/api/student/blacklist", requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                if (responseObj.successful === true && responseObj.exception === false) {
                    resolve("success");
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

export const unblacklistStudent = (id) => {
    let promise = new Promise((resolve, reject) => {

        const requestOption = {
            method: 'POST',
        };
        fetch("/api/student/un-blacklist/" + id, requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
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


export const deleteStudent = (id) => {
    let promise = new Promise((resolve, reject) => {

        const requestOption = {
            method: 'POST',
        };
        fetch("/api/student/delete/" + id, requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
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
