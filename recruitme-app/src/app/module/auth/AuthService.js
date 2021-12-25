import { BASE_URL } from "../../app-constants/AppConstants";

export const isUserStudent=()=>{
    let role=sessionStorage.getItem("role");
    let role2=localStorage.getItem("role");
    if(role=="STUDENT" || role2=="STUDENT") return true;
    return false;
}

export const isUserPlacementCoordinator=()=>{
    let role=sessionStorage.getItem("role");
    let role2=localStorage.getItem("role");
    if(role=="PLACEMENT_COORDINATOR" || role2=="PLACEMENT_COORDINATOR") return true;
    return false;
}

export const isUserPLacementPreperationFaculty=()=>{
    let role=sessionStorage.getItem("role");
    let role2=localStorage.getItem("role");
    if(role=="PLACEMENT_PREPARATION_FACULTY" || role2=="PLACEMENT_PREPARATION_FACULTY") return true;
    return false;
}

export const isUserAdmin=()=>{
    let role=sessionStorage.getItem("role");
    let role2=localStorage.getItem("role");
    if(role==="ADMIN" || role2==="ADMIN") return true;
    return false;
}


export const redirectToLoginPage=()=>{
    window.location.href=BASE_URL+"/login";
}

export const getAuthenticateUserId=()=>{
    let userIdLocalStorage=localStorage.getItem("userId");
    let userIdSessionStorage=sessionStorage.getItem("userId");
    return userIdLocalStorage==null?(userIdLocalStorage==null?null:userIdSessionStorage):userIdLocalStorage;
}

export const getAuthenticateUserName=()=>{
    return localStorage.getItem("name")==null?(sessionStorage.getItem("name")==null?null:sessionStorage.getItem("name")):localStorage.getItem("name");
}

export const logout=()=>{
    sessionStorage.setItem("userId",null);
    sessionStorage.setItem("role",null);
    sessionStorage.setItem("enrollmentNumber",null);
    sessionStorage.setItem("email",null);
    sessionStorage.setItem("name",null);
    localStorage.removeItem("userId",null);
    localStorage.removeItem("role",null);
    localStorage.removeItem("enrollmentNumber",null);
    localStorage.removeItem("email",null);
    localStorage.removeItem("name",null);
    redirectToLoginPage();
}

export const isActive=()=>{
    if(getAuthenticateUserId()==null) return false;
    return true;
}