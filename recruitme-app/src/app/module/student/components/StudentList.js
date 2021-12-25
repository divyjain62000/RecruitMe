import React from 'react';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import { TablePagination } from '@material-ui/core';
import { WrapperContainer, ContainerItem, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import { StudentDetailsWrapper } from '../components/StudentDetailsWrapper';
import studentStyles from '../css/StudentStyles';
import { isUserAdmin, isUserPlacementCoordinator, redirectToLoginPage } from '../../auth/AuthService';
import DeleteIcon from '@material-ui/icons/DeleteForever';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants'
import { BlacklistDialogWrapper } from './BlacklistDialogWrapper';
import { blacklistReasonOptions } from '../../../app-constants/BlacklistReasonOptions';
import { unblacklistStudent } from '../api/StudentAPICaller';
import { getStudentHoldingOfferByStudentId } from '../holding-offer/api/StudentHoldingOfferApiCaller';
import { deleteStudent } from '../api/StudentAPICaller';
import { LoaderComponent } from '../../../theme/global/components/LoaderComponent';

const getStudents = (data) => {
    var promise = new Promise((resolve) => {
        fetch("/api/student/?page=0&size=20").then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj.content)
            })
            .catch(error => {
                console.error("Error while fetching countries: " + error);
            })

    });
    return promise;
}


export const StudentList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };

    const [students, setStudents] = React.useState([]);

    const [pageSize, setPageSize] = React.useState(20);
    const [pageNumber, setPageNumber] = React.useState(1);
    const [showTable, setShowTable] = React.useState(true);
    const [showDetails, setShowDetails] = React.useState(false);
    const [showBlacklistReasonDialogWrapper, setShowBlacklistReasonDialogWrapper] = React.useState(false);
    const [studentObj, setStudentObj] = React.useState(null);
    const [showPage, setShowPage] = React.useState(false);
    const [studentToBlacklistObj, setStudentToBlacklistObj] = React.useState(null);
    const [studentHoldingOfferList, setStudentHoldingOfferList] = React.useState([]);
    const [studentDeleteDialogOpen,setStudentDeleteDialogOpen] = React.useState(false);
    const [progressLoader,setProgressLoader] = React.useState(false);

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


    const showStudentDetails = () => {
        onOpenHandler();
    }


    const unblacklistStudentHelper = async (studentId) => {
        await unblacklistStudent(studentId).then((successful) => {
            //if (successful === true) 
        }, (exception) => {
            //nothing to do right now
        })
            .catch((err) => {

            });
    }


    const getStudentHoldingOfferDetail = async (studentId) => {
        await getStudentHoldingOfferByStudentId(studentId).then((studentHoldingOfferL) => {
            if (studentHoldingOfferL != null) setStudentHoldingOfferList(studentHoldingOfferL);
        })
            .catch((err) => {
                console.log("error occur while fetching student holding offer letter by id");
            });
    }


    const deleteStudentHelper = async (studentId) => {
        await deleteStudent(studentId).then(() => {
            window.location.reload(true);
        })
            .catch((err) => {
                console.log("Error occur while deleting student " + err);
            });
    }



    React.useEffect(() => {
        if (isUserPlacementCoordinator() === false && isUserAdmin() === false) redirectToLoginPage();
        else setShowPage(true);
        let data = {

        };
        setProgressLoader(true);
        getStudents(data).then((studentList) => {
            studentList.sort(function(s1,s2){
                let e1=s1.education.ugBranch.branchCode.toLowerCase();
                let e2=s2.education.ugBranch.branchCode.toLowerCase();
                if(e1<e2) return -1;
                if(e1>e2) return 1;
                return 0;
            });
            setStudents(studentList);
            setProgressLoader(false);
        });
    }, []);


    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            <LoaderComponent 
                open={progressLoader}
            />

            <StudentDetailsWrapper
                open={showDetails}
                onClick={(ev) => {
                    setShowDetails(false);
                    setShowTable(true);
                }}
                student={studentObj}
                studentHoldingoffers={studentHoldingOfferList}
            />

            {showPage && showTable && <WrapperContainer boxShadow={false} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Students</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}><FilterIcon /></Button>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={12} xl={12}>
                        <Table>
                            <TableHead stickyHeader className={cssStyles.globalStyles.tableHeader}>
                                <TableRow>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Enrollment No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Name</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Program (Branch)</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Mobile No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody className={cssStyles.globalStyles.tableBody}>
                                {
                                    students.slice((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize).map((student, idx) => {
                                        return (
                                            <TableRow key={student.id} hover>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                    getStudentHoldingOfferDetail(student.id);
                                                }}>{student.enrollmentNumber}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                    getStudentHoldingOfferDetail(student.id);
                                                }}>{student.name}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                    getStudentHoldingOfferDetail(student.id);
                                                }}>{student.education.ugProgram.programCode + "(" + student.education.ugBranch.branchCode + ")"}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                    getStudentHoldingOfferDetail(student.id);
                                                }}>{student.primaryMobileNumber}</TableCell>
                                                <TableCell>
                                                    {student.isBlacklisted === false &&
                                                        <Button
                                                            onClick={() => {
                                                                setStudentToBlacklistObj(student);
                                                                setShowBlacklistReasonDialogWrapper(true);
                                                            }}
                                                            className={cssStyles.studentStyles.blacklistButton}>
                                                            Blacklist
                                                        </Button>
                                                    }
                                                    {student.isBlacklisted === true &&
                                                        <Button
                                                            onClick={() => {
                                                                unblacklistStudentHelper(student.id);
                                                                window.location.reload(true);
                                                            }}
                                                            className={cssStyles.studentStyles.unblacklistButton}>
                                                            Un-Blacklist
                                                        </Button>}
                                                </TableCell>
                                                <TableCell>
                                                    {(student.isPlaceForFulltime === false && student.isPlaceForInternship === false) && <h4 style={{ color: colors.danger }}>Not Placed</h4>}
                                                    {(student.isPlaceForFulltime || student.isPlaceForInternship) && <h4 style={{ color: colors.success }}>Placed</h4>}
                                                </TableCell>
                                                <TableCell>
                                                    <DeleteIcon
                                                        onClick={() => {
                                                            deleteStudentHelper(student.id);
                                                        }}
                                                        className={cssStyles.globalStyles.deleteIcon} />
                                                </TableCell>
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
                        count={students.length}
                        rowsPerPage={pageSize}
                        page={pageNumber - 1}
                        onChangePage={onPageChanged}
                        onChangeRowsPerPage={onPageSizeChanged}
                        align="right"
                    />
                </WrapperContainer>
            </WrapperContainer>}
            <BlacklistDialogWrapper open={showBlacklistReasonDialogWrapper}
                blacklistReasonOptions={blacklistReasonOptions}
                blacklistStudent={studentToBlacklistObj}
                closeAction={() => {
                    setShowBlacklistReasonDialogWrapper(false);
                }}
            />
            <DialogWrapper
                title={"Success"}
                message={"Student deleted successfully!"}
                buttonText={"Ok"}
                contentCenter={true}
                verifiedLogo={true}
                open={studentDeleteDialogOpen}
                closeAction={(ev) => {
                    setStudentDeleteDialogOpen(false);
                    window.location.reload(true);
                }} />
        </div>
    )
}