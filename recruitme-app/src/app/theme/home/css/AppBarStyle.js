import React from 'react';
import { makeStyles } from '@material-ui/core';
import {colors} from '../../global/css/theme-constants/ThemeConstants';

const appBarStyles = makeStyles(theme => ({
    mainContainer: { flexGrow: 1 },
    appBar: {
        background: colors.lightDark,
        zIndex: theme.zIndex.drawer+1000,
    },
    icon: {
        width: "30px",
        height: "30px",
        cursor: "pointer"
    }
}));


export default appBarStyles;