import React from 'react';
import { makeStyles } from '@material-ui/core';
import { border } from '@mui/system';
import { colors } from '../../../theme/global/css/theme-constants/ThemeConstants';

const studentStyles = makeStyles(theme => ({
    studentFormMainContainer: {
        flexGrow: 1,
        //border: "1px solid red",
        padding: "20px"
    },
    studentFormContainer: {
        //border: "1px solid black",
    },
    studentDetailTitle: {
        fontWeight: "bold",
        color: colors.dark,
        border: "none",
        fontSize: "1.5rem"
    },
    studentDetailOption: {
        fontWeight: "bold",
        color: colors.text2,
        border: "none",

    },
    studentDetail: {
        color: colors.text2,
        border: "none",
    },
    studentDetailTitleRow: {
        border: "none",
        background: colors.dark,
        color: colors.text1,
        fontWeight: "bold"
    },
    studentDetailCloseIcon: {
        width: "30px",
        height: "30px",
        color: colors.black,
        cursor: "pointer"
    },
    blacklistButton: {
        background: colors.danger,
        color: colors.white,
        "&:hover": {
            background: colors.danger,
            color: colors.black,
        }
    },
    unblacklistButton: {
        background: colors.success,
        color: colors.white,
        "&:hover": {
            background: colors.success,
            color: colors.black,
        }
    },
    

}));


export default studentStyles;