import React from 'react';
import globalStyles from '../../../theme/global/css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import studentStyles from '../css/StudentStyles';
import CloseIcon from '@material-ui/icons/Close';
import { ContainerItem, OutlinedSelectWrapper, WrapperContainer, RadioGroupWrapper } from '../../../theme/global/components/ContainerComponents';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { searchForGraduation } from '../../../app-constants/UGPGSearch';
import StudentRegisteredInDrive from '../../../domain/StudentRegisteredInDrive';



/**
 * To get all programs based on institute that user select
 * @param {*} id 
 * @returns promise
 */
const getPrograms = () => {
    var promise = new Promise((resolve) => {
        fetch("/api/programs").then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                resolve(response.content);
            })
            .catch((error) => {
                console.log("Error while fetching institutes: " + error);
            })
    });
    return promise;
}

/**
 * To get all branches based on program that user select
 * @param {*} id 
 * @returns promise
 */
const getBranches = (id) => {
    var promise = new Promise((resolve) => {
        fetch("/api/branches/" + id).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                resolve(response.content);
            })
            .catch((error) => {
                console.log("Error while fetching institutes: " + error);
            })
    });
    return promise;
}


export const StudentFilterBox = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };
    let student = props.student;

    const disabledColor = "#ddd";
    const enabledColor = "#fff";


    const [programs, setPrograms] = React.useState([]);
    const [branches, setBranches] = React.useState([]);
    const [studentRegisteredInDriveObj, setStudentRegisteredInDrive] = React.useState(new StudentRegisteredInDrive());
    const [tmp, setTmp] = React.useState([]);


    const [branchDisable, setBranchDisable] = React.useState(true);

    const [branchTmpBgColor, setBranchTmpBgColor] = React.useState(disabledColor);

    React.useEffect(() => {
        getPrograms().then((programList) => {
            setPrograms(programList);
        });
        getDrives().then((driveList) => {
            setDrives(driveList);
        });
    }, []);


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
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitle}></TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>
                                <DialogActions>
                                    <CloseIcon onClick={() => {
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
                    <ContainerItem xs={12} sm={12} md={6} lg={12} xl={6}>
                        <FormGroup>
                            <FormControl>
                                <OutlinedSelectWrapper
                                    label="Program"
                                    menulist={programs}
                                    onChange={(ev) => {
                                        getBranches(ev.target.value).then((branchList) => {
                                            setBranches(branchList);
                                            setBranchDisable(false);
                                            setBranchTmpBgColor(enabledColor);
                                            studentRegisteredInDriveObj.setProgramId(ev.target.value);
                                            setStudentRegisteredInDrive(studentRegisteredInDriveObj);
                                        });
                                    }}
                                    defaultValue={studentRegisteredInDriveObj.getProgramId()}
                                />
                            </FormControl>
                        </FormGroup>
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={6} lg={12} xl={6}>
                        <FormGroup>
                            <FormControl>
                                <OutlinedSelectWrapper
                                    label="Branch"
                                    menulist={branches}
                                    disabled={branchDisable}
                                    style={{ background: branchTmpBgColor }}
                                    onChange={((ev) => {
                                       // placementCoordinatorObj.setBranchId(ev.target.value);
                                        studentRegisteredInDriveObj.setBranchId(ev.target.value);
                                        setStudentRegisteredInDrive(studentRegisteredInDriveObj);
                                        //  setTmp(ev.target.value)
                                    })}
                                   // defaultValue={studentRegisteredInDriveObj.getBranchId()}
                                />
                            </FormControl>
                        </FormGroup>
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                        <FormGroup>
                            <FormLabel className={cssStyles.globalStyles.formQuestion}>Search For</FormLabel>
                            <FormControl>
                                <RadioGroupWrapper
                                    options={searchForGraduation}
                                    onChange={((ev) => {
                                        if ((ev.target.value) == 'UG') {
                                            studentRegisteredInDriveObj.setSearchForUgStudents(true);
                                            studentRegisteredInDriveObj.setSearchForPgStudents(false);
                                        }
                                        else {
                                            studentRegisteredInDriveObj.setSearchForUgStudents(false);
                                            studentRegisteredInDriveObj.setSearchForPgStudents(true)
                                        }
                                        setStudentRegisteredInDrive(studentRegisteredInDriveObj);
                                        setTmp(ev.target.value);
                                    })}
                                    checkvalue={studentRegisteredInDriveObj.getSearchForUgStudents() ? 'UG' : (studentRegisteredInDriveObj.getSearchForPgStudents() ? 'PG' : null)}
                                />
                            </FormControl>
                        </FormGroup>
                    </ContainerItem>
                    <ContainerItem xs={12} sm={12} md={12} lg={12} xl={3}>
                        <Button
                            className={cssStyles.globalStyles.searchButton}
                            style={{ float: "right" }}
                            onClick={() => {
                                props.searchStudents(studentRegisteredInDriveObj)
                            }}
                        >Search</Button>
                    </ContainerItem>
                </WrapperContainer>
            </DialogContent>
        </Dialog>
    )
}