export const getDashboardData = () => {
      let promise = new Promise((resolve, reject) => {
          fetch("/api/dashboard-data").then((response) => {
              if (response.ok) return response.json();
          })
              .then((responseObj) => {
                  if (responseObj.successful === true && responseObj.exception === false) {
                      resolve(responseObj.result);
                  }
                  else if (responseObj.successful === false && responseObj.exception === true) {
                      reject(responseObj.result);
                  }
              })
              .catch(error => {
                  console.error("Error while fetching dashboard data: " + error);
              })
      });
      return promise;
  }