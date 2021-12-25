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
import { WrapperContainer, ContainerItem, CheckboxWrapper, DialogWrapper } from '../../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../../theme/global/css/AppStyles';
import TableContainer from '@material-ui/core/TableContainer';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import studentStyles from '../../css/StudentStyles';
import { StudentRegisteredInDriveFilterBox } from './StudentRegisteredInDriveFilterBox';
import StudentRegisteredInDrive from '../../../../domain/StudentRegisteredInDrive';
import DownloadIcon from '@material-ui/icons/ArrowDownward';
import DeleteIcon from '@material-ui/icons/DeleteForever';
import { LoaderComponent } from '../../../../theme/global/components/LoaderComponent';



const exportStudentRegisteredInDriveList = (data) => {
    /*    const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };*/
    /*var promise = new Promise((resolve) => {
        fetch(").then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                alert(JSON.stringify(responseObj));
                window.location.replace(JSON.stringify(responseObj));
            })
            .catch(error => {
                console.error("Error while fetching countries: " + error);
            })

    });
    return promise;*/
    //window.location.href = "https://google.com/contact";
    //alert("localhost:8080/api/student-registered-in-drive/export?ug="+data.getSearchForUgStudents()+"&pg="+data.getSearchForPgStudents()+"&bId="+data.getBranchId()+"&dId="+data.getDriveId());
    window.location.href = "http://localhost:8080/api/student-registered-in-drive/export?ug=" + data.getSearchForUgStudents() + "&pg=" + data.getSearchForPgStudents() + "&bId=" + data.getBranchId() + "&dId=" + data.getDriveId();
}



const searchStudentRegisteredInDrive = (data) => {
    const requestOption = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };
    var promise = new Promise((resolve) => {
        fetch("/api/student-registered-in-drive/search/?page=0&size=20", requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj.content);
            })
            .catch(error => {
                console.error("Error while fetching countries: " + error);
            })

    });
    return promise;
}


export const StudentRegisteredInDriveList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };

    const [students, setStudents] = React.useState([]);

    const [pageSize, setPageSize] = React.useState(20);
    const [pageNumber, setPageNumber] = React.useState(1);
    const [selectedStudents, setSelectedStudents] = React.useState([]);
    const [areAllSelected, setAreAllSelected] = React.useState(false);
    const [showTable, setShowTable] = React.useState(false);
    const [showDetails, setShowDetails] = React.useState(true);
    const [studentObj, setStudentObj] = React.useState(null);
    const [studentRegisteredInDriveExportObj, setStudentRegisteredInDriveExportObj] = React.useState(new StudentRegisteredInDrive());
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

    const isStudentSelected = (id) => {
        return selectedStudents.indexOf(id) != -1;
    }

    const onSelectAllClicked = () => {
        var selections = [];
        if (areAllSelected) {
            setAreAllSelected(false);
        }
        else {
            students.forEach((student) => {
                selections.push(student.id);
            });
            setAreAllSelected(true);
        }
        setSelectedStudents(selections);
    }

    const onTableRowClicked = (id) => {
        var selections = [];
        var index = selectedStudents.indexOf(id);
        if (index == -1) //not found
        {
            selections = selections.concat(selectedStudents, id);
        }
        else {
            if (index == 0) selections = selections.concat(selectedStudents.slice(1));
            else if (index == selectedStudents.length - 1) selections = selections.concat(selectedStudents.slice(0, selectedStudents.length - 1));
            else {
                selections = selections.concat(selectedStudents.slice(0, index), selectedStudents.slice(index + 1));
            }
        }
        setSelectedStudents(selections);
        if (selections.length == 0) setAreAllSelected(false);
        if (selections.length == students.length) setAreAllSelected(true);
    }


    const showStudentDetails = () => {
        onOpenHandler();
    }

    const search = (data) => {
        setProgressLoader(true);
        setStudentRegisteredInDriveExportObj(data);
        searchStudentRegisteredInDrive(data).then((studentList) => {
            setStudents(studentList);
            setShowTable(true);
            setShowDetails(false);
            setProgressLoader(false);

        }, (exception) => {
            console.log("Student Registered In drive search: " + JSON.stringify(exception));
        }).catch((error) => {
        });

    }

    return (
        <div className={cssStyles.globalStyles.mainContainer}>

            <LoaderComponent
                open={progressLoader}
            />
            <StudentRegisteredInDriveFilterBox
                open={showDetails}
                onClose={() => {
                    //     alert('close');
                    setShowDetails(true);
                }}
                student={studentObj}
                closeAction={() => {
                    setShowDetails(false);
                }}
                searchStudentRegisteredInDrive={search}
            />

            {showTable && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Registered Students</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}><FilterIcon onClick={() => {
                            setShowTable(false);
                            setShowDetails(true);
                        }} /></Button>

                        <Button onClick={() => {
                            exportStudentRegisteredInDriveList(studentRegisteredInDriveExportObj);
                        }} className={cssStyles.globalStyles.filterIcon}>
                            <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24"><path d="M0 0h24v24H0z" fill="none" /><path d="M19 9h-4V3H9v6H5l7 7 7-7zM5 18v2h14v-2H5z" /></svg>

                        </Button>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={12} xl={12}>
                        <Table>
                            <TableHead stickyHeader className={cssStyles.globalStyles.tableHeader}>
                                <TableRow>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}><CheckboxWrapper
                                        indeterminate={selectedStudents.length > 0 && selectedStudents.length < students.length}
                                        checked={areAllSelected}
                                        onClick={onSelectAllClicked}
                                        style={{ background: "white" }}
                                    /></TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Enrollment No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Name</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Program (Branch)</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Mobile No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Email</TableCell>

                                </TableRow>
                            </TableHead>
                            <TableBody className={cssStyles.globalStyles.tableBody}>
                                {

                                    students.slice((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize).map((student, idx) => {

                                        const selectionState = isStudentSelected(student.id);

                                        return (
                                            <TableRow key={student.id} hover>
                                                <TableCell>
                                                    {student.program != null && <CheckboxWrapper checked={selectionState} onClick={() => {
                                                        onTableRowClicked(student.id);
                                                    }} />}
                                                </TableCell>

                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                }}>{student.enrollmentNumber}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                }}>{student.studentName}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                }}>{student.program != null ? (student.program + "(" + student.branch + ")") : ""}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                }}>{student.primaryMobileNumber}</TableCell>
                                                <TableCell onClick={() => {
                                                    showStudentDetails();
                                                    setStudentObj(student);
                                                }}>{student.primaryEmail}</TableCell>

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
        </div>
    )
}