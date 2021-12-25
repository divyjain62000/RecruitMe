import React from 'react';
import { makeStyles } from '@material-ui/core';
import {colors} from '../../global/css/theme-constants/ThemeConstants';

const carouselStyles = makeStyles(theme => ({
    carouselContainer: {
        borderBottom: "3px solid "+colors.dark
    },
    carouselImg: {
        float: "right",
        marginTop: "10px"
    },
    slogan: {
        textAlign: "center",
        fontSize: "50px",
        color: colors.text2,
        wordSpacing: "10px",
        fontFamily: "sans-serif",
    },
}));


export default carouselStyles;