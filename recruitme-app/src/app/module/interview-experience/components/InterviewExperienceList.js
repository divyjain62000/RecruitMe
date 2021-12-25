import React from 'react';
import { WrapperContainer, ContainerItem, CheckboxWrapper, DialogWrapper, NewlineText } from '../../../theme/global/components/ContainerComponents';
import globalStyles from '../../../theme/global/css/AppStyles';
import Button from '@material-ui/core/Button';
import FilterIcon from '@material-ui/icons/FilterList';
import FormControl from '@material-ui/core/FormControl';
import FormGroup from '@mui/material/FormGroup';
import FormLabel from '@material-ui/core/FormLabel';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';
import { Table, TableBody, TableCell, TableRow, TableHead } from '@material-ui/core';
import Hidden from '@material-ui/core/Hidden';
import { isUserStudent, redirectToLoginPage } from '../../auth/AuthService';
import { LoaderComponent } from '../../../theme/global/components/LoaderComponent';

const getInterviewExperiences = (data) => {
    var promise = new Promise((resolve) => {
        const requestOption = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        };
        fetch("/api/interview-experience/search", requestOption).then((response) => {
            if (response.ok) return response.json();
            throw response;
        })
            .then((responseObj) => {
                //                alert(responseObj);
                // alert(JSON.stringify(responseObj));
                resolve(responseObj.content);
            })
            .catch(error => {
                console.error("Error while fetching interviewExperiences: " + error);
            })

    });
    return promise;
}


export const InterviewExperienceList = () => {

    const cssStyles = {
        globalStyles: globalStyles(),
    };

    const [interviewExperiences, setInterviewExperiences] = React.useState([]);
    const [interviewExperienceObj, setVideoObj] = React.useState(null);
    const [showPage, setShowPage] = React.useState(false);
    const [progressLoader, setProgressLoader] = React.useState(false);

    React.useEffect(() => {
        if (isUserStudent() == false) redirectToLoginPage();
        else setShowPage(true);
        let data = {

        };
        setProgressLoader(true);
        getInterviewExperiences(data).then((interviewExperienceList) => {
            setInterviewExperiences(interviewExperienceList);
            setProgressLoader(false);
        });
    }, []);

    return (
        <div className={cssStyles.globalStyles.mainContainer}>
            <LoaderComponent
                open={progressLoader}
            />

            {showPage && <WrapperContainer boxShadow={0} alignItems="center" justifyContent="center" className={cssStyles.globalStyles.tableMainContainer}>
                <WrapperContainer xs={12} id="card-0" spacing={5} className={cssStyles.globalStyles.formContainer}>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <h1>Interview Experiences</h1>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={6} xl={6}>
                        <Button className={cssStyles.globalStyles.filterIcon}></Button>
                    </ContainerItem>
                    <ContainerItem xs={6} sm={6} md={6} lg={12} xl={12}>
                        <Table>
                            <TableHead stickyHeader style={{ visibility: "hidden" }} className={cssStyles.globalStyles.tableHeader}>
                                <TableRow>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Enrollment No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Name</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Program (Branch)</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Mobile No.</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Black list</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Black list</TableCell>
                                    <TableCell className={cssStyles.globalStyles.tableHeaderCell}>Black list</TableCell>
                                </TableRow>
                            </TableHead>
                            {
                                interviewExperiences.map((interviewExperience) => {
                                    return (

                                        <TableBody>
                                            <TableRow className={cssStyles.globalStyles.tableHeader}>
                                                <TableCell colSpan={3} className={cssStyles.globalStyles.noneBorderCell} style={{ fontSize: "1.5rem", fontWeight: "bold", color: colors.text1, textTransform: "capitalize" }}>
                                                    <span style={{ fontSize: "1rem", fontWeight: "bold", color: colors.text1, textTransform: "capitalize" }}>Experience No. : SVVV{interviewExperience.id}</span>
                                                    <br />
                                                    {interviewExperience.company}
                                                    <br />
                                                    <span style={{ fontSize: "1.2rem", fontWeight: "bold", color: colors.text1, textTransform: "capitalize" }}>Package: {interviewExperience.salary}</span>
                                                    <br />
                                                    <span style={{ fontSize: "1rem", fontWeight: "bold", textTransform: "capitalize" }}>Job Type - {(interviewExperience.isFullTimeVaccany && interviewExperience.isInternshipVaccancy) ? "Internship & Fulltime" : (interviewExperience.isInternshipVaccancy ? "Internship" : "Fulltime")}</span>
                                                </TableCell>
                                                <TableCell className={cssStyles.globalStyles.noneBorderCell} colSpan={3}></TableCell>
                                                <TableCell className={cssStyles.globalStyles.noneBorderCell} style={{ fontSize: "1.5rem", fontWeight: "bold", color: colors.text1, textTransform: "capitalize", }}>{interviewExperience.studentName}
                                                    <br />
                                                    <span style={{ fontSize: "1rem", fontWeight: "bold", textTransform: "capitalize" }}>{interviewExperience.program}({interviewExperience.branch}) - {interviewExperience.passoutYear}</span>
                                                </TableCell>
                                            </TableRow>
                                            <TableRow className={cssStyles.globalStyles.tableBody}>
                                                <TableCell style={{ fontSize: "1rem" }} colSpan={7}><NewlineText text={interviewExperience.experience} /></TableCell>
                                            </TableRow>
                                            <br />
                                        </TableBody>



                                    )
                                })
                            }
                        </Table>
                    </ContainerItem>
                </WrapperContainer>
            </WrapperContainer>}
        </div>
    )
}