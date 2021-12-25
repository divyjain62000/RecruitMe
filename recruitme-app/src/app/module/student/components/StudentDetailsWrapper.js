import React from 'react';
import globalStyles from '../../../theme/global/css/AppStyles';
import DialogTitle from '@mui/material/DialogTitle';
import DialogContent from '@mui/material/DialogContent';
import DialogActions from '@mui/material/DialogActions';
import Dialog from '@mui/material/Dialog';
import Table from '@material-ui/core/Table';
import TableRow from '@material-ui/core/TableRow';
import TableCell from '@material-ui/core/TableCell';
import TableBody from '@material-ui/core/TableBody';
import studentStyles from '../css/StudentStyles';
import CloseIcon from '@material-ui/icons/Close';

export const StudentDetailsWrapper = (props) => {
    const cssStyles = {
        globalStyles: globalStyles(),
        studentStyles: studentStyles()
    };
    let student = props.student;
    let education = student != null ? student.education : null;
    let parents = student != null ? student.parents : null;
    let address = student != null ? student.address : null;
    let studentHoldingOfferList = props.studentHoldingoffers != null ? props.studentHoldingoffers : [];

    return (
        <Dialog
            {...props}
            style={{ marginTop: "50px" }}
        >

            <DialogTitle>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitle}>Student Detail</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>
                                <DialogActions>
                                    <CloseIcon className={cssStyles.studentStyles.studentDetailCloseIcon} />
                                </DialogActions>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </DialogTitle>
            {student != null && <DialogContent>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>General Details:</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Name:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.name != null ? student.name : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Enrollment Number:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.enrollmentNumber != null ? student.enrollmentNumber : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>MOU Company Id:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.mouCompanyReferenceId != null ? student.mouCompanyReferenceId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Mobile Number:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.primaryMobileNumber != null ? student.primaryMobileNumber : "-"} <br /> {student.secondaryMobileNumber}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Email:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.primaryEmail != null ? student.primaryEmail : "-"} <br /> {student.secondaryEmail != null ? student.secondaryEmail : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Gender:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.gender != null ? student.gender : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Indian:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.isIndian != null ? student.isIndian : "-"}</TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Education Details(UG):</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>College:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{education.ugCollegeName != null ? education.ugCollegeName : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Program:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.ugProgramId != null ? education.ugProgramId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Branch:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.ugBranchId != null ? education.ugBranchId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Marks:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.ugMarks != null ? education.ugMarks : "-"} {education.ugMarksType != null ? education.ugMarksType : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Passout Year (Actual/Expected):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.ugPassoutYear != null ? education.ugPassoutYear : "-"}</TableCell>
                        </TableRow>


                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Education Details(12th):</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>School:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{education.highSchool != null ? education.highSchool : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Marks:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.highSchoolMarks != null ? education.highSchoolMarks : "-"} {education.highSchoolMarksType != null ? education.highSchoolMarksType : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Passout Year (Actual/Expected):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.highSchoolPassoutYear != null ? education.highSchoolPassoutYear : "-"}</TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Education Details(10th):</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>School:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{education.seniorSecondarySchool != null ? education.seniorSecondarySchool : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Marks:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.seniorSecondarySchoolMarks != null ? education.seniorSecondarySchoolMarks : "-"} {education.seniorSecondarySchoolMarksType != null ? education.seniorSecondarySchoolMarksType : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Passout Year (Actual/Expected):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.seniorSecondarySchoolPassoutYear != null ? education.seniorSecondarySchoolPassoutYear : "-"}</TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Education Details(PG):</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>College:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{education.pgCollegeName != null ? education.pgCollegeName : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Program:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.pgProgramId != null ? education.pgProgramId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Branch:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.pgBranchId != null ? education.pgBranchId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Marks:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.pgMarks != null ? education.pgMarks : "-"} {education.pgMarksType != null ? education.pgMarksType : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Passout Year (Actual/Expected):</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{education.pgPassoutYear != null ? education.pgPassoutYear : "-"}</TableCell>
                        </TableRow>


                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Parents Details:</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Father Name:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{parents.fatherName != null ? parents.fatherName : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Mother Name:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{parents.motherName != null ? parents.motherName : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Mobile Number:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{parents.mobileNumber != null ? parents.mobileNumber : "-"}</TableCell>
                        </TableRow>


                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Address Details:</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Address Line 1:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{address.addressLine1 != null ? address.addressLine1 : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Address Line 2:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{address.addressLine2 != null ? address.addressLine2 : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>City:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{address.cityId != null ? address.cityId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Postal Code:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{address.pinCode != null ? address.pinCode : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>State:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{address.stateId != null ? address.stateId : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Country:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{address.countryId != null ? address.countryId : "-"}</TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Other Details:</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Placed For Internship:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{student.isPlaceForInternship != null ? student.isPlaceForInternship : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Placed For Fulltime:</TableCell>
                            <TableCell colSpan={3} className={cssStyles.studentStyles.studentDetail}>{student.isPlaceForFulltime != null ? student.isPlaceForFulltime : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Blacklisted:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.isBlacklisted ? "Y" : "N"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Reason For Blacklisted:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.reasonForBlacklist != null ? student.reasonForBlacklist : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Any Criminal Case:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.isAnyCriminalCase != null ? student.isAnyCriminalCase : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Criminal Case Description:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.criminalCaseDescription != null ? student.criminalCaseDescription : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Any Disabities:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.isDisability != null ? student.isDisability : "-"}</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetailOption}>Disability:</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>{student.disability != null ? student.disability : "-"}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell colSpan={4} className={cssStyles.studentStyles.studentDetailTitleRow}>Holding Offer Details:</TableCell>
                        </TableRow>
                        <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetail}><strong>Company</strong></TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}><strong>Job Type</strong></TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}><strong>Package</strong></TableCell>
                        </TableRow>
                        {studentHoldingOfferList.length === 0 && <TableRow >
                            <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetail}>-</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>-</TableCell>
                            <TableCell className={cssStyles.studentStyles.studentDetail}>-</TableCell>
                        </TableRow>}

                        {
                            studentHoldingOfferList.map((sho) => {
                                return (
                                    
                                    <TableRow >
                                        <TableCell colSpan={2} className={cssStyles.studentStyles.studentDetail}>{sho.company}</TableCell>
                                        <TableCell className={cssStyles.studentStyles.studentDetail}>{sho.jobType === "INTERNSHIP_AND_FULLTIME" ? "INTERNSHIP & FULLTIME" : sho.jobType}</TableCell>
                                        <TableCell className={cssStyles.studentStyles.studentDetail} style={{float: "left"}}>
                                        {
                                            Math.floor(sho.salary/100000000)!=0?(sho.salary/100000000+"Cr./Yr"):(Math.floor(sho.salary/100000)!=0?(sho.salary/100000+" LPA"):sho.salary+"Per/Month")
                                        }
                                        </TableCell>
                                    </TableRow>
                                )
                            })
                        }
                    </TableBody>
                </Table>
            </DialogContent>}
        </Dialog>
    )
}