import React, { useEffect } from 'react';
import globalStyles from '../../../theme/global/css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import studentStyles from '../../../module/student/css/StudentStyles';
import CloseIcon from '@material-ui/icons/Close';
import { ContainerItem, WrapperContainer, RadioGroupWrapper, FormErrorLabelWrapper } from '../../../theme/global/components/ContainerComponents';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import { OutlinedTextFieldWrapper } from '../../global/components/ContainerComponents';
import { roleOption } from '../../../app-constants/Role';
import { colors } from '../../global/css/theme-constants/ThemeConstants';
import { requestToResetPassword } from '../../../module/auth/api/AuthApiCaller';






export const ResetPasswordDialogWrapper = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };

    const [email, setEmail] = React.useState("");
    const [role, setRole] = React.useState(null);
    const [resetPasswordErr,setResetPasswordErr] = React.useState({});
    const [success,setSuccess] = React.useState(false);
    const [roles,setRoles] = React.useState(null);

    const requestToResetPasswordHelper= async ()=>{
        let credential = {
            emailOrUsername: email,
            password: null,
            role: role
        }
        await requestToResetPassword(credential).then(()=>{
            setSuccess(true);
            setEmail("");
            setRole(null);
            setResetPasswordErr({});
        },(exception)=>{
            setResetPasswordErr(exception);
            setSuccess(false);
        })
        .catch((err)=>{
            console.log("Error occur while reseting password "+err);
        })

    }

    return (
        <Dialog
            {...props}
            style={{ marginTop: "50px" }}
            onBackdropClick="false"
        >

            <DialogTitle>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitle}>Reset Password</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>
                                <DialogActions>
                                    <CloseIcon onClick={() => {
                                         setEmail("");
                                         setRole(null);
                                         setResetPasswordErr({});
                                         setSuccess(false);
                                        props.closeAction();
                                    }} className={cssStyles.studentStyles.studentDetailCloseIcon} />
                                </DialogActions>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </DialogTitle>
            <DialogContent>
                <WrapperContainer boxShadow={false} id="card-0" style={{ width: "550px", marginTop: "-30px", marginBottom: "-20px" }} spacing={5} className={cssStyles.globalStyles.formContainer}>
                         {success &&   <FormControl>
                            <span style={{color: colors.success,fontSize: "15px", paddingLeft: "15px",marginTop: "5px"}}>We have sent a link to reset your password on your registered email. Please follow the link to set a new password.</span>
                            </FormControl> }
               
                    <ContainerItem xs={12} sm={12} md={6} lg={12} xl={6}>
                        <FormGroup>
                            <FormErrorLabelWrapper errorMessage={resetPasswordErr.EMAIL_ERR} />
                            <FormControl>
                                <OutlinedTextFieldWrapper
                                    label="Email"
                                    onChange={(ev) => {
                                        setEmail(ev.target.value);
                                    }}
                                    value={email}
                                />
                            </FormControl>
                        </FormGroup>
                    </ContainerItem>

                    <ContainerItem xs={12} sm={12} md={6} lg={10} xl={6}>
                        <FormGroup>
                            <FormControl>
                            <FormErrorLabelWrapper errorMessage={resetPasswordErr.ROLE_ERR} />
                                <RadioGroupWrapper
                                    options={roleOption.filter((element, index) => index < roleOption.length - 1)}
                                    rowmode={false}
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
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={3}>
                        <Button
                            className={cssStyles.globalStyles.searchButton}
                            style={{ float: "right" }}
                            onClick={() => {
                                requestToResetPasswordHelper();
                            }}
                        >Submit</Button>
                    </ContainerItem>
                </WrapperContainer>
            </DialogContent>
        </Dialog>
    )
}