import { makeStyles } from '@material-ui/core';
import { colors } from './theme-constants/ThemeConstants';
import { fonts } from './theme-constants/ThemeConstants';

const globalStyles = makeStyles(theme => ({

  container: {
    flexGrow: 1,
    background: colors.bgColor,
    display: 'flex'
  },
  mainContainer: {
    flexGrow: 1,
    marginTop: "10px",
    paddingTop: "10px",
    background: colors.bgColor,
    display: 'flex',
  },
  title: {
    fontWeight: "bold",
    color: colors.dark,
  },
  button1: {
    background: colors.lightDark,
    fontSize: "1.4rem",
    padding: "15px",
    color: colors.text1,
    margin: "25px",
    textDecoration: "none",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      background: colors.lightDark,
      border: "3px solid " + colors.lightDark
    }
  },
  button2: {
    background: colors.lightDark,
    fontSize: "1.4rem",
    paddingLeft: "25px",
    paddingRight: "25px",
    paddingTop: "15px",
    paddingBottom: "15px",
    color: colors.text1,
    margin: "25px",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      border: "3px solid " + colors.lightDark
    }
  },

  /*Table Container related css starts from here */

  tableMainContainer: {
    flexGrow: 1,
    // border: "1px solid red",
    paddingLeft: "120px",
    paddingRight: "120px",
    paddingTop: "50px",
    display: 'flex',
    boxShadow: "none"
  },
  tableHeader: {
    background: colors.dark,
    fontWeight: "bold"
  },
  tableHeaderCell: {
    fontWeight: "bold",
    color: colors.text1,
    fontSize: "1rem"
  },
  noneBorderCell: {
    border: "4px solid "+colors.dark,
  },
  tableBody: {
    background: colors.grey,
    cursor: "pointer"
  },


  /*Table Container related css ends here */


  /*Form realted css starts here */
  formMainContainer: {
    flexGrow: 1,
    // border: "1px solid red",
    paddingLeft: "20px",
    paddingRight: "20px",
    paddingTop: "50px",
    paddingBottom: "20px",
    display: 'flex',
    boxShadow: "none",
  },
  formContainer: {
    paddingRight: "30px",
    paddingLeft: "30px",
    paddingBottom: "30px",
    paddingTop: "20px",
    marginTop: "50px",
    marginBottom: "60px",
    background: colors.white,
    display: 'flex',
  },
  formButtonContainer: {
    paddingRight: "30px",
    paddingLeft: "30px",
    marginTop: "-50px",
    marginBottom: "20px",
  },
  formTitle: {
    // border: "1px solid blue",
    fontSize: "50px",
    color: colors.text1,
    textTransform: "uppercase",
    fontFamily: fonts.title,
  },
  formTitleHeader: {
    marginTop: "20px",
    marginBottom: "20px",
    background: colors.lightDark,
    textAlign: "center"
  },
  formLabelErr: {
    color: colors.error,
    marginBottom: "10px",
  },
  formQuestion: {
    color: colors.text2,
    fontSize: "1.2rem",
    marginBottom: "20px"

  },

  formButtonLeft: {
    background: colors.dark,
    fontSize: "1.4rem",
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "25px",
    paddingRight: "25px",
    color: colors.text1,
    margin: "25px",
    textDecoration: "none",
    float: "left",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      background: colors.dark,

      border: "3px solid " + colors.dark
    },
  },
  formButtonRight: {
    background: colors.dark,
    fontSize: "1.4rem",
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "25px",
    paddingRight: "25px",
    color: colors.text1,
    margin: "25px",
    textDecoration: "none",
    float: "right",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      background: colors.dark,
      border: "3px solid " + colors.dark
    },
  },

  cssLabel: {
    color: colors.dark + ' !important',
    fontWeight: 'bold',
  },

  cssOutlinedInput: {
    '&$cssFocused $notchedOutline': {
      borderColor: colors.dark + ' !important',
    }
  },

  cssFocused: {
    color: colors.text2 + ' !important',
  },

  notchedOutline: {
    borderWidth: '2px',
    borderColor: colors.dark + ' !important',
  },

  /*Select Menu related Styling starts here*/
  selectMenu: {
    icon: {
      fill: "red",
    },
    "& .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "& .MuiInputLabel-root": {
      color: colors.dark,
      fontWeight: "bold",
    },
    "& .MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
      borderWidth: "2px",
    },
    "&:hover .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "&:hover .MuiInputLabel-root": {
      color: colors.dark,
    },
    "&:hover .MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
    },
    "& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "& .MuiInputLabel-root.Mui-focused": {
      color: colors.text2,
    },
    "& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
      textOverflow: "ellipsis"
    },
    "& .MuiSelect-icon": {
      color: colors.dark,
    }
  },
  /*Select Menu related styling ends here*/

  /*Select Menu related Styling starts here*/
  chipInput: {
    margin: "none",

    icon: {
      fill: "red",
    },
    "& .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "& .MuiInputLabel-root": {
      color: colors.dark,
      fontWeight: "bold",
    },
    "& .MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
      borderWidth: "2px",
    },
    "&:hover .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "&:hover .MuiInputLabel-root": {
      color: colors.dark,
    },
    "&:hover .MuiOutlinedInput-root .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
    },
    "& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-input": {
      color: colors.text2,
    },
    "& .MuiInputLabel-root.Mui-focused": {
      color: colors.text2,
    },
    "& .MuiOutlinedInput-root.Mui-focused .MuiOutlinedInput-notchedOutline": {
      borderColor: colors.dark,
    },
    "& .MuiSelect-icon": {
      color: colors.dark,
    }
  },
  /*Select Menu related styling ends here*/



  /*Radio button related styling starts here */
  radio: {
    color: colors.dark,
    marginTop: "10px",
    marginBottom: "10px",
    '&$checked': {
      color: colors.dark
    }
  },
  checked: {

  },
  /*Radio button related styling ends here*/

  /*Checkbox related styling starts from here */
  checkbox: {
    color: colors.dark,
    background: colors.white,
    paddingLeft: "-20px",
    '&$checked': {
      color: colors.dark,
      background: colors.white,
      padding: "-20px",
    }
  },
  /*Checkbox related styling ends here*/

  dialogTitle: {
    marginTop: "50px",
    background: colors.light,
    color: colors.text2,
  },

  dialogButton: {
    background: colors.success,
    fontSize: "1rem",
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "25px",
    paddingRight: "25px",
    color: colors.text1,
    textDecoration: "none",
    marginTop: "10px",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      background: colors.success,
      border: "1px solid " + colors.success
    },
  },


  searchButton: {
    background: colors.dark,
    fontSize: "1rem",
    paddingBottom: "5px",
    paddingTop: "5px",
    paddingLeft: "25px",
    paddingRight: "25px",
    color: colors.text1,
    textDecoration: "none",
    margin: "10px",
    "&:hover": {
      textDecoration: "none",
      color: colors.text2,
      cursor: "pointer",
      background: colors.dark,
      border: "1px solid " + colors.success
    },
  },

  /*Form realted css ends here */


  /*Filter Icon related css starts from here */
  filterIcon: {
    float: "right",
    marginTop: "20px",
    color: colors.black
  },
  deleteIcon: {
    color: colors.danger
  }

}));


export default globalStyles;