import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import { FormLabel } from '@material-ui/core';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
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
import { resetPassword } from '../../../module/auth/api/AuthApiCaller';


/**
 * Reset Password form component
 */
export const ResetPasswordComponent = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [cpassword, setCpassword] = React.useState("");
    const [password, setPassword] = React.useState("");
    const [errOcurred, setErrOccured] = React.useState(false);
    const [msg, setMsg] = React.useState("");
    const [token, setToken] = React.useState("");
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [showPage, setShowPage] = React.useState(false);


    const resetPasswordHelper = async () => {
        if (password.length === 0 && cpassword.length === 0) {
            setErrOccured(true);
            setMsg("Password and Confirm Password required");
        }
        else if (password.length === 0) {
            setErrOccured(true);
            setMsg("Password required");
        }
        else if (cpassword.length === 0) {
            setErrOccured(true);
            setMsg("Confirm Password required");
        }
        else if (cpassword !== password) {
            setErrOccured(true);
            setMsg("Password and Confirm Password not match");
        } else {
          //  alert("hello");
            await resetPassword(token,password).then((id) => {
                setDialogOpen(true);
            }, (exception) => {
                setErrOccured(true);
                setMsg("Sorry, We Can't Update Your Password Due to Some Technical Reason. Try After Some Time");
            }).catch((error) => {

            });
        }
    }

    React.useEffect(() => {
        const queryParams = new URLSearchParams(window.location.search);
        let tokenTmp = queryParams.get("token");
        if (tokenTmp !== null && tokenTmp.length !== 0) {
            setShowPage(true);
            setToken(tokenTmp);
        } else {
            setShowPage(false);
        }
    })

    return (
        <div>
            {showPage && <div className={cssStyles.globalStyles.mainContainer}>
                <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                    <ContainerItem lg={10}>
                        <WrapperContainer spacing={5} className={cssStyles.globalStyles.formTitleHeader}>
                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} className={cssStyles.globalStyles.formTitle}>
                                RESET PASSWORD
                            </ContainerItem>
                        </WrapperContainer>

                        {errOcurred && <Alert severity="error">{msg}</Alert>}

                        <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
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
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <OutlinedTextFieldWrapper
                                            label="Confirm Password"
                                            type="password"
                                            onChange={((ev) => {
                                                setCpassword(ev.target.value);
                                            })}
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
                                             //   alert("asd")
                                                resetPasswordHelper();
                                            }}
                                        >
                                            Reset
                                        </Button>
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>

                        </WrapperContainer>

                    </ContainerItem>
                </WrapperContainer>
                <DialogWrapper
                    title={"Success"}
                    message={"Password Reset Successfully!"}
                    buttonText={"Ok"}
                    contentCenter={true}
                    verifiedLogo={true}
                    open={dialogOpen}
                    closeAction={(ev) => {
                        setDialogOpen(false)
                        window.location.href = BASE_URL + "/login";
                    }} />
            </div>}
        </div>
    )
}
