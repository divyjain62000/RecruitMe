import React from 'react';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import CircularProgress from '@material-ui/core/CircularProgress';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Checkbox from '@material-ui/core/Checkbox';
import TablePaginationActions from '@material-ui/core/TablePagination/TablePaginationActions';
import { TablePagination } from '@material-ui/core';
import { WrapperContainer, ContainerItem, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import TableContainer from '@material-ui/core/TableContainer';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import { DriveDetailsWrapper } from '../component/DriveDetailsWrapper';
import driveStyles from '../css/DriveStyle';
import { isUserPlacementCoordinator, isUserAdmin, redirectToLoginPage, isUserStudent, getAuthenticateUserId } from '../../auth/AuthService';
import { LoaderComponent } from '../../../theme/global/components/LoaderComponent';

const getDrives = () => {
    var promise = new Promise((resolve) => {
        fetch("/api/drive/").then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                resolve(response);
            })
            .catch((error) => {
                console.log("Error while fetching institutes: " + error);
            })
    });
    return promise;
}


const applyForDrive = (driveId, studentId) => {
    var promise = new Promise((resolve, reject) => {
        let studentRegisteredInDriveDTO = {
            "id": 0,
            "driveId": driveId,
            "studentId": studentId
        };
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(studentRegisteredInDriveDTO)
        };
        fetch("/api/student-registered-in-drive/add", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
                if (response.successful) {
                    resolve(JSON.stringify(response.result));
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


export const DriveList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        driveStyles: driveStyles()
    };

    const [drives, setDrives] = React.useState([]);

    const [pageSize, setPageSize] = React.useState(20);
    const [pageNumber, setPageNumber] = React.useState(1);
    const [selectedDrives, setSelectedDrives] = React.useState([]);
    const [areAllSelected, setAreAllSelected] = React.useState(false);
    const [showTable, setShowTable] = React.useState(true);
    const [showDetails, setShowDetails] = React.useState(false);
    const [driveObj, setDriveObj] = React.useState(null);
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [showPage, setShowPage] = React.useState(false);
    const [progressLoader,setProgressLoader] = React.useState(false);
    const [message, setMessage] = React.useState("");

    const onOpenHandler = () => {
        setShowDetails(true);
        //  setShowTable(false);
    }


    const onPageSizeChanged = (ev) => {
        setPageSize(ev.target.value);
        setPageNumber(1);
    }

    const onPageChanged = (ev, pg) => {
        setPageNumber(pg + 1);
    }

    const isStudentSelected = (id) => {
        return selectedDrives.indexOf(id) != -1;
    }

    const onSelectAllClicked = () => {
        var selections = [];
        if (areAllSelected) {
            setAreAllSelected(false);
        }
        else {
            drives.forEach((drive) => {
                selections.push(drive.id);
            });
            setAreAllSelected(true);
        }
        setSelectedDrives(selections);
    }

    const onTableRowClicked = (id) => {
        var selections = [];
        var index = selectedDrives.indexOf(id);
        if (index == -1) //not found
        {
            selections = selections.concat(selectedDrives, id);
        }
        else {
            if (index == 0) selections = selections.concat(selectedDrives.slice(1));
            else if (index == selectedDrives.length - 1) selections = selections.concat(selectedDrives.slice(0, selectedDrives.length - 1));
            else {
                selections = selections.concat(selectedDrives.slice(0, index), selectedDrives.slice(index + 1));
            }
        }
        setSelectedDrives(selections);
        if (selections.length == 0) setAreAllSelected(false);
        if (selections.length == drives.length) setAreAllSelected(true);
    }


    const showDriveDetails = () => {
        onOpenHandler();
    }

    React.useEffect(() => {
        if (isUserAdmin() == false && isUserPlacementCoordinator() == false && isUserStudent() == false) redirectToLoginPage();
        else setShowPage(true);
        setProgressLoader(true);
        getDrives().then((driveList) => {
            setDrives(driveList);
            setProgressLoader(false);
        });
    }, []);

    const getStudentId = () => {
        return getAuthenticateUserId();
    }

    const applyForDriveHelper = async (driveId, studentId) => {
        await applyForDrive(driveId, studentId).then((id) => {
            setDialogOpen(true);
            setMessage("Registered Successfully!");
        }, (exception) => {
            console.log("DriveErr: " + JSON.stringify(exception));

            let errMsg = exception.STUDENT_ID_ERR != undefined ? (exception.STUDENT_ID_ERR + "\n") : "";
            errMsg += exception.APPLY_DRIVE_ERR != undefined ? (exception.APPLY_DRIVE_ERR + "\n") : "";
            errMsg += exception.UG_PROGRAM_ERR != undefined ? (exception.UG_PROGRAM_ERR + "\n") : "";
            errMsg += exception.UG_BRANCH_ERR != undefined ? (exception.UG_BRANCH_ERR + "\n") : "";
            errMsg += exception.PG_PROGRAM_ERR != undefined ? (exception.PG_PROGRAM_ERR + "\n") : "";
            errMsg += exception.PG_BRANCH_ERR != undefined ? (exception.PG_BRANCH_ERR + "\n") : "";
            errMsg += exception.REQUIRED_10TH_MARKS_ERR != undefined ? (exception.REQUIRED_10TH_MARKS_ERR + "\n") : "";
            errMsg += exception.REQUIRED_12TH_MARKS_ERR != undefined ? (exception.REQUIRED_12TH_MARKS_ERR + "\n") : "";
            errMsg += exception.REQUIRED_UG_MARKS_ERR != undefined ? (exception.REQUIRED_UG_MARKS_ERR + "\n") : "";
            errMsg += exception.REQUIRED_PG_MARKS_ERR != undefined ? (exception.REQUIRED_PG_MARKS_ERR + "\n") : "";
            errMsg += exception.DRIVE_FOR_ERR != undefined ? (exception.DRIVE_FOR_ERR + "\n") : "";
            errMsg += exception.NUMBER_OF_CURRENT_BACKLOG_ERR != undefined ? (exception.NUMBER_OF_CURRENT_BACKLOG_ERR + "\n") : "";

            //            let errMsg= exception.UG_BRANCH_ERR+"\n"+(exception.PG_BRANCH_ERR!=undefined?"asd":"23");
            setMessage(errMsg);
            setDialogOpen(true);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }

    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            <LoaderComponent
                open={progressLoader}
            />

            <DriveDetailsWrapper
                open={showDetails}
                onClick={(ev) => {
                    setShowDetails(false);
                    setShowTable(true);
                }}
                drive={driveObj}
            />

            {showPage && showTable && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Drives</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}><FilterIcon style={{ visibility: "hidden" }} /></Button>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={12} xl={12}>
                        <Table>
                            <TableHead stickyHeader className={cssStyles.globalStyles.tableHeader}>
                                <TableRow>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Drive Name</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Company</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Package</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Last Date To Apply</TableCell>
                                    {isUserStudent() && <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>}
                                </TableRow>
                            </TableHead>
                            <TableBody className={cssStyles.globalStyles.tableBody}>
                                {
                                    drives.slice((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize).map((drive, idx) => {
                                        const selectionState = isStudentSelected(drive.id);
                                        return (
                                            <TableRow key={drive.id} hover>

                                                <TableCell onClick={() => {
                                                    showDriveDetails();
                                                    setDriveObj(drive);
                                                }}>{drive.driveName}</TableCell>
                                                <TableCell onClick={() => {
                                                    showDriveDetails();
                                                    setDriveObj(drive);
                                                }}>{drive.company}</TableCell>
                                                <TableCell onClick={() => {
                                                    showDriveDetails();
                                                    setDriveObj(drive);
                                                }}>{drive.salary}</TableCell>
                                                <TableCell onClick={() => {
                                                    showDriveDetails();
                                                    setDriveObj(drive);
                                                }}>{drive.lastDateToApply}</TableCell>
                                                {isUserStudent() && <TableCell>
                                                    <Button className={cssStyles.driveStyles.applyButton}
                                                        onClick={() => {
                                                            let studentId = getStudentId();
                                                            applyForDriveHelper(drive.id, studentId);
                                                        }}
                                                    >Apply</Button>
                                                </TableCell>}
                                            </TableRow>
                                        );
                                    })
                                }
                            </TableBody>
                        </Table>
                    </ContainerItem>
                    <TablePagination
                        component={ContainerItem}
                        rowsPerPageOptions={[20, 25, 30, 35, 50, 100]}
                        count={drives.length}
                        rowsPerPage={pageSize}
                        page={pageNumber - 1}
                        onChangePage={onPageChanged}
                        onChangeRowsPerPage={onPageSizeChanged}
                        align="right"
                    />
                </WrapperContainer>
                <DialogWrapper open={dialogOpen}
                    message={message}
                    buttonText={"Ok"}
                    closeAction={() => {
                        setDialogOpen(false);
                    }}
                    onClick={(ev) => {
                        if (dialogOpen == true) setDialogOpen(false);
                        else setDialogOpen(true);
                    }} />
            </WrapperContainer>}
        </div>
    )
}