import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper, OutlinedMutliSelectWrapper, ChipInputWrapper } from '../../../theme/global/components/ContainerComponents';
import { gender } from '../../../app-constants/Gender';
import Button from '@material-ui/core/Button';
import Drive from '../../../domain/Drive';
import { yesNoOption } from '../../../app-constants/YesNoOption';
import { jobVacancy } from '../../../app-constants/JobVacancy';
import driveStyles from '../css/DriveStyle';
import { driveForGender, driveForGraduation } from '../../../app-constants/DriveFor';
import { isUserPlacementCoordinator, isUserAdmin, redirectToLoginPage } from '../../auth/AuthService';

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
 * @param {*} driveObj 
 * @returns promise
 */
const addDrive = (driveObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(driveObj)
        };
        fetch("/api/drive/add-drive", requestOption).then((response) => {
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
export const DriveAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
        driveStyles: driveStyles()
    };


    const [programs, setPrograms] = React.useState([]);
    const [branches, setBranches] = React.useState([]);

    const [driveObj, setDriveObj] = React.useState(new Drive());

    const [driveErr, setDriveErr] = React.useState({});

    const [dialogOpen, setDialogOpen] = React.useState(false);

    const [tmp, setTmp] = React.useState("");

    const [selectedPrograms, setSelectedPrograms] = React.useState([]);
    const [selectedBranches, setSelectedBranches] = React.useState([]);

    const [formCloseDate, setFormCloseDate] = React.useState("1997-02-06");
    const [formCloseTime, setFormCloseTime] = React.useState("23:59");

    const [showPage,setShowPage]=React.useState(false);

    const submitDrive = async () => {
        if(isUserAdmin()) driveObj.setUploadedById(356);
        await addDrive(driveObj).then((id) => {
            setDialogOpen(true);
            setDriveObj(new Drive());
            setDriveErr({});
        }, (exception) => {
            console.log("DriveErr: " + JSON.stringify(exception));
            setDriveErr(exception);
            setDialogOpen(false);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }

    const submitDriveHelper = () => {
        let placementCoordinatorId = getPlacementCoordinatorId();
        driveObj.setUploadedById(placementCoordinatorId);
        setDriveObj(driveObj);
        submitDrive();
    }

    const getPlacementCoordinatorId = () => {
        //Need to add code to fetch actual user id
        return 1;
    }



    const handleTabClosing = () => {
       // alert("handle");
    }


    const alertUser = (event) => {
        event.preventDefault();
        event.returnValue = 'Ares you sure';
    }

    React.useEffect(() => {

        if (isUserAdmin() == false && isUserPlacementCoordinator()==false) redirectToLoginPage();
        else setShowPage(true);
        getPrograms().then((programList) => {
            setPrograms(programList);
        });
        getBranches(0).then((branchList) => {
            setBranches(branchList);
        });
    }, []);
    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem lg={10}>
                    <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} style={{ marginBottom: "-70px" }}>
                            <h1>Add Drive</h1>
                        </ContainerItem>
                        <WrapperContainer boxShadow={0} spacing={5} className={cssStyles.driveStyles.formContainer}>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.COMPANY_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Company"
                                            onInput={((ev) => {
                                                driveObj.setCompany(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getCompany()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.DRIVE_NAME_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Drive Name"
                                            onChange={((ev) => {
                                                driveObj.setDriveName(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getDriveName()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={12}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.VALID_PROGRAM_LIST_ERR} />
                                        <OutlinedMutliSelectWrapper
                                            label="Valid Programs"
                                            menulist={programs}
                                            onChange={(ev) => {
                                                const {
                                                    target: { value },
                                                } = ev;
                                                setSelectedPrograms(
                                                    // On autofill we get a the stringified value.
                                                    typeof value === 'string' ? value.split(',') : value,
                                                );
                                                driveObj.setValidPrograms(ev.target.value);
                                            }}
                                            selectedValue={selectedPrograms}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={12}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.VALID_BRANCH_LIST_ERR} />
                                        <OutlinedMutliSelectWrapper
                                            label="Valid Branches"
                                            menulist={branches}
                                            onChange={(ev) => {
                                                const {
                                                    target: { value },
                                                } = ev;
                                                setSelectedBranches(
                                                    // On autofill we get a the stringified value.
                                                    typeof value === 'string' ? value.split(',') : value,
                                                );
                                                driveObj.setValidBranches(ev.target.value);
                                            }}
                                            selectedValue={selectedBranches}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.MIN_PACKAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Min Package"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setMinPackage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getMinPackage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.MAX_PACKAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Max Package"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setMaxPackage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getMaxPackage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.LAST_DATE_TO_APPLY_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Form Close Date"
                                            type="date"
                                            onChange={((ev) => {
                                                setFormCloseDate(ev.target.value);
                                                let lastDateToApply = formCloseDate + "T" + formCloseTime;
                                                driveObj.setLastDateToApply(lastDateToApply);
                                                setDriveObj(driveObj);
                                            })}
                                            value={formCloseDate}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.PRIMARY_MOBILE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Form Close Time"
                                            type="time"
                                            onChange={((ev) => {
                                                setFormCloseTime(ev.target.value);
                                                let lastDateToApply = formCloseDate + "T" + formCloseTime;
                                                driveObj.setLastDateToApply(lastDateToApply);
                                                setDriveObj(driveObj);
                                            })}
                                            value={formCloseTime}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={3}>
                                <FormGroup>
                                    <FormErrorLabelWrapper errorMessage={driveErr.VACANCY_TYPE_ERR} />
                                    <FormLabel className={cssStyles.globalStyles.formQuestion}>Placed For</FormLabel>
                                    <FormControl>
                                        <RadioGroupWrapper
                                            options={jobVacancy}
                                            onChange={((ev) => {
                                                if ((ev.target.value) == 'F') {
                                                    driveObj.setIsFullTimeVaccancy(true);
                                                    driveObj.setIsInternshipVaccancy(false);
                                                }
                                                else if ((ev.target.value) == 'I') {
                                                    driveObj.setIsFullTimeVaccancy(false);
                                                    driveObj.setIsInternshipVaccancy(true);
                                                } else if ((ev.target.value) == 'FI') {
                                                    driveObj.setIsFullTimeVaccancy(true);
                                                    driveObj.setIsInternshipVaccancy(true);
                                                }
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            checkvalue={(driveObj.getIsFullTimeVaccancy() && driveObj.getIsInternshipVaccancy()) ? 'FI' : (driveObj.getIsFullTimeVaccancy() ? 'F' : (driveObj.getIsInternshipVaccancy() ? 'I' : null))}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={3}>
                                <FormGroup>
                                    <FormErrorLabelWrapper errorMessage={driveErr.DRIVE_FOR_GENDER_ERR} />
                                    <FormLabel className={cssStyles.globalStyles.formQuestion}>Drive For</FormLabel>
                                    <FormControl>
                                        <RadioGroupWrapper
                                            options={driveForGender}
                                            onChange={((ev) => {
                                                if ((ev.target.value) == 'G') {
                                                    driveObj.setIsOnlyForGirls(true);
                                                    driveObj.setIsOnlyForBoys(false);
                                                }
                                                else if ((ev.target.value) == 'B') {
                                                    driveObj.setIsOnlyForGirls(false);
                                                    driveObj.setIsOnlyForBoys(true);
                                                } else if ((ev.target.value) == 'BG') {
                                                    driveObj.setIsOnlyForGirls(true);
                                                    driveObj.setIsOnlyForBoys(true);
                                                }
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            checkvalue={(driveObj.getIsOnlyForGirls() && driveObj.getIsOnlyForBoys()) ? 'BG' : (driveObj.getIsOnlyForBoys() ? 'B' : (driveObj.getIsOnlyForGirls() ? 'G' : null))}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>

                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={3}>
                                <FormGroup>
                                    <FormErrorLabelWrapper errorMessage={driveErr.DRIVE_FOR_GRADUATION_ERR} />
                                    <FormLabel className={cssStyles.globalStyles.formQuestion}>Drive For</FormLabel>
                                    <FormControl>
                                        <RadioGroupWrapper
                                            options={driveForGraduation}
                                            onChange={((ev) => {
                                                if ((ev.target.value) == 'UG') {
                                                    driveObj.setOnlyForUgStudents(true);
                                                    driveObj.setOnlyForPgStudents(false);
                                                    driveObj.setForBothUgPgStudents(false);
                                                }
                                                else if ((ev.target.value) == 'PG') {
                                                    driveObj.setOnlyForUgStudents(false);
                                                    driveObj.setOnlyForPgStudents(true);
                                                    driveObj.setForBothUgPgStudents(false);
                                                } else if ((ev.target.value) == 'UGPG') {
                                                    driveObj.setOnlyForUgStudents(false);
                                                    driveObj.setOnlyForPgStudents(false);
                                                    driveObj.setForBothUgPgStudents(true);
                                                }
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            checkvalue={driveObj.getOnlyForUgStudents() ? 'UG' : (driveObj.getOnlyForPgStudents() ? 'PG' : (driveObj.getForBothUgPgStudents() ? 'UGPG' : null))}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                        </WrapperContainer>


                        <WrapperContainer boxShadow={0} spacing={5} className={cssStyles.driveStyles.formContainer}>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_10TH_CGPA_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum 10th CGPA"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequired10thCGPA(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequired10thCGPA()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_10TH_PERCENTAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum 10th %"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequired10thPercentage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequired10thPercentage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_12TH_CGPA_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum 12th CGPA"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequired12thCGPA(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequired12thCGPA()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_12TH_PERCENTAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum 12th %"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequired12thPercentage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequired12thPercentage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_UG_CGPA_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum UG CGPA"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequiredUGCGPA(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequiredUGCGPA()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_UG_PERCENTAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum UG %"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequiredUGPercentage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequiredUGPercentage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_PG_CGPA_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum PG CGPA"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequiredPGCGPA(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequiredPGCGPA()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.REQUIRED_PG_PERCENTAGE_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Minimum PG %"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setRequiredPGPercentage(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getRequiredPGPercentage()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.MINIMUM_BACKLOG_ALLOWED_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Min Backlogs Allowed"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setMinimumBacklogsAllowed(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getMinimumBacklogsAllowed()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={driveErr.MINIMUM_GAP_ALLOWED_IN_EDUCATION_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Min Gap Allowed In Education"
                                            type="number"
                                            onChange={((ev) => {
                                                driveObj.setMinGapAllowInEducation(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getMinGapAllowInEducation()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                        </WrapperContainer>

                        <WrapperContainer boxShadow={0} spacing={5} className={cssStyles.driveStyles.formContainer}>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                                <FormGroup>
                                    <FormErrorLabelWrapper errorMessage={driveErr.UG_PASSOUT_YEAR_ALLOW_ERR} />
                                    <FormControl>
                                        <ChipInputWrapper
                                            label="UG Passout Years Allowed"
                                            onChange={(chips) => {
                                                console.log(chips);
                                                driveObj.setUgPassoutYearAllow(chips)
                                            }}
                                            defaultValue={driveObj.getUgPassoutYearAllow()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                                <FormGroup>
                                    <FormErrorLabelWrapper errorMessage={driveErr.PG_PASSOUT_YEAR_ALLOW_ERR} />
                                    <FormControl>
                                        <ChipInputWrapper
                                            label="PG Passout Years Allowed"
                                            onChange={(chips) => {
                                                console.log(chips);
                                                driveObj.setPgPassoutYearAllow(chips)
                                            }}
                                            defaultValue={driveObj.getPgPassoutYearAllow()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                        </WrapperContainer>

                        <WrapperContainer boxShadow={0} spacing={5} className={cssStyles.driveStyles.formContainer}>
                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                <FormGroup>
                                    <FormControl>
                                        <OutlinedTextFieldWrapper
                                            label="Other Details"
                                            multiline
                                            rows={10}
                                            onChange={((ev) => {
                                                driveObj.setOtherDetails(ev.target.value);
                                                setDriveObj(driveObj);
                                                setTmp(ev.target.value)
                                            })}
                                            value={driveObj.getOtherDetails()}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                        </WrapperContainer>


                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={submitDriveHelper}
                            >
                                Add
                            </Button>
                        </ContainerItem>
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>}
            <DialogWrapper open={dialogOpen}
                message={"Drive added sucessfully!"}
                buttonText={"Ok"}
                onClick={(ev) => {
                    if (dialogOpen == true) setDialogOpen(false);
                    else setDialogOpen(true);
                    ev.preventDefault();
                    window.location.reload(false);
                }} />
        </div>
    )
}