import React from 'react';
import globalStyles from '../../../theme/global/css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableBody from '@material-ui/core/TableBody';
import studentStyles from '../../../module/student/css/StudentStyles';
import CloseIcon from '@material-ui/icons/Close';

export const DriveDetailsWrapper = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };
    let drive=props.drive;
    return (
        <Dialog
            {...props}
            style={{ marginTop: "50px" }}
        >

       <DialogTitle>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitle}>Drive Detail</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>
                                <DialogActions>
                                    <CloseIcon className={cssStyles.studentStyles.studentDetailCloseIcon} />
                                </DialogActions>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </DialogTitle>
            { drive!=null &&  <DialogContent>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>General Details:</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Company Name:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.company!=null? drive.company: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Drive Name:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.driveName!=null? drive.driveName: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Salary:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.salary!=null? drive.salary: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Last date to apply:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.lastDateToApply!=null ? drive.lastDateToApply: "-"} </TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Vacancy Type:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.vacancyType!=null ? drive.vacancyType: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Who can apply:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.driveForGender!=null ? drive.driveForGender: "-"}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Eligibility Criteria :</TableCell>
                        </TableRow>
       
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Branches:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.validBranches!=null ? drive.validBranches: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Programs:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.validPrograms!=null ? drive.validPrograms: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required 10th CGPA:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.required10thCGPA!=null ? drive.required10thCGPA: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required 12th CGPA:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.required12thCGPA!=null ? drive.required12thCGPA: "-"}</TableCell>
                        </TableRow>


                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required UG CGPA:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{drive.requiredUGCGPA!=null ? drive.requiredUGCGPA: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required PG CGPA:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.requiredPGCGPA!=null ? drive.requiredPGCGPA: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Number of minimum backlogs allowed:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.minimumBacklogsAllowed!=null ? drive.minimumBacklogsAllowed: "-"}</TableCell>
                        </TableRow>

                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Drive for graduation:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{drive.driveForGraduation!=null ? drive.driveForGraduation: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required Year of passing(For UG candidates):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.ugPassoutYearAllow!=null ? drive.ugPassoutYearAllow: "-"} </TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Required Year of passing(For PG candidates):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.pgPassoutYearAllow!=null ? drive.pgPassoutYearAllow: "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Minimum Gaps allowed:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{drive.minGapAllowInEducation!=null ? drive.minGapAllowInEducation: "-"}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Other Details:</TableCell>                            
                        </TableRow>
                        <TableRow >
                        <TableCell colSpan={7} className={cssStyles.studentStyles.studentDetail}>{drive.otherDelatails!=null ? drive.otherDelatails: "-"}</TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </DialogContent> }
        </Dialog>
    )
}