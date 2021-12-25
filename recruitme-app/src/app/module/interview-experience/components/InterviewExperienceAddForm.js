import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, OutlinedSelectWrapper, RadioGroupWrapper, CheckboxWrapper, DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import { FormErrorLabelWrapper } from '../../../theme/global/components/ContainerComponents';
import { gender } from '../../../app-constants/Gender';
import Button from '@material-ui/core/Button';
import InterviewExperience from '../../../domain/InterviewExperience';
import { searchForGraduation } from '../../../app-constants/UGPGSearch';
import { jobVacancy } from '../../../app-constants/JobVacancy';
import { getAuthenticateUserId, isUserStudent, redirectToLoginPage } from '../../auth/AuthService';


/**
 * To add student interview experience
 * @param {*} interviewExperienceObj 
 * @returns promise
 */
const addInterviewExperience = (interviewExperienceObj) => {
    var promise = new Promise((resolve, reject) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(interviewExperienceObj)
        };
        fetch("/api/interview-experience/add", requestOption).then((response) => {
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
 * Interview experience add form component
 */
export const InterviewExperienceAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const disabledColor = "#ddd";
    const enabledColor = "#fff";

    const [interviewExperienceObj, setInterviewExperienceObj] = React.useState(new InterviewExperience());

    const [interviewExperienceErr, setInterviewExperienceErr] = React.useState({});

    const [dialogOpen, setDialogOpen] = React.useState(false);

    const [tmp, setTmp] = React.useState("");

    const [showPage, setShowPage] = React.useState(false);



    const submitInterviewExperience = () => {
        //code to add placement preparation faculty method call
        interviewExperienceObj.setStudentId(getStudentId());
        addInterviewExperience(interviewExperienceObj).then((id) => {
            setDialogOpen(true);
            setInterviewExperienceObj(new InterviewExperience());
            setInterviewExperienceErr({});
        }, (exception) => {
            console.log("InterviewExperienceErr: " + JSON.stringify(exception));
            setInterviewExperienceErr(exception);
            setDialogOpen(false);
        }).catch((error) => {
            setDialogOpen(false);
        });
    }

    const getStudentId = () => {
        return getAuthenticateUserId();
    }



    const handleTabClosing = () => {
        //  alert("handle");
    }


    const alertUser = (event) => {
        event.preventDefault();
        event.returnValue = 'Ares you sure';
    }

    React.useEffect(() => {
        if (isUserStudent() == false) redirectToLoginPage();
        else setShowPage(true);
    }, []);
    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            {showPage && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                <ContainerItem lg={10}>
                    <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <h1>Add Interview Experience</h1>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={interviewExperienceErr.COMPANY_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Company"
                                        onInput={((ev) => {
                                            interviewExperienceObj.setCompany(ev.target.value);
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={interviewExperienceObj.getCompany()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={interviewExperienceErr.PASSOUT_YEAR_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Passout Year"
                                        type="number"
                                        onChange={((ev) => {
                                            interviewExperienceObj.setPassoutYear(ev.target.value);
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={interviewExperienceObj.getPassoutYear()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                            <FormGroup>
                                <FormControl>
                                    <FormErrorLabelWrapper errorMessage={interviewExperienceErr.SALARY_ERR} />
                                    <OutlinedTextFieldWrapper
                                        label="Salary"
                                        onChange={((ev) => {
                                            interviewExperienceObj.setSalary(ev.target.value);
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={interviewExperienceObj.getSalary()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>

                        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={6}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={interviewExperienceErr.GRADUATION_TYPE_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Select Graduation type</FormLabel>
                                <FormControl>
                                    <RadioGroupWrapper
                                        options={searchForGraduation}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'UG') {
                                                interviewExperienceObj.setUgStudent(true);
                                                interviewExperienceObj.setPgStudent(false);
                                            }
                                            else {
                                                interviewExperienceObj.setUgStudent(false);
                                                interviewExperienceObj.setPgStudent(true)
                                            }
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value);
                                        })}
                                        checkvalue={interviewExperienceObj.getUgStudent() ? 'UG' : (interviewExperienceObj.getPgStudent() ? 'PG' : null)}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>
                        <ContainerItem xs={12} sm={12} md={12} lg={6} xl={3}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={interviewExperienceErr.VACANCY_TYPE_ERR} />
                                <FormLabel className={cssStyles.globalStyles.formQuestion}>Placed For</FormLabel>
                                <FormControl>
                                    <RadioGroupWrapper
                                        options={jobVacancy}
                                        onChange={((ev) => {
                                            if ((ev.target.value) == 'F') {
                                                interviewExperienceObj.setIsFullTimeVacancy(true);
                                                interviewExperienceObj.setIsInternshipVacancy(false);
                                            }
                                            else if ((ev.target.value) == 'I') {
                                                interviewExperienceObj.setIsFullTimeVacancy(false);
                                                interviewExperienceObj.setIsInternshipVacancy(true);
                                            } else if ((ev.target.value) == 'FI') {
                                                interviewExperienceObj.setIsFullTimeVacancy(true);
                                                interviewExperienceObj.setIsInternshipVacancy(true);
                                            }
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value)
                                        })}
                                        checkvalue={(interviewExperienceObj.getIsFullTimeVacancy() && interviewExperienceObj.getIsInternshipVacancy()) ? 'FI' : (interviewExperienceObj.getIsFullTimeVacancy() ? 'F' : (interviewExperienceObj.getIsInternshipVacancy() ? 'I' : null))}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>

                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <FormGroup>
                                <FormErrorLabelWrapper errorMessage={interviewExperienceErr.EXPERIENCE_ERR} />
                                <FormControl>
                                    <OutlinedTextFieldWrapper
                                        label="Interview Experience"
                                        multiline
                                        rows={10}
                                        onChange={((ev) => {
                                            interviewExperienceObj.setExperience(ev.target.value);
                                            setInterviewExperienceObj(interviewExperienceObj);
                                            setTmp(ev.target.value)
                                        })}
                                        value={interviewExperienceObj.getExperience()}
                                    />
                                </FormControl>
                            </FormGroup>
                        </ContainerItem>



                        <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                            <Button
                                className={cssStyles.globalStyles.formButtonRight}
                                variant="contained"
                                onClick={submitInterviewExperience}
                            >
                                Add
                            </Button>
                        </ContainerItem>
                    </WrapperContainer>
                </ContainerItem>
            </WrapperContainer>}
            <DialogWrapper open={dialogOpen} onClick={(ev) => {
                if (dialogOpen == true) setDialogOpen(false);
                else setDialogOpen(true);
                ev.preventDefault();
                window.location.reload(false);
            }}
                message={"Added Successfully!"}
                button={"Ok"}
            />
        </div>
    )
}