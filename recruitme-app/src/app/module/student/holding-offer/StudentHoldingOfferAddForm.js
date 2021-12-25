import React from 'react';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import { FormLabel } from '@material-ui/core';
import { WrapperContainer, ContainerItem, OutlinedTextFieldWrapper, RadioGroupWrapper, FormErrorLabelWrapper } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import Alert from '@mui/material/Alert';
import { jobVacancy } from '../../../app-constants/JobVacancy';
import { Redirect } from 'react-router';
import { BASE_URL } from '../../../app-constants/AppConstants';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import Typography from '@material-ui/core/Typography';
import { isActive } from '../../auth/AuthService';
import { getAuthenticateUserId } from '../../auth/AuthService';
import { addStudentHoldingOffer } from './api/StudentHoldingOfferApiCaller';
import { DialogWrapper } from '../../../theme/global/components/ContainerComponents';
import StudentHoldingOffer from '../../../domain/StudentHoldingOffer';

/**
 * Student registration form component
 */
export const StudentHoldingOfferComponentAddForm = () => {
    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [company, setCompany] = React.useState("");
    const [jobTypeTmp, setJobTypeTmp] = React.useState("");
    const [jobType, setJobType] = React.useState(null);
    const [salary, setSalary] = React.useState(null);
    const [dialogOpen, setDialogOpen] = React.useState(false);
    const [studentHoldingOfferErr, setStudentHoldingOfferErr] = React.useState({});


    const addStudentHoldingOfferHelper = async () => {
        let studentHoldingOfferObj = new StudentHoldingOffer();
        studentHoldingOfferObj.setCompany(company);
        studentHoldingOfferObj.setJobType(jobType);
        studentHoldingOfferObj.setSalary(salary);
        studentHoldingOfferObj.setStudentId(getAuthenticateUserId());
        await addStudentHoldingOffer(studentHoldingOfferObj).then(() => {
            setDialogOpen(true);
        }, (exception) => {
            setStudentHoldingOfferErr(exception);
        }).catch((error) => {

        });

    }

    return (
        <div>
            <div className={cssStyles.globalStyles.mainContainer}>
                <WrapperContainer boxShadow={false} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.formMainContainer}>
                    <ContainerItem boxShadow={false} lg={10}>

                        <WrapperContainer id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                            <ContainerItem xs={12} sm={12} md={12} lg={6} xl={12}>
                                <h1>Offer You Hold</h1>
                            </ContainerItem>
                            <ContainerItem style={{visibility: "hidden"}} xs={12} sm={12} md={12} lg={6} xl={12}>
                                <h1>Offer You Hold</h1>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={studentHoldingOfferErr.COMPANY_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Company"
                                            type="text"
                                            onChange={((ev) => {
                                                setCompany(ev.target.value);
                                            })}
                                            value={company}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={6} md={6} lg={6} xl={6}>
                                <FormGroup>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={studentHoldingOfferErr.SALARY_ERR} />
                                        <OutlinedTextFieldWrapper
                                            label="Package"
                                            type="number"
                                            onChange={((ev) => {
                                                setSalary(ev.target.value);
                                            })}
                                            value={salary}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>

                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                <FormGroup>
                                    <FormLabel className={cssStyles.globalStyles.formQuestion}>Select job type</FormLabel>
                                    <FormControl>
                                        <FormErrorLabelWrapper errorMessage={studentHoldingOfferErr.JOB_TYPE_ERR} />
                                        <RadioGroupWrapper
                                            options={jobVacancy}
                                            onChange={((ev) => {
                                                if (ev.target.value === 'F') setJobType("FULLTIME");
                                                else if (ev.target.value === 'I') setJobType("INTERNSHIP");
                                                else if (ev.target.value === 'FI') setJobType("INTERNSHIP_AND_FULLTIME");
                                                setJobTypeTmp(ev.target.value);
                                            })}
                                            checkvalue={jobTypeTmp}
                                        />
                                    </FormControl>
                                </FormGroup>
                            </ContainerItem>
                            <ContainerItem xs={12} sm={12} md={12} lg={12} xl={12}>
                                <Button
                                    className={cssStyles.globalStyles.formButtonRight}
                                    variant="contained"
                                    onClick={() => {
                                        addStudentHoldingOfferHelper();
                                    }}
                                >
                                    Add
                                </Button>
                            </ContainerItem>
                        </WrapperContainer>

                    </ContainerItem>
                </WrapperContainer>
                <DialogWrapper
                    title={"Success"}
                    message={"Your detail added successfully"}
                    buttonText={"Ok"}
                    contentCenter={true}
                    verifiedLogo={true}
                    open={dialogOpen}
                    closeAction={() => {
                        setCompany("");
                        setSalary(NaN);
                        setJobType(null);
                        setJobTypeTmp(null);
                        setDialogOpen(false);
                        setStudentHoldingOfferErr({});
                    }}
                />
            </div>

        </div>
    )
}
