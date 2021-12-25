import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper } from '../../../../theme/global/components/ContainerComponents';
import { gender } from '../../../../app-constants/Gender';
import Button from '@material-ui/core/Button';
import PlacementCoordinator from '../../../../domain/PlacementCoordinator';
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
 * @param {*} placementCoordinatorObj 
 * @returns promise
 */
 const addPlacementCoordinator = (placementCoordinatorObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(placementCoordinatorObj)
        };
        fetch("/api/placement-coordinator/add-placement-coordinator", requestOption).then((response) => {
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
 * Placement Coordinator add form component
 */
export const PlacementCoordinatorAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const disabledColor = "#ddd";
    const enabledColor = "#fff";


    const [programs, setPrograms] = React.useState([]);
    const [branches, setBranches] = React.useState([]);

    const [branchDisable, setBranchDisable] = React.useState(true);

    const [branchTmpBgColor, setBranchTmpBgColor] = React.useState(disabledColor);

    const [placementCoordinatorObj, setPlacementCoordinatorObj] = React.useState(new PlacementCoordinator());

    const [placementCoordinatorErr,setPlacementCoordinatorErr] = React.useState({});

    const [dialogOpen,setDialogOpen] =  React.useState(false);

    const [tmp,setTmp] = React.useState("");
    const [showPage, setShowPage] = React.useState(false);



    const submitPlacementCoordinator = () => {
        //code to add placement coordinator method call
        addPlacementCoordinator(placementCoordinatorObj).then((id) => {
            setDialogOpen(true);
            setPlacementCoordinatorObj(new PlacementCoordinator());
            setPlacementCoordinatorErr({});
        }, (exception) => {
            console.log("PlacementCoordinatorErr: " + JSON.stringify(exception));
            setPlacementCoordinatorErr(exception);
            setDialogOpen(false);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }


    
    const handleTabClosing = () => {
      //  alert("handle");
    }


    const alertUser = (event) => {
        event.preventDefault();
        event.returnValue = 'Ares you sure';
    }

    React.useEffect(() => {
        if(isUserAdmin()==false) redirectToLoginPage();
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
                            <h1>Add Placement Coordinator</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Name"
                                        onInput={((ev) => {
                                            placementCoordinatorObj.setName(ev.target.value);
                                            setPlacementCoordinatorObj(placementCoordinatorObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementCoordinatorObj.getName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.PRIMARY_EMAIL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Email"
                                        onChange={((ev) => {
                                            placementCoordinatorObj.setPrimaryEmail(ev.target.value);
                                            setPlacementCoordinatorObj(placementCoordinatorObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementCoordinatorObj.getPrimaryEmail()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.PRIMARY_MOBILE_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Mobile No."
                                        onChange={((ev) => {
                                            placementCoordinatorObj.setPrimaryMobileNumber(ev.target.value);
                                            setPlacementCoordinatorObj(placementCoordinatorObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={placementCoordinatorObj.getPrimaryMobileNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.PROGRAM_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Program"
                                        menulist={programs}
                                        onChange={(ev) => {
                                          //  alert(ev.target.value);
                                            getBranches(ev.target.value).then((branchList) => {
                                               // alert(JSON.stringify(branchList));
                                                setBranches(branchList);
                                                setBranchDisable(false);
                                                setBranchTmpBgColor(enabledColor);
                                                placementCoordinatorObj.setProgramId(ev.target.value);
                                                setPlacementCoordinatorObj(placementCoordinatorObj);
                                          //      setTmp(ev.target.value)
                                            });
                                        }}
                                        //defaultValue={placementCoordinatorObj.getProgramId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.BRANCH_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Branch"
                                        menulist={branches}
                                        disabled={branchDisable}
                                        style={{ background: branchTmpBgColor }}
                                        onChange={((ev) => {
                                            placementCoordinatorObj.setBranchId(ev.target.value);
                                            setPlacementCoordinatorObj(placementCoordinatorObj);
                                          //  setTmp(ev.target.value)
                                        })}
                                        //defaultValue={placementCoordinatorObj.getBranchId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={12} xl={4}>
                            <FormGroup>
                            <FormErrorLabelWrapper errorMessage={placementCoordinatorErr.GENDER_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Select the gender by which you identified</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        rowmode={true}
                                        options={gender}
                                        onChange={((ev) => {
                                            placementCoordinatorObj.setGender(ev.target.value);
                                            setPlacementCoordinatorObj(placementCoordinatorObj);
                                            setTmp(ev.target.value)
                                        })}
                                        checkvalue={placementCoordinatorObj.getGender()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={submitPlacementCoordinator}
                            >
                                Add
                            </Button>
                        </ContainerItem>
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>}
            <DialogWrapper 
                message={"Placement Coordinator Added Successfully!"}
                buttonText={"Ok"}
                open={dialogOpen} onClick={(ev)=>{
                if(dialogOpen==true) setDialogOpen(false);
                else setDialogOpen(true);
                ev.preventDefault();
                window.location.reload(false);
            }}/>
        </div>
    )
}