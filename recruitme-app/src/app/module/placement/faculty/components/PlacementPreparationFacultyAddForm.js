import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper } from '../../../../theme/global/components/ContainerComponents';
import { gender } from '../../../../app-constants/Gender';
import Button from '@material-ui/core/Button';
import PlacementPreparationFaculty from '../../../../domain/PlacementPreparationFaculty';
import { isUserAdmin, redirectToLoginPage } from '../../../auth/AuthService';

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


/**
 * To add student
 * @param {*} placementPreparationFacultyObj 
 * @returns promise
 */
const addPlacementPreparationFaculty = (placementPreparationFacultyObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(placementPreparationFacultyObj)
        };
        fetch("/api/placement-preparation-faculty/add-placement-preparation-faculty", requestOption).then((response) => {
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



/**
 * Placement Preparation Faculty add form component
 */
export const PlacementPreparationFacultyAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const disabledColor = "#ddd";
    const enabledColor = "#fff";


    const [programs, setPrograms] = React.useState([]);
    const [branches, setBranches] = React.useState([]);

    const [branchDisable, setBranchDisable] = React.useState(true);

    const [branchTmpBgColor, setBranchTmpBgColor] = React.useState(disabledColor);

    const [placementPreparationFacultyObj, setPlacementPreparationFacultyObj] = React.useState(new PlacementPreparationFaculty());

    const [placementPreparationFacultyErr, setPlacementPreparationFacultyErr] = React.useState({});

    const [dialogOpen, setDialogOpen] = React.useState(false);

    const [tmp, setTmp] = React.useState("");

    const [showPage, setShowPage] = React.useState(false);



    const submitPlacementPreparationFaculty = () => {
        //code to add placement preparation faculty method call
        addPlacementPreparationFaculty(placementPreparationFacultyObj).then((id) => {
            setDialogOpen(true);
            setPlacementPreparationFacultyObj(new PlacementPreparationFaculty());
            setPlacementPreparationFacultyErr({});
        }, (exception) => {
            console.log("PlacementPreparationFacultyErr: " + JSON.stringify(exception));
            setPlacementPreparationFacultyErr(exception);
            setDialogOpen(false);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }



    const handleTabClosing = () => {
    //    alert("handle");
    }


    const alertUser = (event) => {
        event.preventDefault();
        event.returnValue = 'Ares you sure';
    }

    React.useEffect(() => {
        if (isUserAdmin() == false) redirectToLoginPage();
        else setShowPage(true);
        getPrograms().then((programList) => {
            setPrograms(programList);
        });
    }, []);
    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem lg={10}>
                    <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Add Placement Preparation Faculty</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Name"
                                        onInput={((ev) => {
                                            placementPreparationFacultyObj.setName(ev.target.value);
                                            setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementPreparationFacultyObj.getName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.PRIMARY_EMAIL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Email"
                                        onChange={((ev) => {
                                            placementPreparationFacultyObj.setPrimaryEmail(ev.target.value);
                                            setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementPreparationFacultyObj.getPrimaryEmail()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.PRIMARY_MOBILE_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Mobile No."
                                        onChange={((ev) => {
                                            placementPreparationFacultyObj.setPrimaryMobileNumber(ev.target.value);
                                            setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementPreparationFacultyObj.getPrimaryMobileNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.PROGRAM_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Program"
                                        menulist={programs}
                                        onChange={(ev) => {
                                            //alert(ev.target.value);
                                            getBranches(ev.target.value).then((branchList) => {
                                                //  alert(JSON.stringify(branchList));
                                                setBranches(branchList);
                                                setBranchDisable(false);
                                                setBranchTmpBgColor(enabledColor);
                                                placementPreparationFacultyObj.setProgramId(ev.target.value);
                                                setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                                //      setTmp(ev.target.value)
                                            });
                                        }}
                                    //defaultValue={placementPreparationFacultyObj.getProgramId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.BRANCH_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Branch"
                                        menulist={branches}
                                        disabled={branchDisable}
                                        style={{ background: branchTmpBgColor }}
                                        onChange={((ev) => {
                                            placementPreparationFacultyObj.setBranchId(ev.target.value);
                                            setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                            //  setTmp(ev.target.value)
                                        })}
                                    //defaultValue={placementPreparationFacultyObj.getBranchId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={12} xl={4}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={placementPreparationFacultyErr.GENDER_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Select the gender by which you identified</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        rowmode={true}
                                        options={gender}
                                        onChange={((ev) => {
                                            placementPreparationFacultyObj.setGender(ev.target.value);
                                            setPlacementPreparationFacultyObj(placementPreparationFacultyObj);
                                            setTmp(ev.target.value)
                                        })}
                                        checkvalue={placementPreparationFacultyObj.getGender()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={submitPlacementPreparationFaculty}
                            >
                                Add
                            </Button>
                        </ContainerItem>
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>}
            <DialogWrapper
                message={"Placement Preparation Faculty Added Successfully!"}
                buttonText={"Ok"}
                open={dialogOpen}
                onClick={(ev) => {
                    if (dialogOpen == true) setDialogOpen(false);
                    else setDialogOpen(true);
                    ev.preventDefault();
                    window.location.reload(false);
                }} />
        </div>
    )
}