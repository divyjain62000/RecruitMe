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
import placementCoordinatorStyles from '../css/PlacementCoordinatorStyle';
import { isUserAdmin, redirectToLoginPage } from '../../../auth/AuthService';
import DeleteIcon from '@material-ui/icons/DeleteForever';
import { LoaderComponent } from '../../../../theme/global/components/LoaderComponent';


const getPlacementCoordinators = () => {
    var promise = new Promise((resolve) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({})
        };
        fetch("/api/placement-coordinator/search", requestOption).then((response) => {
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

export const PlacementCoordinatorList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
        placementCoordinatorStyles: placementCoordinatorStyles()
    };

    const [placementCoordinators, setPlacementCoordinators] = React.useState([]);

    const [pageSize, setPageSize] = React.useState(20);
    const [pageNumber, setPageNumber] = React.useState(1);
    const [selectedPlacementCoordinators, setSelectedPlacementCoordinators] = React.useState([]);
    const [areAllSelected, setAreAllSelected] = React.useState(false);
    const [showTable, setShowTable] = React.useState(true);
    const [showPage, setShowPage] = React.useState(false);
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [message, setMessage] = React.useState("");
    const [progressLoader,setProgressLoader] = React.useState(false);

    const onPageSizeChanged = (ev) => {
        setPageSize(ev.target.value);
        setPageNumber(1);
    }

    const onPageChanged = (ev, pg) => {
        setPageNumber(pg + 1);
    }

    const isPlacementCoordinatorSelected = (id) => {
        return selectedPlacementCoordinators.indexOf(id) != -1;
    }

    const onSelectAllClicked = () => {
        var selections = [];
        if (areAllSelected) {
            setAreAllSelected(false);
        }
        else {
            placementCoordinators.forEach((placementCoordinator) => {
                selections.push(placementCoordinator.id);
            });
            setAreAllSelected(true);
        }
        setSelectedPlacementCoordinators(selections);
    }

    const onTableRowClicked = (id) => {
        var selections = [];
        var index = selectedPlacementCoordinators.indexOf(id);
        if (index == -1) //not found
        {
            selections = selections.concat(selectedPlacementCoordinators, id);
        }
        else {
            if (index == 0) selections = selections.concat(selectedPlacementCoordinators.slice(1));
            else if (index == selectedPlacementCoordinators.length - 1) selections = selections.concat(selectedPlacementCoordinators.slice(0, selectedPlacementCoordinators.length - 1));
            else {
                selections = selections.concat(selectedPlacementCoordinators.slice(0, index), selectedPlacementCoordinators.slice(index + 1));
            }
        }
        setSelectedPlacementCoordinators(selections);
        if (selections.length == 0) setAreAllSelected(false);
        if (selections.length == placementCoordinators.length) setAreAllSelected(true);
    }


    React.useEffect(() => {
        if (isUserAdmin() == false) redirectToLoginPage();
        else setShowPage(true);
        setProgressLoader(true);
        getPlacementCoordinators().then((placementCoordinatorList) => {
            setPlacementCoordinators(placementCoordinatorList);
            setProgressLoader(false);
        });
    }, []);

    const getPlacementCoordinatorId = () => {
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
                        <h1>Placement Coordinators</h1>
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
                                    {/* {isUserAdmin() && <TableCell className={cssStyles.globalStyles.tableHeaderCell}></TableCell>} */}
                                </TableRow>
                            </TableHead>
                            <TableBody className={cssStyles.globalStyles.tableBody}>
                                {
                                    placementCoordinators.slice((pageNumber - 1) * pageSize, (pageNumber - 1) * pageSize + pageSize).map((placementCoordinator, idx) => {
                                        const selectionState = isPlacementCoordinatorSelected(placementCoordinator.id);
                                        if (placementCoordinator.primaryEmail != 'admin@gmail.com') {
                                            return (
                                                <TableRow key={placementCoordinator.id} hover>

                                                    <TableCell>{placementCoordinator.name}</TableCell>
                                                    <TableCell>{placementCoordinator.program + "(" + placementCoordinator.branch + ")"}</TableCell>
                                                    <TableCell>{placementCoordinator.primaryEmail}</TableCell>
                                                    <TableCell>{placementCoordinator.primaryMobileNumber}</TableCell>
                                                    <TableCell>{placementCoordinator.gender == 'M' ? "Male" : (placementCoordinator.gender == 'F' ? "Female" : "Other")}</TableCell>
                                                    {/* <TableCell>
                                                <DeleteIcon className={cssStyles.globalStyles.deleteIcon} />
                                                </TableCell> */}
                                                </TableRow>
                                            );
                                        }
                                    })
                                }
                            </TableBody>
                        </Table>
                    </ContainerItem>
                    <TablePagination
                        component={ContainerItem}
                        rowsPerPageOptions={[20, 25, 30, 35, 50, 100]}
                        count={placementCoordinators.length}
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