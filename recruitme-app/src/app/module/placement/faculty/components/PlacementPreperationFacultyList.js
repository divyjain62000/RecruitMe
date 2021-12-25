import React from 'react';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import { TablePagination } from '@material-ui/core';
import { WrapperContainer, ContainerItem, DialogWrapper } from '../../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import placementPreperationFacultyStyles from '../css/PlacementPreperationFacultyStyles';
import { isUserAdmin, redirectToLoginPage } from '../../../auth/AuthService';
import DeleteIcon from '@material-ui/icons/DeleteForever';
import { LoaderComponent } from '../../../../theme/global/components/LoaderComponent';

const getPlacementPreperationFaculties = () => {
    var promise = new Promise((resolve) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        };
        fetch("/api/placement-preparation-faculty/search", requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                resolve(response.content);
            })
            .catch((error) => {
                console.log("Error while fetching preparation faculties list: " + error);
            })
    });
    return promise;
}

export const PlacementPreperationFacultyList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        placementPreperationFacultyStyles: placementPreperationFacultyStyles()
    };

    const [placementPreperationFaculties, setPlacementPreperationFaculties] = React.useState([]);

    const [pageSize, setPageSize] = React.useState(20);
    const [pageNumber, setPageNumber] = React.useState(1);
    const [selectedPlacementPreperationFaculties, setSelectedPlacementPreperationFaculties] = React.useState([]);
    const [areAllSelected, setAreAllSelected] = React.useState(false);
    const [showTable, setShowTable] = React.useState(true);
    const [showPage, setShowPage] = React.useState(false);
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [message, setMessage] = React.useState("");
    const [progressLoader, setProgressLoader] = React.useState(false);

    const onPageSizeChanged = (ev) => {
        setPageSize(ev.target.value);
        setPageNumber(1);
    }

    const onPageChanged = (ev, pg) => {
        setPageNumber(pg + 1);
    }

    const isPlacementPreperationFacultySelected = (id) => {
        return selectedPlacementPreperationFaculties.indexOf(id) != -1;
    }

    const onSelectAllClicked = () => {
        var selections = [];
        if (areAllSelected) {
            setAreAllSelected(false);
        }
        else {
            placementPreperationFaculties.forEach((placementPreperationFaculty) => {
                selections.push(placementPreperationFaculty.id);
            });
            setAreAllSelected(true);
        }
        setSelectedPlacementPreperationFaculties(selections);
    }

    const onTableRowClicked = (id) => {
        var selections = [];
        var index = selectedPlacementPreperationFaculties.indexOf(id);
        if (index == -1) //not found
        {
            selections = selections.concat(selectedPlacementPreperationFaculties, id);
        }
        else {
            if (index == 0) selections = selections.concat(selectedPlacementPreperationFaculties.slice(1));
            else if (index == selectedPlacementPreperationFaculties.length - 1) selections = selections.concat(selectedPlacementPreperationFaculties.slice(0, selectedPlacementPreperationFaculties.length - 1));
            else {
                selections = selections.concat(selectedPlacementPreperationFaculties.slice(0, index), selectedPlacementPreperationFaculties.slice(index + 1));
            }
        }
        setSelectedPlacementPreperationFaculties(selections);
        if (selections.length == 0) setAreAllSelected(false);
        if (selections.length == placementPreperationFaculties.length) setAreAllSelected(true);
    }


    React.useEffect(() => {
        if (isUserAdmin() == false) redirectToLoginPage();
        else setShowPage(true);
        setProgressLoader(true);
        getPlacementPreperationFaculties().then((placementPreperationFacultyList) => {
            setPlacementPreperationFaculties(placementPreperationFacultyList);
            setProgressLoader(false);
        });
    }, []);

    const getPlacementPreperationFacultyId = () => {
        return 20;
    }

    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            <LoaderComponent
                open={progressLoader}
            />
            {showPage && showTable && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Placement Preparation Faculties</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}><FilterIcon style={{ visibility: "hidden" }} /></Button>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={12} xl={12}>
                        <Table>
                            <TableHead stickyHeader className={cssStyles.globalStyles.tableHeader}>
                                <TableRow>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Name</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Department</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Email</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Mobile No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Gender</TableCell>
                                    {isUserAdmin() && <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>}
                                </TableRow>
                            </TableHead>
                            <TableBody className={cssStyles.globalStyles.tableBody}>
                                {
                                    placementPreperationFaculties.slice((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize).map((placementPreperationFaculty, idx) => {
                                        const selectionState = isPlacementPreperationFacultySelected(placementPreperationFaculty.id);
                                        return (
                                            <TableRow key={placementPreperationFaculty.id} hover>

                                                <TableCell>{placementPreperationFaculty.name}</TableCell>
                                                <TableCell>{placementPreperationFaculty.program + "(" + placementPreperationFaculty.branch + ")"}</TableCell>
                                                <TableCell>{placementPreperationFaculty.primaryEmail}</TableCell>
                                                <TableCell>{placementPreperationFaculty.primaryMobileNumber}</TableCell>
                                                <TableCell>{placementPreperationFaculty.gender == 'M' ? "Male" : (placementPreperationFaculty.gender == 'F' ? "Female" : "Other")}</TableCell>
                                                <TableCell>
                                                    <DeleteIcon className={cssStyles.globalStyles.deleteIcon} />
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
                        count={placementPreperationFaculties.length}
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
                    onClick={(ev) => {
                        if (dialogOpen == true) setDialogOpen(false);
                        else setDialogOpen(true);
                    }} />
            </WrapperContainer>}
        </div>
    )
}