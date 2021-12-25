import React from 'react';
import globalStyles from '../../../theme/global/css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import studentStyles from '../css/StudentStyles';
import CloseIcon from '@material-ui/icons/Close';
import { RadioGroupWrapper } from '../../../theme/global/components/ContainerComponents';
import { blacklistStudent } from '../api/StudentAPICaller';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';


export const BlacklistDialogWrapper = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };

    const [studentBlacklistReasonErr, setStudentBlacklistReasonErr] = React.useState("");
    const [blacklistReason,setBlacklistReason] = React.useState("");

    const blacklistStudentHelper = async () => {
        props.blacklistStudent.reasonForBlacklist=blacklistReason;
        await blacklistStudent(props.blacklistStudent).then(() => {
          props.closeAction();
            window.location.reload(true);
        }, (exception) => {
            setStudentBlacklistReasonErr(exception.BLACKLIST_REASON_ERR);
        })
            .catch((err) => {
                console.log("Error occur while blacklisting student");
            })
    }


    return (

        <Dialog
            {...props}
            style={{ marginTop: "50px" }}
        >

            <DialogTitle>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitle}>Student Detail</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>
                                <DialogActions>
                                    <CloseIcon
                                        onClick={() => {
                                            props.closeAction();
                                            setStudentBlacklistReasonErr("");
                                            setBlacklistReason(null);
                                        }}
                                        className={cssStyles.studentStyles.studentDetailCloseIcon} />
                                </DialogActions>
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={5} style={{ border: "none", color: colors.danger, fontSize: "18px" }}>
                                {studentBlacklistReasonErr}
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </DialogTitle>
            <DialogContent>
                <RadioGroupWrapper
                    options={props.blacklistReasonOptions}
                    onChange={(ev) => {
                       setBlacklistReason(ev.target.value);
                    }}
                    checkvalue={blacklistReason}
                />
            </DialogContent>
            <DialogActions>
                <Button
                    className={cssStyles.globalStyles.searchButton}
                    onClick={() => {
                        blacklistStudentHelper();
                        setStudentBlacklistReasonErr("");
                        setBlacklistReason(null);
                        window.location.reload(true);
                    }}>Blacklist
                </Button>
            </DialogActions>
        </Dialog>
    )
}