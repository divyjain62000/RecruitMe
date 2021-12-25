import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import Typography from '@material-ui/core/Typography';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper } from '../../../theme/global/components/ContainerComponents';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import { marksType } from '../../../app-constants/MarksType';
import { boardType } from '../../../app-constants/BoardType';
import Accordion from '@material-ui/core/Accordion';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import ExpandIcon from '@material-ui/icons/ExpandMoreOutlined';
import { gender } from '../../../app-constants/Gender';
import { yesNoOption } from '../../../app-constants/YesNoOption';
import Button from '@material-ui/core/Button';
import Student from '../../../domain/Student';
import Address from '../../../domain/Address';
import Parents from '../../../domain/Parents';
import Education from '../../../domain/Education';
import CircularProgress from '@material-ui/core/CircularProgress';
import Alert from '@mui/material/Alert';
import { Hidden } from '@material-ui/core';
import { render } from '@testing-library/react';
import { EditRounded } from '@material-ui/icons';
import { isUserStudent, redirectToLoginPage, getAuthenticateUserId } from '../../auth/AuthService';
import { BASE_URL } from '../../../app-constants/AppConstants';






/**
 * To get all countries
 * @returns promise
 */

const getCountries = () => {
    var promise = new Promise((resolve) => {
        fetch("/api/countries").then((response) => {
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

/**
 * To get all states based on country that user selected
 * @param {*} id 
 * @returns promise
 */
const getStates = (id) => {
    var promise = new Promise((resolve) => {
        fetch("/api/states/" + id).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                resolve(responseObj.content)
            })
            .catch(error => {
                console.error("Error while fetching states: " + error);
            })

    });
    return promise;
}

/**
 * To get all cities based on state that user selected
 * @param {*} id 
 * @returns promise
 */
const getCities = (id) => {
    var promise = new Promise((resolve) => {
        fetch("/api/cities/" + id).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                resolve(response.content);
            })
            .catch((error) => {
                console.error("Error while fetching cities: " + error);
            })
    });
    return promise;
}



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
 * To get all programs based on institute that user select
 * @param {*} id 
 * @returns promise
 */
const getStudentById = (studentId) => {
    var promise = new Promise((resolve) => {
        fetch("/api/student/get-student/" + studentId).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((response) => {
                //alert(JSON.stringify(response.result).getName());
                resolve(response.result);
            })
            .catch((error) => {
                console.log("Error while fetching institutes: " + error);
            })
    });
    return promise;
}




/**
 * To add student
 * @param {*} studentObj 
 * @returns promise
 */
const addStudent = (studentObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(studentObj)
        };
        fetch("/api/student/edit-student", requestOption).then((response) => {
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
 * To edit education
 * @param {*} educationObj 
 * @returns promise
 */
const editEducation = (educationObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(educationObj)
        };
        fetch("/api/edit-education", requestOption).then((response) => {
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
 * To edit address
 * @param {*} addressObj 
 * @returns promise
 */
const editAddress = (addressObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(addressObj)
        };
        fetch("/api/edit-address", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
                if (response.successful) {

                    resolve(JSON.stringify(response));
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
 * To edit parents
 * @param {*} parentsObj 
 * @returns promise
 */
const editParents = (parentsObj) => {

    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(parentsObj)
        };
        fetch("/api/edit-parents", requestOption).then((response) => {
            if (response.ok) return response.json();
        })
            .then((response) => {
                if (response.successful) {

                    resolve(JSON.stringify(response));
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



const totalCard = 7;

/**
 * Student registration form component
 */
export const StudentEditFormComponent = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const disabledColor = "#ddd";
    const enabledColor = "#fff";


    const [cities, setCities] = React.useState([]);
    const [states, setStates] = React.useState([]);
    const [countries, setCountries] = React.useState([]);

    const [cityDisable, setCityDisable] = React.useState(true);
    const [stateDisable, setStateDisable] = React.useState(true);

    const [cityTmpBgColor, setCityTmpBgColor] = React.useState(disabledColor);
    const [stateTmpBgColor, setStateTmpBgColor] = React.useState(disabledColor);

    const [nextOrSaveButtton, setNextOrSaveButton] = React.useState("Next");

    const [termsAndConditionChecked, setTermsAndConditionChecked] = React.useState(false);

    const [programs, setPrograms] = React.useState([]);
    const [branches, setBranches] = React.useState([]);

    const [branchDisable, setBranchDisable] = React.useState(true);

    const [branchTmpBgColor, setBranchTmpBgColor] = React.useState(disabledColor);

    const [currentCardIndex, setCurrentCardIndex] = React.useState(-1);

    const [showForm, setShowForm] = React.useState(false);

    const [termsAndConditionErr, setTermsAndConditionErr] = React.useState("");

    const [studentObj, setStudentObj] = React.useState(new Student());
    const [parentsObj, setParentsObj] = React.useState(new Parents());
    const [addressObj, setAddressObj] = React.useState(new Address());
    const [educationObj, setEducationObj] = React.useState(new Education());

    const [educationId, setEducationId] = React.useState(null);
    const [parentsId, setParentsId] = React.useState(null);
    const [addressId, setAddressId] = React.useState(null);

    const [studentErr, setStudentErr] = React.useState({});
    const [educationErr, setEducationErr] = React.useState({});
    const [addressErr, setAddressErr] = React.useState({});
    const [parentsErr, setParentsErr] = React.useState({});

    const [errOccured, setErrOccured] = React.useState(false);

    const [tmp, setTmp] = React.useState("");
    const [disabilityTmp, setDisabilityTmp] = React.useState("");
    const [indianTmp, setIndianTmp] = React.useState("");
    const [criminalTmp, setCriminalTmp] = React.useState("");



    const [dialogOpen, setDialogOpen] = React.useState(false);

    const [cpassword, setCPassword] = React.useState("");
    const [cpasswordErr, setCPasswordErr] = React.useState("");

    const [showPage, setShowPage] = React.useState(false);


    const nextCardOnClick = async () => {
        let flag = true;

        //Call function to save parents details
        if (currentCardIndex == 1) {
            await editParents(parentsObj).then(() => {
                //if success full then necessary action need to take if have any
                flag = true;
                setParentsErr({});
            }, (exception) => {
                console.log("ParentsErr: " + exception);
                setParentsErr(exception);
                flag = false;
            }).catch((error) => {

            });
        }

        //Call function to save address details
        if (currentCardIndex == 2) {
            await editAddress(addressObj).then(() => {
                //if success full then necessary action need to take if have any
                flag = true;
                setAddressErr({});
            }, (exception) => {
                console.log("AddressErr: " + JSON.stringify(exception));
                setAddressErr(exception);
                flag = false;
            }).catch((error) => {

            });
        }

        //Call function to save education details
        if (currentCardIndex == 3) {
            //   alert("education obj save");

            await editEducation(educationObj).then(() => {
                //if success full then necessary action need to take if have any
                flag = true;
                setEducationErr({});
            }, (exception) => {
                console.log("EducationErr: " + JSON.stringify(exception));
                setEducationErr(exception);
                flag = false;
                flag = true;
            }).catch((error) => {

            });
        }
        if (flag) setCurrentCardIndex(currentCardIndex + 1);
        else setCurrentCardIndex(currentCardIndex);
        if (currentCardIndex == totalCard - 2) setNextOrSaveButton("Submit");
        if (currentCardIndex == totalCard - 1) {
            if (termsAndConditionChecked == false) setTermsAndConditionErr("Accept all the terms and condition firs");
            else editStudent();
            setCurrentCardIndex(currentCardIndex);
        }

    }

    const backCardOnClick = () => {
        setCurrentCardIndex(currentCardIndex - 1);
        setNextOrSaveButton("Next");
        setTermsAndConditionErr("");
    }

    const termsAndConditionOnClick = (ev) => {
        setTermsAndConditionChecked(ev.target.checked);
        if (termsAndConditionChecked == false) setTermsAndConditionErr("");
    }


    const editStudent = () => {
        studentObj.setEducationId(educationObj.getId());
        studentObj.setParentsId(parentsObj.getId());
        studentObj.setAddressId(addressObj.getId());
       // alert(studentObj.getIsAnyCriminalCase());
        if (cpasswordErr.length == 0) {
            setErrOccured(false);
            addStudent(studentObj).then((id) => {
                // studentId=id;
                setErrOccured(false);
                setDialogOpen(true);
            }, (exception) => {
                console.log("StudentErr: " + JSON.stringify(exception));
                setStudentErr(exception);
                setErrOccured(true);
                setDialogOpen(false);
            }).catch((error) => {
                setDialogOpen(false);
            });
        } else {
            setErrOccured(true);
        }
    }





    React.useEffect(() => {
        if (isUserStudent() == false) redirectToLoginPage();
        else setShowPage(true);
        let studentId = getStudentId();
        getCountries().then((countryList) => {
            setCountries(countryList);
        });
        getPrograms().then((programList) => {
            setPrograms(programList);
        });
        getStudentById(studentId).then((stud) => {
            console.log(JSON.stringify(stud));
            setStudentObj(Object.assign(new Student(), stud));
            setEducationObj(convertToEducation(stud.education));
            setParentsObj(Object.assign(new Parents(), stud.parents));
            setAddressObj(convertToAddress(stud.address));
            setEducationId(stud.education.id);
            setAddressId(stud.address.id);
            setParentsId(stud.parents.id);
            setCurrentCardIndex(0);
            setShowForm(true);
        });
    }, []);


    const getStudentId = () => {
        return getAuthenticateUserId();
    }

    const convertToAddress = (address) => {
        let newAddressObj = new Address();
        getStates(address.country.id).then((stateList) => {
            setStates(stateList);
        });
        getCities(address.state.id).then((cityList) => {
            setCities(cityList);
        });
        newAddressObj.setId(address.id);
        newAddressObj.setAddressLine1(address.addressLine1);
        newAddressObj.setAddressLine2(address.addressLine2);
     //   alert(address.country.id);
        newAddressObj.setCountryId(address.country.id);
        newAddressObj.setStateId(address.state.id);
        newAddressObj.setCityId(address.city.id);
        newAddressObj.setPinCode(address.pinCode);
        return newAddressObj;
    }

    const convertToEducation = (education) => {
        let edu = Object.assign(new Education(), education)
        edu.setUgProgramId(edu.ugProgram.id);
        edu.setUgBranchId(edu.ugBranch.id);
        edu.setPgProgramId(edu.pgProgram != null ? edu.pgProgram.id : null);
        edu.setPgBranchId(edu.pgBranch != null ? edu.pgBranch.id : null);
        getBranches(edu.ugProgram.id).then((branchList) => {
            setBranches(branchList);
        });
        return edu;
    }


    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage && showForm && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem lg={10}>
                    <WrapperContainer spacing={5} className={cssStyles.globalStyles.formTitleHeader}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12} className={cssStyles.globalStyles.formTitle}>
                            Edit Profile
                        </ContainerItem>
                    </WrapperContainer>

                    {errOccured && <Alert severity="error">There is some field left or invalid data you fill go back and correct you details</Alert>}

                    {currentCardIndex == 0 && <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>General Details</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Name"
                                        disabled={false}
                                        onChange={((ev) => {
                                            console.log(ev.target.value);
                                            studentObj.setName(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value);
                                        })}
                                        value={studentObj.getName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={3} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.MOU_COMAPANY_REFERENCE_ID_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="MOU Company Id"
                                        disabled={true}
                                        onChange={((ev) => {
                                            studentObj.setMouCompanyReferenceId(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)

                                        })}
                                        value={studentObj.getMouCompanyReferenceId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={3} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.ENROLLEMENT_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Enrollment Number"
                                        disabled={true}
                                        onChange={((ev) => {
                                            studentObj.setEnrollmentNumber(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getEnrollmentNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.PRIMARY_EMAIL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Email"
                                        disabled={true}
                                        onChange={((ev) => {
                                            studentObj.setPrimaryEmail(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getPrimaryEmail()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.SECONDARY_EMAIL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Secondary Email"
                                        onChange={((ev) => {
                                            studentObj.setSecondaryEmail(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getSecondaryEmail()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.PRIMARY_MOBILE_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Primary Mobile No."
                                        disabled={true}
                                        onChange={((ev) => {
                                            studentObj.setPrimaryMobileNumber(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getPrimaryMobileNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={studentErr.SECONDARY_MOBILE_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Secondary Mobile No."
                                        onChange={((ev) => {
                                            studentObj.setSecondaryMobileNumber(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getSecondaryMobileNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 1 && <WrapperContainer id="card-1" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={4} xl={6}>
                            <h1>Parents Details</h1>
                        </ContainerItem>
                        <Hidden only={['xs', 'sm', 'md']}>
                            <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={4} xl={6}>
                                <h1>Parents Details</h1>
                            </ContainerItem>
                            <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={4} xl={6}>
                                <h1>Parents Details</h1>
                            </ContainerItem>
                        </Hidden>
                        <ContainerItem xs={12} sm={6} md={6} lg={4} xl={4}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={parentsErr.FATHER_NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Father Name"
                                        onChange={((ev) => {
                                            parentsObj.setFatherName(ev.target.value);
                                            setParentsObj(parentsObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={parentsObj.getFatherName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={4} xl={4}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={parentsErr.MOTHER_NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Mother Name"
                                        onChange={((ev) => {
                                            parentsObj.setMotherName(ev.target.value);
                                            setParentsObj(parentsObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={parentsObj.getMotherName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={4} xl={4}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={parentsErr.MOBILE_NUMBER_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Parents Mobile No."
                                        onChange={((ev) => {
                                            parentsObj.setMobileNumber(ev.target.value);
                                            setParentsObj(parentsObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={parentsObj.getMobileNumber()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 2 && <WrapperContainer id="card-2" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Address Details</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={addressErr.ADDRESS_LINE1_ERR} />
                                    <OutlinedTextFieldWrapper
                                        multiline
                                        rows={5}
                                        label="Address Line 1"
                                        onChange={((ev) => {
                                            addressObj.setAddressLine1(ev.target.value);
                                            setAddressObj(addressObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={addressObj.getAddressLine1()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={""} />
                                    <OutlinedTextFieldWrapper
                                        multiline
                                        rows={5}
                                        label="Address Line 2"
                                        onChange={((ev) => {
                                            addressObj.setAddressLine2(ev.target.value);
                                            setAddressObj(addressObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={addressObj.getAddressLine2()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={addressErr.COUNTRY_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Country"
                                        menulist={countries}
                                        onChange={(ev) => {
                                            getStates(ev.target.value).then((stateList) => {
                                                setStates(stateList);
                                                addressObj.setCountryId(ev.target.value);
                                                setAddressObj(addressObj);
                                            });
                                        }}
                                        defaultValue={addressObj.getCountryId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={addressErr.STATE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="State"
                                        menulist={states}
                                        onChange={(ev) => {
                                            getCities(ev.target.value).then((cityList) => {
                                                setCities(cityList);
                                                addressObj.setStateId(ev.target.value);
                                                setAddressObj(addressObj);
                                            });
                                        }}
                                        defaultValue={addressObj.getStateId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={addressErr.CITY_ERR} />
                                    <OutlinedSelectWrapper
                                        label="City"
                                        menulist={cities}
                                        onChange={((ev) => {
                                            addressObj.setCityId(ev.target.value);
                                            setAddressObj(addressObj);
                                        })}
                                        defaultValue={addressObj.getCityId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={addressErr.PIN_CODE_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="PIN Code"
                                        onChange={((ev) => {
                                            addressObj.setPinCode(ev.target.value);
                                            setAddressObj(addressObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={addressObj.getPinCode()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 3 && <WrapperContainer id="card-3" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Education Details</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h2>UG Details</h2>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_COLLEGE_NAME_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="College Name"
                                        onChange={((ev) => {
                                            educationObj.setUgCollegeName(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getUgCollegeName()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_PROGRAM_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Program"
                                        menulist={programs}
                                        onChange={(ev) => {
                                            getBranches(ev.target.value).then((branchList) => {
                                                setBranches(branchList);
                                                setBranchDisable(false);
                                                setBranchTmpBgColor(enabledColor);
                                                educationObj.setUgProgramId(ev.target.value);
                                                setEducationObj(educationObj);
                                            });
                                        }}
                                        defaultValue={educationObj.getUgProgramId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_BRANCH_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Branch"
                                        menulist={branches}
                                        onChange={((ev) => {
                                            educationObj.setUgBranchId(ev.target.value);
                                            setEducationObj(educationObj);
                                        })}
                                        defaultValue={educationObj.getUgBranchId()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_MARKS_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Marks"
                                        onChange={((ev) => {
                                            educationObj.setUgMarks(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        defaultValue={educationObj.getUgMarks()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_MARKS_TYPE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Marks Type"
                                        menulist={marksType}
                                        onChange={((ev) => {
                                            educationObj.setUgMarksType(ev.target.value);
                                            setEducationObj(educationObj);

                                        })}
                                        defaultValue={educationObj.getUgMarksType()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={8} sm={8} md={4} lg={4} xl={4}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.UG_PASSOUT_YEAR_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Passout Year"
                                        onChange={((ev) => {
                                            educationObj.setUgPassoutYear(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getUgPassoutYear()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h2>12th Details</h2>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.HIGH_SCHOOL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="High School Name"
                                        onChange={((ev) => {
                                            educationObj.setHighSchool(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getHighSchool()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.HIGH_SCHOOL_BOARD_TYPE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="12th Board"
                                        menulist={boardType}
                                        onChange={((ev) => {
                                            educationObj.setHighSchoolBoard(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        defaultValue={educationObj.getHighSchoolBoard()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.HIGH_SCHOOL_PASSOUT_YEAR_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Passout Year"
                                        onChange={((ev) => {
                                            educationObj.setHighSchoolPassoutYear(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getHighSchoolPassoutYear()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.HIGH_SCHOOL_MARKS_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Marks"
                                        onChange={((ev) => {
                                            educationObj.setHighSchoolMarks(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getHighSchoolMarks()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.HIGH_SCHOOL_MARKS_TYPE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Marks Type"
                                        menulist={marksType}
                                        onChange={((ev) => {
                                            educationObj.setHighSchoolMarksType(ev.target.value);
                                            setEducationObj(educationObj);
                                        })}
                                        defaultValue={educationObj.getHighSchoolMarksType()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h2>10th Details</h2>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.SENIOR_SECONDARY_SCHOOL_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Senior Secondary School Name"
                                        onChange={((ev) => {
                                            educationObj.setSeniorSecondarySchool(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getSeniorSecondarySchool()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.SENIOR_SECONDARY_BOARD_TYPE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="10th Board"
                                        menulist={boardType}
                                        onChange={((ev) => {
                                            educationObj.setSeniorSecondarySchoolBoard(ev.target.value);
                                            setEducationObj(educationObj);
                                        })}
                                        defaultValue={educationObj.getSeniorSecondarySchoolBoard()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={8} sm={8} md={6} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.SENIOR_SECONDARY_SCHOOL_PASSOUT_YEAR_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Passout Year"
                                        className={cssStyles.globalStyles.selectMenu}
                                        onChange={((ev) => {
                                            educationObj.setSeniorSecondarySchoolPassoutYear(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getSeniorSecondarySchoolPassoutYear()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.SENIOR_SECONDARY_SCHOOL_MARKS_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Marks"
                                        onChange={((ev) => {
                                            educationObj.setSeniorSecondarySchoolMarks(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getSeniorSecondarySchoolMarks()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.SENIOR_SECONDARY_SCHOOL_MARKS_TYPE_ERR} />
                                    <OutlinedSelectWrapper
                                        label="Marks Type"
                                        menulist={marksType}
                                        onChange={((ev) => {
                                            educationObj.setSeniorSecondarySchoolMarksType(ev.target.value);
                                            setEducationObj(educationObj);
                                        })}
                                        defaultValue={educationObj.getSeniorSecondarySchoolMarksType()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 3 && <WrapperContainer spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Accordion elevation={0}>
                                <AccordionSummary expandIcon={<ExpandIcon style={{ color: colors.dark, width: "45px", height: "45px" }} />}>
                                    <h2>PG Details (Only for students enrolled in PG Courses)</h2>
                                </AccordionSummary>
                                <AccordionDetails>
                                    <WrapperContainer boxShadow={0} spacing={5}>
                                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"College name required"} />
                                                    <OutlinedTextFieldWrapper
                                                        label="College Name"
                                                        onChange={(ev) => {
                                                            educationObj.setPgCollegeName(ev.target.value);
                                                            setTmp(ev.target.value)
                                                        }}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"Program required"} />
                                                    <OutlinedSelectWrapper
                                                        label="Program"
                                                        menulist={programs}
                                                        onChange={(ev) => {
                                                            getBranches(ev.target.value).then((branchList) => {
                                                                setBranches(branchList);
                                                                setBranchDisable(false);
                                                                setBranchTmpBgColor(enabledColor);
                                                            });
                                                        }}
                                                        defaultValue={educationObj.getPgProgramId()}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={12} sm={12} md={6} lg={6} xl={6}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"Branch required"} />
                                                    <OutlinedSelectWrapper
                                                        label="Branch"
                                                        menulist={branches}
                                                        disabled={branchDisable}
                                                        style={{ background: branchTmpBgColor }}
                                                        onChange={(ev) => {
                                                            educationObj.setPgBranchId(ev.target.value);
                                                        }}
                                                        defaultValue={educationObj.getPgBranchId()}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"Marks required"} />
                                                    <OutlinedTextFieldWrapper
                                                        label="Marks"
                                                        onChange={((ev) => {
                                                            educationObj.setPgMarks(ev.target.value);
                                                            setEducationObj(educationObj);
                                                            setTmp(ev.target.value)
                                                        })}
                                                        value={educationObj.getPgMarks()}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={12} sm={12} md={6} lg={3} xl={3}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"Marks type required"} />
                                                    <OutlinedSelectWrapper
                                                        label="Marks Type"
                                                        menulist={marksType}
                                                        onChange={((ev) => {
                                                            educationObj.setPgMarksType(ev.target.value);
                                                            setEducationObj(educationObj);
                                                        })}
                                                        defaultValue={educationObj.getPgMarksType()}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={4} sm={4} md={2} lg={2} xl={2}>
                                            <FormGroup>
                                                <FormControl>
                                                    <br />
                                                    <br />
                                                    <Typography style={{ fontSize: "18px", textAlign: "right", color: colors.dark, fontWeight: "bold" }}>Passout Year</Typography>
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                        <ContainerItem xs={8} sm={8} md={4} lg={4} xl={4}>
                                            <FormGroup>
                                                <FormControl>
                                                    <FormErrorLabelWrapper errorMessage={"Passout Year required"} />
                                                    <OutlinedTextFieldWrapper
                                                        label="Passout Year"
                                                        onChange={((ev) => {
                                                            educationObj.setPgPassoutYear(ev.target.value);
                                                            setEducationObj(educationObj);
                                                            setTmp(ev.target.value)
                                                        })}
                                                        value={educationObj.getPgPassoutYear()}
                                                    />
                                                </FormControl>
                                            </FormGroup>
                                        </ContainerItem>
                                    </WrapperContainer>
                                </AccordionDetails>
                            </Accordion>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 3 && <WrapperContainer id="card-5" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h2>Other Educational Details</h2>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={educationErr.IS_ANY_EDUCATIONAL_GAP_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Q1.You have any educational gap?</FormLabel>
                                <FormControl>
                                    <RadioGroupWrapper
                                        options={yesNoOption}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'Y') educationObj.setIsAnyGapInEducation(true);
                                            else educationObj.setIsAnyGapInEducation(false);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        checkvalue={educationObj.getIsAnyGapInEducation() ? 'Y' : educationObj.getIsAnyGapInEducation() == false ? 'N' : null}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={educationErr.REASON_FOR_EDUCATION_GAP_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>If in above question you mark YES then provide details</FormLabel>
                                <FormControl>
                                    <OutlinedTextFieldWrapper
                                        onChange={((ev) => {
                                            educationObj.setReasonForEducationalGap(ev.target.value)
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getReasonForEducationalGap()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={educationErr.NUMBER_OF_CURRENT_BACKLOG_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Number of Backlogs"
                                        onChange={((ev) => {
                                            educationObj.setNumberOfCurrentBacklog(ev.target.value);
                                            setEducationObj(educationObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={educationObj.getNumberOfCurrentBacklog()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 4 && <WrapperContainer id="card-4" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={3} xl={3}>
                            <h1>Gender Details</h1>
                        </ContainerItem>
                        <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={3} xl={3}>
                            <h1>Gender Details</h1>
                        </ContainerItem>
                        <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={3} xl={3}>
                            <h1>Gender Details</h1>
                        </ContainerItem>
                        <ContainerItem style={{ visibility: "hidden" }} xs={12} sm={12} md={12} lg={3} xl={3}>
                            <h1>Gender Details</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={6} md={6} lg={12} xl={4}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={studentErr.GENDER_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Select the gender by which you identified</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        options={gender}
                                        onChange={((ev) => {
                                            studentObj.setGender(ev.target.value);
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        checkvalue={studentObj.getGender()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 5 && <WrapperContainer id="card-5" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Other Details</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={studentErr.IS_INDIAN_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Q1. Are you Indian citizen?</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        options={yesNoOption}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'Y') studentObj.setIsIndian(true);
                                            else studentObj.setIsIndian(false);
                                            setStudentObj(studentObj);
                                            setIndianTmp(ev.target.value);
                                        })}
                                        checkvalue={studentObj.getIsIndian() ? 'Y' : studentObj.getIsIndian() == false ? 'N' : null}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={studentErr.IS_ANY_CRIMINAL_CASE_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Q2. Are you involve in any criminal case?</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        options={yesNoOption}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'Y') studentObj.setIsAnyCriminalCase(true);
                                            else studentObj.setIsAnyCriminalCase(false);
                                            setStudentObj(studentObj);
                                            setCriminalTmp(ev.target.value);
                                        })}
                                        checkvalue={studentObj.getIsAnyCriminalCase() ? 'Y' : (studentObj.getIsAnyCriminalCase() == false ? 'N' : null)}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>If in above question you mark YES then provide details</FormLabel>
                                <FormControl>
                                    <OutlinedTextFieldWrapper
                                        onChange={((ev) => {
                                            studentObj.setCriminalCaseDescription(ev.target.value)
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value);
                                        })}
                                        value={studentObj.getCriminalCaseDescription()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={studentErr.IS_DISABILITY_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Q3. You have any disability?</FormLabel>
                                <FormControl>
                                    < RadioGroupWrapper
                                        options={yesNoOption}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'Y') studentObj.setIsDisability(true);
                                            else studentObj.setIsDisability(false);
                                            setStudentObj(studentObj);
                                            setDisabilityTmp(ev.target.value);
                                            setTmp(ev.target.value);
                                        })}
                                        checkvalue={studentObj.getIsDisability() ? 'Y' : (studentObj.getIsDisability() == false ? 'N' : null)}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>If in above question you mark YES then provide details</FormLabel>
                                <FormControl>
                                    <OutlinedTextFieldWrapper
                                        onChange={((ev) => {
                                            studentObj.setDisability(ev.target.value)
                                            setStudentObj(studentObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={studentObj.getDisability()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                    </WrapperContainer>}

                    {currentCardIndex == 6 && <WrapperContainer spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>I hereby declare that all the details metioned by me in form is correct. If any thing is wrong then I am responsibel for it</FormLabel>
                            </FormGroup>
                            <FormErrorLabelWrapper errorMessage={termsAndConditionErr} />
                        </ContainerItem>

                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormLabel style={{visibility: "hidden"}} className={cssStyles.globalStyles.formQuestion}>I hereby declare that all the details metioned by me in form is correct. If any thing is wrong then I am responsibel for it</FormLabel>
                                <CheckboxWrapper
                                    onClick={termsAndConditionOnClick}
                                    checked={termsAndConditionChecked}
                                />
                            </FormGroup>
                            <FormErrorLabelWrapper errorMessage={termsAndConditionErr} />
                        </ContainerItem>

                    </WrapperContainer>}

                    <WrapperContainer spacing={5} className={cssStyles.globalStyles.formButtonContainer}>
                        <ContainerItem xs={10} sm={10} md={6} lg={6} xl={6}>
                            {currentCardIndex > 0 && <Button
                                className={cssStyles.globalStyles.formButtonLeft}
                                variant="contained"
                                onClick={backCardOnClick}
                            >
                                Back
                            </Button>}
                        </ContainerItem>
                        {currentCardIndex <= (totalCard - 1) && <ContainerItem xs={8} sm={8} md={6} lg={6} xl={6}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={nextCardOnClick}
                            >
                                {nextOrSaveButtton}
                            </Button>
                        </ContainerItem>}
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>}
            <DialogWrapper
                title={"Success"}
                message={"Details edited successfully!"}
                buttonText={"Ok"}
                contentCenter={true}
                verifiedLogo={true}
                open={dialogOpen}
                closeAction={(ev) => {
                    setDialogOpen(false);
                    window.location.reload(true);
                }} />
        </div>
    )
}