import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import { FormLabel } from '@material-ui/core';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, RadioGroupWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import { roleOption } from '../../../app-constants/Role';
import { Redirect } from 'react-router';
import { BASE_URL } from '../../../app-constants/AppConstants';
import { colors } from '../../global/css/theme-constants/ThemeConstants';
import Typography from '@material-ui/core/Typography';
import { AppBarComponent } from './AppBarComponent';
import { isActive } from '../../../module/auth/AuthService';
import { ResetPasswordDialogWrapper } from './ResetPasswordDialogWrapper';






/**
 * To edit parents
 * @param {*} parentsObj 
 * @returns promise
 */
const authenticate = (credentialObj) => {

    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(credentialObj)
        };
        fetch("/api/auth", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
                if (response.successful && (response.result).role == null && (response.result).name == null && (response.result).email != null) {
                    reject(null);
                }
                else if (response.successful) {
                    sessionStorage.setItem("userId", (response.result).id);
                    sessionStorage.setItem("role", (response.result).role);
                    sessionStorage.setItem("enrollmentNumber", (response.result).enrollmentNumber);
                    sessionStorage.setItem("email", (response.result).email);
                    sessionStorage.setItem("name", (response.result).name);
                    localStorage.setItem("userId", (response.result).id);
                    localStorage.setItem("role", (response.result).role);
                    localStorage.setItem("enrollmentNumber", (response.result).enrollmentNumber);
                    localStorage.setItem("email", (response.result).email);
                    localStorage.setItem("name", (response.result).name);
                    resolve(JSON.stringify(response));
                }
                else {

                    reject(response.result);
                }
            })
            .catch((error) => {
                console.log("Error while adding parents" + error);
            })
    });
    return promise;
}



const totalCard = 7;

/**
 * Student registration form component
 */
export const LoginComponent = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [email, setEmail] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [role, setRole] = React.useState(null);
    const [errOcurred, setErrOccured] = React.useState(false);
    const [msg, setMsg] = React.useState("");
    const [resetPasswordDialogOpen,setResetPasswordDialogOpen] = React.useState(false);


    const login = async () => {
        let credential = {
            emailOrUsername: email,
            password: password,
            role: role
        }
        if (role == null) {
            setMsg("Please select your role");
            setErrOccured(true);
        } else {
            setErrOccured(false);
            await authenticate(credential).then((id) => {
                if (role == "STUDENT") window.location.href = BASE_URL + "/student-panel/drives";
                else if (role == "PLACEMENT_COORDINATOR") window.location.href = BASE_URL + "/placement-coordinator-panel/dashboard";
                else if (role == "PLACEMENT_PREPARATION_FACULTY") window.location.href = BASE_URL + "/placement-faculty-panel/videos";
                else if (role == "ADMIN") window.location.href = BASE_URL + "/admin-panel/dashboard";
            }, (exception) => {
                setErrOccured(true);
                if (exception == null) {
                    setMsg("First Verify Your Email.We have already send you verification link on your email.");
                } else {
                    setMsg("Invalid Credentials");
                }
            }).catch((error) => {

            });
        }
    }

    return (
        <div>
            <AppBarComponent />
            <div className={cssStyles.globalStyles.mainContainer}>
                <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                    <ContainerItem lg={10}>
                        <WrapperContainer spacing={5} className={cssStyles.globalStyles.formTitleHeader}>
                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} className={cssStyles.globalStyles.formTitle}>
                                Login
                            </ContainerItem>
                        </WrapperContainer>

                        {errOcurred && <Alert severity="error">{msg}</Alert>}

                        <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <OutlinedTextFieldWrapper
                                            label="Primary Email"
                                            type="text"
                                            onChange={((ev) => {
                                                setEmail(ev.target.value);
                                            })}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <OutlinedTextFieldWrapper
                                            label="Password"
                                            type="password"
                                            onChange={((ev) => {
                                                setPassword(ev.target.value);
                                            })}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>

                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                <FormGroup>
                                    <FormLabel className={cssStyles.globalStyles.formQuestion}>Select your role</FormLabel>
                                    <FormControl>
                                        <RadioGroupWrapper
                                            options={roleOption}
                                            rowmode={true}
                                            onChange={((ev) => {
                                                if ((ev.target.value) == 'PLACEMENT_COORDINATOR') setRole("PLACEMENT_COORDINATOR")
                                                else if ((ev.target.value) == 'PLACEMENT_PREPARATION_FACULTY') setRole("PLACEMENT_PREPARATION_FACULTY")
                                                else if ((ev.target.value) == 'STUDENT') setRole("STUDENT")
                                                else if ((ev.target.value) == 'ADMIN') setRole("ADMIN")
                                            })}
                                            checkvalue={role}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={4} xl={3}>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={4} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <Button
                                            className={cssStyles.globalStyles.formButtonRight}
                                            variant="contained"
                                            onClick={() => {
                                                login();
                                            }}
                                        >
                                            Login
                                        </Button>
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <WrapperContainer id="card-0" spacing={5} boxShadow={false}>
                                <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup>
                                        <FormControl>
                                            <FormLabel
                                                style={{ color: colors.dark, cursor: "pointer", fontWeight: "bold" }}
                                                onClick={()=>{
                                                   setResetPasswordDialogOpen(true); 
                                                }}
                                            >
                                                Reset Password
                                            </FormLabel>
                                        </FormControl>
                                    </FormGroup>
                                </ContainerItem>
                                <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                    <FormGroup style={{ float: "right" }}>
                                        <FormControl>
                                            <FormLabel style={{ color: colors.dark, cursor: "pointer", fontWeight: "bold" }}
                                                onClick={() => {
                                                    window.location.href = BASE_URL + "/register";
                                                }}
                                            >Create New Account
                                            </FormLabel>
                                        </FormControl>
                                    </FormGroup>
                                </ContainerItem>
                            </WrapperContainer>
                        </WrapperContainer>

                    </ContainerItem>
                </WrapperContainer>
                <ResetPasswordDialogWrapper 
                    open={resetPasswordDialogOpen}
                    closeAction={()=>{
                        setResetPasswordDialogOpen(false);
                    }}
                />
            </div>
        </div>
    )
}
