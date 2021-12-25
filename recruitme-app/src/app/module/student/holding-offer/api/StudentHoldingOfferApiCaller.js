export const addStudentHoldingOffer = (studentHoldingObj) => {
    let promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(studentHoldingObj)
        };
        fetch("/api/student-holding-offers/add", requestOption).then((response) => {
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
                console.error("Error while adding student holding offer: " + error);
            })

    });
    return promise;
}

export const getStudentHoldingOfferByStudentId=(studentId)=>{
    let promise = new Promise((resolve, reject) => {
        fetch("/api/student-holding-offers/student?studentId="+studentId).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj);
            })
            .catch(error => {
                console.error("Error while adding student holding offer: " + error);
            })

    });
    return promise;
}